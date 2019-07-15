/**
 * @author: Amith Ramanagar Chandrashekar
 */

package main.java.commandparser;

import com.beust.jcommander.JCommander;


/**
 * Command Parser which reads the args and returns the config class which holds all the
 * values
 */
public class CommandParser {

    private JCommander parse = null;
    private Config config = null;
    private String[] args = null;



    public CommandParser(String  ... args)
    {
        config = new Config();
        this.args = args;
        parse = createParser();
        config.setParser(parse);
    }

    private JCommander createParser()
    {
        if(parse == null)
        {
            parse = JCommander.newBuilder()
                    .addObject(config)
                    .build();
            try
            {
                parse.parse(args);
            }catch (com.beust.jcommander.ParameterException pe)
            {
                pe.getMessage();
                pe.usage();
            }

        }
        return parse;
    }

    public Config getConfig() {
        return config;
    }


}
