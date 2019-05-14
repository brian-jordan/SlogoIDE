package back_end.props_parser_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: Anshu Dwibhashi
 * Parser to read from Language.properties
 */
public class ClassNameParser {
    private static Map<String, String> commandNameCache; // e.g. fd -> Forward

    private static boolean init = false;

    public static Collection<String> getAvailableCommands() throws RuntimeException {
        if (!init) {
            throw (new RuntimeException("Haven't initialised parser yet. Make sure you've called ClassNameParser.init() before you use it."));
        }
        return commandNameCache.keySet();
    }

    /**
     * Initialise parser with a language
     * @param language language like "English" or "French"
     */
    public static void init(String language) throws RuntimeException{
        init = true;
        commandNameCache = new HashMap<>();

        String propertiesFileName = "data/config/"+language+".properties";
        File file = new File(propertiesFileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.startsWith("#") && !line.isEmpty() && !line.isBlank()) {
                    String[] parts = line.split(" = ");
                    String[] names = parts[1].split("\\|");
                    for (String name : names) {
                        commandNameCache.put(name.replaceAll("\\\\", "").toLowerCase(), parts[0]);
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw (new RuntimeException("Unknown language"));
        }

    }

    private static void checks(String command) throws RuntimeException {
        if (! init) {
            throw (new RuntimeException("Haven't initialised parser yet. Make sure you've called ClassNameParser.init() before you use it."));
        }
        if (! commandNameCache.containsKey(command)) {
            throw (new RuntimeException("Command "+ command +" not found"));
        }
    }

    /**
     * Find out what a command's classname is
     * @param command String representing a command like "Forward"
     * @return Integer representing the number of arguments it takes like 1
     * @throws RuntimeException If you haven't initialised the parser, or if the command doesn't exist
     */
    public static String getClassName(String command) throws RuntimeException {
        checks(command);
        return commandNameCache.get(command.toLowerCase());
    }


    private static final String customClassName="CustomCommand";
    public static void addClassName(String command) throws RuntimeException {
        System.out.println();
        commandNameCache.put(command,customClassName);
        checks(command);
//        return commandNameCache.get(command.toLowerCase());
    }

}
