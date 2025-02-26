package it.unipv.ingsfw.bitebyte.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

public class PortafoglioVirtualeDAO implements IPortafoglioVirtualeDAO {
	//Alice
	private Connection connection;
	private String schema;

	public PortafoglioVirtualeDAO() {
		super();
		this.schema = "progettog25";
	}

	@Override
	public boolean creaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "INSERT INTO portafoglio_virtuale (ID_port, saldo, Cf, Tipo_pagamento) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, portafoglio.getIdPort());
			ps.setDouble(2, portafoglio.getSaldo());
			ps.setString(3, cliente.getCf());
			ps.setString(4, portafoglio.getTipologiaPagamento().name());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnection.closeConnection(connection);
		}
	}

	@Override
	public PortafoglioVirtuale leggiPortafoglio(String cf) {
		connection = DBConnection.startConnection(connection, schema);
		PortafoglioVirtuale portafoglio = null;
		String query = "SELECT ID_Port, Saldo, Tipo_pagamento FROM portafoglio_virtuale WHERE Cf = ?";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, cf);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				String idPort = String.valueOf(rs.getInt("ID_Port"));
				double saldo = rs.getDouble("Saldo");
				String tipoPagamentoStr = rs.getString("Tipo_pagamento");
                TipologiaPagamento tipoPagamento = TipologiaPagamento.valueOf(tipoPagamentoStr.toUpperCase());

				portafoglio = new PortafoglioVirtuale(idPort, saldo, tipoPagamento);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}

		return portafoglio;
	}

	@Override
	public boolean aggiornaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "UPDATE portafoglio_virtuale SET saldo = ? WHERE Cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setBigDecimal(1, BigDecimal.valueOf(portafoglio.getSaldo()));
			ps.setString(2, cliente.getCf());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnection.closeConnection(connection);
		}
	}
	
	
	//Davide

    // Metodo per recuperare il saldo
    public double getSaldo(String codiceFiscale) {
        double saldo = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Connessione al database
            connection = DBConnection.startConnection(connection, schema);

            // Query SQL per recuperare il saldo
            String query = "SELECT p.Saldo " +
                           "FROM progettog25.portafoglio_virtuale p " +
                           "JOIN progettog25.cliente c ON c.Cf = p.Cf " +
                           "WHERE c.Cf = ?";

            // Prepara la query
            ps = connection.prepareStatement(query);
            ps.setString(1, codiceFiscale); // Imposta il codice fiscale come parametro

            // Esecuzione della query
            rs = ps.executeQuery();

            // Verifica se il risultato esiste e ottieni il saldo
            if (rs.next()) {
                saldo = rs.getDouble("Saldo");
            }
        } catch (SQLException e) {
            // Gestione dell'errore: stampa l'eccezione o rilancia
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero del saldo per il codice fiscale: " + codiceFiscale, e);
        } finally {
            // Chiusura delle risorse
            DBConnection.closeConnection(connection);
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return saldo;
    }

    public void aggiornaSaldo(String codiceFiscale, double nuovoSaldo) {
        String query = "UPDATE progettog25.portafoglio_virtuale SET saldo = ? WHERE Cf = ?";

        try (Connection connection = DBConnection.startConnection(null, schema);
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, nuovoSaldo); // Imposta il nuovo saldo
            ps.setString(2, codiceFiscale); // Imposta il codice fiscale
            ps.executeUpdate(); // Esegui l'aggiornamento

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nell'aggiornamento del saldo per il codice fiscale: " + codiceFiscale, e);
        }
    }
    
    public int getIdPortByCliente(String codiceFiscale) {
        int idPort = 0;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = DBConnection.startConnection(connection, schema);
            String query = "SELECT ID_Port " +
                           "FROM progettog25.portafoglio_virtuale " +
                           "WHERE Cf = ?";

            ps = connection.prepareStatement(query);
            ps.setString(1, codiceFiscale); // Imposta il codice fiscale come parametro
            rs = ps.executeQuery();
            if (rs.next()) {
                idPort = rs.getInt("ID_Port");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Errore nel recupero dell'ID del portafoglio per il codice fiscale: " + codiceFiscale, e);
        } finally {
            // Chiusura delle risorse
            DBConnection.closeConnection(connection);
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idPort;
    }

}
