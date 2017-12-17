package de.agentlab.sourcehog.ui.indexing;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class IndexActionPopupController {
    @FXML
    public TextField dbfile;

    @FXML
    public TextField dirs;

    public String getDbFileValue() {
        return this.dbfile.getText();
    }

    public String getDirsValue() {
        return this.dirs.getText();
    }

    public void setDbFileValue(String value) {
        this.dbfile.setText(value);
    }

    public void setDirsValue(String value) {
        this.dirs.setText(value);
    }
}
