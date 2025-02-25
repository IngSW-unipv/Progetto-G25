package it.unipv.ingsfw.bitebyte.dao;

import java.sql.*;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import java.time.LocalDate;

public class ClienteDAO implements IClienteDAO {
	private Connection connection;
	private String schema;

	public ClienteDAO() {
		super();
		this.schema = "progettog25";
	}

	@Override
	public boolean esisteUsername(String username) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "SELECT COUNT(*) FROM cliente WHERE username = ? ";

		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}

		return false;
	}

	@Override
	public boolean esisteCliente(String email) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "SELECT COUNT(*) FROM cliente WHERE email = ? ";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return false;
	}

	@Override
	public void registraCliente(Cliente cliente) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "INSERT INTO cliente (Cf, Nome, Cognome, Username, Data_N, Email, Passw) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement ps = connection.prepareStatement(query);

			ps.setString(1, cliente.getCf());
			ps.setString(2, cliente.getNome());
			ps.setString(3, cliente.getCognome());
			ps.setString(4, cliente.getUsername());
			ps.setDate(5, java.sql.Date.valueOf(cliente.getDataN()));
			ps.setString(6, cliente.getEmail());
			ps.setString(7, cliente.getPassword());
			ps.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Errore durante l'inserimento del cliente: " + e.getMessage());
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}

	}

	@Override
	public boolean verificaLogin(String username, String password) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "SELECT * FROM cliente WHERE username = ? and passw = ? ";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			return rs.next();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}

		return false;
	}

	@Override
	public Cliente getCliente(String username, String password) {
		Cliente cliente = null;

		try {
			connection = DBConnection.startConnection(connection, schema);
			String query = "SELECT * FROM cliente WHERE username = ? AND passw = ? ";
			PreparedStatement stmt = connection.prepareStatement(query);
			stmt.setString(1, username);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Date dataNascitaSQL = rs.getDate("Data_N");
				LocalDate dataNascita = dataNascitaSQL.toLocalDate();

				cliente = new Cliente(rs.getString("cf"), rs.getString("nome"), rs.getString("cognome"),
						rs.getString("email"), rs.getString("passw"), dataNascita, rs.getString("username"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}

		return cliente;
	}

	@Override
	public boolean modificaProfilo(Cliente clienteModificato) {

		try {
			connection = DBConnection.startConnection(connection, schema);
			String query = "UPDATE Cliente SET nome = ?, cognome = ?, username = ?, passw = ? WHERE cf = ? ";
			PreparedStatement stmt = connection.prepareStatement(query);

			stmt.setString(1, clienteModificato.getNome());
			stmt.setString(2, clienteModificato.getCognome());
			stmt.setString(3, clienteModificato.getUsername());
			stmt.setString(4, clienteModificato.getPassword());
			stmt.setString(5, clienteModificato.getCf());

			// Eseguo l'update e controllo se almeno una riga è stata modificata
			int rowsUpdated = stmt.executeUpdate();
			return rowsUpdated > 0;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}

		return false;
	}

}
