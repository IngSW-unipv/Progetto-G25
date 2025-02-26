package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.payment.IPaymentAdapter;
import it.unipv.ingsfw.bitebyte.payment.PaymentAdapterFactory;
import it.unipv.ingsfw.bitebyte.services.PortafoglioService;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PortafoglioVirtualeController {

	// Bottoni PortafoglioVirtuale
	@FXML
	private Button tornaProfilo;
	@FXML
	private Button tornaTipologia;
	@FXML
	private Button ricarica;

	// campi PortafoglioVirtuale
	@FXML
	private TextField saldoTXT;
	@FXML
	private TextField idTXT;
	@FXML
	private TextField tipoTXT;

	@FXML
	public void initialize() {
		if (Sessione.getInstance().getClienteConnesso() != null) {
			caricaPortafoglio();
		}
	}

	@FXML
	public void cambiaScena(ActionEvent event) {
		SwitchSceneUtils switchSceneUtils = new SwitchSceneUtils();
		switchSceneUtils.Scene(tornaProfilo, "ProfiloCliente.fxml", "Profilo Cliente");
	}

	@FXML
	public void ricarica(ActionEvent event) {
		IPaymentAdapter adattatore = PaymentAdapterFactory
				.getPaymentAdapter(Sessione.getInstance().getPortafoglioCliente().getTipologiaPagamento());
		Sessione.getInstance().getPortafoglioCliente().ricarica(5, adattatore);
		PortafoglioService.aggiornaPortafoglio(Sessione.getInstance().getPortafoglioCliente(),
				Sessione.getInstance().getClienteConnesso());
		SwitchSceneUtils switchSceneUtils = new SwitchSceneUtils();
		switchSceneUtils.Scene(ricarica, "ProfiloCliente.fxml", "Profilo Cliente");
	}

	@FXML
	public void caricaPortafoglio() {
		PortafoglioVirtuale portafoglio = Sessione.getInstance().getPortafoglioCliente();
		saldoTXT.setText(String.valueOf(portafoglio.getSaldo()));
		idTXT.setText(portafoglio.getIdPort());
		tipoTXT.setText(portafoglio.getTipologiaPagamento().name());
	}

}
