package planning;
import modelling.*;
import java.util.Map;
import java.util.Objects;


public interface Goal {


    boolean isSatisfiedBy( Map<Variable,Object> state);
    Map<Variable, Object> getGoal();
    
}


