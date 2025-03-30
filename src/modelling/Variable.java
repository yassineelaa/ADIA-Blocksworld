package modelling;

import java.util.Objects;
import java.util.Set;


public class Variable {
    private String name;
    private Set<Object> domain;

    public Variable(String name, Set<Object> domain) {
        this.name = name;
        this.domain = domain;
    }

   /*Méthode equals pour comparer deux variables*/
   @Override
    public boolean equals(Object obj) {
    // Vérifie si l'objet actuel est comparé à lui-même
        if (this == obj) {
            return true;
        }

    // Vérifie que l'objet n'est pas nul et que l'objet est de type Variable (ou sous-type)
        if (obj == null || !(obj instanceof Variable)) {
            return false;
    }

    // Cast l'objet en Variable et compare les noms
        Variable variable = (Variable) obj;
        return this.getName().equals(variable.getName());
}

       /*Méthode hashCode pour générer un code de hachage basé sur le nom de la variable */
    @Override
    public int hashCode() {
       return Objects.hash(name);
    }  
    
     public String getName() {
        return name;
    }

    public Set<Object> getDomain() {
        return domain;
    }

    @Override
    public String toString() {
        return "Variable{name='" + name + "', domain=" + domain + "}";
    }

}