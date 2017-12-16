package de.agentlab.sourcehog;

public class Commandline {

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("  usage: command params");
            System.exit(-1);
        }
        String command = args[0];
        if (command.equals("index")) {

        } else if (command.equals("lindex")) {

        } else if (command.equals("find")) {

        } else {
            System.out.println("unknown command '" + command + "'");
        }
    }
}
