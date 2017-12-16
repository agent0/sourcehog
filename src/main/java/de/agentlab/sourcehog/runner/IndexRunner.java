package de.agentlab.sourcehog.runner;

import java.io.IOException;
import java.util.List;

public class IndexRunner extends AbstractRunner {

    public static void main(String argv[]) throws IOException {
        List<String> result = new IndexRunner().run("d:/tmp/java.ctags", "d:/Programming/Java", "d:/Programming/Javascript");
        System.out.println(result);
    }

    @Override
    public String[] getEnv() {
        return new String[]{"path=%PATH%;C:/Software/bin/;C:/Users/jli/.babun/cygwin/bin/"};
    }

    @Override
    public String[] getCommandline(String... params) {
        String dirs = "";
        for (int i = 1; i < params.length; i++) {
            dirs += params[i] + " ";
        }
        return new String[]{"C:/Users/jli/.babun/cygwin/bin/bash.exe", "-c", "hog index " + params[0] + " " + dirs};
    }
}
