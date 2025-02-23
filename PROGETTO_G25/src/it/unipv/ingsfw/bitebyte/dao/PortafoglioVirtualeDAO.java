package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

public class PortafoglioVirtualeDAO implements IPortafoglioVirtualeDAO {
	private Connection connection;
	private String schema;

	public PortafoglioVirtualeDAO() {
		super();
		this.schema = "progettog25";
	}

	@Override
	public boolean creaPortafoglio(PortafoglioVirtuale portafoglio) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "INSERT INTO portafoglio_virtuale (ID_port, saldo, cf, tipologiaPagamento) VALUES (?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, portafoglio.getIdPort());
			ps.setDouble(2, portafoglio.getSaldo());
			ps.setString(3, portafoglio.getCf());
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
		String query = "SELECT * FROM portafoglio_virtuale WHERE cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, cf);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				  return new PortafoglioVirtuale(
	                        rs.getInt("id_port"),
	                        rs.getDouble("saldo"),
	                        rs.getString("cf"),
	                        TipologiaPagamento.valueOf(rs.getString("tipologiaPagamento"))
	                );
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return null;
	}

	@Override
	public boolean aggiornaPortafoglio(PortafoglioVirtuale portafoglio) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "UPDATE portafoglio_virtuale SET saldo = ?, tipologiaPagamento = ? WHERE cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setDouble(1, portafoglio.getSaldo());
			ps.setString(4, portafoglio.getTipologiaPagamento().name());
			ps.setString(3, portafoglio.getCf());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnection.closeConnection(connection);
		}
	}

	@Override
	public boolean eliminaPortafoglio(String cf) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "DELETE FROM portafoglio_virtuale WHERE cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, cf);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnection.closeConnection(connection);
		}
	}

	@Override
	public List<PortafoglioVirtuale> getAllPortafogli() {
		connection = DBConnection.startConnection(connection, schema);
		List<PortafoglioVirtuale> portafogli = new ArrayList<>();
		String query = "SELECT * FROM portafoglio_virtuale";
		try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
			while (rs.next()) {
				portafogli.add(new PortafoglioVirtuale(rs.getInt("id_port"), rs.getString("cf"), rs.getDouble("saldo"),
						rs.getString("tipologiaPagamento")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return portafogli;
	}

}
