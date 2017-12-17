package de.agentlab.sourcehog.indexer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractIndexer {

    public void index(String outfilename, String... dirs) {
        PrintStream out;
        try {

            if (outfilename == null) {
                out = System.out;
            } else {
                out = new PrintStream(new FileOutputStream(outfilename, true));
            }

            for (String dir : dirs) {
                Files.walk(Paths.get(dir))
                        .filter(f -> Files.isRegularFile(f) && f.toString().endsWith("java"))
                        .forEach(f -> this.indexFileContents(f.toAbsolutePath().toString(), out));
            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public abstract void indexFileContents(String filename, PrintStream out);
}
