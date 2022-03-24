package yams;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Objects;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParserException;

public class Main {
    public static void main(String[] args) {
        var argsParser = ArgumentParsers.newFor("YAMS").build()
                .defaultHelp(true)
                .description("Yet Another Microblogging Server");

        argsParser.addArgument("--publisherClient").action(Arguments.storeConst()).setConst(1);
        argsParser.addArgument("--followerClient").action(Arguments.storeConst()).setConst(1);
        argsParser.addArgument("--reposterClient").action(Arguments.storeConst()).setConst(1);

        argsParser.addArgument("--port", "-p")
                .type(Integer.class)
                .setDefault(12345);
        argsParser.addArgument("--host")
                .type(String.class)
                .setDefault("localhost");

        Map<String, Object> parsedArgs = new HashMap<>();
        try {
            argsParser.parseArgs(args, parsedArgs);
        } catch (ArgumentParserException e) {
            argsParser.handleError(e);
            System.exit(1);
        }
        Integer port = (Integer) parsedArgs.get("port");
        String host = (String) parsedArgs.get("host");
        boolean isPublisherClient = Objects.equal(1, parsedArgs.get("publisherClient"));
        boolean isFollowerClient = Objects.equal(1, parsedArgs.get("followerClient"));
        boolean isReposterClient = Objects.equal(1, parsedArgs.get("reposterClient"));

        if (isPublisherClient)
            PublisherClient.run(host, port);
        else if (isFollowerClient)
            FollowerClient.run(host, port);
        else if (isReposterClient)
            ReposterClient.run(host, port);
        else
            ServerMain.run(port);
    }
}
