package cp;

import modelling.*;
import java.util.*;

/**
 * La classe HeuristicMACSolver étend AbstractSolver et utilise des heuristiques pour résoudre un CSP en suivant l'algorithme MAC.
 */
public class HeuristicMACSolver extends AbstractSolver {
    private VariableHeuristic variableHeuristic;// Heuristique de choix des variables
    
    private ValueHeuristic valueHeuristic;// Heuristique de choix des valeurs     

    /**
     * Constructeur de la classe
     *
     * @param variables          L'ensemble des variables du problème
     * @param constraints        L'ensemble des contraintes du problème
     * @param variableHeuristic  L'heuristique de choix des variables
     * @param valueHeuristic     L'heuristique de choix des valeurs
     */
    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints,
                              VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    /**
     * Méthode qui résout le CSP en utilisant l'algorithme MAC avec des heuristiques.
     *
     * @return Une instanciation des variables du problème, ou null si aucune solution n'est trouvée
     */
    @Override
    public Map<Variable, Object> solve() {
        // Initialisation de l'instanciation partielle
        Map<Variable, Object> instanciationParielle = new HashMap<>();

        // liste variable
        LinkedList<Variable> ListVariables = new LinkedList<>(variables);

        // Suivre l'évolution des domaines 
        Map<Variable, Set<Object>> evoDomains = new HashMap<>();
        for (Variable var : variables) {
            evoDomains.put(var, new HashSet<>(var.getDomain()));
        }

        return MAC(instanciationParielle, ListVariables, evoDomains);
    }

    /**
     * Fonction récursive implémentant l'algorithme MAC avec heuristiques.
     *
     * @param instanciationParielle      L'instanciation partielle actuelle
     * @param listVariables  Les variables 
     * @param evoDomains Les domaines évolutifs des variables
     * @return Une instanciation des variables du problème, ou null si aucune solution n'est trouvée
     */
    private Map<Variable, Object> MAC(Map<Variable, Object> instanciationParielle,LinkedList<Variable> listVariables, Map<Variable, Set<Object>> evoDomains) {
        // Conditions d'arrêt de la récursivité
        if (listVariables.isEmpty()) {
            return instanciationParielle;
        } else {
            // Réduction des domaines des variables par l'arc-cohérence
            ArcConsistency ac = new ArcConsistency(constraints);
            boolean isAC = ac.ac1(evoDomains);

            if (!isAC) {
                return null;
            }

            // Choisir une variable non encore instanciée
            Variable var = variableHeuristic.best(new HashSet<>(listVariables), evoDomains);

            for (Object vi : valueHeuristic.ordering(var, evoDomains.get(var))) {
                // Créer une nouvelle instanciation 
                Map<Variable, Object> newInstanciation = new HashMap<>(instanciationParielle);
                newInstanciation.put(var, vi);

                // Vérifier si N est consistant
                if (isConsistent(newInstanciation)) {
                    
                    LinkedList<Variable> newListVariables = new LinkedList<>(listVariables);

                    
                    newListVariables.remove(var);

                    // Appeler récursive
                    Map<Variable, Object> result = MAC(newInstanciation, newListVariables, evoDomains);

                    if (result != null) {
                        return result;
                    }
                }
            }

            return null;
        }
    }

    
}


