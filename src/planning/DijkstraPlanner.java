package planning ;
import modelling.*;
import java.util.*;



/*
  Cette classe implémente un planificateur basé sur l'algorithme de Dijkstra pour trouver le chemin optimal
  pour atteindre un objectif à partir d'un état initial. Elle utilise un ensemble d'actions possibles
  et un état initial pour générer un plan vers l'objectif.
 */
public class DijkstraPlanner implements Planner {

    private Map<Variable, Object> initial_State; // L'état initial du problème.
    private Set<Action> set_Actions; // ensemble d'actions possibles.
    private Goal goal; // L'objectif.
    private boolean countNodes ; // Pour activer/désactiver le comptage des nœuds
    private int nodeCount; // Compteur de nœuds explorés
        private boolean active;     // Variable pour gérer l'état actif du comptage des nœuds



    public DijkstraPlanner(Map<Variable, Object> initial_State, Set<Action> set_Actions , Goal goal) {
        this.initial_State =initial_State;
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

    
    /*
   La méthode plan() est la méthode principale qui renvoie une liste d'actions pour atteindre l'état cible. Elle utilise l'algorithme de Dijkstra pour trouver le chemin le plus court.
   */
    @Override
    public List<Action> plan(){
       
        Map<Map<Variable, Object>,Action> plan = new HashMap<>(); // Cree une carte pour stocker le plan

        Map<Map<Variable,Object>,Float> distance = new HashMap<>();  // Cree une carte pour stocker les distances

        Map< Map<Variable, Object>,  Map<Variable, Object >> father = new HashMap<>(); // Cree une carte pour stocker le parent

        return Dijkstra(plan,distance,father);

    }

    /**
     * Algorithme de Dijkstra pour générer un plan optimisé en termes de coûts.
     * @param plan Plan 
     * @param distance Distance réelle pour chaque état.
     * @param father le parent.
     * @return Une liste d'actions représentant le plan trouvé, ou null si aucun plan n'est trouvé.
     */

    private List<Action> Dijkstra(Map<Map<Variable, Object>,Action> plan, Map<Map<Variable,Object>,Float> distance, Map< Map<Variable, Object>,  Map<Variable, Object >> father){
       
       // Cree une file d'attente prioritaire pour explorer les états
       PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(getComparator(distance));

        Set<Map<Variable, Object>> goals = new HashSet<>();

        father.put(this.initial_State, null);

        distance.put(this.initial_State, 0f);

        open.add(this.initial_State);

        while (!open.isEmpty()){

            Map<Variable, Object> instantiation = open.poll();

            // Incrémenter le compteur si le comptage est activé
            if (this.active) {
                nodeCount++;
            }

            if (this.goal.isSatisfiedBy(instantiation)) {
              goals.add(instantiation);
            }
            // Exploration des actions possibles pour l'état actuel
            for (Action action : this.set_Actions){

                if (action.isApplicable(instantiation)) {
                    
                    // Calcul de l'état suivant
                    Map<Variable, Object> next_State = action.successor(instantiation);
                    // Vérification si l'état suivant n'a pas deja ete explore
                    if (!distance.containsKey(next_State)) {

                    distance.put(next_State, Float.MAX_VALUE);

                }
                    // mise a jour de la distance et du parent pour l'etat suivant
                    if (distance.get(next_State) > distance.get(instantiation) + action.getCost()) {

                        distance.put(next_State, distance.get(instantiation) + action.getCost());

                        father.put(next_State, instantiation);

                        plan.put(next_State, action);

                        open.add(next_State);

                    }
                }

            }

        }
        // verifie si des objectifs ont été trouver
        if (goals.isEmpty()) {

            return null; 

        } else {

            return get_dijkstra_plan(father, plan, goals, distance);

        }      

}
    
    /**
     * Reconstruit le plan en remontant depuis le but avec la plus petite distance jusqu'à l'état initial.
     * @param father le parent.
     * @param plan Plan en cours de construction.
     * @param goals Ensemble des états qui satisfont l'objectif.
     * @param distance Map associant les états à leur distance réelle.
     * @return Une liste d'actions représentant le plan trouvé.
     */
    private List<Action> get_dijkstra_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Set<Map<Variable, Object>> goals, Map<Map<Variable, Object>, Float> distance) {

        PriorityQueue<Map<Variable, Object>> goalQueue = new PriorityQueue<>(getComparator(distance));


        for (Map<Variable, Object> item : goals) {

            goalQueue.add(item);

        }

        Map<Variable, Object> goal_final = goalQueue.poll(); // Obtient le but avec la plus petite distance

        List<Action> dijkstraPlan = new ArrayList<>();

        while (father.get(goal_final) != null) {

            dijkstraPlan.add(plan.get(goal_final));

            goal_final = father.get(goal_final);

        }

        Collections.reverse(dijkstraPlan);

        return dijkstraPlan;

    }
    
    /**
     * Retourne un comparateur utilisé par la file de priorité pour trier les états en fonction de leur distance.
     * @param distance Map associant les états à leur distance réelle.
     * @return Un comparateur permettant de comparer deux états.
     */
    private Comparator<Map<Variable, Object>> getComparator(Map<Map<Variable, Object>, Float> distance) {
    return new Comparator<Map<Variable, Object>>() {
        @Override
        public int compare(Map<Variable, Object> o1, Map<Variable, Object> o2) {
            return distance.get(o1).compareTo(distance.get(o2));
        }
    }; 
  }


    

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initial_State;
    }
    
    
    @Override
    public Set<Action> getActions() {
        return this.set_Actions;
    }
    
    
    @Override
    public Goal getGoal() {
        return this.goal;
    }


}
