package cp ;
import modelling.*;
import java.util.*;


public interface VariableHeuristic{

    Variable best(Set<Variable> Variables, Map<Variable, Set<Object>> Domaines);
}