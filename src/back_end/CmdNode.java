package back_end;

import back_end.command.Command;
import back_end.command.CommandFactory;
import back_end.command.CustomCommand;
import back_end.command.ListCommand;
import back_end.command.user_defined.VariableCommand;
import back_end.props_parser_utils.ClassNameParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CmdNode {
    //could be either a cmd node or a simple number or delimiter

    private static final String numPattern="-?[0-9]+\\.?[0-9]*";
    private static final String CMDPattern="[a-zA-Z_]+(\\?)?";
    private static final String VarPattern=":[a-zA-Z_]+";
    private static final String customClassName="CustomCommand";

    private String CmdType;

    private double Param;
    private boolean isHead =false;
    private boolean isVar =false;//might not exist
    private boolean likelyCMD =false;//might be user defined cmd or not
    private boolean isConst=false;
    private boolean isDelimiter=false;
    private boolean isUnlimitedDelimiter=false;
    private boolean isMakingCustom=false;
    private boolean isCustom=false;

    private int neededParamNum=0;
    private int neededListNum=0;

    private ArrayList<CmdNode> ChildNodes;
    private Command command;
    //which also contains a list of child cmds

    private CommandFactory factory=new CommandFactory();

    private CmdNode(String cmd_type){
//        System.out.println(cmd_type);
        ChildNodes=new ArrayList<>();
        CmdType=cmd_type;
        //which also determines if it's delimiter
        checkProperties(cmd_type);
        processCmd();
    }


    private void processCmd(){
        if (isConst){
            Param=Double.parseDouble(CmdType);
//                    Integer.parseInt(cmd_type);
            isHead=true;
            //this is a value cmd, make it
            command=factory.makeValueCMD(Param);
        } else if (isVar){
            isHead=true;
            command=factory.makeVariableCMD(CmdType);
        }
//        else if (isMakingCustom){
//
//        }
        else if (!CmdType.equals("List")){
            //neither constant nor variable, likely cmd
            try {
                neededParamNum = factory.getArgsNum(CmdType);
                neededListNum = factory.getListsNum(CmdType);
            }catch (Exception e){
//                e.printStackTrace();
                System.out.println("Not recognized "+CmdType+" but fine");
            }
        }
        if (neededParamNum+neededListNum==0){
            isHead=true;
        }
    }

    //didn't really need the param since cmdtype already global
    //need refactoring
    private void checkProperties(String cmd_type){
        //needs implementation
        isConst=cmd_type.matches(numPattern);
        isDelimiter=cmd_type.equals("[")|cmd_type.equals("]");
        isUnlimitedDelimiter=cmd_type.equals("(")|cmd_type.equals(")");

        isMakingCustom=cmd_type.equals("to");
        try {
            isCustom = ClassNameParser.getClassName(cmd_type).equals(customClassName);
        }catch (Exception e){
            //not a custom commadns
        }
        likelyCMD =cmd_type.matches(CMDPattern);
//        System.out.println(cmd_type+" is command? "+ likelyCMD);
        isVar = cmd_type.matches(VarPattern);
//        System.out.println(cmd_type);

    }

    private void addChilds(Iterator<String> iter){
        if (isMakingCustom){
            CmdNode customname=new CmdNode(iter.next());
            customname.command=new VariableCommand(customname.CmdType);
            this.addHead(customname);
        }
        while (ChildNodes.size()<neededParamNum){
//            System.out.println(this.CmdType);
            this.addHead(new CmdNode(iter));
        }
        int numoflists=0;
//        System.out.println(CmdType+"after add param children size "+ChildNodes.size());
        while (numoflists<neededListNum){
            this.addHead(addList(iter));
            numoflists+=1;
        }

        while(!(this.isHead |this.isDelimiter)){
//            neededParamNum=getArgsNum();
//            System.out.println("adding heads for "+CmdType);
            this.addHead(new CmdNode(iter));
        }
    }

    /**
     * Constructor that takes the string of the first command string
     * and also a iterator that contains the rest of the command
     * @param firstnode
     * @param iter
     */
    public CmdNode(String firstnode, Iterator<String> iter){
        this(firstnode);

        addChilds(iter);
        if (command==null) {
            makeCommand();
        }
    }

    /**
     * -A CmdNode constructor that takes a iterator
     * Parse recursively until returning a head node
     * -Head node means cmdnode that contains a complete
     * command object that satisfies the parsing rules
     * @param iter of the commands string array
     */
    public CmdNode(Iterator<String> iter){
        this(iter.next());

        //not implemented yet
        if (isUnlimitedDelimiter){
            this.addHead(addUnlimList(iter));
        }

        addChilds(iter);

        if (command==null) {
            makeCommand();
        }
        if (isMakingCustom){
            List<Command> args=command.getArguments();
            String commandname=((VariableCommand) args.get(0)).getVariableName();
            int paramnum=args.get(1).getArguments().size();
//            System.out.println("command added is "+commandname+" number of param needed is "+paramnum);
            factory.addCustomRules(commandname,paramnum);
        }
    }


    private void makeCommand(){
        ArrayList<Command> childrencommands=new ArrayList<>();
        for (CmdNode c:ChildNodes) {
            childrencommands.add(c.command);
        }

        if (neededParamNum+neededListNum==0&&!isCustom){
            command = factory.makeNoArgCMD(CmdType);
        }else {
            if (isCustom){
                command=new CustomCommand(childrencommands,CmdType);
            }else {
                command = factory.makeARGSCMD(CmdType, childrencommands);
            }
        }

//            System.out.println("command made for "+ CmdType + " is "+command);
    }

    /**
     *
     * @return the command in this node
     */
    public Command getCommand(){
        return command;
    }

    //to be implemented
    private CmdNode addUnlimList(Iterator<String> iter){

        return null;
    }

    /**
     * parse the commands in the list
     * @param iter of the command string iterator
     * @return a cmdnode that contains a listcommand type
     * which has all commands inside the list
     * assume the parsing succeed or some exception happens
     */
    private CmdNode addList(Iterator<String> iter){
        ArrayList<CmdNode> cmdnodes=new ArrayList<>();
        ArrayList<Command> commands=new ArrayList<>();
        if (!iter.next().equals("[")){
            throw new IllegalArgumentException("Expecting delimiter for cmd "+this.CmdType);
        }
        String nextcmd=iter.next();
        while (!nextcmd.matches("]")){
//            System.out.println("adding cmd "+curnode.getCmdType());
            CmdNode cmdnode=new CmdNode(nextcmd,iter);
            cmdnodes.add(cmdnode);
            commands.add(cmdnode.command);
            /*
            cmdinbracket.add(nextcmd);
            String s=null;
            try {
                s = iter.next();
            } catch (Exception e){
                System.out.println("String s is "+s);
                throw new RuntimeException("Add list failed");
            }
            */
//            nextcmd=s;
            nextcmd=iter.next();
        }

        CmdNode cmdinside=new CmdNode("List");
        cmdinside.command=new ListCommand(commands);

        return cmdinside;
    }

    /**
     * 1. adding head to this node that are
     * recursively constructed elsewhere
     * 2. also check if the command rules is satisfied
     * if satisfied, this command node is considered head
     * 3. Assumed adding only one head at a time
     * changed to greater to solve a parsing issue
     * @param next_node
     */
    private void addHead(CmdNode next_node){
        ChildNodes.add(next_node);
//        Param=next_node.Param;

        if (ChildNodes.size()>=neededParamNum+neededListNum){
            isHead =true;
        }
        //can make head here
    }

    /**
     *
     * @return a list of children nodes for this command node
     */
    public List<CmdNode> getChilds(){
        return ChildNodes;
    }


    /**
     *
     * @return the command syntax string such as fd, rt
     */
    public String getCmdType(){
        return CmdType;
    }

    /**
     *
     * @return String that represents the cmd structure
     * not recommended to use anymore since the command side implement it
     */
    public String toString(){
        String s=new String();
        if (CmdType.matches("-?[0-9]+\\.?[0-9]*")){
            return Double.toString(Param);
//            return Integer.toString(Param);
        }
        if (isDelimiter){
            return CmdType;
        }
        for (CmdNode n:ChildNodes) {
            s=s+" "+n.toString();
        }
        return CmdType+s;
    }

}
