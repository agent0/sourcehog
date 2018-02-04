package de.agentlab.sourcehog.indexer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class JavascriptIndexer extends AbstractIndexer {

    private static String[] keywords = new String[]{"abstract", "arguments", "await*", "boolean", "break", "byte", "case", "catch", "char", "class*", "const", "continue", "debugger", "default", "delete", "do", "double", "else", "enum*", "eval", "export*", "extends*", "false", "final", "finally", "float", "for", "function", "goto", "if", "implements", "import*", "in", "instanceof", "int", "interface", "let*", "long", "native", "new", "null", "package", "private", "protected", "public", "return", "short", "static", "super*", "switch", "synchronized", "this", "throw", "throws", "transient", "true", "try", "typeof", "var", "void", "volatile", "while", "with", "yield"};
    private static Map<String, String> keywordMap = new HashMap<>();

    static {
        Arrays.stream(keywords).forEach(k -> keywordMap.put(k, k));
    }

    public String[] getExcludeDirs() {
        return exclude_dirs;
    }

    public String[] getExcludeExt() {
        return new String[]{"min.js"};
    }

    public String[] getIncludeExt() {
        return new String[]{"js"};
    }

    @Override
    protected boolean skip(String line) {
        return false;
    }

    @Override
    protected String getSanitationRegex() {
        return "[^A-Za-z0-9_]";
    }

    @Override
    protected Map<String, String> getKeywordMap() {
        return keywordMap;
    }
}
