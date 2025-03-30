package datamining;
import modelling.*;
import java.util.*;

/**
 * Classe représentant un itemset (ensemble d'items) avec une fréquence associée
 * dans une base de données transactionnelle.
 * Cette classe est utilisée pour stocker les itemsets fréquents identifiés par des algorithmes de fouille de données.
 */
public class Itemset {
    
    private Set<BooleanVariable> Items;
    private float frequence;

    /**
     * Constructeur pour initialiser un itemset avec un ensemble d'items et une fréquence.
     *
     * @param Items L'ensemble des items de cet itemset.
     * @param frequence La fréquence de cet itemset dans la base de données, entre 0 et 1.
     */
    public Itemset(Set<BooleanVariable> Items, float frequence) {
        this.Items = Items;
        this.frequence = frequence;
    }


    public Set<BooleanVariable> getItems() {
        return this.Items;
    }


    public float getFrequency() {
        return this.frequence;
    }
    @Override
    public String toString(){
        return "Items: " + Items.toString() + ", Fréquence: " + frequence;
    }
}
