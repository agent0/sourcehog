package de.agentlab.sourcehog.runner;

import de.agentlab.sourcehog.model.Configuration;

public class ExplorerRunner extends AbstractRunner {

    @Override
    public String[] getEnv() {
        return new String[]{};
    }

    @Override
    public String[] getCommandline(String... params) {
        Configuration configuration = new Configuration();
        configuration.load();

        return new String[]{"explorer.exe", "/select," + params[0]};
    }
}
