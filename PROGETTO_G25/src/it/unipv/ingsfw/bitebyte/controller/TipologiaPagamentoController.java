package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.PortafoglioService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TipologiaPagamentoController {

	// Bottoni TipologiaPagamento
	@FXML
	private Button sceglibancomat;
	@FXML
	private Button sceglipaypal;

	private final SwitchSceneUtils switchScene;

	public TipologiaPagamentoController() {
		this.switchScene = new SwitchSceneUtils();
	}

	@FXML
	public void cambiaScena(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();

		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("sceglibancomat")) {
			System.out.println("sono in switch scene");
			PortafoglioVirtuale nuovoPortafoglio = new PortafoglioVirtuale(PortafoglioService.generaIdCasuale(), 0.00,
					TipologiaPagamento.BANCOMAT);
			Sessione.getInstance().setPortafoglioCliente(nuovoPortafoglio);
			PortafoglioService.creaPortafoglio(nuovoPortafoglio, Sessione.getInstance().getClienteConnesso());

			switchScene.Scene(clickedButton, "Bancomat.fxml", "Bancomat");
		} else if (clickedButton.getId().equals("sceglipaypal")) {
			AlertUtils.showAlert("PayPal", "ti sei connesso al tuo account PayPal");
			PortafoglioVirtuale nuovoPortafoglio = new PortafoglioVirtuale(PortafoglioService.generaIdCasuale(), 0.00,
					TipologiaPagamento.PAYPAL);
			Sessione.getInstance().setPortafoglioCliente(nuovoPortafoglio);
			PortafoglioService.creaPortafoglio(nuovoPortafoglio, Sessione.getInstance().getClienteConnesso());

			switchScene.Scene(clickedButton, "PortafoglioVirtuale.fxml", "PortafoglioVirtuale");
		}
	}

}
