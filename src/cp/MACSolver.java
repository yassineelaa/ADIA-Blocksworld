package cp;

import modelling.*;
import java.util.*;

/**
 * La classe MACSolver hérite de la classe AbstractSolver et utilise l'algorithme MAC pour résoudre un problème de satisfaction de contraintes (CSP).
 */
public class MACSolver extends AbstractSolver {

    /**
     * Constructeur de la classe.
     * @param variables   L'ensemble des variables du problème.
     * @param constraints L'ensemble des contraintes du problème.
     */
    public MACSolver(Set<Variable> variables, Set<Constraint> constraints) {
        super(variables, constraints);
    }

    /**
     * Méthode qui résout le CSP en utilisant l'algorithme MAC.
     * @return Une instanciation complète des variables satisfaisant toutes les contraintes, ou null si aucune solution n'est trouvée.
     */
    @Override
    public Map<Variable, Object> solve() {
        // Initialisation de l'instanciation partielle vide.
        Map<Variable, Object> partialInstantiation = new HashMap<>();
        // Liste des variables 
        LinkedList<Variable> listVariables = new LinkedList<>(variables);
        // Initialisation des domaines évolutifs avec une copie des domaines actuels des variables.
        Map<Variable, Set<Object>> evoDomain = new HashMap<>();
        for (Variable var : variables) {
            evoDomain.put(var, new HashSet<>(var.getDomain()));
        }
    
        return solverecMac(partialInstantiation, listVariables, evoDomain);
    }

    /**
     * Méthode récursive implémentant l'algorithme MAC.
     * @param partialInstantiation L'instanciation partielle 
     * @param listVariables        La liste des variables 
     * @param evoDomain            Les domaines évolutifs des variables.
     * @return Une instanciation complète satisfaisant toutes les contraintes, ou null si aucune solution n'est trouvée
     */
    private Map<Variable, Object> solverecMac(Map<Variable, Object> partialInstantiation,LinkedList<Variable> listVariables,Map<Variable, Set<Object>> evoDomain) {
        // Condition d'arrêt 
        if (listVariables.isEmpty()) {
            return new HashMap<>(partialInstantiation);
        }

        
        ArcConsistency arcConsistency = new ArcConsistency(constraints);

     
        if (!arcConsistency.ac1(evoDomain)) {
            return null;
        }

        Variable var = listVariables.pollFirst();

        for (Object value : var.getDomain()) {
            Map<Variable, Object> newInstantiation = new HashMap<>(partialInstantiation);
            newInstantiation.put(var, value);
            if (isConsistent(newInstantiation)) {
                
                Map<Variable, Object> result = solverecMac(newInstantiation, listVariables, evoDomain);

                if (result != null) {
                    return result;
                }
            }
        }

        listVariables.addFirst(var);

        return null;
    }

    
}


