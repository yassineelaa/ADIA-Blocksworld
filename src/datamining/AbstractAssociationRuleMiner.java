package datamining;
import modelling.*;
import java.util.*;

/**
 * Classe abstraite pour les mineurs de règles d'association.
 * Elle fournit les fonctionnalités de base pour l'extraction de règles d'association
 * à partir d'une base de données transactionnelle, incluant le calcul de la fréquence
 * d'un itemset et la confiance d'une règle d'association.
 */
public abstract class AbstractAssociationRuleMiner implements AssociationRuleMiner {

   
    protected BooleanDatabase database;

    /**
     * Constructeur pour initialiser le mineur de règles d'association avec une base de données.
     * 
     * @param database La base de données utilisée pour extraire les règles d'association.
     */
    public AbstractAssociationRuleMiner(BooleanDatabase database) {
        this.database = database;
    }

    
    @Override
    public BooleanDatabase getDatabase() {
        return this.database;
    }

    /**
     * Méthode statique pour calculer la fréquence d'un itemset donné dans un ensemble d'itemsets.
     * 
     * @param items L'ensemble des items dont on souhaite calculer la fréquence.
     * @param itemsets Le jeu d'itemsets dans lequel rechercher.
     * @return La fréquence de l'itemset spécifié.
     * @throws IllegalArgumentException si l'itemset n'est pas trouvé dans l'ensemble des itemsets.
     */
    public static float frequency(Set<BooleanVariable> items, Set<Itemset> itemsets) {
        for (Itemset itemset : itemsets) {
            if (itemset.getItems().equals(items)) {
                return itemset.getFrequency();
            }
        }
        throw new IllegalArgumentException("Itemset not found in the set of itemsets.");
    }

    /**
     * Méthode statique pour calculer la confiance d'une règle d'association.
     * La confiance est définie comme le ratio entre la fréquence de l'union de la prémisse
     * et de la conclusion, et la fréquence de la prémisse seule. 
     * Si la fréquence de la prémisse est zéro, la confiance est définie comme zéro.
     * 
     * @param premise L'ensemble d'items représentant la prémisse de la règle.
     * @param conclusion L'ensemble d'items représentant la conclusion de la règle.
     * @param itemsets Le jeu d'itemsets fréquents dans lequel rechercher les fréquences.
     * @return La confiance de la règle, ou 0.0 si la fréquence de la prémisse est zéro.
     */
    public static float confidence(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion,
                                   Set<Itemset> itemsets) {
        Set<BooleanVariable> union = new HashSet<>(premise);
        union.addAll(conclusion);
        float freqUnion = frequency(union, itemsets);
        float freqPremise = frequency(premise, itemsets);

        
        if (freqPremise == 0.0f) {
            return 0.0f;
        }

        return freqUnion / freqPremise;
    }
}
