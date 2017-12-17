package de.agentlab.sourcehog.indexer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavaIndexer extends AbstractIndexer {

    private static String[] keywords = new String[]{"abstract", "continue", "for", "new", "switch", "assert", "default", "goto", "package", "synchronized", "boolean", "do", "if", "private", "this", "break", "double", "implements", "protected", "throw", "byte", "else", "import", "public", "throws", "case", "enum", "instanceof", "return", "transient", "catch", "extends", "int", "short", "try", "char", "final", "interface", "static", "void", "class", "finally", "long", "strictfp", "volatile", "const", "float", "native", "super", "while"};
    private static Map<String, String> keywordMap = new HashMap<>();

    static {
        Arrays.stream(keywords).forEach(k -> keywordMap.put(k, k));
    }

    @Override
    public String[] getExcludeDirs() {
        return exclude_dirs;
    }

    @Override
    public String[] getExcludeExt() {
        return new String[]{};
    }

    @Override
    public String[] getIncludeExt() {
        return new String[]{"java"};
    }

    @Override
    protected boolean skip(String line) {
        return line.startsWith("package") || line.startsWith("import");
    }

    @Override
    protected String getSanitationRegex() {
        return "[^A-Za-z0-9_]";
    }

    @Override
    protected Map<String, String> getKeywordMap() {
        return keywordMap;
    }

    public static void main(String[] args) {
        new JavaIndexer().index(null, "D:\\Programming\\Java\\sourcehog\\src\\main\\java\\de\\agentlab\\sourcehog");
    }
}
