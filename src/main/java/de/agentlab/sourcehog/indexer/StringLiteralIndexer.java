package de.agentlab.sourcehog.indexer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLiteralIndexer extends AbstractIndexer {

    public static final String LITERAL_REGEX = "\"([^\"]*)\"";

    public static void main(String[] args) {
        StringLiteralIndexer stringLiteralIndexer = new StringLiteralIndexer();
        stringLiteralIndexer.index(null, args);
    }

    @Override
    protected String getExtension() {
        return "java";
    }

    public void indexFileContents(String filename, PrintStream out) {
//        System.out.println("Indexing " + filename);
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 1;
                while ((line = br.readLine()) != null) {

                    Pattern p = Pattern.compile(LITERAL_REGEX);
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
