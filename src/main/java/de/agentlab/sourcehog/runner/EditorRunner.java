package de.agentlab.sourcehog.runner;

import java.io.IOException;
import java.util.List;

public class EditorRunner extends AbstractRunner {

    public static void main(String argv[]) throws IOException {
        List<String> result = new EditorRunner().run("d:/Projekte/T-Com/ANTKdroid/src/de/kdg/droid/ui/components/imagezoom/ImageViewTouchBase.java", "271");
        System.out.println(result.size());
    }

    @Override
    public String[] getEnv() {
        return new String[]{"path=%PATH%;C:/Software/Notepad++/"};
    }

    @Override
    public String[] getCommandline(String... params) {
        return new String[]{"C:/Software/Notepad++/notepad++.exe", "-multiInst", params[0], "-n" + params[1]};
    }
}
