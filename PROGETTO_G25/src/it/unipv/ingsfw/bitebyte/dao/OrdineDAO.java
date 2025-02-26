package it.unipv.ingsfw.bitebyte.dao;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Prodotto;
import it.unipv.ingsfw.bitebyte.types.Categoria;
import it.unipv.ingsfw.bitebyte.types.StatoOrd;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO implements IOrdineDAO {

    private Connection connection;
    private String schema;

    public OrdineDAO() {
        this.schema = "progettog25";
    }

    @Override
    public boolean inserisciOrdine(Ordine ordine) {
        if (ordine == null || ordine.getCliente() == null) {
            throw new IllegalArgumentException("L'ordine o il cliente non possono essere null");
        }

        connection = DBConnection.startConnection(connection, schema);

        String query = "INSERT INTO " + schema + ".Ordine (ID_Ordine, data_ord, Totale, Stato, ID_Prodotto, Cf) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, ordine.getIdOrdine());
            stmt.setTimestamp(2, Timestamp.valueOf(ordine.getDataOrd()));
            stmt.setBigDecimal(3, ordine.getTotale());
            stmt.setString(4, ordine.getStatoOrd().name());
            stmt.setInt(5, ordine.getProdotto().getIdProdotto());
            stmt.setString(6, ordine.getCliente().getCf());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Errore durante l'inserimento dell'ordine: " + e.getMessage());
            return false;
        } finally {
            DBConnection.closeConnection(connection);
        }
    }

    @Override
    public List<Ordine> getOrdiniByCliente(Cliente cliente) {
        List<Ordine> ordini = new ArrayList<>();
        if (cliente == null) {
            System.out.println("Errore: cliente nullo!");
            return ordini;
        }

        connection = DBConnection.startConnection(connection, schema);

        String query = "SELECT o.ID_Ordine, o.data_ord, o.Totale, o.Stato, o.ID_Prodotto, " +
                       "p.Nome_p, p.Prezzo, p.Categoria_P " +
                       "FROM " + schema + ".Ordine o " +
                       "JOIN " + schema + ".Prodotto p ON o.ID_Prodotto = p.ID_Prodotto " +
                       "WHERE o.Cf = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cliente.getCf());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setIdOrdine(rs.getString("ID_Ordine"));
                ordine.setDataOrd(rs.getTimestamp("data_ord").toLocalDateTime());
                ordine.setTotale(rs.getBigDecimal("Totale"));
                ordine.setStatoOrd(StatoOrd.valueOf(rs.getString("Stato").toUpperCase()));

                Prodotto prodotto = new Prodotto(
                    rs.getInt("ID_Prodotto"),
                    rs.getString("Nome_p"),
                    rs.getBigDecimal("Prezzo"),
                    Categoria.fromDatabaseValue(rs.getString("Categoria_P"))
                );

                ordine.setProdotto(prodotto);
                ordine.setCliente(cliente);
                ordini.add(ordine);
            }
        } catch (SQLException e) {
            System.err.println("Errore durante il recupero degli ordini: " + e.getMessage());
        } finally {
            DBConnection.closeConnection(connection);
        }

        return ordini;
    }
}
