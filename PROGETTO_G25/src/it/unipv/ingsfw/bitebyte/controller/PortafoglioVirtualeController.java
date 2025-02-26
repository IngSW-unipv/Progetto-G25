package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.payment.IPaymentAdapter;
import it.unipv.ingsfw.bitebyte.payment.PaymentAdapterFactory;
import it.unipv.ingsfw.bitebyte.services.PortafoglioService;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
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

	private PortafoglioService portafoglioService;
	private SwitchSceneUtils switchScene;

	public PortafoglioVirtualeController() {
		this.portafoglioService = new PortafoglioService();
		this.switchScene = new SwitchSceneUtils();
	}

	// Costruttore con iniezione delle dipendenze
	public PortafoglioVirtualeController(PortafoglioService portafoglioService, SwitchSceneUtils switchScene) {
		this.portafoglioService = portafoglioService;
		this.switchScene = switchScene;
	}

	@FXML
	public void initialize() {
		if (Sessione.getInstance().getClienteConnesso() != null) {
			caricaPortafoglio();
		}
	}

	@FXML
	public void cambiaScena(ActionEvent event) {
		switchScene.Scene(tornaProfilo, "ProfiloCliente.fxml", "Profilo Cliente");
	}

	@FXML
	public void ricarica(ActionEvent event) {
		IPaymentAdapter adattatore = PaymentAdapterFactory
				.getPaymentAdapter(portafoglioService.getTipologiaPagamento());

		portafoglioService.getPortafoglioCliente().ricarica(5, adattatore); // importo di ricarica fisso a 5€
		AlertUtils.showAlert("Ricarica", "Ricaricato di 5€!");
		PortafoglioService.aggiornaPortafoglio(portafoglioService.getPortafoglioCliente(),
				portafoglioService.getClienteConnesso());
		caricaPortafoglio();
	}

	@FXML
	public void caricaPortafoglio() {
		PortafoglioVirtuale portafoglio = portafoglioService.getPortafoglioCliente();
		// Sessione.getInstance().getPortafoglioCliente();
		saldoTXT.setText(String.valueOf(portafoglio.getSaldo()));
		idTXT.setText(portafoglio.getIdPort());
		tipoTXT.setText(portafoglio.getTipologiaPagamento().name());
	}

}
