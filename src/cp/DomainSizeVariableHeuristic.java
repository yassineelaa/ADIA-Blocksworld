package cp;

import modelling.*;
import java.util.*;

/**
 * La classe DomainSizeVariableHeuristic implémente l'interface VariableHeuristic et fournit une heuristique basée sur la taille du domaine de chaque variable.
 */
public class DomainSizeVariableHeuristic implements VariableHeuristic {
    private boolean bestDomain;

    /**
     * Constructeur de la classe
     *
     * @param bestDomain indique si l'on préfère les variables avec le plus grand domaine (true) ou avec le plus petit domaine (false)
     */
    public DomainSizeVariableHeuristic(boolean bestDomain) {
        this.bestDomain = bestDomain;
    }

    /**
     * Sélectionne la variable ayant le plus grand ou le plus petit domaine en fonction du paramètre bestDomain
     * @param variables l'ensemble des variables parmi lesquelles choisir
     * @param variableDomains   l'ensemble des domaines des variables
     * @return la variable choisie en fonction de la taille de son domaine
     */
    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> variableDomains) {
        Variable selectedVariable = null;
        /**Si bestDomain est true, on initialise à Integer.MIN_VALUE pour trouver la taille de domaine maximale.
Si bestDomain est false, on initialise à Integer.MAX_VALUE pour trouver la taille de domaine minimale. */
        int optimalDomainSize = bestDomain ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (Variable var : variables) {
            int domainSize = variableDomains.get(var).size();

            if (bestDomain) {
                if (domainSize > optimalDomainSize) {
                    optimalDomainSize = domainSize;
                    selectedVariable = var;
                }
            } else {
                if (domainSize < optimalDomainSize) {
                    optimalDomainSize = domainSize;
                    selectedVariable = var;
                }
            }
        }

        return selectedVariable;
    }
}

