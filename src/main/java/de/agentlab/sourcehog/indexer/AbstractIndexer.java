package de.agentlab.sourcehog.indexer;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractIndexer {
    protected static String[] exclude_dirs = new String[]{".svn", ".git", ".idea", "lib", "build", "target", "branches", "node_modules", "bower_components", "dist", "tmp", "src-gen", "minified"};

    protected boolean accept(Path f) {
        if (excludePath(f.toString())) {
            return false;
        }
        if (excludeExt(f.toString())) {
            return false;
        }
        if (includeExt(f.toString())) {
            return true;
        }
        return false;
    }

    private boolean excludeExt(String path) {
        String[] ext = getExcludeExt();

        for (int i = 0; i < ext.length; i++) {
            if (path.endsWith(ext[i])) {
                return true;
            }
        }
        return false;
    }


    private boolean includeExt(String path) {
        String[] ext = getIncludeExt();

        for (int i = 0; i < ext.length; i++) {
            if (path.endsWith(ext[i])) {
                return true;
            }
        }
        return false;
    }

    private boolean excludePath(String path) {
        String[] dirs = getExcludeDirs();
        for (int i = 0; i < dirs.length; i++) {
            if (path.contains("/" + dirs[i] + "/")) {
                return true;
            }
            if (path.contains("\\" + dirs[i] + "\\")) {
                return true;
            }
        }
        return false;
    }


    public void index(String outfilename, String... dirs) {
        PrintStream out;
        try {

            if (outfilename != null) {
                out = new PrintStream(new FileOutputStream(outfilename, true));
            } else {
                out = System.out;
            }
            for (String dir : dirs) {
                Files.walk(Paths.get(dir))
                        .filter(f -> accept(f)).filter(f -> Files.isRegularFile(f))
                        .forEach(f -> this.indexFileContents(f.toAbsolutePath().toString(), out));
            }
            if (out != null) {
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexFileContents(String filename, PrintStream out) {
        out.println("_" + "SH" + "__FILE__" + filename);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 0;

                while ((line = br.readLine()) != null) {
                    index++;

                    if (skip(line)) {
                        continue;
                    }

                    String sanitized = line.replaceAll(getSanitationRegex(), " ");

                    if (line.trim().length() == 0) {
                        continue;
                    }

                    Set<String> tags = new HashSet<>();

                    String[] split = sanitized.split("\\s+");
                    for (String s : split) {
                        if (getKeywordMap().containsKey(s)) {
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


    protected abstract String[] getIncludeExt();

    protected abstract String[] getExcludeExt();

    protected abstract String[] getExcludeDirs();

    protected abstract boolean skip(String line);

    protected abstract String getSanitationRegex();

    protected abstract Map<String, String> getKeywordMap();


}
