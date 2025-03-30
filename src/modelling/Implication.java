package modelling ;
import java.util.*;


public class Implication implements Constraint{
    private Variable v1;
    private Set<Object> s1;
    private Variable v2;
    private Set<Object> s2;

    public Implication(Variable v1,Set<Object> s1,Variable v2, Set<Object> s2){
        this.v1 = v1;
        this.s1 = s1;
        this.v2 = v2;
        this.s2 = s2;
    }

    public Set<Variable> getScope(){
        Set<Variable> scope = new HashSet<>();
        scope.add(v1);
        scope.add(v2);
        return scope;
        

    }
          // Vérifie si la contrainte est satisfaite par une assignation donnée
       public boolean isSatisfiedBy(Map<Variable, Object> instantiation ) {
        // Vérifie que toutes les variables requises sont présentes dans l'assignation
        for(Variable v : getScope()){
            if ( instantiation.get(v) == null){
               throw new IllegalArgumentException("L'assignation ne contient pas toutes les variables requises.");
            }
        }
        // Vérifie si la condition d'implication est satisfaite 
        return !(this.s1.contains(instantiation.get(this.v1))) || (this.s2.contains(instantiation.get(this.v2)));
        
    }

    @Override
    public String toString() {
        return "implication{var1=" + this.v1 + ", domain1=" + this.s1 + ", var2= "+ this.v2 +  ", domain2=" + this.s2 + "}";
    }


}