package planning ;
import modelling.*;
import java.util.*;


public class BasicGoal implements Goal{

    private Map<Variable, Object> goal_state;

    public BasicGoal(Map<Variable, Object> goal_state){
        this.goal_state = goal_state;
    }

    
    /**
      Méthode qui vérifie si l'état donné satisfait l'objectif.
     * @param state L'état à vérifier sous forme de map contenant les variables et leurs valeurs.
     * @return true si l'état satisfait l'objectif, false sinon.
     */
    @Override
    public boolean isSatisfiedBy(Map<Variable,Object> state)
    {
         for(Variable key : this.goal_state.keySet())
         {  
            if(!state.containsKey(key)  || !this.goal_state.get(key).equals(state.get(key)))
                  
            return false;
        }
        return true;
    }
    @Override
    public Map<Variable, Object> getGoal(){
        return this.goal_state;
    }


}