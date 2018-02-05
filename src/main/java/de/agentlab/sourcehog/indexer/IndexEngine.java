package de.agentlab.sourcehog.indexer;

import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.utils.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IndexEngine {

    public void index() {
        try {
            Configuration configuration = new Configuration();
            configuration.load();

            Log.info("indexing to database dir " + configuration.getDatabaseDir());

            if (Files.exists(Paths.get(configuration.getDatabaseDir()))) {
                this.deleteIndexFiles(configuration.getDatabaseDir());
            } else {
                Files.createDirectory(Paths.get(configuration.getDatabaseDir()));
            }
            new JavaIndexer().index(configuration.getDatabaseDir(), configuration.getMaxFileSize(), configuration.getSourcedirs());
            new JavascriptIndexer().index(configuration.getDatabaseDir(), configuration.getMaxFileSize(), configuration.getSourcedirs());
            new TypescriptIndexer().index(configuration.getDatabaseDir(), configuration.getMaxFileSize(), configuration.getSourcedirs());
            new CSSIndexer().index(configuration.getDatabaseDir(), configuration.getMaxFileSize(), configuration.getSourcedirs());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteIndexFiles(String databaseDir) {
        try {
            Files.walk(Paths.get(databaseDir)).forEach(p -> {
                if (p.getFileName().toString().startsWith("sourcehog.db.")) {
                    Log.info("delete " + p);
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