package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.PortafoglioVirtuale;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.payment.BancomatPaymentAdapter;
import it.unipv.ingsfw.bitebyte.payment.BancomatService;
import it.unipv.ingsfw.bitebyte.payment.IPaymentAdapter;
import it.unipv.ingsfw.bitebyte.payment.PayPalPaymentAdapter;
import it.unipv.ingsfw.bitebyte.payment.PayPalService;
import it.unipv.ingsfw.bitebyte.payment.PaymentAdapterFactory;
import it.unipv.ingsfw.bitebyte.services.PortafoglioService;
import it.unipv.ingsfw.bitebyte.types.TipologiaPagamento;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PortafoglioVirtualeController {
    
    // Bottoni PortafoglioVirtuale
    @FXML
    private Button tornaProfilo;
    @FXML
    private Button tornaTipologia;
    @FXML
    private Button ricarica;

    //campi PortafoglioVirtuale
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
        }}
    
/*	//private Sessione sessione;
	private BancomatController bancomatController;

	public PortafoglioVirtualeController(Sessione sessione, BancomatController bancomatController) {
		//this.sessione = sessione;
		this.bancomatController = bancomatController;
	}*/
	
	
/*
	public void gestisciPortafoglio() {
		if (Sessione.getInstance().getPortafoglioCliente() == null) {
			System.out.println("Errore: Nessun portafoglio virtuale associato alla sessione.");
			return;
		}

		if (Sessione.getInstance().getPortafoglioCliente().getTipologiaPagamento() == null) {
			apriTipologiaPagamentoView();
		} else {
			apriPortafoglioVirtualeView();
		}
	}*/

 /*	private void apriTipologiaPagamentoView() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/TipologiaPagamento.fxml"));
        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("Login	");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
       
	}*/
	
	@FXML
	public void cambiaScena(ActionEvent event) {
		Button clickedButton = (Button) event.getSource();
		Stage stage = (Stage) clickedButton.getScene().getWindow(); // Ottieni lo Stage dal bottone premuto
		// Verifica quale bottone è stato premuto e cambia scena di conseguenza
		if (clickedButton.getId().equals("tornaProfilo")) {
			switchScene(stage, "ProfiloCliente.fxml", "Profilo Cliente");
		}else if (clickedButton.getId().equals("tornaTipologia")) {
			switchScene(stage, "TipologiaPagamento.fxml", "Tipologia Pagamento");
		}
	}
	
	private void switchScene(Stage stage, String fxml, String title) {
		try {
			System.out.println("sono in switch scene");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/unipv/ingsfw/bitebyte/view/fxml/" + fxml));
			Parent root = loader.load();
			stage.setTitle(title);
			stage.setScene(new Scene(root));
			stage.show();
		} catch (Exception e) {
			e.printStackTrace(); // Questo mostrerà eventuali errori
		}
	}
	
	@FXML
	public void ricarica(ActionEvent event) {
		//BancomatPaymentAdapter bpa = new BancomatPaymentAdapter(new BancomatService());
		//PayPalPaymentAdapter ppa = new PayPalPaymentAdapter(new PayPalService());
		IPaymentAdapter adattatore = PaymentAdapterFactory.getPaymentAdapter(Sessione.getInstance().getPortafoglioCliente().getTipologiaPagamento());
		Sessione.getInstance().getPortafoglioCliente().ricarica(5, adattatore);
		PortafoglioService.aggiornaPortafoglio(Sessione.getInstance().getPortafoglioCliente(),Sessione.getInstance().getClienteConnesso()); 
		//PaymentAdapterFactory.getPaymentAdapter(Sessione.getInstance().getPortafoglioCliente().getTipologiaPagamento())
	}
	
	@FXML
	public void caricaPortafoglio() {
		PortafoglioVirtuale portafoglio = Sessione.getInstance().getPortafoglioCliente();
	       
        saldoTXT.setText(String.valueOf(portafoglio.getSaldo()));
        idTXT.setText(portafoglio.getIdPort());
        tipoTXT.setText(portafoglio.getTipologiaPagamento().name());
	}
	
	
	

}



	
	/*
	

	private void apriPortafoglioVirtualeView() {
		PortafoglioVirtualeView portafoglioVirtualeView = new PortafoglioVirtualeView(this);
		portafoglioVirtualeView.mostra();
	}

	public void aggiornaTipologiaPagamento(String nuovaTipologia) {
		PortafoglioVirtuale portafoglio = sessione.getPortafoglioVirtuale();
		if (portafoglio != null) {
			portafoglio.setTipologiaPagamento(nuovaTipologia);

			if ("paypal".equalsIgnoreCase(nuovaTipologia)) {
				JOptionPane.showMessageDialog(null, "Ti sei connesso al tuo account PayPal");
				apriPortafoglioVirtualeView();
			} else if ("bancomat".equalsIgnoreCase(nuovaTipologia)) {
				bancomatController.apriBancomatView();
			} else {
				apriPortafoglioVirtualeView();
			}
		} else {
			System.out.println("Errore: Impossibile aggiornare la tipologia di pagamento.");
		}
	}*/


