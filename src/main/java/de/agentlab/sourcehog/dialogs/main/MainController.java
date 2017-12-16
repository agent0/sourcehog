package de.agentlab.sourcehog.dialogs.main;

import de.agentlab.sourcehog.EditorRunner;
import de.agentlab.sourcehog.QueryRunner;
import de.agentlab.sourcehog.dialogs.indexing.IndexAction;
import de.agentlab.sourcehog.model.CTag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    @FXML
    public TextField searchterm;

    @FXML
    private TableView<CTag> tableView;

    private IndexAction indexAction = new IndexAction();
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() && e.getClickCount() == 2) {
                CTag ctag = tableView.getSelectionModel().getSelectedItem();
                new EditorRunner().run(ctag.getFile(), ctag.getLine());
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
        List<CTag> result = new QueryRunner().run(term).stream().map(line -> new CTag(line)).collect(Collectors.toList());
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
}