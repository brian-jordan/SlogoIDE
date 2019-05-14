package back_end.props_parser_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: Anshu Dwibhashi
 * Parser to read from NumberOfArguments.properties
 */
public class NumArgsParser {
    private static Map<String, Integer> numArgsCache; // e.g. Forward -> 1
    private static Map<String, Integer> numListsCache; // e.g. Forward -> 0

    private static boolean init = false;

    public static void init() {
        init = true;
        numArgsCache = new HashMap<>();
        numListsCache = new HashMap<>();

        String propertiesFileName = "data/config/NumberOfArguments.properties";
        File file = new File(propertiesFileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.startsWith("#") && !line.isEmpty() && !line.isBlank()) {
                    String[] parts = line.split("=");
                    String[] args = parts[1].split(",");
                    numArgsCache.put(parts[0], Integer.valueOf(args[0]));
                    numListsCache.put(parts[0], Integer.valueOf(args[1]));
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void checks(String command) throws RuntimeException {
        if (! init) {
            throw (new RuntimeException("Haven't initialised parser yet. Make sure you've called NumArgsParser.init() before you use it."));
        }
        if (! numArgsCache.containsKey(command)) {
            throw (new RuntimeException("Command not found"));
        }
    }

    /**
     * Find out how many arguments a command takes
     * @param command String representing a command like "Forward"
     * @return Integer representing the number of arguments it takes like 1
     * @throws RuntimeException If you haven't initialised the parser, or if the command doesn't exist
     */
    public static int getNumberOfArguments(String command) throws RuntimeException {
        checks(command);
        return numArgsCache.get(command);
    }

    /**
     * Find out how many lists a command takes
     * @param command String representing a command like "Forward"
     * @return Integer representing the number of lists it takes like 0
     * @throws RuntimeException If you haven't initialised the parser, or if the command doesn't exist
     */
    public static int getNumberOfLists(String command) throws RuntimeException {
        checks(command);
        return numListsCache.get(command);
    }

    /**
     * add rules for a custom command
     * @param command String representing a custom command like "xx"
     * @param numargs number of arguments
     * @return nothing
     */
    public static void addNumberOfArguments(String command, int numargs){
//        System.out.println("Num args parser adding number of args for "+command+" number needed "+numargs);
        numArgsCache.put(command,numargs);
        numListsCache.put(command,0);
    }

}
