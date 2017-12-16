package de.agentlab.sourcehog.dialogs.indexing;

import de.agentlab.sourcehog.Configuration;
import de.agentlab.sourcehog.StringLiteralIndexer;
import de.agentlab.sourcehog.utils.ArrayUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class IndexAction {

    private Configuration configuration = new Configuration();

    public void process(ActionEvent event, Stage stage) {

        try {
            Dialog<ButtonType> settingsDialog = new Dialog<>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
            settingsDialog.getDialogPane().setContent((Parent) loader.load());

            ButtonType goButton = new ButtonType("Go!", ButtonBar.ButtonData.OK_DONE);

            settingsDialog.getDialogPane().getButtonTypes().addAll(
                    goButton, ButtonType.CLOSE
            );

            Optional<ButtonType> result = null;
            result = settingsDialog.showAndWait();
            if ((result.get()) == goButton) {
                System.out.println("Go!");
                IndexActionPopupController controller = loader.getController();
                System.out.println(controller.getDbFileValue());
            }
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
