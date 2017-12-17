package de.agentlab.sourcehog.indexer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractIndexer {

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

    protected abstract String[] getIncludeExt();

    protected abstract String[] getExcludeExt();

    protected abstract String[] getExcludeDirs();

    public abstract void indexFileContents(String filename, PrintStream out);
}
