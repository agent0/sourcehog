package de.agentlab.sourcehog;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

import java.util.List;

public class IndexAction {

    public void process(ActionEvent event) {
        List<String> result = new IndexRunner().run("d:/tmp/java.ctags", "d:/Programming/Java", "d:/Programming/Javascript");
        System.out.println(result);

        new StringLiteralIndexer().index("d:/tmp/java.ctags", "d:/Programming/Java");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Done");
        alert.setHeaderText(null);
        alert.setContentText("Indexing complete");

        alert.showAndWait();
    }
}
