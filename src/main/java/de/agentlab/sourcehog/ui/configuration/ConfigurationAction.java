package de.agentlab.sourcehog.ui.configuration;

import de.agentlab.sourcehog.model.Configuration;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class ConfigurationAction {


    public void process(ActionEvent event, Stage stage) {
        Configuration configuration = new Configuration();
        configuration.load();
        try {
            Dialog<ButtonType> configurationDialog = new Dialog<>();
            FXMLLoader loader = new FXMLLoader(ConfigurationAction.class.getResource("ConfigurationPopup.fxml"));
            configurationDialog.getDialogPane().setContent(loader.load());
            ConfigurationPopupController controller = loader.getController();

            ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);

            configurationDialog.getDialogPane().getButtonTypes().addAll(
                    saveButton, ButtonType.CLOSE
            );

            controller.setDbDirValue(configuration.getDatabaseDir());
            controller.setDirsValue(configuration.getSourceDirsAsString());
            controller.setEditorpathValue(configuration.getEditorpath());
            Optional<ButtonType> result = null;
            result = configurationDialog.showAndWait();
            if ((result.get()) == saveButton) {
                configuration.setDatabaseDir(controller.getDbDirValue());
                configuration.setSourceDirsFromString(controller.getDirsValue());
                configuration.setEditorpath(controller.getEditorpathValue());
                configuration.save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
