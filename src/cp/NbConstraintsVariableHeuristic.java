package cp;

import modelling.*;
import java.util.*;

/**
 * La classe NbConstraintsVariableHeuristic implémente l'interface VariableHeuristic et fournit une heuristique basée sur le nombre de contraintes liées à chaque variable.
 */
public class NbConstraintsVariableHeuristic implements VariableHeuristic {

    private Set<Constraint> constraints;
    // Détermine si l'on choisit la variable avec le maximum (true) ou le minimum (false) de contraintes
    private boolean choseConstraints;

    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean choseConstraints) {
        this.constraints = constraints;
        this.choseConstraints = choseConstraints;
    }

    /**
     * Sélectionne la variable ayant le maximum ou le minimum de contraintes en fonction du paramètre choseConstraints
     *
     * @param variables l'ensemble des variables parmi lesquelles choisir
     * @param variableDomains   l'ensemble des domaines des variables 
     * @return la variable choisie en fonction du nombre de contraintes qui la lient
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> variableDomains) {
        int constraintCount = choseConstraints ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        Variable selectedVariable = null;

        for (Variable var : variables) {
            int numConstraints = 0;
            for (Constraint constraint : this.constraints) {
                if (constraint.getScope().contains(var)) {
                    numConstraints++;
                }
            }

            if (choseConstraints) {
                if (numConstraints > constraintCount) {
                    selectedVariable = var;
                    constraintCount = numConstraints;
                }
            } else {
                if (numConstraints < constraintCount) {
                    selectedVariable = var;
                    constraintCount = numConstraints;
                }
            }
        }
        return selectedVariable;
    }
}


