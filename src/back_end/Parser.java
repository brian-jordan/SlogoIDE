package back_end;

import back_end.command.Command;
import back_end.command.ListCommand;
import back_end.command.ValueCommand;
import back_end.command.user_defined.VariableCommand;
import back_end.props_parser_utils.ClassNameParser;
import back_end.props_parser_utils.ConstructorParser;
import back_end.props_parser_utils.NumArgsParser;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class Parser {

    /**
     * Default constructor
     */
    public Parser() {
//        NumArgsParser.init();
//        ConstructorParser.init();
//        ClassNameParser.init("English");
//        cmdlist=new ArrayList<>();
        //temporary
//        Command valcmd=new ValueCommand(10);
//        dum_cmdlist.add(valcmd);
    }

    /**
     * Parse the commands and return a list of command heads
     * the command heads have tree structure
     * -expect the comment lines already been eliminated
     * @param text
     * @return List of command
     * @throws RuntimeException
     */
    public List<Command> parse(String[] text) throws RuntimeException{

        ArrayList<CmdNode> cmdlist=new ArrayList<>();
//        cmdlist.clear();
        Iterator<String> iter = Arrays.stream(text).iterator();
        while (iter.hasNext()){
//            System.out.println(iter.next());
            CmdNode currentHead = new CmdNode(iter);
            cmdlist.add(currentHead);
        }
//        System.out.println(cmdlist.toString());
        return makeCommands(cmdlist.iterator());
    }

    private List<Command> makeCommands(Iterator<CmdNode> iter){
        ArrayList<Command> rtlist=new ArrayList<>();
        while (iter.hasNext()){
            CmdNode cmdnode=iter.next();
            Command cmd = cmdnode.getCommand();
            rtlist.add(cmd);
        }
        return rtlist;
    }


//    private void printreturncmd(ArrayList<Command> printcmdlist){
//        for (Command c:printcmdlist) {
//            System.out.println(c.getClass());
//        }
//    }

}
