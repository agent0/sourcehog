package de.agentlab.sourcehog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EditorRunner {

    public static void main(String argv[]) throws IOException {
        List<String> result = new EditorRunner().run("d:/Projekte/T-Com/ANTKdroid/src/de/kdg/droid/ui/components/imagezoom/ImageViewTouchBase.java", "271");
        System.out.println(result.size());
    }

    public List<String> run(String filename, String linenr) {

        List<String> result = new ArrayList<>();
        try {
            Runtime r = Runtime.getRuntime();
            Process p;
            BufferedReader is;
            String line;

            String[] env = new String[]{"path=%PATH%;C:/Software/Notepad++/"};
            p = r.exec(new String[]{"C:/Software/Notepad++/notepad++.exe", "-multiInst", filename, "-n" + linenr}, env);


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
