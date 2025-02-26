package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.OrdineService;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;
import it.unipv.ingsfw.bitebyte.view.ViewStorico;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.ScrollPane;
import javafx.event.ActionEvent;
import java.util.List;

public class StoricoController {

    @FXML
    private VBox contenitoreOrdini;
    @FXML
    private Button btnTornaIndietro;
    
    private ListView<Ordine> listViewOrdini;  // Riferimento alla ListView in FXML

    private OrdineService ordineService;
    private ViewStorico viewStorico;  // Riferimento alla classe ViewStorico
    
    public StoricoController() {
        // Inizializza il servizio per recuperare gli ordini
        ordineService = new OrdineService();
    }

    @FXML
    public void initialize() {
        // Recupera il cliente loggato
        Cliente clienteLoggato = Sessione.getInstance().getClienteConnesso();
        ViewStorico viewStorico = new ViewStorico();


        if (clienteLoggato != null) {
            // Recupera gli ordini del cliente loggato
            List<Ordine> ordini = ordineService.getOrdiniPerCliente(clienteLoggato);
            
            // Passa la lista degli ordini alla ViewStorico per la visualizzazione
            System.out.println(viewStorico);
            if (viewStorico != null) {
            	
                viewStorico.creaInterfaccia(ordini);
                // Aggiungi un ScrollPane per abilitare lo scrolling
                javafx.scene.control.ScrollPane scrollPane = viewStorico.creaInterfaccia(ordini);
                contenitoreOrdini.getChildren().clear();  // Rimuovi eventuali contenuti precedenti
                contenitoreOrdini.getChildren().add(scrollPane);
            }
        }
    }

    // Metodo per settare la ViewStorico (che può essere chiamato dal main o dal controller che carica questa vista)
    public void setViewStorico(ViewStorico viewStorico) {
        this.viewStorico = viewStorico;
    }

    @FXML
    public void buttonTornaIndietro(ActionEvent event) {
    	SwitchSceneUtils.switchScene(event, "/it/unipv/ingsfw/bitebyte/view/fxml/ProfiloCliente.fxml", "Profilo cliente");
    }
}
