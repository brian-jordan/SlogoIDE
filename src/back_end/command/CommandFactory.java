package back_end.command;

//import back_end.CommandStringParser;
import back_end.command.user_defined.VariableCommand;
import back_end.props_parser_utils.ClassNameParser;
import back_end.props_parser_utils.CommandType;
import back_end.props_parser_utils.ConstructorParser;
import back_end.props_parser_utils.NumArgsParser;

import java.lang.reflect.Constructor;
import java.util.*;

public class CommandFactory {

    private static final String customClassName="CustomCommand";

    public CommandFactory(){

    }


    /**
     * add rules for a custom command
     * @param cmdname
     * @param num_args
     */
    public void addCustomRules(String cmdname, int num_args){

        ClassNameParser.addClassName(cmdname);
//        ConstructorParser
        NumArgsParser.addNumberOfArguments(cmdname,num_args);

    }


    /**
     * make a variable command object
     * @param varname
     * @return the command
     */
    public Command makeVariableCMD(String varname){
        return new VariableCommand(varname);
    }

    /**
     * make a command with no argument
     * not recommended to use since this bypass command constructor's checking
     * @param cmdtype
     * @return command
     */
    public Command makeNoArgCMD(String cmdtype){
        String commandname=ClassNameParser.getClassName(cmdtype);
        Constructor<Command> c = (Constructor<Command>) ConstructorParser.getConstructor(commandname, CommandType.NO_ARGS);

        try {
            Command cmd=c.newInstance();//
            return cmd;
        }catch (Exception e){
            //e.printStackTrace();
            throw new RuntimeException("Can't find command "+cmdtype);
        }

    }

    /**
     * make a command with argument
     * @param cmdtype
     * @param lc list of children commands
     * @return the command object
     */
    public Command makeARGSCMD(String cmdtype, List<Command> lc){

        String commandname=ClassNameParser.getClassName(cmdtype);
        Constructor<Command> c = (Constructor<Command>) ConstructorParser.getConstructor(commandname, CommandType.WITH_ARGS);

        try {
            Command cmd=c.newInstance(lc);
//            System.out.println("constructor for "+cmdtype+" is "+c);
//            System.out.println("cmd made by factory for "+cmdtype+" is "+cmd);
            return cmd;
        }catch (Exception e){
            //e.printStackTrace();
            throw new RuntimeException("Can't find command "+cmdtype);
        }
    }

    /**
     * make a value command object
     * @param value
     * @return
     */
    public Command makeValueCMD(double value){
        Command valcmd=new ValueCommand(value);
        return valcmd;
    }

    /**
     * get a command name, such as fd, rt and return the number of lists for that command
     * @param cmdtype
     * @return number of lists
     */
    public int getListsNum(String cmdtype){
        String commandname=ClassNameParser.getClassName(cmdtype);
        if (commandname.equals(customClassName)){
            return NumArgsParser.getNumberOfLists(cmdtype);
        }else {
            return NumArgsParser.getNumberOfLists(commandname);
        }
    }

    /**
     * get a command name, such as fd, rt and return the number of arguments for that command
     * @param cmdtype
     * @return number of arguments
     */
    public int getArgsNum(String cmdtype){
        String commandname=ClassNameParser.getClassName(cmdtype);
        if (commandname.equals(customClassName)){
//            System.out.println("for "+cmdtype+" it's a custom command");
//            System.out.println("returning "+NumArgsParser.getNumberOfArguments(cmdtype));
            return NumArgsParser.getNumberOfArguments(cmdtype);
        }else {
            return NumArgsParser.getNumberOfArguments(commandname);
        }
    }


}
