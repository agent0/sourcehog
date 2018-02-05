package de.agentlab.sourcehog.ui.configuration;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ConfigurationPopupController {
    @FXML
    public TextField dbDir;

    @FXML
    public TextField dirs;

    @FXML
    public TextField editorpath;

    public String getDbDirValue() {
        return this.dbDir.getText();
    }

    public String getDirsValue() {
        return this.dirs.getText();
    }

    public String getEditorpathValue() {
        return this.editorpath.getText();
    }

    public void setDbDirValue(String value) {
        this.dbDir.setText(value);
    }

    public void setDirsValue(String value) {
        this.dirs.setText(value);
    }

    public void setEditorpathValue(String value) {
        this.editorpath.setText(value);
    }

}
