package back_end;

import back_end.command.Command;
import javafx.scene.paint.Color;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.ParserTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.*;

public class CommandStringParser {
    private static Map<String, String> cache = new HashMap<>();

    public enum CommandType {
        WITH_ARGS, WITH_VALUE, NO_ARGS
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
            System.out.println(className);
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

//    //will be implemented later
//    public static int getCommandArgsNum(String cmdString){
//        File inputFile = new File(COMMAND_TRANSLATION_PATH);
//        Element rootNode = ParserTools.getRootNode(inputFile);
//
//        NodeList commands = rootNode.getElementsByTagName("Command");
//        for(int i = 0; i < commands.getLength(); i++) {
//            Node node = commands.item(i);
//            String key = ((Element) node).getAttribute("str");
//            String cls = ((Element) node).getAttribute("className");
//
//            if (key.equals(cmdString)) {
//                className = cls;
//            }
//        }
//    }

    private static String propertiesFileName = "data/config/English.properties";

    public static void setPropertiesFileName(String propertiesFileName) {
        cache.clear(); // Forget about our old language now
        CommandStringParser.propertiesFileName = propertiesFileName;
    }

    public static String getClassName(String cmdString){
        if (!cache.containsKey(cmdString)) {
            getCommandFromString(cmdString,CommandType.NO_ARGS);
        }
        return cache.get(cmdString);
    }

    public static Constructor<?> getCommandFromString(String cmdString, CommandType type) throws RuntimeException{
        String className = null;
        cmdString = cmdString.toLowerCase();
        if (cache.containsKey(cmdString)) {
            className = cache.get(cmdString);
        } else {

            File file = new File(propertiesFileName);
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (!line.startsWith("#") && !line.isEmpty() && !line.isBlank()) {
                        String parts[] = line.split(" = ");
                        String clazz = "back_end.command."+parts[0];
                        String cmdStrings[] = parts[1].split("\\|");
                        for(String cc : cmdStrings) {
                            // System.out.println(cc+" "+clazz);
                            if(cc.equals(cmdString)) {
                                className = clazz;
                            }
                            cache.put(cc.replaceAll("\\\\", ""), clazz);
                        }
                    }
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (className == null) {
            throw (new RuntimeException("Can't find command "+cmdString));
        } else {
            if (type == CommandType.WITH_ARGS) {
                return makeCtorFromClassName(className);
            } else if (type == CommandType.WITH_VALUE){
                return makeCtorFromClassNameWithValue(className);
            } else if (type == CommandType.NO_ARGS){
                return makeCtorFromClassNameWithNoArgs(className);
            }else {
                throw (new RuntimeException("Unknown command type"));
            }
        }
    }
}
