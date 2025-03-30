package modelling;
import java.util.*;

public class DifferenceConstraint implements Constraint{
    private Variable v1 , v2;

    public DifferenceConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope(){
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
        

    }
    
        // Vérifie si la contrainte est satisfaite par une assignation donnée
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation) 
    {
        for(Variable v : getScope()){
            if ( instantiation.get(v) == null)
            {
               throw new IllegalArgumentException("L'assignation ne contient pas toutes les variables requises.");
            }

        }
               // Vérifie que les valeurs des deux variables sont différentes
        return !(Objects.equals(instantiation.get(this.v1),instantiation.get(this.v2)));
    }

    @Override
    public String toString() {
        return "DifferenceConstraint{var1=" + v1 + ", var2=" + v2 +"}";
    }



}