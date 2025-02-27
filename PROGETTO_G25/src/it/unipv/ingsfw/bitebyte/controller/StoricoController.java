package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.dao.IOrdineDAO;
import it.unipv.ingsfw.bitebyte.dao.OrdineDAO;
import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Ordine;
import it.unipv.ingsfw.bitebyte.models.Sessione;
import it.unipv.ingsfw.bitebyte.services.IOrdineService;
import it.unipv.ingsfw.bitebyte.services.OrdineService;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;
import it.unipv.ingsfw.bitebyte.view.ViewStorico;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.util.List;

public class StoricoController {

    @FXML
    private VBox contenitoreOrdini;
    
    @FXML
    private Button btnTornaIndietro;

    private OrdineService ordineService;
    private ViewStorico viewStorico;

    public StoricoController() {
        OrdineDAO ordineDAO = new OrdineDAO(); // Inizializza il DAO
        this.ordineService = new OrdineService(ordineDAO); // Inietta il servizio con il DAO
        this.viewStorico = new ViewStorico(); // Inizializza la vista
    }
    @FXML
    public void initialize() {
        // Recupera il cliente loggato dalla sessione
        Cliente clienteLoggato = Sessione.getInstance().getClienteConnesso();

        if (clienteLoggato != null) {
            // Recupera gli ordini del cliente loggato
            List<Ordine> ordini = ordineService.getOrdiniPerCliente(clienteLoggato);

            // Debug: stampa gli ordini recuperati
            System.out.println("Ordini recuperati: " + ordini.size());

            if (viewStorico != null) {
                // Crea l'interfaccia con gli ordini
                ScrollPane scrollPane = viewStorico.creaInterfaccia(ordini);
                
                // Rimuove eventuali contenuti precedenti
                contenitoreOrdini.getChildren().clear();
                
                // Aggiunge il nuovo ScrollPane con la lista degli ordini
                contenitoreOrdini.getChildren().add(scrollPane);
            } else {
                System.err.println("Errore: ViewStorico non è stato inizializzato!");
            }
        } else {
            System.err.println("Errore: Nessun cliente loggato.");
        }
    }

    public void setViewStorico(ViewStorico viewStorico) {
        this.viewStorico = viewStorico;
    }

    @FXML
    public void buttonTornaIndietro(ActionEvent event) {
        SwitchSceneUtils.switchScene(event, "ProfiloCliente.fxml", "Profilo cliente");
    }
}
