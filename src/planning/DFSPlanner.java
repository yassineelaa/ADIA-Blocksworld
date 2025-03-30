package planning;
import modelling.*;
import java.util.*;


/**
  Cette classe implémente un planificateur utilisant la recherche en profondeur (DFS). Le but est d'explorer tous les chemins potentiels en profondeur jusqu'à atteindre l'objectif.
 */

public class DFSPlanner implements Planner{
    private Map<Variable, Object> initial_State;
    private Set<Action> set_Actions;
    private Goal goal;
    private boolean countNodes;// Pour activer/désactiver le comptage des nœuds
    private int nodeCount; // Compteur de nœuds explorés
    private boolean active;     // Variable pour gérer l'état actif du comptage des nœuds

    public DFSPlanner(Map<Variable, Object> initial_State,Set<Action> set_Actions,Goal goal){
        this.initial_State = initial_State;
        this.set_Actions = set_Actions;
        this.goal=goal;
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

    
    public Map<Variable, Object> getInitialState(){
        return this.initial_State;
    }
    public Set<Action> getActions(){
        return this.set_Actions;
    }
    public Goal getGoal(){
        return this.goal;
    }

    
    /**
      Méthode principale qui démarre la recherche DFS pour trouver un plan.
     * @return Liste d'actions représentant le plan trouvé, ou null si aucun plan n'est trouvé.
     */
    public List<Action> plan(){
        Set<Map<Variable, Object>> visited = new HashSet<>(); 
        List<Action> plan = new ArrayList<Action>();
        return dFSPlanner(this.initial_State,plan,visited);

    }
    
    /**
      méthode récursive qui implémente l'algorithme DFS pour explorer les états et générer un plan.
     * @param EtatCourant L'état actuel dans l'exploration.
     * @param plan La liste d'actions qui constitue le plan en cours de construction.
     * @param visited Les états déjà visités pour éviter les boucles.
     * @return Le plan trouvé sous forme de liste d'actions, ou null si aucun plan n'est trouvé.
     */
    private List<Action> dFSPlanner(Map<Variable, Object> EtatCourant,List<Action> plan,Set<Map<Variable, Object>> visited){

        // Incrémenter le compteur si le comptage est activé
        if (this.active) {
            nodeCount++;
        }

        if(this.goal.isSatisfiedBy(EtatCourant)){
            return plan;
        }
        
        else{
            for(Action action:this.set_Actions){

                if(action.isApplicable(EtatCourant)){

                    Map<Variable, Object> next = action.successor(EtatCourant);
                    if(!visited.contains(next)){

                        plan.add(action);
                        visited.add(next);
                        List<Action> subplan = dFSPlanner(next,plan,visited);
                        
                        if(subplan!=null){

                            return subplan;
                        }
                        else{
                            plan.remove(plan.size()-1);
                        }
                    }
                }
            }
            
        }
        return null;
    }
}