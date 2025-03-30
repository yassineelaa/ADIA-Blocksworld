package planning ;
import java.util.List;
import java.util.Map;
import java.util.Set;
import modelling.*;


public interface Heuristic {


    /**
      estime le coût heuristique à partir d'un état donné.
      @param heuristic L'état pour lequel l'heuristique doit être estimée.
      @return Un coût heuristique estimé.
     */
    float estimate(Map<Variable, Object> heuristic);
}