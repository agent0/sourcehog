package de.agentlab.sourcehog.indexer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TypescriptIndexer extends AbstractIndexer {

    private static String[] keywords = new String[]{"break", "case", "catch", "class", "const", "continue", "debugger", "default", "delete", "do", "else", "enum", "export", "extends", "false", "finally", "for", "function", "if", "import", "in", "instanceof", "new", "null", "return", "super", "switch", "this", "throw", "true", "try", "typeof", "var", "void", "while", "with", "as", "implements", "interface", "let", "package", "private", "protected", "public", "static", "yield", "any", "boolean", "constructor", "declare", "get", "module", "require", "number", "set", "string", "symbol", "type", "from", "of"};
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
        return new String[]{"ts"};
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

    public static void main(String[] args) {
        new TypescriptIndexer().index(null, "d:\\Programming\\Java\\Sandbox");
    }
}
