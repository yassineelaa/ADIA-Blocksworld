package cp;

import modelling.*;
import java.util.*;
/**
 *  la classe RandomValueHeuristic implémente l'interface ValueHeuristic en fournissant un ordre aléatoire des valeurs du domaine d'une variable.
 *  Elle utilise un générateur aléatoire pour mélanger les valeurs de manière pseudo-uniforme,
 */
public class RandomValueHeuristic implements ValueHeuristic {
    // Générateur aléatoire utilisé pour mélanger les valeurs
    private Random randomGenerator;

    /**
     * Constructeur de la classe.
     *
     * @param randomGenerator un générateur aléatoire utilisé pour mélanger les valeurs
     */
    public RandomValueHeuristic(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    /**
     * Retourne une liste des valeurs du domaine de la variable, melangees de manière pseudo-uniforme.
     * @param variable la variable dont le domaine doit être ordonné
     * @param domain   le domaine actuel de la variable
     * @return une liste des valeurs mélangées de manière pseudo-uniforme
     */
    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> values = new ArrayList<>(domain);
        // Mélanger la liste 
        Collections.shuffle(values, randomGenerator);
        
        return values;
    }
}
