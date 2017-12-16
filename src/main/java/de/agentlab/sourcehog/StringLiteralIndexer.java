package de.agentlab.sourcehog;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLiteralIndexer {

    public static void main(String[] args) {
        StringLiteralIndexer stringLiteralIndexer = new StringLiteralIndexer();
        stringLiteralIndexer.index(null, args);
    }

    public void index(String outfilename, String... dirs) {
        StringLiteralIndexer stringLiteralIndexer = new StringLiteralIndexer();
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
                        .forEach(f -> stringLiteralIndexer.indexFileContents(f.toAbsolutePath().toString(), out));
            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void indexFileContents(String filename, PrintStream out) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 1;
                while ((line = br.readLine()) != null) {

                    Pattern p = Pattern.compile("\"([^\"]*)\"");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        String text = m.group(1);
                        String[] split = text.split("\\s+");
                        for (String s : split) {
                            if (s.length() > 2) {
                                out.println(s + "\t" + filename + "\t" + index);
                            }
                        }
                    }
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
