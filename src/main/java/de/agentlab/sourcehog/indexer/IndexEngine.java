package de.agentlab.sourcehog.indexer;

import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.utils.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IndexEngine {

    public void index2() {
        Configuration configuration = new Configuration();
        configuration.load();

        Log.info("indexing to database dir " + configuration.getDatabaseDir());
        this.deleteIndexFiles(configuration.getDatabaseDir());
        new JavaIndexer().index2(configuration.getDatabaseDir(), configuration.getSourcedirs());
        new JavascriptIndexer().index2(configuration.getDatabaseDir(), configuration.getSourcedirs());
        new TypescriptIndexer().index2(configuration.getDatabaseDir(), configuration.getSourcedirs());
        new CSSIndexer().index2(configuration.getDatabaseDir(), configuration.getSourcedirs());
    }

    private void deleteIndexFiles(String databaseDir) {
        try {
            Files.walk(Paths.get(databaseDir)).forEach(p -> {
                if (p.getFileName().toString().startsWith("sourcehog.db.")) {
                    System.out.println("delete " + p);
                    try {
                        Files.delete(p);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}