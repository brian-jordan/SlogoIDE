package back_end;

import java.util.HashMap;
import java.util.Set;

public class ParsingRules {

    //expr is equivalent to variable, command or constant
    public enum ArgType {
        VARIABLE, COMMAND, CONSTANT, LIST, EXPR
    }
    private Set<ArgType> options;
    //assumption rules are not loaded
    private HashMap<ArgType,ParsingRules> rules;

    public ParsingRules(){
        rules=new HashMap<>();
        //not sure if the keyset is modifiable
        options=rules.keySet();
    }

    //making end
    public ParsingRules(ArgType lastrule){

    }

    //null indicating end
    public void addLastRules(ArgType lastrule){
        rules.put(lastrule,null);
    }
}
