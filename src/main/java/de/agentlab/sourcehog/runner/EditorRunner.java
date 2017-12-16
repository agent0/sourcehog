package de.agentlab.sourcehog.runner;

import de.agentlab.sourcehog.model.Configuration;

public class EditorRunner extends AbstractRunner {

    @Override
    public String[] getEnv() {
        return new String[]{};
    }

    @Override
    public String[] getCommandline(String... params) {
        Configuration configuration = new Configuration();
        configuration.load();

        return new String[]{configuration.getEditorpath(), "-multiInst", params[0], "-n" + params[1]};
    }
}
