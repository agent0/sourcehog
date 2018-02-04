package de.agentlab.sourcehog.ui.indexing;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IndexActionPopupController {
    @FXML
    public TextField dbDir;

    @FXML
    public TextField dirs;

    public String getDbDirValue() {
        return this.dbDir.getText();
    }

    public String getDirsValue() {
        return this.dirs.getText();
    }

    public void setDbDirValue(String value) {
        this.dbDir.setText(value);
    }

    public void setDirsValue(String value) {
        this.dirs.setText(value);
    }
}
