package de.agentlab.sourcehog;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.List;

public class IndexAction {

    private Configuration configuration = new Configuration();

    public void process(ActionEvent event) {

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
