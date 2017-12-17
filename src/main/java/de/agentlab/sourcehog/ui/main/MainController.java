package de.agentlab.sourcehog.ui.main;

import de.agentlab.sourcehog.ui.indexing.IndexAction;
import de.agentlab.sourcehog.model.IndexEntry;
import de.agentlab.sourcehog.query.QueryEngine;
import de.agentlab.sourcehog.runner.EditorRunner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public TextField searchterm;

    @FXML
    private TableView<IndexEntry> tableView;

    private IndexAction indexAction = new IndexAction();
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                IndexEntry entry = tableView.getSelectionModel().getSelectedItem();
                new EditorRunner().run(entry.getFile(), entry.getLine());
            }
        });
    }

    @FXML
    public void onEnter(ActionEvent ae) {
        String term = this.searchterm.getText();
        search(term);
    }

    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String term = this.searchterm.getText();
        search(term);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void search(String term) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Searching...");
        alert.setHeaderText(null);
        alert.setContentText("Searching...");

        alert.show();

        List<IndexEntry> result = new QueryEngine().find(term);

        alert.close();
        tableView.getItems().clear();
        tableView.getItems().addAll(result);
    }

    @FXML
    private void handleExitAction(final ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void handleIndexAction(final ActionEvent event) {
        this.indexAction.process(event, this.stage);
    }

    @FXML
    private void handleKeyRelease(final KeyEvent event) {
        KeyCode key = event.getCode();
        if (key == KeyCode.ENTER) {
            IndexEntry entry = tableView.getSelectionModel().getSelectedItem();
            new EditorRunner().run(entry.getFile(), entry.getLine());
        }
    }
}
