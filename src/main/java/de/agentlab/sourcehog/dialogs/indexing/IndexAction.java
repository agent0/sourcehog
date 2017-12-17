package de.agentlab.sourcehog.dialogs.indexing;

import de.agentlab.sourcehog.dialogs.main.Main;
import de.agentlab.sourcehog.indexer.JavaIndexer;
import de.agentlab.sourcehog.indexer.StringLiteralIndexer;
import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.runner.IndexRunner;
import de.agentlab.sourcehog.utils.ArrayUtils;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.Executors;

public class IndexAction {

    private boolean running;

    public void process(ActionEvent event, Stage stage) {
        if (this.running) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Already running ");
            alert.setHeaderText(null);
            alert.setContentText("Indexing allready in progress");

            alert.showAndWait();
            return;
        }
        Configuration configuration = new Configuration();
        configuration.load();
        try {
            Dialog<ButtonType> indexDialog = new Dialog<>();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
            indexDialog.getDialogPane().setContent(loader.load());
            IndexActionPopupController controller = loader.getController();

            ButtonType goButton = new ButtonType("Go!", ButtonBar.ButtonData.OK_DONE);

            indexDialog.getDialogPane().getButtonTypes().addAll(
                    goButton, ButtonType.CLOSE
            );

            controller.setDbFileValue(configuration.getDatabase());
            controller.setDirsValue(configuration.getSourceDirsAsString());
            Optional<ButtonType> result = null;
            result = indexDialog.showAndWait();
            if ((result.get()) == goButton) {
                configuration.setDatabase(controller.getDbFileValue());
                configuration.setSourceDirsFromString(controller.getDirsValue());
                configuration.save();

                this.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void run() {
        this.running = true;
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Configuration configuration = new Configuration();
                configuration.load();

                if (Main.FORMAT.equals("N")) {
                    new JavaIndexer().index(configuration.getDatabase(), configuration.getSourcedirs());
                } else {
                    String[] params = ArrayUtils.join(configuration.getDatabase(), configuration.getSourcedirs());
                    new IndexRunner().run(params);

                    new StringLiteralIndexer().index(configuration.getDatabase(), configuration.getSourcedirs());
                }

                IndexAction.this.running = false;
                return null;
            }
        };

        task.setOnSucceeded(t -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Done");
            alert.setHeaderText(null);
            alert.setContentText("Indexing complete");

            alert.showAndWait();
        });

        Executors.newCachedThreadPool().submit(task);
    }
}
