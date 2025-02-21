

package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.filtri.CompositeFilter;
import it.unipv.ingsfw.bitebyte.filtri.FilterByDisponibilità;
import it.unipv.ingsfw.bitebyte.filtri.FilterByNome;
import it.unipv.ingsfw.bitebyte.models.Distributore;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.utils.CalcolaDistanza;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class DistributoreDAO implements IDistributoreDAO {
    
    private Connection connection;
    private String schema;
    private StockDAO stockDAO;

    public DistributoreDAO() {
        super();
        this.schema = "progettog25";  // Cambia se necessario
        this.stockDAO = new StockDAO();  // Istanza di StockDAO
    }

    @Override
    public Distributore getDistributoreById(int idDistributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "SELECT * FROM distributore WHERE ID_Distributore = ?";
        Distributore distributore = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idDistributore);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                distributore = new Distributore(
                        rs.getInt("ID_Distributore"),
                        rs.getString("Tipo_D"),
                        rs.getString("Citta"),
                        rs.getString("Via"),
                        rs.getString("N_civico"),
                        rs.getInt("ID_Inventario"),
                        rs.getDouble("LAT"),
                        rs.getDouble("LON")
                );

                // Recupera e associa gli stock tramite l'ID_Inventario
                List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
                distributore.setStockList(stockList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return distributore;
    }

   /* @Override
    public List<Distributore> getAllDistributori() {
        connection = DBConnection.startConnection(connection, schema);
        String query = "SELECT * FROM distributore";
        List<Distributore> distributori = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Distributore distributore = new Distributore(
                        rs.getInt("ID_Distributore"),
                        rs.getString("Tipo_D"),
                        rs.getString("Citta"),
                        rs.getString("Via"),
                        rs.getString("N_civico"),
                        rs.getInt("ID_Inventario"),
                        rs.getDouble("LAT"),
                        rs.getDouble("LON")
                );

                // Associa gli stock
                List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
                distributore.setStockList(stockList);

                distributori.add(distributore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return distributori;
    }
    
    */
    
    @Override
    public List<Distributore> getAllDistributori() {
        connection = DBConnection.startConnection(connection, schema);
        String query = "SELECT * FROM distributore";
        List<Distributore> distributori = new ArrayList<>();

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Distributore distributore = new Distributore(
                        rs.getInt("ID_Distributore"),
                        rs.getString("Tipo_D"),
                        rs.getString("Citta"),
                        rs.getString("Via"),
                        rs.getString("N_civico"),
                        rs.getInt("ID_Inventario"),
                        rs.getDouble("LAT"),
                        rs.getDouble("LON")
                );
                List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
                distributore.setStockList(stockList);
                distributori.add(distributore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
        return distributori;
    }

    /**
     * Restituisce i distributori (della stessa tipologia) che possiedono almeno uno stock
     * in cui il nome del prodotto contiene 'nomeProdotto' e la quantità disponibile è > 0.
     * Viene escluso il distributore corrente.
     * La lista è ordinata per distanza dal distributore corrente.
     */
    public List<Distributore> getDistributoriConProdottoDisponibileByName(int idDistributoreCorrente, String nomeProdotto) {
        // Recupera il distributore corrente
        Distributore corrente = getDistributoreById(idDistributoreCorrente);
        if (corrente == null) return new ArrayList<>();

        // Recupera tutti i distributori e filtra per tipologia ed escludi il corrente
        List<Distributore> distributoriTipologia = getAllDistributori().stream()
            .filter(d -> d.getTipo().equals(corrente.getTipo()) && d.getIdDistr() != corrente.getIdDistr())
            .collect(Collectors.toList());

        // Imposta il filtro composito: per nome e disponibilità
        CompositeFilter compositeFilter = new CompositeFilter();
        compositeFilter.addFilter(new FilterByNome(nomeProdotto));
        compositeFilter.addFilter(new FilterByDisponibilità());

        List<Distributore> distributoriDisponibili = new ArrayList<>();
        for (Distributore d : distributoriTipologia) {
            // Applica il filtro sulla lista degli stock di questo distributore
            List<Stock> stockFiltrati = compositeFilter.applyFilter(d.getStockList());
            if (!stockFiltrati.isEmpty()) {
                d.setStockList(stockFiltrati); // Aggiorna la lista con gli stock filtrati
                distributoriDisponibili.add(d);
            }
        }

        // Ordina i distributori per distanza dal distributore corrente
        distributoriDisponibili.sort(Comparator.comparingDouble(d ->
            CalcolaDistanza.calcolaDistanza(corrente, d)
        ));

        return distributoriDisponibili;
    }

   /* public List<Distributore> getDistributoriVicini(int idDistributore, double maxDistanzaKm) {
        // Recupera il distributore corrente
        Distributore distributoreCorrente = getDistributoreById(idDistributore);
        if (distributoreCorrente == null) {
            return new ArrayList<>();
        }
        
        

        // Recupera tutti i distributori
        List<Distributore> tuttiDistributori = getAllDistributori();
        List<Distributore> distributoriVicini = new ArrayList<>();

        for (Distributore d : tuttiDistributori) {
            // Evita di considerare lo stesso distributore
            if (d.getIdDistr() != distributoreCorrente.getIdDistr()) {
                double distanza = CalcolaDistanza.calcolaDistanza(distributoreCorrente, d);
                if (distanza <= maxDistanzaKm) {
                    // Se lo desideri, puoi memorizzare la distanza nel distributore (aggiungendo un campo e setter)
                    // d.setDistanza(distanza);
                    distributoriVicini.add(d);
                }
            }
        }

        // Ordina la lista in base alla distanza (calcolata dinamicamente)
        distributoriVicini.sort(Comparator.comparingDouble(d ->
                CalcolaDistanza.calcolaDistanza(distributoreCorrente, d)
        ));

        return distributoriVicini;
    }
    
    */
    
    @Override
    public void addDistributore(Distributore distributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "INSERT INTO distributore (ID_Distributore, Tipo_D , Citta, Via,  N_civico, ID_Inventario, LAT, LON) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	
        	stmt.setInt(1, distributore.getIdDistr());
        	stmt.setString(2, distributore.getTipo());
            stmt.setString(3, distributore.getCitta());
            stmt.setString(4, distributore.getVia());
            stmt.setString(5, distributore.getNCivico());
            stmt.setInt(6, distributore.getIdInventario());
            stmt.setDouble(7, distributore.getLat());
            stmt.setDouble(8, distributore.getLon());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public void updateDistributore(Distributore distributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "UPDATE distributore SET ID_Distributore = ?, Tipo_D = ?, Citta = ?, Via = ?, N_civico = ?, ID_Inventario = ?, LAT = ?, LON = ? WHERE ID_Distributore = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
        	stmt.setInt(1, distributore.getIdDistr());
        	stmt.setString(2, distributore.getTipo().toString());
            stmt.setString(3, distributore.getCitta());
            stmt.setString(4, distributore.getVia());
            stmt.setString(5, distributore.getNCivico());
            stmt.setInt(6, distributore.getIdInventario());
            stmt.setDouble(7, distributore.getLat());
            stmt.setDouble(8, distributore.getLon());
            stmt.executeUpdate();

            // Dopo aver aggiornato, aggiorna ogni singolo stock
            for (Stock stock : distributore.getStockList()) {
                stockDAO.updateStock(stock); // Utilizza il metodo per aggiornare lo stock
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }


    @Override
    public void deleteDistributore(int idDistributore) {
        connection = DBConnection.startConnection(connection, schema);
        String query = "DELETE FROM distributore WHERE ID_Distributore = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idDistributore);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public List<Stock> getStockByDistributore(int idDistributore) {
        Distributore distributore = getDistributoreById(idDistributore);
        return stockDAO.getStockByInventario(distributore.getIdInventario());
    }
    
    
  /*  public List<Distributore> getDistributoriConProdottoDisponibileByName(int idDistributoreCorrente, String nomeProdotto) {
        connection = DBConnection.startConnection(connection, schema);
        List<Distributore> distributoriDisponibili = new ArrayList<>();

        // Recupera il distributore corrente per ottenere il tipo
        Distributore corrente = getDistributoreById(idDistributoreCorrente);
        if (corrente == null) return distributoriDisponibili;
        String tipoCorrente = corrente.getTipo();

        String query = 
        		
        	    "SELECT d.*, s.ID_Prodotto, s.Q_disp " +  // Aggiungi ID_Prodotto e Q_disp
        	    "FROM distributore d " +
        	    "JOIN stock_dettagli s ON d.ID_Inventario = s.ID_Inventario " +
        	    "JOIN prodotto p ON s.ID_Prodotto = p.ID_Prodotto " +
        	    "WHERE LOWER(p.Nome) LIKE ? " +  // Usa Nome invece di Nome_p
        	    "AND s.Q_disp > 0 " +
        	    "AND d.Tipo_D = ? " +
        	    "AND d.ID_Distributore != ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nomeProdotto.toLowerCase() + "%");
            stmt.setInt(2, idDistributoreCorrente);
            stmt.setString(3, tipoCorrente);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Distributore distributore = new Distributore(
                    rs.getInt("ID_Distributore"),
                    rs.getString("Tipo_D"),
                    rs.getString("Citta"),
                    rs.getString("Via"),
                    rs.getString("N_civico"),
                    rs.getInt("ID_Inventario"),
                    rs.getDouble("LAT"),
                    rs.getDouble("LON")
                );
                // Associa gli stock del distributore
                List<Stock> stockList = stockDAO.getStockByInventario(distributore.getIdInventario());
                distributore.setStockList(stockList);
                distributoriDisponibili.add(distributore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBConnection.closeConnection(connection);
        }

        return distributoriDisponibili;
    }
    
    */
}

