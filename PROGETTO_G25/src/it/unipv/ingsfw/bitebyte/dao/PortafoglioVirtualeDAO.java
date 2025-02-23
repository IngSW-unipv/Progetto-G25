package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import it.unipv.ingsfw.bitebyte.models.Cliente;
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
		String query = "SELECT * FROM portafoglio_virtuale WHERE Cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, cf);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new PortafoglioVirtuale(rs.getString("id_port"), rs.getDouble("saldo"),
						TipologiaPagamento.valueOf(rs.getString("Tipo_pagamento")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return null;
	}

	@Override
	public boolean aggiornaPortafoglio(PortafoglioVirtuale portafoglio, Cliente cliente) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "UPDATE portafoglio_virtuale SET saldo = ?, Tipo_pagamento = ? WHERE Cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setDouble(1, portafoglio.getSaldo());
			ps.setString(2, portafoglio.getTipologiaPagamento().name());
			ps.setString(3, cliente.getCf());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnection.closeConnection(connection);
		}
	}

}
