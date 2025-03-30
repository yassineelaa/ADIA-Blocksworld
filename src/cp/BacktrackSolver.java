package cp ;
import modelling.*;
import java.util.*;


public class BacktrackSolver extends AbstractSolver {

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Résout le problème en utilisant une approche de backtracking.
     * @return une Map représentant une instanciation des variables qui satisfait toutes les contraintes, ou null si aucune solution n'est trouvée.
     */

    @Override
    public Map<Variable, Object> solve() {
        // Créer une instanciation partielle vide
        Map<Variable, Object> instantiation = new HashMap<>();
        // Convertir l'ensemble des variables non instanciées en LinkedList
        LinkedList<Variable> setVariables = new LinkedList<>(variables);

        // Appeler la fonction BT avec l'instanciation vide et les variables non instanciées
        return BT(instantiation, setVariables);
    }
    
    /**
     * Fonction récursive BT (Backtracking) pour rechercher une solution en explorant les instanciations possibles.
     * 
     * @param instantiationPartielle une Map représentant l'instanciation partielle des variables.
     * @param setVariable une liste de variables non encore instanciées.
     * @return une Map représentant une instanciation complète des variables satisfaisant toutes les contraintes, ou null si aucune solution n'est trouvée.
     */
    private Map<Variable, Object> BT(Map<Variable, Object> instantiationPartielle, LinkedList<Variable> setVariable) {
        // Condition d'arrêt : si toutes les variables sont instanciées
        if (setVariable.isEmpty()) {
            return instantiationPartielle;
        }

        
        Variable xi = setVariable.removeFirst();

        for (Object vi : xi.getDomain()) {
            
            Map<Variable, Object> N = new HashMap<>(instantiationPartielle);
            N.put(xi, vi);

            // Vérifier si l'instanciation est cohérente
            if (isConsistent(N)) {
                // Appel récursif avec la nouvelle instanciation et les variables restantes
                Map<Variable, Object> R = BT(N, setVariable);

                if (R != null) {
                    return R; // Solution trouvée
                }
            }
            
        }

        // Remettre xi dans la liste des variables non instanciées
        setVariable.addFirst(xi);

        return null; // Aucune solution trouvée avec cette instanciation
    }


    
}
