package de.agentlab.sourcehog.ui.main;

import de.agentlab.sourcehog.indexer.IndexEngine;
import de.agentlab.sourcehog.model.IndexEntry;
import de.agentlab.sourcehog.query.QueryEngine;
import de.agentlab.sourcehog.utils.Log;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Sourcehog");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();

        MainController controller = loader.getController();
        controller.setStage(primaryStage);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            String command = args[0];
            if (command.equals("index")) {
                System.out.println("index");
                new IndexEngine().index2();
                System.exit(1);
            } else if (command.equals("find")) {
                long start = System.currentTimeMillis();
                List<IndexEntry> result = new QueryEngine().find2(args[1]);
                long end = System.currentTimeMillis();
                Log.info("find took " + (end - start) + "ms");
                for (IndexEntry entry : result) {
                    System.out.println(entry.getTag() + " " + entry.getFile() + " " + entry.getLine());
                }

                System.exit(1);
            }
        } else {
            launch(args);
        }
    }
}
