package de.agentlab.sourcehog.dialogs.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Sourcehog");
        primaryStage.setScene(new Scene(root, 800, 400));
        primaryStage.show();

        MainController controller = loader.getController();
        controller.setStage(primaryStage);
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            String command = args[0];
            if (command.equals("index")) {
                System.out.println("index");

            } else if (command.equals("lindex")) {

            } else if (command.equals("find")) {

            }
        } else {
            launch(args);
        }
    }
}
