package back_end.props_parser_utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Author: Anshu Dwibhashi
 * Parser to return constructors from classnames
 */
public class ConstructorParser {
    private static Map<String, String> packageNameClass; // e.g. Forward -> turtle_basic.

    private static boolean init = false;

    private static final String customClassName="CustomCommand";
    private static final String customPackage="";
    public static void init(){
        init = true;
        packageNameClass = new HashMap<>();

        packageNameClass.put(customClassName,customPackage);

        String propertiesFileName = "data/config/Package.properties";
        File file = new File(propertiesFileName);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (!line.startsWith("#") && !line.isEmpty() && !line.isBlank()) {
                    String[] parts = line.split("=");
                    if (parts.length == 2) {
                        packageNameClass.put(parts[0], parts[1]);
                    } else {
                        packageNameClass.put(parts[0], "");
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
            throw (new RuntimeException("Haven't initialised parser yet. Make sure you've called ConstructorParser.init() before you use it."));
        }
        if (! packageNameClass.containsKey(command)) {
            throw (new RuntimeException("Command not found"));
        }
    }

    private static Constructor<?> makeCtorFromClassNameWithNoArgs(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.getConstructor();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Constructor<?> makeCtorFromClassName(String className) {
        try {
            Class<?> clazz = Class.forName(className);

            return clazz.getConstructor(List.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Constructor<?> makeCtorFromClassNameWithValue(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return clazz.getConstructor(double.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Fetch a class's constructor
     * @param command String representing a command like "Forward"
     * @return Constructor for the class
     * @throws RuntimeException If you haven't initialised the parser, or if the command doesn't exist
     */
    public static Constructor<?> getConstructor(String command, CommandType type) throws RuntimeException {
        checks(command);
        String className = "back_end.command."+packageNameClass.get(command) + command;
        if (type == CommandType.WITH_ARGS) {
            return makeCtorFromClassName(className);
        } else if (type == CommandType.WITH_VALUE){
            return makeCtorFromClassNameWithValue(className);
        } else if (type == CommandType.NO_ARGS){
            return makeCtorFromClassNameWithNoArgs(className);
        }
        return null;
    }

//    public static void addCons
}
