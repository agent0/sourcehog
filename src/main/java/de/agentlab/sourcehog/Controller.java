package de.agentlab.sourcehog;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    public TextField searchterm;

    @FXML
    private TableView<CTag> tableView;

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

    private void search(String term) {
        List<CTag> result = new QueryRunner().run(term).stream().map(line -> new CTag(line)).collect(Collectors.toList());
        tableView.getItems().clear();
        tableView.getItems().addAll(result);
    }
}
