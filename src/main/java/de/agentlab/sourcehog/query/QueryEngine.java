package de.agentlab.sourcehog.query;

import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.model.IndexEntry;
import de.agentlab.sourcehog.utils.CommonUtils;
import de.agentlab.sourcehog.utils.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

public class QueryEngine {

    public List<IndexEntry> find2(String query) {
        Configuration configuration = new Configuration();
        configuration.load();
        Log.info("find from " + configuration.getDatabaseDir());

        List<IndexEntry> result = new ArrayList<>();

        List<Path> files = this.getDbfilenames(configuration.getDatabaseDir());
        List<Callable<List<IndexEntry>>> callables = new ArrayList<>();

        for (Path file : files) {
            callables.add(() -> QueryEngine.this.find(query, Paths.get(configuration.getDatabaseDir(), file.getFileName().toString()).toString()));
        }

        Log.info("# db files " + callables.size());

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(callables.size());
        try {
            List<Future<List<IndexEntry>>> futures = executor.invokeAll(callables);
            for (Future<List<IndexEntry>> future : futures) {
                result.addAll(future.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return result;
    }

    private List<Path> getDbfilenames(String databaseDir) {
        try {
            List<Path> files = Files.walk(Paths.get(databaseDir)).filter(f -> f.getFileName().toString().startsWith("sourcehog.db")).collect(Collectors.toList());
            return files;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<IndexEntry> find(String query, String database) {

        String[] split = query.split("\\s+");

        List<IndexEntry> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(database));
            String line;
            String curfile = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("_SH")) {
                    curfile = line.substring(11);
                } else {
                    StringTokenizer st = new StringTokenizer(line, "\t", false);

                    String[] dbfields = new String[]{st.nextToken(), st.nextToken()};
                    if (CommonUtils.containsIgnoreCase(dbfields[0], split[0])) {

                        IndexEntry indexEntry = new IndexEntry(dbfields[0], curfile, dbfields[1]);
                        result.add(indexEntry);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.stream().filter(entry -> {
            if (split.length == 1) {
                return true;
            } else if (split.length == 2) {
                return CommonUtils.containsIgnoreCase(entry.getFile(), split[1]);
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }
}
