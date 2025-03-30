package cp ;
import modelling.*;
import java.util.*;

public abstract class AbstractSolver implements Solver {

    Set<Variable> variables;
     
    Set<Constraint> constraints;

    public AbstractSolver(Set<Variable> variables, Set<Constraint> constraints){
        this.variables = variables;
        this.constraints = constraints;
    }

    public abstract Map<Variable, Object> solve();


    /**Cette méthode vérifie si une affectation partielle des variables est cohérente avec les contraintes. */
    public boolean isConsistent(Map<Variable, Object> partial) {
        for (Constraint constraint : this.constraints) {
            if (partial.keySet().containsAll(constraint.getScope())) {
                if (!constraint.isSatisfiedBy(partial)) {
                    return false;
                }
            }
        }
        return true;
    }


}

