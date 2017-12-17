package de.agentlab.sourcehog.model;

import javafx.beans.property.SimpleStringProperty;

public class IndexEntry {

    private final SimpleStringProperty tagProperty = new SimpleStringProperty("");
    private final SimpleStringProperty fileProperty = new SimpleStringProperty("");
    private final SimpleStringProperty lineProperty = new SimpleStringProperty("");

    public IndexEntry() {
        this("", "", "");
    }

    public IndexEntry(String tag, String file, String line) {
        setTag(tag);
        setFile(file);
        setLine(line);
    }

    public IndexEntry(String line) {
        String[] split = line.split(" ");
        setTag(split[0]);
        setFile(split[1]);
        setLine(split[2]);
    }

    public String getTag() {
        return tagProperty.get();
    }

    public void setTag(String tag) {
        this.tagProperty.set(tag);
    }

    public String getFile() {
        return fileProperty.get();
    }

    public void setFile(String file) {
        this.fileProperty.set(file);
    }

    public String getLine() {
        return lineProperty.get();
    }

    public void setLine(String file) {
        this.lineProperty.set(file);
    }

    @Override
    public String toString() {
        return "IndexEntry{" +
                "tag=" + tagProperty.get() +
                ", file=" + fileProperty.get() +
                ", line=" + lineProperty.get() +
                '}';
    }
}
