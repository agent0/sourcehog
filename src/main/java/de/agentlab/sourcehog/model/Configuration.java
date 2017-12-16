package de.agentlab.sourcehog.model;

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
}
