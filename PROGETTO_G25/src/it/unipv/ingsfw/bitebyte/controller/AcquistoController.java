package it.unipv.ingsfw.bitebyte.controller;

import it.unipv.ingsfw.bitebyte.models.Cliente;
import it.unipv.ingsfw.bitebyte.models.Stock;
import it.unipv.ingsfw.bitebyte.services.AcquistoService;
import it.unipv.ingsfw.bitebyte.utils.AlertUtils;
import it.unipv.ingsfw.bitebyte.utils.SwitchSceneUtils;
import it.unipv.ingsfw.bitebyte.view.ViewPrSelected;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AcquistoController {

    private final AcquistoService acquistoService;
    private final ViewPrSelected view;
    private final Stage previousStage;
    private final Stage newStage;

    public AcquistoController(AcquistoService acquistoService, Stage previousStage, Stage newStage) {
        this.acquistoService = acquistoService;
        this.view = new ViewPrSelected();
        this.previousStage = previousStage;
        this.newStage = newStage;
    }

    public void mostraInterfaccia(Stock stock) {
        Cliente clienteLoggato = acquistoService.ottieniClienteLoggato();
        if (clienteLoggato == null) {
            AlertUtils.showAlert("Errore", "Nessun cliente loggato.");
            return;
        }

        double saldo = acquistoService.ottieniSaldoCliente(clienteLoggato);
        VBox vbox = view.creaInterfaccia(stock, this, newStage, previousStage, clienteLoggato, saldo);
        configuraEVisualizzaFinestra(vbox);
    }

    public void acquistaProdotto(Stock stock) {
        acquistoService.acquistaProdotto(stock);
        tornaAllaPaginaProfiloCliente();
    }
    
    private void configuraEVisualizzaFinestra(VBox vbox) {
        Scene scene = new Scene(vbox, 650, 500);
        newStage.setScene(scene);
        newStage.setResizable(false);
        newStage.show();
    }

    private void tornaAllaPaginaProfiloCliente() {
        SwitchSceneUtils utils = new SwitchSceneUtils();
        utils.switchSceneStage(newStage, "/it/unipv/ingsfw/bitebyte/view/fxml/ProfiloCliente.fxml", "Profilo Cliente");
        previousStage.close();
    }
}
