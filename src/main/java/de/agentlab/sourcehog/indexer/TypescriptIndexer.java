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

public class TypescriptIndexer extends AbstractIndexer {

    private static String[] exclude_dirs = new String[]{".svn", ".git", ".idea", "lib", "build", "target", "branches", "node_modules", "dist", "tmp", "src-gen", "minified"};
    private static String[] exclude_ext = new String[]{};
    private static String[] include_ext = new String[]{"ts"};

    private static String[] keywords = new String[]{"break", "case", "catch", "class", "const", "continue", "debugger", "default", "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for", "function", "if", "import", "in", "instanceof", "new", "null", "return", "super", "switch", "this", "throw", "true", "try", "typeof", "var", "void", "while", "with", "as", "implements", "interface", "let", "package", "private", "protected", "public", "static", "yield", "any", "boolean", "constructor", "declare", "get", "module", "require", "number", "set", "string", "symbol", "type", "from", "of"};
    private static Map<String, String> keywordMap = new HashMap<>();

    static {
        Arrays.stream(keywords).forEach(k -> keywordMap.put(k, k));
    }

    public static void main(String[] args) {
        new TypescriptIndexer().index(null, "d:\\Programming\\Java\\Sandbox");
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
        if (out != null) {
            out.println("_" + "SH" + "__FILE__" + filename);
        }
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                int index = 0;

                while ((line = br.readLine()) != null) {
                    index++;

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
                        if (out != null) {
                            out.println(tag + "\t" + index);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
