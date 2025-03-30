package blocksworld;

import modelling.*;
import java.util.*;



public class BlockWorldConstraints extends BlocksWorld {

    private Set<Constraint> differenceConstraintSet;
    private Set<Constraint> fixedConstraintSet;
    private Set<Constraint> freeConstraintSet;

    public BlockWorldConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.differenceConstraintSet = new HashSet<>();
        this.fixedConstraintSet= new HashSet<>();
        this.freeConstraintSet= new HashSet<>();

        differenceConstraints();
        fixedConstraint();
        freeConstraint();
    }

    public Set<Constraint> differenceConstraints() {
        for (int index = 0; index < super.getNbBlocks(); index++) {
            for (int j = index + 1; j < super.getNbBlocks(); j++) {
                differenceConstraintSet.add(new DifferenceConstraint(super.getOnBlocks().get(index), super.getOnBlocks().get(j)));
            }
        }
        return differenceConstraintSet;
    }

    /**
     * La méthode fixedConstraint nous permet de vérifier si un bloc a une valeur b' alors le fixed b' doit avoir la valeur true.
 */
    public Set<Constraint> fixedConstraint() {
        
        for (Variable currentBlockVar : super.getOnBlocks().values()) {
            for (Object domainValue : currentBlockVar.getDomain()) {
                Integer index = (Integer) domainValue;
                if (index >= 0) { // Vérifie que index est un bloc
                    Set<Object> domainOfBlock1 = new HashSet<>();
                    domainOfBlock1.add(index);
                    Set<Object> domainOfBlock2 = new HashSet<>();
                    domainOfBlock2.add(true);
                    //Si le bloc 0 est sur le bloc 1 (onBlocks.get(0) = 1), alors le bloc 1 doit être fixe (fixedBlocks.get(1) = true).
                    fixedConstraintSet.add(new Implication(currentBlockVar, domainOfBlock1, super.getFixedBlocks().get(index), domainOfBlock2));
                }
            }
        }
        return fixedConstraintSet;
    }
  
    /*
     * La méthode freeConstraint nous permet de vérifier si un bloc b a une valeur p alors le freeP doit avoir la valeur false.
     */
    public Set<Constraint> freeConstraint() {
        
        for (Variable currentBlockVar : super.getOnBlocks().values()) {
            for (Object domainValue : currentBlockVar.getDomain()) {
                Integer index = (Integer) domainValue;
                if (index < 0) { // Vérifie que index est une pile
                    Set<Object> domainOfBlock1 = new HashSet<>();
                    domainOfBlock1.add(index);
                    Set<Object> domainOfBlock2 = new HashSet<>();
                    domainOfBlock2.add(false);
                    //Si Bloc 0 est sur la pile -1 (onBlocks.get(0) = -1), alors freePiles.get(-1) = false.
                    freeConstraintSet.add(new Implication(currentBlockVar, domainOfBlock1, super.getFreePiles().get(index), domainOfBlock2));
                }
            }
        }
        return freeConstraintSet;
    }
 
    

    /**
     * Méthode pour mettre à jour automatiquement les freePiles en fonction des onBlocks.
     * @param config L'instanciation actuelle des variables.
     */
    public void updateFreePiles(Map<Variable, Object> config) {
        // Réinitialiser toutes les piles à 'true' (libres)
        for (BooleanVariable freePile : getFreePiles().values()) {
            config.put(freePile, true);
        }

        // Parcourir toutes les positions des blocs pour marquer les piles occupées
        for (Map.Entry<Integer, Variable> entry : getOnBlocks().entrySet()) {
            Integer block = entry.getKey();
            Variable onVar = entry.getValue();
            Object position = config.get(onVar);
            if (position instanceof Integer) {
                Integer pos = (Integer) position;
                if (pos < 0) { // Si la position est une pile
                    BooleanVariable freePile = getFreePiles().get(pos);
                    if (freePile != null) {
                        config.put(freePile, false);
                    }
                }
            }
        }
    }
    


    public boolean isConfigSatisfyConstraints(Map<Variable, Object> instantiation) {
    boolean allSatisfied = true;
    for (Constraint c : this.getAllConstraints()) {
        if (!c.isSatisfiedBy(instantiation)) {
            System.out.println("Contrainte de base non satisfaite : " + c);
            allSatisfied = false;
        }
    }
    if (allSatisfied) {
        System.out.println("Toutes les contraintes de base sont satisfaites.");
    }
    return allSatisfied;
}


    public Set<Constraint> getAllConstraints() {
        Set<Constraint> allConstraints = new HashSet<>();
        allConstraints.addAll(this.differenceConstraints());
        allConstraints.addAll(this.fixedConstraint());
        allConstraints.addAll(this.freeConstraint());
        return allConstraints;
    }

    @Override
    public String toString() {
        return "BlocksWorldConstraints [nbBlocs=" + getNbBlocks() + ", nbPiles=" + getNbPiles() + ", constraints=" + getAllConstraints().size() + "]";
    }

    public void afficheDefference(){
        System.out.println("-----ContraintDefferece----------");
        for(Constraint contraint : this.differenceConstraintSet){
            System.out.println(contraint);
        }
    }

    public void fixedConstraintSet(){
        System.out.println("-----fixedConstraintSet----------");
        for(Constraint contraint : this.fixedConstraintSet){
            System.out.println(contraint);
        }
    }

    public void freeConstraintSet(){
        System.out.println("-----freeConstraintSet----------");
        for(Constraint contraint : this.freeConstraintSet){
            System.out.println(contraint);
        }
    }

    


}

