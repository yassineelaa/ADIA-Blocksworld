package planning;
import modelling.*;
import java.util.*;


/**
  Cette classe implémente un planificateur basé sur l'algorithme de recherche en largeur (BFS). Elle génère un plan en explorant les états possibles à partir d'un état initial pour atteindre un objectif donné.
 */

public class BFSPlanner implements Planner {
    private Map<Variable, Object> initial_State;  // État initial
    private Set<Action> set_Actions;         // Ensemble des actions
    private Goal goal;            // Objectif

    private boolean active;     // Variable pour gérer l'état actif du comptage des nœuds
    private boolean countNodes; // Pour activer/désactiver le comptage des nœuds
    private int nodeCount ; // Compteur de nœuds explorés

    public BFSPlanner(Map<Variable, Object> initial_State, Set<Action> set_Actions, Goal goal){
        this.initial_State = initial_State;
        this.set_Actions = set_Actions;
        this.goal = goal;
        this.nodeCount = 0;
        this.active = false;
    }

    // Méthode pour activer/désactiver le comptage des nœuds
    public void activateNodeCount(boolean activated) {
        this.active = activated;
    }

    // Méthode pour récupérer le nombre de nœuds explorés
    public int getNodeCount() {
        return nodeCount;
    }
    

    public Map<Variable, Object> getInitialState() {
        return this.initial_State;
    }

    public Set<Action> getActions() {
        return this.set_Actions;
    }

    public Goal getGoal() {
        return this.goal;
    }
    public List<Action> plan() {
        // Initialisation des structures
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>(); // Père de chaque état
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();                 // Action associée à chaque état
        Set<Map<Variable, Object>> closed = new HashSet<>();                       // Ensemble des états visités
        Queue<Map<Variable, Object>> open = new LinkedList<>();                    // File d'attente des états à explorer

        // Ajouter l'état initial à la file des ouverts et à l'ensemble des fermés
        open.add(this.initial_State);
        closed.add(this.initial_State);
        father.put(this.initial_State, null);  // L'état initial n'a pas de père

        // Appel à bFSPlanner avec les structures initialisées
        return bFSPlanner(father, plan, closed, open);
    }
    
    /**
     * Algorithme de recherche en largeur (BFS) pour trouver un plan.
     * @param father le parent.
     * @param plan Action associée à chaque état.
     * @param closed Ensemble des états visités.
     * @param open File d'attente des états à explorer.
     * @return Une liste d'actions représentant le plan trouvé, ou null si aucun plan n'est trouvé.
     */
    public List<Action> bFSPlanner(Map<Map<Variable, Object>, Map<Variable, Object>> father,Map<Map<Variable, Object>, Action> plan,Set<Map<Variable, Object>> closed,Queue<Map<Variable, Object>> open) {
        if (this.goal.isSatisfiedBy(this.initial_State)) {
            return new ArrayList<>();
        }

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = open.poll();

            // Incrémenter le compteur si le comptage est activé
            if (this.active) {
                nodeCount++;
            }

            closed.add(instantiation);

            for (Action action : this.set_Actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);

                    if (!closed.contains(next) && !open.contains(next)) {
                        father.put(next, instantiation);
                        plan.put(next, action);

                        if (this.goal.isSatisfiedBy(next)) {
                            return getBFSPlan(father, plan, next);  // Utilisation de getBFSPlan()
                        }

                        open.add(next);
                    }
                }
            }
        }

        return null;
    }

    /**
     * Reconstruit le plan en remontant depuis l'objectif jusqu'à l'état initial.
     * @param father le parent.
     * @param plan Action associée à chaque état.
     * @param goal L'état final qui satisfait l'objectif.
     * @return Une liste d'actions représentant le plan trouvé.
     */
    private List<Action> getBFSPlan(Map<Map<Variable, Object>, Map<Variable, Object>> father,Map<Map<Variable, Object>, Action> plan,Map<Variable, Object> goal) {
        
        LinkedList<Action> BFS_plan = new LinkedList<>(); 

        while (goal != null) {
            Action action = plan.get(goal);
            if (action != null) {
                BFS_plan.addFirst(action);  
            }
            goal = father.get(goal);
        }

        
        return BFS_plan;  
    }



    
    
}
