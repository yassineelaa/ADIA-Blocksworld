package datamining;

import modelling.*;
import java.util.*;

/**
 * La classe BruteForceAssociationRuleMiner est une implémentation simple du mineur de règles d'association.
 * Elle extrait les règles d'association en générant toutes les combinaisons possibles de prémisses et de conclusions
 * à partir des itemsets fréquents.
 */
public class BruteForceAssociationRuleMiner extends AbstractAssociationRuleMiner {

    /**
     * Constructeur de la classe BruteForceAssociationRuleMiner.
     * @param database La base de données binaire utilisée par le mineur de règles d'association.
     */
    public BruteForceAssociationRuleMiner(BooleanDatabase database) {
        super(database);
    }

    /**
     * Méthode qui génère tous les sous-ensembles non vides et propres d'un ensemble d'items donné.
     * Cette version évite l'utilisation des opérations sur les bits pour être plus compréhensible.
     * @param items L'ensemble d'items à partir duquel générer les prémisses.
     * @return Un ensemble de tous les sous-ensembles possibles de prémisses.
     */
    public static Set<Set<BooleanVariable>> allCandidatePremises(Set<BooleanVariable> items) {
        Set<Set<BooleanVariable>> result = new HashSet<>();
        
        
        result.add(new HashSet<>());
        
       
        for (BooleanVariable item : items) {
           
            Set<Set<BooleanVariable>> newSubsets = new HashSet<>();
            
            for (Set<BooleanVariable> subset : result) {
                
                Set<BooleanVariable> newSubset = new HashSet<>(subset);
                newSubset.add(item);
                newSubsets.add(newSubset);
            }
            
            
            result.addAll(newSubsets);
        }
        
        result.remove(Collections.emptySet());
        result.remove(items);
        
        return result;
    }

    /**
     * Méthode qui extrait les règles d'association de la base de données selon les seuils de fréquence et de confiance donnés.
     * @param minFrequency La fréquence minimale requise pour les itemsets fréquents.
     * @param minConfidence La confiance minimale requise pour les règles d'association.
     * @return Un ensemble des règles d'association extraites.
     */
    @Override
    public Set<AssociationRule> extract(float minFrequency, float minConfidence) {
        Set<AssociationRule> associationRules = new HashSet<>();

        Apriori apriori = new Apriori(this.getDatabase());
        Set<Itemset> frequentItemsets = apriori.extract(minFrequency);

        Map<Set<BooleanVariable>, Float> itemsetFrequencies = new HashMap<>();
        for (Itemset itemset : frequentItemsets) {
            itemsetFrequencies.put(itemset.getItems(), itemset.getFrequency());
        }

       
        for (Itemset itemset : frequentItemsets) {
            Set<BooleanVariable> items = itemset.getItems();

           
            if (items.size() < 2) {
                continue;
            }

           
            Set<Set<BooleanVariable>> candidatePremises = allCandidatePremises(items);

            for (Set<BooleanVariable> premise : candidatePremises) {
                Set<BooleanVariable> conclusion = new HashSet<>(items);
                conclusion.removeAll(premise);

                if (conclusion.isEmpty()) {
                    continue;
                }

                Float premiseFrequency = itemsetFrequencies.get(premise);
                if (premiseFrequency == null) {
                   
                    continue;
                }

                float confidence = itemset.getFrequency() / premiseFrequency;

                if (confidence >= minConfidence) {
                    AssociationRule rule = new AssociationRule(premise, conclusion, itemset.getFrequency(), confidence);
                    associationRules.add(rule);
                }
            }
        }

        return associationRules;
    }
}
