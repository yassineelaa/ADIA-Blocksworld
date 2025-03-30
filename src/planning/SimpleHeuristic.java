package planning;

import java.util.Map;
import modelling.Variable;

public class SimpleHeuristic implements Heuristic {

    @Override
    public float estimate(Map<Variable, Object> currentState) {
        /* Implémentation simple : retourne une estimation de coût heuristique
         Par ex ici on peut calculer un coût basé sur une simple règle */

        float heuristicValue = 0;

        for (Object value : currentState.values()) {
            if (value instanceof Number) {
                heuristicValue += ((Number) value).floatValue();
            }
        }

        // Retourne l'estimation heuristique
        return heuristicValue;
    }
}
