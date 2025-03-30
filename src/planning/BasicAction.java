package planning ;
import modelling.*;
import java.util.Objects;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Collections;


public class BasicAction implements Action{

    private Map<Variable, Object> pr_Condition;
    private Map<Variable, Object> effet;
    int cout;
    
    /**
      Constructeur de BasicAction.
     * @param pr_Condition Map des variables et leurs valeurs représentant les préconditions.
     * @param effet Map des variables et leurs valeurs représentant les effets de l'action.
     * @param cout Le coût de l'action.
     */
    public BasicAction(Map<Variable, Object> pr_Condition, Map<Variable, Object> effet, int cout){
        this.pr_Condition = pr_Condition;
        this.effet = effet;
        this.cout = cout;
    }


    
    
    /**
      Applique l'action à l'état donné et retourne l'état résultant.
     * @param state L'état actuel sous forme de map.
     * @return L'état successeur après application de l'action.
     */
    @Override
    public boolean isApplicable(Map<Variable, Object> state) {
          
        for (Variable key : this.pr_Condition.keySet()) {   
            
            if(!state.containsKey(key) || !this.pr_Condition.get(key).equals(state.get(key)))
                return false;
        }
        return true ;
    }
    
    /** 
      Applique l'action à l'état donné et retourne l'état résultant.
     * @param state L'état actuel sous forme de map.
     * @return L'état successeur après application de l'action.
     */
    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> state) {
        Map<Variable, Object> next_State = new HashMap<>();
        
        
        if (isApplicable(state)) {
            
            for (Map.Entry<Variable, Object> e : state.entrySet()) {
                next_State.put(e.getKey(), e.getValue());
            }

            
            for (Map.Entry<Variable, Object> e : this.effet.entrySet()) {
                next_State.put(e.getKey(), e.getValue());
            }

            
            return next_State;
        } else {
            for (Map.Entry<Variable, Object> e : state.entrySet()) {
                next_State.put(e.getKey(), e.getValue());
            }
            return next_State;
        }
    }

    /** 
      Retourne le coût de l'action.
     * @return Le coût de l'action.
     */
    @Override
    public int getCost(){
        return this.cout ;
        }

    
    @Override 
    public String toString(){
         return "Action avec préconditions " + pr_Condition + " et effets " + effet + ", coût : " + cout; 
         }


    
}