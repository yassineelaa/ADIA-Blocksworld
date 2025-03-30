package blocksworld;
import java.util.*;
import modelling.*;

/**
 * La classe BlocksWorld représente le monde des blocs avec des piles. 
 * Elle définit les variables nécessaires pour modéliser les positions, les états fixes des blocs, 
 * et les états libres des piles.
 */
public class BlocksWorld{

    private int nbBlocks; // Nombre total de blocs
    private int nbPiles; // Nombre total de piles
    private Map<Integer, Variable> onBlocks; // Variables représentant où se trouve chaque bloc
    private Map<Integer, BooleanVariable> fixedBlocks; // Variables représentant si un bloc est fixe
    private Map<Integer, BooleanVariable> freePiles; // Variables représentant si une pile est libre

    public BlocksWorld(int nbBlocks, int nbPiles){
        this.nbBlocks = nbBlocks;
        this.nbPiles = nbPiles;
        this.onBlocks = new HashMap<>();
        this.fixedBlocks = new HashMap<>();
        this.freePiles = new HashMap<>();
        initializeVariables();
    }

    // Méthode privée pour initialiser les variables
    private void initializeVariables() {
        Set<Object> domain = new HashSet<>();
        // Remplir le domaine avec les identifiants des piles et des blocs
        for (int i = -nbPiles; i < nbBlocks; i++) {
            domain.add(i);
        }
        // Créer des variables pour les blocs
        for (int i = 0; i < nbBlocks; i++) {
            Set<Object> newDomain = new HashSet<>(domain);
            newDomain.remove(i); // Un bloc ne peut pas être sur lui-même
            this.onBlocks.put(i, new Variable("on " + i, newDomain));
            this.fixedBlocks.put(i, new BooleanVariable("fixed " + i));
        }
        // Créer des variables pour les piles
        for (int i = 0; i < nbPiles; i++) {
            int p = -(i + 1);
            this.freePiles.put(p, new BooleanVariable("free " + p));
        }
    }
    

    public Set<Variable> getallVariables() {
        Set<Variable> allVariables = new HashSet<>();
        allVariables.addAll(this.onBlocks.values());
        allVariables.addAll(this.fixedBlocks.values());
        allVariables.addAll(this.freePiles.values());
        return allVariables;
    }

    public Map<Integer, Variable> getOnBlocks() {
        return onBlocks;
    }

    public Map<Integer, BooleanVariable> getFixedBlocks() {
        return fixedBlocks;
    }

    public Map<Integer, BooleanVariable> getFreePiles() {
        return freePiles;
    }

    public int getNbBlocks() {
        return nbBlocks;
    }

    public int getNbPiles() {
        return nbPiles;
    }
    

    @Override
    public String toString() {
        return "BlocksWorld:(nbBlocs: " + this.nbBlocks + ", nbPiles: " + this.nbPiles + ")";
    }

    public void afficheVar() {
        System.out.println("-------onBlocks-------------");
        for (Map.Entry<Integer, Variable> entry : this.onBlocks.entrySet()) {
            System.out.println("Block " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("-------fixedBlocks-------------");
        for (Map.Entry<Integer, BooleanVariable> entry : this.fixedBlocks.entrySet()) {
            System.out.println("Block " + entry.getKey() + " fixed: " + entry.getValue());
        }

        System.out.println("-------freePiles-------------");
        for (Map.Entry<Integer, BooleanVariable> entry : this.freePiles.entrySet()) {
            System.out.println("Pile " + entry.getKey() + " free: " + entry.getValue());
        }
    }
    


}