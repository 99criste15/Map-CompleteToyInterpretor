package sample;

import Controller.Ctrl;
import Domain.ProgramState.*;
import View.Interpreter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private ArrayList<Ctrl> controllers;
    @FXML
    private TableColumn<Pair<String, String>, String> heapAddr;
    @FXML
    private TableColumn<Pair<String, String>, String> heapVal;
    @FXML
    private TableColumn<Pair<String, String>, String> symName;
    @FXML
    private TableColumn<Pair<String, String>, String> symVal;


    @FXML
    private ListView<String> programs;

    @FXML
    private TableView<Pair<String, String>> symView;

    @FXML
    private TableView<Pair<String, String>> heapView;

    @FXML
    private ListView<String> stackView;

    @FXML
    private ListView<String> outView;

    @FXML
    private ListView<String> tableView;

    @FXML
    private ListView<String> statesView;

    @FXML
    private TextField stateId;

    private int selectedPrg = -1;

    private int selectedState = -1;

    private Ctrl crtCtrl = null;

    public void allStepButtonPush() {
        if (controllers.get(selectedPrg).getRepo().getList().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("The program stack is empty. You need to restart it for another evaluation \n");
            alert.showAndWait();
        }

        while (!crtCtrl.getRepo().getList().isEmpty()) {
            oneStepButtonPush();
        }
        crtCtrl.getRepo().setList(new ArrayList<>());
        statesView.getItems().clear();


    }

    public void checkIfSelectedState() throws Exception {
        selectedState = statesView.getSelectionModel().getSelectedIndex();
        symView.getItems().clear();
        stackView.getItems().clear();
        stackView.setItems(
                FXCollections.observableList(crtCtrl.stackToString(selectedState))
        );
        symView.setItems(FXCollections.observableList(crtCtrl.symToString(selectedState))
        );
    }

    public void resetProgram() throws Exception {

        tableView.getItems().clear();
        statesView.getItems().clear();
        heapView.getItems().clear();
        symView.getItems().clear();
        outView.getItems().clear();
        stackView.getItems().clear();
        crtCtrl.getRepo().addPrg(new PrgState(new MyStack(), new MyDictionary(), new Heap(), new MyList(), new FileTable(), crtCtrl.getProgram()));
        statesView.setItems(FXCollections.observableList(controllers.get(selectedPrg).getRepo().getIndexes()));

    }

    public void checkIfSelectedProgram() throws Exception {

        int prgNr = programs.getSelectionModel().getSelectedIndex();
        if (prgNr == selectedPrg) return;
        selectedState = -1;
        selectedPrg = prgNr;
        crtCtrl = controllers.get(selectedPrg);
        stateId.setText(selectedPrg + "");
        tableView.getItems().clear();
        statesView.getItems().clear();
        heapView.getItems().clear();
        symView.getItems().clear();
        outView.getItems().clear();
        stackView.getItems().clear();
        statesView.setItems(FXCollections.observableList(crtCtrl.getRepo().getIndexes()));
        if (!crtCtrl.getRepo().getList().isEmpty()) {
            stackView.setItems(
                    FXCollections.observableList(crtCtrl.stackToString(0))
            );
        }

    }

    public void oneStepButtonPush() {

        crtCtrl.getHeap().setContent(Domain.ProgramState.GarbageCollector.safeGarbageCollector(
                Domain.ProgramState.GarbageCollector.getAddrFromTables(GarbageCollector.toDict(crtCtrl.getRepo().getList()), crtCtrl.getHeap().getContent().values()),
                crtCtrl.getHeap().getContent()));

        try {
            crtCtrl.oneStepForAllPrg(crtCtrl.getRepo().getList());

            statesView.setItems(FXCollections.observableList(crtCtrl.getRepo().getIndexes()));

            heapView.setItems(FXCollections.observableList(crtCtrl.heapToString()));

            outView.setItems(FXCollections.observableList(crtCtrl.outToString()));

            tableView.setItems(FXCollections.observableList(crtCtrl.fileTableToString()));

            if (selectedState > -1) {
                symView.setItems(FXCollections.observableList(crtCtrl.symToString(selectedState)));
                stackView.setItems(
                        FXCollections.observableList(crtCtrl.stackToString(selectedState)));
            }
        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(ex.toString());
            alert.showAndWait();
        }
        crtCtrl.getRepo().setList(Ctrl.removeCompletedPrg(crtCtrl.getRepo().getList()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        controllers = new ArrayList<>();


        heapAddr.setCellValueFactory(p -> {
            // for second column we use value
            return new SimpleStringProperty(p.getValue().getKey());
        });

        heapVal.setCellValueFactory(p -> {
            // for second column we use value
            return new SimpleStringProperty(p.getValue().getValue());
        });

        symName.setCellValueFactory(p -> {
            // for second column we use value
            return new SimpleStringProperty(p.getValue().getKey());
        });

        symVal.setCellValueFactory(p -> {
            // for second column we use value
            return new SimpleStringProperty(p.getValue().getValue());
        });

        try {
            Interpreter.create(controllers);
            programs.setItems(FXCollections.observableList(Interpreter.getPrograms()));
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText(e.toString());

            alert.showAndWait();
        }


    }
}
