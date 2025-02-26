package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Bancomat;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.payment.BancomatService;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BancomatController {

	// campi e bottoni Bancomat.fxml
	@FXML
	private TextField cvv;
	@FXML
	private TextField circuito;
	@FXML
	private TextField numcarta;
	@FXML
	private TextField titolare;
	@FXML
	private TextField datascad;
	@FXML
	private Button pulsanteSalva;

	private final BancomatService bancomatService;
	private final SwitchSceneUtils switchScene;

	public BancomatController() {
		this.bancomatService = new BancomatService();
		this.switchScene = new SwitchSceneUtils();

	}

	// Dependency Injection
	public BancomatController(BancomatService bancomatService, SwitchSceneUtils navigator) {
		this.bancomatService = bancomatService;
		this.switchScene = navigator;
	}

	@FXML
	public void registraCarta(ActionEvent event) {
		try {
			Bancomat bancomat = bancomatService.creaBancomat(numcarta.getText(), titolare.getText(), circuito.getText(),
					cvv.getText(), datascad.getText(), Sessione.getInstance().getClienteConnesso());
			bancomatService.salvaBancomat(bancomat);
			AlertUtils.showAlert("Successo", "Dati Bancomat caricati con successo!");
			switchScene.Scene(pulsanteSalva, "PortafoglioVirtuale.fxml", "Portafoglio Virtuale");
		} catch (IllegalArgumentException e) {
			AlertUtils.showAlert("Errore", e.getMessage());
		} catch (Exception e) {
			AlertUtils.showAlert("Errore", "Errore durante il salvataggio dei dati");
			e.printStackTrace();
		}
	}

}
