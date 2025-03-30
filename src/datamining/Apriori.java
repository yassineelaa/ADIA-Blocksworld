package datamining;
import java.util.*;
import modelling.*;

/**
 * Classe Apriori pour l'extraction d'itemsets fréquents en utilisant l'algorithme Apriori.
 * Cette classe hérite d'AbstractItemsetMiner et utilise une base de données transactionnelle.
 */
public class Apriori extends AbstractItemsetMiner {

    /**
     * Constructeur pour initialiser l'extracteur Apriori avec une base de données.
     * @param database La base de données utilisée pour l'extraction d'itemsets fréquents.
     */
    public Apriori(BooleanDatabase database) {
        super(database); 
    }

    /**
     * Retourne la base de données associée à cet objet Apriori.
     * @return La base de données utilisée par cet extracteur.
     */
    @Override
    public BooleanDatabase getDatabase() {
        return this.base;
    }

    /**
     * Méthode pour trouver tous les items fréquents de taille 1 (singletons).
     * Un item est fréquent si sa fréquence dans la base est supérieure ou égale à minFrequency.
     * @param minFrequency La fréquence minimale requise pour qu'un item soit considéré comme fréquent.
     * @return Un ensemble contenant tous les itemsets fréquents de taille 1.
     */
    public Set<Itemset> frequentSingletons(float minFrequency) {
        Set<Itemset> singletons = new HashSet<>();
        for (BooleanVariable item : getDatabase().getItems()) {
            Set<BooleanVariable> singleton = new HashSet<>();
            singleton.add(item);
            float freq = frequency(singleton); 
            if (freq >= minFrequency) { 
                singletons.add(new Itemset(singleton, freq));
            }
        }
        return singletons;
    }

    /**
     * Méthode statique pour combiner deux ensembles triés d'items.
     * Cette combinaison est possible si les deux ensembles ont la même taille et partagent les mêmes éléments, sauf le dernier.
     * @param set1 Le premier ensemble d'items triés.
     * @param set2 Le deuxième ensemble d'items triés.
     * @return Un nouvel ensemble d'items combiné, ou null si la combinaison n'est pas possible.
     */
    public static SortedSet<BooleanVariable> combine(
            SortedSet<BooleanVariable> set1, SortedSet<BooleanVariable> set2) {

        if (set1.size() != set2.size() || set1.isEmpty()) {
            return null;
        }

        Iterator<BooleanVariable> it1 = set1.iterator();
        Iterator<BooleanVariable> it2 = set2.iterator();
        SortedSet<BooleanVariable> result = new TreeSet<>(COMPARATOR);

        for (int i = 0; i < set1.size() - 1; i++) {
            BooleanVariable var1 = it1.next();
            BooleanVariable var2 = it2.next();
            if (!var1.equals(var2)) {
                return null; 
            }
            result.add(var1);
        }

        BooleanVariable last1 = it1.next();
        BooleanVariable last2 = it2.next();
        if (last1.equals(last2)) {
            return null; 
        }

        result.add(last1);
        result.add(last2);

        return result;
    }

    /**
     * Méthode statique pour vérifier si tous les sous-ensembles d'un itemset sont fréquents.
     * Cette vérification est nécessaire pour générer des itemsets plus grands dans l'algorithme Apriori.
     * @param itemset L'ensemble d'items à vérifier.
     * @param frequentSubsets La collection de tous les ensembles d'items fréquents de taille k-1.
     * @return true si tous les sous-ensembles de l'itemset sont fréquents, false sinon.
     */
    public static boolean allSubsetsFrequent(
            Set<BooleanVariable> itemset,
            Collection<SortedSet<BooleanVariable>> frequentSubsets) {

        for (BooleanVariable item : itemset) {
            SortedSet<BooleanVariable> subset = new TreeSet<>(COMPARATOR);
            subset.addAll(itemset);
            subset.remove(item); 

            if (!frequentSubsets.contains(subset)) {
                return false; 
            }
        }
        return true;
    }

    /**
     * Implémente la méthode extract pour extraire tous les itemsets fréquents de la base de données.
     * Utilise une fréquence minimale pour filtrer les itemsets fréquents de toutes tailles.
     * @param minFrequency La fréquence minimale pour qu'un itemset soit considéré comme fréquent.
     * @return Un ensemble contenant tous les itemsets fréquents.
     */
    @Override
    public Set<Itemset> extract(float minFrequency) {
        Set<Itemset> frequentItemsets = new HashSet<>();
        List<SortedSet<BooleanVariable>> currentFrequentSets = new ArrayList<>();

        // Ajoute tous les items fréquents de taille 1 (singletons)
        Set<Itemset> singletons = frequentSingletons(minFrequency);
        frequentItemsets.addAll(singletons);

        for (Itemset itemset : singletons) {
            SortedSet<BooleanVariable> set = new TreeSet<>(COMPARATOR);
            set.addAll(itemset.getItems());
            currentFrequentSets.add(set);
        }

        // Génère les combinaisons d'itemsets pour trouver des itemsets fréquents de plus grande taille
        while (!currentFrequentSets.isEmpty()) {
            List<SortedSet<BooleanVariable>> nextLevelSets = new ArrayList<>();

            for (int i = 0; i < currentFrequentSets.size(); i++) {
                for (int j = i + 1; j < currentFrequentSets.size(); j++) {
                    SortedSet<BooleanVariable> candidate = combine(currentFrequentSets.get(i), currentFrequentSets.get(j));

                    if (candidate != null && allSubsetsFrequent(candidate, currentFrequentSets)) {
                        float frequency = frequency(candidate);
                        if (frequency >= minFrequency) {
                            frequentItemsets.add(new Itemset(candidate, frequency));
                            nextLevelSets.add(candidate);
                        }
                    }
                }
            }
            currentFrequentSets = nextLevelSets; 
        }
        return frequentItemsets;
    }
}

