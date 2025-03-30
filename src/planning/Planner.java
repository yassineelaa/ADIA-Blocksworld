package planning ;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelling.*;


public interface Planner {

    List<Action>  plan();

    Map<Variable, Object> getInitialState();

    Set<Action> getActions();

    Goal getGoal();

    void activateNodeCount(boolean activate);

    int getNodeCount();
}