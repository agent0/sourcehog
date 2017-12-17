package de.agentlab.sourcehog.indexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CSSIndexer extends AbstractIndexer {

    private static String[] exclude_dirs = new String[]{".svn", ".git", ".idea", "lib", "build", "target", "branches", "node_modules", "dist", "tmp", "src-gen", "minified"};
    private static String[] exclude_ext = new String[]{};
    private static String[] include_ext = new String[]{"css"};

    private static String[] keywords = new String[]{};
    private static Map<String, String> keywordMap = new HashMap<>();

    static {
        Arrays.stream(keywords).forEach(k -> keywordMap.put(k, k));
    }

    public static void main(String[] args) {
        new CSSIndexer().index(null, "D:\\Projekte\\BMW\\AIDA\\Anwendung\\aida-project");
    }

    public String[] getExcludeExt() {
        return exclude_ext;
    }

    public String[] getExcludeDirs() {
        return exclude_dirs;
    }

    public String[] getIncludeExt() {
        return include_ext;
    }

    public void indexFileContents(String filename, PrintStream out) {
        out.println("_" + "SH" + "__FILE__" + filename);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 0;

                while ((line = br.readLine()) != null) {
                    index++;

                    if (!line.contains("{")) {
                        continue;
                    }
                    String sanitized = line.replaceAll("[^A-Za-z0-9_]", " ");

                    if (line.trim().length() == 0) {
                        continue;
                    }

                    Set<String> tags = new HashSet<>();

                    String[] split = sanitized.split("\\s+");
                    for (String s : split) {
                        if (keywordMap.containsKey(s)) {
                            continue;
                        }
                        if (s.length() > 3) {
                            tags.add(s);
                        }
                    }
                    for (String tag : tags) {
                        out.println(tag + "\t" + index);

                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
