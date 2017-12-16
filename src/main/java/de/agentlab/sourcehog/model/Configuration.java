package de.agentlab.sourcehog.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Configuration {
    private String database;
    private String[] sourceDirs;

    public Configuration() {
        this.database = "d:/tmp/sourcehog.db";
        this.sourceDirs = new String[]{"d:/Programming/Java", "d:/Programming/Javascript"};
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String[] getSourceDirs() {
        return sourceDirs;
    }

    public void setSourceDirs(String[] sourceDirs) {
        this.sourceDirs = sourceDirs;
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

        this.database = props.getProperty("database", System.getProperty("user.home") + "\\sourcehog.db");
        this.setSourceDirsFromString(props.getProperty("sourcedirs"));
    }

    public void save() {
        try {
            Properties props = new Properties();
            props.setProperty("database", this.database);
            props.setProperty("sourcedirs", this.getSourceDirsAsString());
            File f = new File(System.getProperty("user.home") + "\\sourcehog.properties");
            OutputStream out = new FileOutputStream(f);
            props.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getSourceDirsAsString() {
        return String.join(";", this.getSourceDirs());
    }

    public void setSourceDirsFromString(String dirs) {
        if (dirs != null) {
            this.sourceDirs = dirs.split("\\s*;\\s*");
        }
    }
}
