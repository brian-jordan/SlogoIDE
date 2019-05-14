package back_end;

import back_end.command.Command;
import back_end.command.CommandFactory;
import back_end.props_parser_utils.ClassNameParser;
import back_end.props_parser_utils.CommandType;
import back_end.props_parser_utils.ConstructorParser;
import back_end.props_parser_utils.NumArgsParser;

import java.lang.reflect.Constructor;
import java.util.List;

//a test main class for parsing
public class parserTestMain {

    public static void main (String[] args) {

        NumArgsParser.init();
        ConstructorParser.init();
        ClassNameParser.init("English");
//
//        try {
//            System.out.println("Testing classnameparser for fd: " + ClassNameParser.getClassName("fd"));
//            System.out.println("Testing numargsparser for Forward: " + NumArgsParser.getNumberOfArguments("Forward") +
//                    ", " + NumArgsParser.getNumberOfLists("Forward"));
//            System.out.println("Testing constructor parser for Forward...");
//            Constructor<?> constructor = ConstructorParser.getConstructor("Forward", CommandType.WITH_ARGS);
//            System.out.println("Succeeded in all!");
//        } catch (Exception e) {
//            System.out.println("Failed somewhere with: " + e.getMessage());
//        }


        CommandFactory cf=new CommandFactory();
//        System.out.println(cf.getArgsNum("rt"));
//        cf.getArgsNum("fd");
//        ArrayList<Command> dum_cmdlist=new ArrayList<>();
//        dum_cmdlist.add(new ValueCommand(0));
//        dum_cmdlist.add(new ValueCommand(0));
//        Constructor<Command> c=(Constructor<Command>) CommandStringParser.getCommandFromString("make",CommandStringParser.CommandType.NO_ARGS);
//        Command cmd, cmd2;
//        try {
//            cmd=c.newInstance();//
//            cmd2=c.newInstance();
//            System.out.println(cmd.getNumberOfArguments());
//        }catch (Exception e){
//            e.printStackTrace();
//            throw new RuntimeException("Can't find command");
//        }
//        System.out.println("is equal? "+cmd.equal(cmd2));


//        String examples = "fd fd fd 50";
//        String examples = "repeat 360 [ fd 50 rt 1 ]";
        //test for repeat
//        String examples = "fd 50 repeat 50 [ fd 50 rt 50 ]";
        //test for sp
//        String examples = "goto fd 50 rt 50 if 50 [ fd 50 ]";
//        String examples = "make :x 10";
//        String examples = "fd :x fd fd 5";
//        String examples = "ifelse 1 [ fd 5 ] [ fd 5 ]";
//        String examples = "for [ :x 1 100 1 ] [ fd 50 rt 1 ]";
//        String examples = "dotimes [ :x 1 100 1 ] [ fd 50 rt 1 ]";
//        String examples = "rt 1";
//        String examples = "repeat 5 [ repeat 5 [ fd 50 ] ]";
//        String examples="repeat 11 [ dotimes [ :t 360 ] [ fd 1 rt / sin :t 2 ] ]";
        String examples="to xx [ ] [ fd :a ]";
        String[] text= examples.split(" ");
        System.out.println("number of text entry is "+text.length);



        String cuscmd="xx";
        String[] custext=cuscmd.split(" ");
        // set up the parser
        var parser = new Parser();

        List<Command> cmdlist=parser.parse(text);
        System.out.println("root cmd is "+cmdlist);
        System.out.println("Number of cmd node is "+cmdlist.size());

        cf.getArgsNum("xx");

        for (Command cm:cmdlist) {
            System.out.println(cm.toString());
//            System.out.println("Number of arguments is "+cm.getArguments().size());
        }


        cmdlist=parser.parse(custext);
        System.out.println("root cmd is "+cmdlist);
        System.out.println("Number of cmd node is "+cmdlist.size());

        for (Command cm:cmdlist) {
            System.out.println(cm.toString());
//            System.out.println("Number of arguments is "+cm.getArguments().size());
        }


//        System.out.println("repeat command has arguments: "+cmdlist.get(1).getArguments());
//        System.out.println("repeat command's listcmd has arguments: "+cmdlist.get(1).getArguments().get(1).getArguments());
        //want to see inside to verify. But can't unless running it.
        //still hard even if running it

//        ArrayList<CmdNode> parsedcmdlist=parser.parse(text);
//
//        System.out.println("Head node numer is "+parsedcmdlist.size());
//        parser.makeCommand();
//


    }
}
