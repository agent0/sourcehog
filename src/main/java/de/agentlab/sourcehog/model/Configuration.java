package de.agentlab.sourcehog.model;

import de.agentlab.sourcehog.utils.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Configuration {
    private String databaseDir;
    private int maxFileSize;
    private String[] sourcedirs;
    private String editorpath;

    public Configuration() {
        this.databaseDir = "d:/tmp/sourcehog";
        this.sourcedirs = new String[]{"d:/Programming/Java", "d:/Programming/Javascript"};
    }

    public String getDatabaseDir() {
        return databaseDir;
    }

    public void setDatabaseDir(String databaseDir) {
        this.databaseDir = databaseDir;
    }

    public int getMaxFileSize() {
        return maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize) {
        this.maxFileSize = maxFileSize;
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
            File f;
            String config = System.getProperty("sourcehog.config", System.getProperty("user.home") + "\\sourcehog.properties");
            Log.info("Loading properties from " + config);
            f = new File(config);
            is = new FileInputStream(f);
            props.load(is);
        } catch (Exception e) {
            is = null;
        }

        this.setDatabaseDir(props.getProperty("database_dir", System.getProperty("user.home") + "\\sourcehog"));
        this.setMaxFileSize(Integer.parseInt(props.getProperty("max_file_size", "10000000")));
        this.setSourceDirsFromString(props.getProperty("sourcedirs"));
        this.setEditorpath(props.getProperty("editorpath", ""));
    }

    public void save() {
        try {
            Properties props = new Properties();
            props.setProperty("database_dir", this.databaseDir);
            props.setProperty("max_file_size", String.valueOf(this.maxFileSize));
            props.setProperty("sourcedirs", this.getSourceDirsAsString());
            props.setProperty("editorpath", this.getEditorpath());

            String config = System.getProperty("sourcehog.config", System.getProperty("user.home") + "\\sourcehog.properties");
            File f = new File(config);
            OutputStream out = new FileOutputStream(f);
            props.store(out, "saved on " + new SimpleDateFormat().format(new Date()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
