package de.agentlab.sourcehog.runner;

import de.agentlab.sourcehog.model.Configuration;

import java.io.IOException;
import java.util.List;

public class QueryRunner extends AbstractRunner {


    public static void main(String argv[]) throws IOException {
        List<String> result = new QueryRunner().run();
        System.out.println(result.size());
    }

    @Override
    public String[] getEnv() {
        Configuration configuration = new Configuration();
        configuration.load();

        return new String[]{"path=%PATH%;" + configuration.getHogdir() + ";" + configuration.getCygwindir()};
    }

    @Override
    public String[] getCommandline(String... params) {
        Configuration configuration = new Configuration();
        configuration.load();

        return new String[]{configuration.getCygwindir() + "/bash.exe", "-c", "hog find " + configuration.getDatabase() + " " + params[0]};
    }
}
