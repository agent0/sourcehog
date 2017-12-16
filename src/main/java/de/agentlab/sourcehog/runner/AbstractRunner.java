package de.agentlab.sourcehog.runner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRunner {

    public List<String> run(String... params) {

        List<String> result = new ArrayList<>();
        try {
            Runtime r = Runtime.getRuntime();
            Process p;
            BufferedReader is;
            String line;

            p = r.exec(getCommandline(params), getEnv());

            is = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = is.readLine()) != null) {
                result.add(line);
            }

            p.waitFor();

        } catch (Exception e) {
            System.err.println(e);
            return result;
        }
        return result;
    }

    public abstract String[] getEnv();

    public abstract String[] getCommandline(String... params);
}
