package datamining;
import modelling.*;
import java.util.*;

/**
 * Classe représentant une base de données transactionnelle composée d'items booléens.
 * Chaque transaction est un ensemble d'items (BooleanVariable), et la base de données
 * conserve tous les items possibles ainsi qu'une liste de transactions.
 */
public class BooleanDatabase {

    private Set<BooleanVariable> EnsembleDesItems;
    private List<Set<BooleanVariable>> Transactions;

    /**
     * Constructeur pour initialiser la base de données avec un ensemble d'items.
     * La liste des transactions est initialement vide.
     * 
     * @param EnsembleDesItems L'ensemble des items possibles dans la base de données.
     */
    public BooleanDatabase(Set<BooleanVariable> EnsembleDesItems) {
        this.EnsembleDesItems = new HashSet<>(EnsembleDesItems);
        this.Transactions = new ArrayList<>();
    }  

    /**
     * Ajoute une nouvelle transaction à la base de données.
     * Une transaction est un ensemble d'items présents dans une occurrence de transaction.
     * 
     * @param ens La transaction à ajouter à la base de données.
     */
    public void add(Set<BooleanVariable> ens) {
        this.Transactions.add(ens);
    }

  
    public Set<BooleanVariable> getItems() {
        return this.EnsembleDesItems;
    }


    public List<Set<BooleanVariable>> getTransactions() {
        return this.Transactions;
    }

    @Override
    public String toString(){
        return "EnsembleDesItems: " + EnsembleDesItems + ", fTransactions: " + Transactions;
    }

}
