package de.agentlab.sourcehog.query;

import de.agentlab.sourcehog.dialogs.main.CommonUtils;
import de.agentlab.sourcehog.model.CTag;
import de.agentlab.sourcehog.model.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QueryEngine {

    public List<CTag> find(String query) {
        Configuration configuration = new Configuration();
        configuration.load();

        String[] split = query.split("\\s+");

        List<CTag> result = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(configuration.getDatabase()));
            String line;
            while ((line = br.readLine()) != null) {
                String[] dbfields = line.split("\\t");
                if (CommonUtils.containsIgnoreCase(dbfields[0], split[0])) {

                    CTag cTag = new CTag(dbfields[0], dbfields[1], sanitize(dbfields[2]));
                    result.add(cTag);
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

    private String sanitize(String s) {
        if (s.contains(";")) {
            return s.substring(0, s.length() - 2);
        } else {
            return s;
        }
    }
}
