package de.agentlab.sourcehog.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
    private String database;
    private String[] sourcedirs;
    private String editorpath;

    public Configuration() {
        this.database = "d:/tmp/sourcehog.db";
        this.sourcedirs = new String[]{"d:/Programming/Java", "d:/Programming/Javascript"};
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String[] getSourcedirs() {
        return sourcedirs;
    }

    public void setSourcedirs(String[] sourcedirs) {
        this.sourcedirs = sourcedirs;
    }

    public String getSourceDirsAsString() {
        return String.join(";", this.getSourcedirs());
    }

    public void setSourceDirsFromString(String dirs) {
        if (dirs != null) {
            this.sourcedirs = dirs.split("\\s*;\\s*");
        }
    }

    public String getEditorpath() {
        return editorpath;
    }

    public void setEditorpath(String editorpath) {
        this.editorpath = editorpath;
    }

    public void load() {
        Properties props = new Properties();
        InputStream is = null;

        try {
            File f = new File(System.getProperty("user.home") + "\\sourcehog.properties");
            is = new FileInputStream(f);
            props.load(is);
        } catch (Exception e) {
            is = null;
        }

        this.setDatabase(props.getProperty("database", System.getProperty("user.home") + "\\sourcehog.db"));
        this.setSourceDirsFromString(props.getProperty("sourcedirs"));
        this.setEditorpath(props.getProperty("editorpath", ""));
    }

    public void save() {
        try {
            Properties props = new Properties();
            props.setProperty("database", this.database);
            props.setProperty("sourcedirs", this.getSourceDirsAsString());
            props.setProperty("editorpath", this.getEditorpath());
            File f = new File(System.getProperty("user.home") + "\\sourcehog.properties");
            OutputStream out = new FileOutputStream(f);
            props.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
