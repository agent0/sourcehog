package de.agentlab.sourcehog;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringLiteralIndexer {

    public static void main(String[] args) {
        StringLiteralIndexer stringLiteralIndexer = new StringLiteralIndexer();

        try {
            Files.walk(Paths.get("D:\\Programming\\Java\\sourcehog\\src\\main\\java\\de\\agentlab\\sourcehog\\"))
                    .filter(f -> f.toString().endsWith("java"))
                    .forEach(f -> stringLiteralIndexer.index(f.toAbsolutePath().toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void index(String filename) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 0;
                while ((line = br.readLine()) != null) {

                    Pattern p = Pattern.compile("\"([^\"]*)\"");
                    Matcher m = p.matcher(line);
                    while (m.find()) {
                        String text = m.group(1);
                        String[] split = text.split("\\s+");
                        for (String s : split) {
                            if (s.length() > 2) {
                                System.out.println(s + "\t" + filename + "\t" + index);
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
