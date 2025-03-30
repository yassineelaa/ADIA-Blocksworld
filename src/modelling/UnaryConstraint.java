package modelling ;
import java.util.*;


public class UnaryConstraint implements Constraint {
    private Variable v;// La variable sur laquelle la contrainte unaire est appliqué
    private Set<Object> subsetDomain;// L'ensemble de valeurs autorisées pour la variable

  
    public UnaryConstraint(Variable v, Set<Object> subsetDomain) {
        this.v = v;
        this.subsetDomain = subsetDomain;
    }
    
 
    @Override
    public Set<Variable> getScope() {
        Set<Variable> scope = new HashSet<>();
        scope.add(v);
        
        return scope;
    }   
    
    // Vérifie si la contrainte est satisfaite par une assignation donnée
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instantiation ) {
        if (!instantiation.containsKey(v)){
           throw new IllegalArgumentException("L'assignation ne contient pas toutes les variables requises.");
        }
        // Vérifie si la valeur de la variable est dans l'ensemble autorisé
        return this.subsetDomain.contains(instantiation.get(this.v));
    }

    @Override
    public String toString() {
        return "UnaryConstraint{var1=" + this.v + ", subsetDomain=" + this.subsetDomain + "}";
    }

}