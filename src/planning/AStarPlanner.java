package  planning ;
import modelling.*;
import java.util.*;


/*
  Cette classe implémente l'algorithme de planification A* pour résoudre des problèmes de planification.
  Elle utilise un ensemble d'actions, un état initial et un objectif pour générer un plan optimisé en fonction de l'heuristique.
 */

public class AStarPlanner implements Planner , Heuristic {

    private Map<Variable, Object> initial_State; // L'état initial du problème.
    private Set<Action> set_Actions; // ensemble d'actions possibles.
    private Goal goal; // L'objectif.
    private Heuristic heuristic ; //heuristique utilisée pour estimer les couts.
    private boolean countNodes; // Pour activer/désactiver le comptage des nœuds
    private int nodeCount; // Compteur de nœuds explorés
    private boolean active;     // Variable pour gérer l'état actif du comptage des nœuds

    public AStarPlanner(Map<Variable, Object> initial_State, Set<Action> set_Actions , Goal goal, Heuristic heuristic){
        this.initial_State =initial_State;
        this.set_Actions = set_Actions;
        this.goal = goal;
        this.heuristic = heuristic;
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

    @Override
    public List<Action> plan(){

        Map<Map<Variable, Object>,Action>  plan = new HashMap<>();

        Map< Map<Variable, Object>,  Map<Variable, Object >> father = new HashMap<>();

        Map<Map<Variable,Object>,Float> distance = new HashMap<>();

        Map<Map<Variable,Object>,Float> value = new HashMap<>();
        
        return aStarPlanner(plan,father,distance,value); 
    }
    

    /** 
       Algorithme A* pour générer un plan optimisé.
       @param plan Plan en cours de construction.
       @param father le parent.
       @param distance Distance réelle pour chaque état.
       @param value Valeur estimée (distance + heuristique) pour chaque état.
       @return Une liste d'actions représentant le plan trouvé, ou null si aucun plan n'est trouvé.
     */
    private List<Action> aStarPlanner( Map<Map<Variable, Object>,Action> plan,Map<Map<Variable, Object>,Map<Variable, Object>> father,Map<Map<Variable,Object>,Float> distance,Map<Map<Variable,Object>,Float> value){

                PriorityQueue<Map<Variable, Object>> open = new PriorityQueue<>(getComparator(value));

                open.add(this.initial_State);
                
                father.put(this.initial_State, null);

                distance.put(this.initial_State, 0f);

                value.put(this.initial_State, estimate(this.initial_State));

                while(!open.isEmpty()){

                    Map<Variable, Object> instantiation = open.poll();
                    
                    // Incrémenter le compteur si le comptage est activé
                    if (this.active) {
                        nodeCount++;
                    }

                    if (this.goal.isSatisfiedBy(instantiation)) {
                        return get_bfs_plan(father, plan, instantiation);
                    

                    }else{

                        for (Action action : this.set_Actions){
                            if (action.isApplicable(instantiation)){
                                
                                Map<Variable, Object> next = action.successor(instantiation);

                                if (!distance.containsKey(next)){

                                    distance.put(next,Float.MAX_VALUE);
                                }

                                if(distance.get(next) > (distance.get(instantiation)+ action.getCost())){

                                    float next_value= (distance.get(instantiation)+ action.getCost());
                                    distance.put(next,next_value);

                                    float value_estimer= (distance.get(instantiation)+ estimate(next));
                                    value.put(next,value_estimer);

                                    father.put(next,instantiation);
                                    plan.put(next,action);
                                    open.add(next);
                                }
                           
                            }
                        }

                    }   
                    
                }return null; // Aucun plan trouvé
    }

        
        /**
        Reconstruit le plan en remontant depuis l'objectif jusqu'à l'état initial.
        @param father le parent.
        @param plan Plan en cours de construction.
        @param goal L'état final satisfaisant l'objectif.
        @return Une liste d'actions représentant le plan trouvé.
      */
        private List<Action> get_bfs_plan(Map<Map<Variable, Object>, Map<Variable, Object>> father, Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> goal){
            List<Action> bfs_plan = new ArrayList<>();
            Map<Variable, Object> current_State = goal;

            // Remonter depuis l'objectif jusqu'à l'état initial
            while (father.get(current_State) != null) {
                
                bfs_plan.add(plan.get(current_State));
                current_State = father.get(current_State);
            }

            Collections.reverse(bfs_plan); // Inverser la liste pour obtenir l'ordre correct
            return bfs_plan;
        }



    /**
       Estime le coût heuristique d'un état donné. Cette méthode est surchargée selon le besoin.
       @param heuristic L'état pour lequel on souhaite estimer le coût.
      @return Le coût heuristique estimé.
     */
     @Override
    public  float estimate(Map<Variable, Object> state) {
        return this.heuristic.estimate(state);
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

    /**
       Estime le coût heuristique d'un état donné.
       @param heuristic L'état pour lequel on souhaite estimer le coût.
       @return Le coût heuristique estimé.
     */
    private Comparator<Map<Variable, Object>> getComparator(Map<Map<Variable, Object>, Float> distance) {
        return new Comparator<Map<Variable, Object>>() {
            @Override
            public int compare(Map<Variable, Object> o1, Map<Variable, Object> o2) {
                return distance.get(o1).compareTo(distance.get(o2));
            }
        }; 



    }


  }






