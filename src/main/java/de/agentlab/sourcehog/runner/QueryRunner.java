package de.agentlab.sourcehog.runner;

import de.agentlab.sourcehog.model.Configuration;

import java.io.IOException;
import java.util.List;

public class QueryRunner extends AbstractRunner {

    private Configuration configuration = new Configuration();

    public static void main(String argv[]) throws IOException {
        List<String> result = new QueryRunner().run();
        System.out.println(result.size());
    }

    @Override
    public String[] getEnv() {
        return new String[]{"path=%PATH%;C:/Software/bin/;C:/Users/jli/.babun/cygwin/bin/"};
    }

    @Override
    public String[] getCommandline(String... params) {
        return new String[]{"C:/Users/jli/.babun/cygwin/bin/bash.exe", "-c", "hog find " + this.configuration.getDatabase() + " " + params[0]};
    }
}
