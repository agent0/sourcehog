package de.agentlab.sourcehog;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class IndexAction {

    private Configuration configuration = new Configuration();

    public void process(ActionEvent event, Stage stage) {

        try {
            Dialog<Settings> settingsDialog = new Dialog<>();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
            settingsDialog.getDialogPane().setContent((Parent) loader.load());
            settingsDialog.getDialogPane().getButtonTypes().addAll(
                    ButtonType.CLOSE
            );

            settingsDialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void run() {
        String[] params = ArrayUtils.join(this.configuration.getDatabase(), this.configuration.getSourceDirs());

        List<String> result = new IndexRunner().run(params);
        System.out.println(result);

        new StringLiteralIndexer().index(this.configuration.getDatabase(), this.configuration.getSourceDirs());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText(null);
        alert.setContentText("Indexing complete");

        alert.showAndWait();
    }
}
