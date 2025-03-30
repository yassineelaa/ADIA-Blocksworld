package datamining;
import modelling.*;
import java.util.*;

/**
 * Classe abstraite pour l'extraction d'itemsets fréquents dans une base de données transactionnelle.
 * Elle fournit des méthodes de base pour les mineurs de motifs fréquents, y compris le calcul de la
 * fréquence d'un ensemble d'items (itemset).
 */
public abstract class AbstractItemsetMiner implements ItemsetMiner {

    /** La base de données utilisée pour l'extraction des itemsets */
    protected BooleanDatabase base;

    /** Comparateur statique pour trier les BooleanVariable en fonction de leur nom */
    public static final Comparator<BooleanVariable> COMPARATOR = (var1, var2) -> var1.getName().compareTo(var2.getName());

    /**
     * Constructeur pour initialiser le mineur d'itemsets avec une base de données.
     *
     * @param base La base de données transactionnelle utilisée pour extraire les itemsets fréquents.
     */
    public AbstractItemsetMiner(BooleanDatabase base) {
        this.base = base;
    }

    /**
     * Retourne la base de données utilisée par ce mineur d'itemsets.
     *
     * @return La base de données transactionnelle associée.
     */
    public BooleanDatabase getBase() {
        return this.base;
    }

    /**
     * Méthode abstraite pour extraire tous les itemsets fréquents de la base de données,
     * en fonction d'une fréquence minimale spécifiée.
     * Cette méthode doit être implémentée par les sous-classes concrètes.
     *
     * @param minFreq La fréquence minimale pour qu'un itemset soit considéré comme fréquent.
     * @return Un ensemble contenant tous les itemsets fréquents répondant au seuil de fréquence.
     */
    public abstract Set<Itemset> extract(float minFreq);

    /**
     * Calcule la fréquence d'un sous-ensemble d'items (itemset) dans la base de données.
     * La fréquence est définie comme le ratio du nombre de transactions contenant l'itemset
     * sur le nombre total de transactions.
     *
     * @param subsetItems L'ensemble d'items dont on souhaite calculer la fréquence dans les transactions.
     * @return La fréquence de l'itemset dans la base de données.
     */
    public float frequency(Set<BooleanVariable> subsetItems) {
        float count = 0;
        for (Set<BooleanVariable> transaction : this.base.getTransactions()) {
            if (transaction.containsAll(subsetItems)) {
                count++;
            }
        }
        return count / this.base.getTransactions().size();
    }
}

   
    


