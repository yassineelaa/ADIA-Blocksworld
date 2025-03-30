package blocksworld;

import modelling.*;
import planning.*;
import java.util.*;

/**
 * Cette heuristique compte le nombre de blocs qui ne sont pas dans leur position finale souhaitée.
 *  Chaque bloc mal placé nécessite au moins un mouvement pour être correctement positionné
 */
public class BwHeuristique1 implements Heuristic {

   
    private final Goal goal;

    
    public BwHeuristique1(Goal goal) {
        this.goal = goal;
    }
    
    @Override
public float estimate(Map<Variable, Object> state) {
    float count = 0;
    System.out.println("Appel de l'heuristique pour un nouvel état.");

    for (Variable var : this.goal.getGoal().keySet()) {
        if (var.getName().startsWith("on ")) {
            Object goalValue = this.goal.getGoal().get(var);
            Object stateValue = state.get(var);

            if (!state.containsKey(var)) {
                System.out.println("  Variable " + var.getName() + " n'est pas présente dans l'état.");
                count++;
            } else {
                System.out.println("Variable : " + var.getName());
                System.out.println("  Valeur dans l'état : " + stateValue);
                System.out.println("  Valeur dans l'objectif : " + goalValue);

                if (!stateValue.equals(goalValue)) {
                    count++;
                    System.out.println("  Bloc " + var.getName() + " mal placé. Incrémentation de l'heuristique.");
                } else {
                    System.out.println("  Bloc " + var.getName() + " bien placé.");
                }
            }
        }
    }

    System.out.println("Valeur heuristique pour l'état actuel : " + count);
    return count;
}


}