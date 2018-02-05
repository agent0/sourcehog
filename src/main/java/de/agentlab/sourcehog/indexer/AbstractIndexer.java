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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void index2(String outdirname, int maxFileSize, String... dirs) {
        try {
            String curOutfilename = getCurFilename(outdirname);
            Path path = Paths.get(outdirname, curOutfilename);

            PrintStream out = new PrintStream(new FileOutputStream(path.toFile(), true));
            for (String dir : dirs) {
                List<Path> files = Files.walk(Paths.get(dir))
                        .filter(f -> accept(f)).filter(f -> Files.isRegularFile(f))
                        .collect(Collectors.toList());

                for (Path fileToIndex : files) {
                    if (Files.exists(path)) {
                        long size = Files.size(path);
                        if (size > maxFileSize) {
                            out.close();
                            path = Paths.get(outdirname, this.getNextFilename(outdirname));
                            out = new PrintStream(new FileOutputStream(path.toFile(), true));
                        }
                    }
                    this.indexFileContents(fileToIndex.toString(), out);
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCurFilename(String outdirname) {
        return this.getDbFilename(outdirname, 0);
    }

    private String getNextFilename(String outdirname) {
        return this.getDbFilename(outdirname, 1);
    }

    private String getDbFilename(String outdirname, int offset) {
        Path path = Paths.get(outdirname);
        try {
            long count = Files.walk(path).filter(file -> file.getFileName().toString().startsWith("sourcehog.db")).count();
            if (count == 0) {
                count = 1;
            }
            return "sourcehog.db." + (count + offset);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
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
