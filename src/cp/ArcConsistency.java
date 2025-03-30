package cp;

import modelling.*;
import java.util.*;

public class ArcConsistency {

    private Set<Constraint> constraints;

    /**
     * Constructeur de la classe ArcConsistency.
     * @param constraints l'ensemble des contraintes à vérifier dans le CSP.
     * @throws IllegalArgumentException si une contrainte n'est ni unaire ni binaire.
     */

    public ArcConsistency(Set<Constraint> constraints) {

        for (Constraint constraint : constraints) {
            Set<Variable> scope = constraint.getScope();
            if (scope.size() != 1 && scope.size() != 2) {
                throw new IllegalArgumentException("Ni unaire ni binaire.");
            }
        }
        this.constraints = constraints;
    }

     /**
     * Applique la consistance de nœud sur les domaines des variables pour les contraintes unaires.
     * Cette méthode réduit les domaines en supprimant les valeurs qui ne satisfont pas les contraintes unaires.
     * 
     * @param evolutionDomain une Map des variables avec leurs domaines associés.
     * @return true si aucun domaine n'est vide après l'application, false sinon.
     */
    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> evolutionDomain) {
    boolean domainNonVide = true;

    //parcourir toutes les contraintes pour trouver les contraintes unaires
    for (Constraint constraint : constraints) {
        Set<Variable> scope = constraint.getScope();

        if (scope.size() == 1) {
            /*Nous obtenons la variable concernée par la contrainte unaire en récupérant le premier élément de l'itérateur sur le scope. */
            Variable var = scope.iterator().next();
            /*Nous obtenons le domaine actuel de la variable var à partir de la map evolutionDomain */
            Set<Object> domain = evolutionDomain.get(var);
            
            if (domain == null) {
                throw new IllegalArgumentException("Le domaine de la variable " + var.getName() + " est manquant.");
            }
            
            Set<Object> tmp = new HashSet<>(domain);
            
            for (Object value : tmp) {
                //Nous créons une affectation partielle où la variable var est assignée à la valeur value.
                Map<Variable, Object> assignment = Map.of(var, value);
                if (!constraint.isSatisfiedBy(assignment)) {
                    domain.remove(value);
                }
            }
            //Si le domaine est vide, cela signifie qu'il n'y a plus aucune valeur possible pour la variable var qui satisfasse les contraintes unaires, rendant le CSP insolvable dans son état actuel.
            if (domain.isEmpty()) {
                //Nous mettons domainNonVide à false pour signaler qu'au moins un domaine a été vidé
                domainNonVide = false;
            }
        }
    }

    return domainNonVide;
}

   /**
     * Révise le domaine de var1 en supprimant les valeurs sans support dans le domaine de var2
     * pour les contraintes binaires entre var1 et var2.
     * 
     * @param var1 la première variable.
     * @param domainvar1 le domaine de var1.
     * @param var2 la seconde variable.
     * @param domainvar2 le domaine de var2.
     * @return true si le domaine de var1 a été modifié, false sinon.
     */
   public boolean revise(Variable var1, Set<Object> domainvar1, Variable var2, Set<Object> domainvar2) {
    boolean Del = false;
    Set<Object> var1Values = new HashSet<>(domainvar1);

    for (Object vi : var1Values) {
        boolean viable = false;

        for (Object vj : domainvar2) {
            boolean toutSatisfait = true;

            for (Constraint c : constraints) {
                Set<Variable> scope = c.getScope();
                if (scope.size() == 2 && scope.contains(var1) && scope.contains(var2)) {
                    Map<Variable, Object> N = new HashMap<>();
                    N.put(var1, vi);
                    N.put(var2, vj);

                    if (!c.isSatisfiedBy(N)) {
                        toutSatisfait = false;
                        break;
                    }
                }
            }

            if (toutSatisfait) {
                viable = true;
                break;
            }
        }

        if (!viable) {
            domainvar1.remove(vi);
            Del = true;
        }
    }

    return Del;
}
   
   /**
     * Applique l'algorithme AC1 pour assurer la consistance des arcs sur les domaines des variables.
     * Il utilise enforceNodeConsistency pour les contraintes unaires et revise pour les contraintes binaires.
     * 
     * @param evolutionDomain une Map des variables avec leurs domaines associés.
     * @return true si aucun domaine n'est vide après application de l'algorithme, false sinon.
     */

   public boolean ac1(Map<Variable, Set<Object>> evolutionDomain) {
        //au moins un domaine est vide
        if (!enforceNodeConsistency(evolutionDomain)) {
            return false;
        }
        //change est un indicateur qui sera true si des domaines ont été modifiés.
        boolean change = true;
        while (change) {
            change = false;
            for (Variable variable1 : evolutionDomain.keySet()) {
                for (Variable variable2 : evolutionDomain.keySet()) {
                    if (!variable1.equals(variable2)) {
                        if (revise(variable1, evolutionDomain.get(variable1), variable2, evolutionDomain.get(variable2))) {
                            change = true;
                    }
                    }
                }
            }
        }
        for (Variable variable : evolutionDomain.keySet()) {
            if (evolutionDomain.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }

  
}
