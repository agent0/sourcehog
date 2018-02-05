package de.agentlab.sourcehog.ui.main;

import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.model.IndexEntry;
import de.agentlab.sourcehog.query.QueryEngine;
import de.agentlab.sourcehog.runner.EditorRunner;
import de.agentlab.sourcehog.runner.ExplorerRunner;
import de.agentlab.sourcehog.ui.configuration.ConfigurationAction;
import de.agentlab.sourcehog.ui.indexing.IndexAction;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    public TextField searchterm;

    @FXML
    private TableView<IndexEntry> tableView;

    private ConfigurationAction configurationAction = new ConfigurationAction();
    private IndexAction indexAction = new IndexAction();
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu cm = new ContextMenu();
        MenuItem mi1 = new MenuItem("Datei im Explorer anzeigen");
        mi1.setOnAction(e -> {
            IndexEntry entry = tableView.getSelectionModel().getSelectedItem();
            new ExplorerRunner().run(entry.getFile());
        });
        cm.getItems().add(mi1);

        tableView.setOnMousePressed(e -> {
            if (e.isSecondaryButtonDown()) {
                cm.show(tableView, e.getScreenX(), e.getScreenY());
            } else if (e.isPrimaryButtonDown() && e.isShiftDown() && e.getClickCount() == 2) {
                IndexEntry entry = tableView.getSelectionModel().getSelectedItem();
                new ExplorerRunner().run(entry.getFile());
            } else if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
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

        if (term.length() > 2) {

            Configuration configuration = new Configuration();
            configuration.load();
            if (!Files.exists(Paths.get(configuration.getDatabaseDir()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Searching...");
                alert.setHeaderText(null);
                alert.setContentText("The specified database directory does not exist");
                alert.show();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Searching...");
            alert.setHeaderText(null);
            alert.setContentText("Searching...");
            alert.show();

            List<IndexEntry> result = new QueryEngine().find(term);

            alert.close();
            tableView.scrollTo(0);
            tableView.getItems().clear();
            tableView.getItems().addAll(result);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Searching...");
            alert.setHeaderText(null);
            alert.setContentText("Search term too short...");
            alert.show();
        }
    }

    @FXML
    private void handleSettingsAction(final ActionEvent event) {
        this.configurationAction.process(event, this.stage);
    }

    @FXML
    private void handleExitAction(final ActionEvent event) {
        Platform.exit();
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
