package de.agentlab.sourcehog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QueryRunner {

    public static void main(String argv[]) throws IOException {
        List<String> result = new QueryRunner().run("zoomTo");
        System.out.println(result.size());
    }

    public List<String> run(String term) {

        List<String> result = new ArrayList<>();
        try {
            Runtime r = Runtime.getRuntime();
            Process p;
            BufferedReader is;
            String line;

            String[] env = new String[]{"path=%PATH%;C:/Software/bin/;C:/Users/jli/.babun/cygwin/bin/"};
//        p = r.exec(new String[]{"C:\\Users\\jli\\.babun\\cygwin\\bin\\ls.exe", "-la"});
            p = r.exec(new String[]{"C:/Users/jli/.babun/cygwin/bin/bash.exe", "-c", "hog find " + term}, env);


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
}
