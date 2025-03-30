package planning;
import modelling.*;
import java.util.Map;
import java.util.Objects;


public interface Action {
    boolean isApplicable(Map<Variable,Object> state);

    Map<Variable, Object> successor(Map<Variable, Object> state);

    int getCost();


}