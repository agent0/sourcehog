package de.agentlab.sourcehog.runner;

import de.agentlab.sourcehog.model.Configuration;

public class IndexRunner extends AbstractRunner {

    @Override
    public String[] getEnv() {
        Configuration configuration = new Configuration();
        configuration.load();

        return new String[]{"path=%PATH%;" + configuration.getHogdir() + ";" + configuration.getCygwindir()};
    }

    @Override
    public String[] getCommandline(String... params) {
        String dirs = "";
        for (int i = 1; i < params.length; i++) {
            dirs += params[i] + " ";
        }

        Configuration configuration = new Configuration();
        configuration.load();

        return new String[]{configuration.getCygwindir() + "/bash.exe", "-c", "hog index " + params[0] + " " + dirs};
    }
}
