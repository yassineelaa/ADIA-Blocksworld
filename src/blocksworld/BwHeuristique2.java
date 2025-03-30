package blocksworld;

import modelling.*;
import planning.*;
import java.util.*;

/**
 * Cette heuristique compte le nombre de piles dont le statut "libre" ne correspond pas à l'état final souhaité.
 * Chaque pile mal configurée nécessite au moins une action pour être corrigée.
 */
public class BwHeuristique2 implements Heuristic {

    private final Goal goal;

    public BwHeuristique2(Goal goal) {
        this.goal = goal;
    }

    @Override
    public float estimate(Map<Variable, Object> state) {
        float count = 0;

        // Parcours des variables "free" présentes dans l'état actuel
        for (Variable var : state.keySet()) {
            if (var.getName().startsWith("free ")) {
                Object stateValue = state.get(var);
                Object goalValue = this.goal.getGoal().get(var);

                // Si l'objectif ne spécifie pas de valeur pour cette pile, on suppose qu'elle doit être false
                if (goalValue == null) {
                    goalValue = Boolean.FALSE;
                }

                if (!stateValue.equals(goalValue)) {
                    count++;
                    System.out.println("Pile " + var.getName() + " mal configurée. Incrémentation de l'heuristique.");
                } else {
                    System.out.println("Pile " + var.getName() + " bien configurée.");
                }
            }
        }


        System.out.println("Valeur heuristique pour l'état actuel : " + count);
        return count;
    }
}
