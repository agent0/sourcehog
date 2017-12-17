package de.agentlab.sourcehog.query;

import de.agentlab.sourcehog.utils.CommonUtils;
import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.model.IndexEntry;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class QueryEngine {

    public static void main(String[] args) {
        List<IndexEntry> result = new QueryEngine().find("zoom");
        System.out.println(result);
    }

    public List<IndexEntry> find(String query) {
        Configuration configuration = new Configuration();
        configuration.load();

        String[] split = query.split("\\s+");

        List<IndexEntry> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(configuration.getDatabase()));
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
