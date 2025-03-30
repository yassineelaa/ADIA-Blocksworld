package modelling;
import java.util.*;


public class BooleanVariable extends Variable {

    public BooleanVariable(String name) {
        super(name, getBooleanDomain());
    }

    private static Set<Object> getBooleanDomain() {
        Set<Object> booleanDomain = new HashSet<>();
        booleanDomain.add(true);
        booleanDomain.add(false);
        return booleanDomain;
    }


}