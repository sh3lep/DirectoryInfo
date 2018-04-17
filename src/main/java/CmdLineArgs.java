import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

class CmdLineArgs {

    @Option(name = "-l", usage = "switch output to long format")
    boolean longFormat;

    @Option(name = "-h", usage = "switch output to human-readable format")
    boolean humanReadable;

    @Option(name = "-r", usage = "reverse output")
    boolean reverse;

    @Option(name = "-o", usage = "output to this file", metaVar = "OUTPUT")
    String out;

    @Argument()
    String dir;

    CmdLineArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

            if (dir.isEmpty()) {
                System.err.println("No argument is given"); // ???
                return;
            }

            if (!longFormat && humanReadable) {
                throw new CmdLineException("-h couldn't be set without -l");
            }

        } catch (CmdLineException e) {
            System.err.print("invalid input: ");
            System.err.println(e.getMessage());
            return;
        }

        if (longFormat) System.out.println("-l is set");
        if (humanReadable) System.out.println("-h is set");
        if (reverse) System.out.println("-r is set");
        if (out != null) System.out.println("-o file is set");
    }
}