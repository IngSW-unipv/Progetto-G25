package it.unipv.ingsfw.bitebyte.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;

import it.unipv.ingsfw.bitebyte.models.Bancomat;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;

public class BancomatDAO implements IBancomatDAO {
	private Connection connection;
	private String schema;

	public BancomatDAO() {
		super();
		this.schema = "progettog25";
	}

	@Override
	public boolean creaBancomat(Bancomat bancomat, Cliente cliente) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "INSERT INTO bancomat (Num_carta, Data_Scad, CVV, Titolare, Circuito, Cf) VALUES (?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, bancomat.getNumCarta());
			ps.setString(2, bancomat.getDataScadenza().toString());
			ps.setInt(3, bancomat.getCodice());
			ps.setString(4, bancomat.getTitolare());
			ps.setString(5, bancomat.getCircuito());
			ps.setString(6, cliente.getCf());

			// aggiornaTipologiaPagamento(cliente.getCf(), TipologiaPagamento.BANCOMAT);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBConnection.closeConnection(connection);
		}
	}

	@Override
	public Bancomat leggiBancomat(String cf) {
		connection = DBConnection.startConnection(connection, schema);
		String query = "SELECT * FROM bancomat WHERE Cf = ?";
		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, cf);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				YearMonth dataScadenza = YearMonth.parse(rs.getString("Data_Scad"));
				return new Bancomat(rs.getString("Num_carta"), rs.getString("Titolare"), dataScadenza,
						rs.getString("Circuito"), rs.getInt("CVV"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnection.closeConnection(connection);
		}
		return null;
	}

}
