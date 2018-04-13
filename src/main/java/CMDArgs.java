import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;

public class CMDArgs {

    @Option(name = "-l", usage = "switch output to long format")
    public boolean longFormat;

    @Option(name = "-h", usage = "switch output to human-readable format")
    public boolean humanReadable;

    @Option(name = "-r", usage = "reverse output")
    public boolean reverse;

    @Option(name = "-o", usage = "output to this file", metaVar = "OUTPUT")
    public String out;

    @Argument()
    public String dir;

    public CMDArgs(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);

        try {
            parser.parseArgument(args);

            if (dir.isEmpty()) throw new CmdLineException(parser, "No argument is given"); // ???


        } catch (CmdLineException e) {
            System.err.println("invalid input");
            return;
        }

        if (longFormat) System.out.println("-l is set");
        if (humanReadable) System.out.println("-h is set");
        if (reverse) System.out.println("-r is set");
        if (out != null) System.out.println("-o file is set");
    }
}
