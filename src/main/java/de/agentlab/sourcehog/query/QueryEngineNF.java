package de.agentlab.sourcehog.query;

import de.agentlab.sourcehog.dialogs.main.CommonUtils;
import de.agentlab.sourcehog.model.CTag;
import de.agentlab.sourcehog.model.Configuration;
import de.agentlab.sourcehog.model.MultiValueMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryEngineNF {

    private static MultiValueMap<String, CTag> DATABASE;


    public static void main(String[] args) {
        List<CTag> result = new QueryEngineNF().find("zoom");
        System.out.println(result);
    }

    public List<CTag> find(String query) {
        Configuration configuration = new Configuration();
        configuration.load();

        String[] split = query.split("\\s+");

        List<CTag> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(configuration.getDatabase()));
            String line;
            String curfile = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("_SH")) {
                    curfile = line.substring(11);
                } else {
                    String[] dbfields = line.split("\\t");
                    if (CommonUtils.containsIgnoreCase(dbfields[0], split[0])) {

                        CTag cTag = new CTag(dbfields[0], curfile, dbfields[1]);
                        result.add(cTag);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result.stream().filter(ctag -> {
            if (split.length == 1) {
                return true;
            } else if (split.length == 2) {
                return CommonUtils.containsIgnoreCase(ctag.getFile(), split[1]);
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }

    public List<CTag> find_new(String query) {
        if (DATABASE == null) {
            DATABASE = new MultiValueMap<>();
            this.readDB();
        }
        List<CTag> result = new ArrayList<>();

        String[] split = query.split("\\s+");

        List<String> keys = new ArrayList<>(DATABASE.keySet());
        for (String key : keys) {
            if (CommonUtils.containsIgnoreCase(key, split[0])) {
                result.addAll(DATABASE.get(key));
            }

        }

        return result.stream().filter(ctag -> {
            if (split.length == 1) {
                return true;
            } else if (split.length == 2) {
                return CommonUtils.containsIgnoreCase(ctag.getFile(), split[1]);
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }


    public void readDB() {
        Configuration configuration = new Configuration();
        configuration.load();

        try {
            BufferedReader br = new BufferedReader(new FileReader(configuration.getDatabase()));
            String line;
            String curfile = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("_SH")) {
                    curfile = line.substring(11);
                } else {
                    String[] dbfields = line.split("\\t");
                    CTag cTag = new CTag(dbfields[0], curfile, dbfields[1]);

                    DATABASE.put(cTag.getTag(), cTag);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
