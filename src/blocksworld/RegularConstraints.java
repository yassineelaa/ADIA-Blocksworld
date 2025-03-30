package blocksworld;

import modelling.*;
import java.util.*;



public class RegularConstraints extends BlockWorldConstraints {
    
    protected Set<Constraint> setOfConfReg;

    public RegularConstraints(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.setOfConfReg = new HashSet<>();
        

         Set<Object> domianePiles = new HashSet<>(this.getFreePiles().keySet());

        for (Integer i : this.getOnBlocks().keySet()) {
            Variable vi = this.getOnBlocks().get(i);
            for (Object o : vi.getDomain()) {
                Integer j = (Integer) o;

                Integer diff = j - i;
                Integer allowedBlock = j + diff;

                if (j >= 0) {
                    Variable vj = this.getOnBlocks().get(j);
                    Set<Object> newDom = new HashSet<>(domianePiles);
                    if (allowedBlock >= 0 && allowedBlock < this.getNbBlocks()) {
                        newDom.add(allowedBlock);
                    }
                    Set<Object> firstDom = Set.of(j);
                    Constraint c = new Implication(vi, firstDom, vj, newDom);
                    this.setOfConfReg.add(c);
                }
            }
        }
    }

    

    /**
     * Récupère l'ensemble des contraintes de régularité.
     */
    public Set<Constraint> getSetOfConfReg() {
        return this.setOfConfReg;
    }

    /**
     * Vérifie si une configuration est régulière.
     */
    public boolean isConfigReg(Map<Variable, Object> instantiation){
        if(!super.isConfigSatisfyConstraints(instantiation)){
            return false;
        }
        for(Constraint constraint : this.setOfConfReg){
            if(!constraint.isSatisfiedBy(instantiation)){
                System.out.println("Contrainte non satisfaite : " + instantiation);
                return false;
            }
        }
        return true;
    }

    /**
     * Récupère toutes les contraintes, y compris celles de régularité.
     */
    @Override
    public Set<Constraint> getAllConstraints(){
        Set<Constraint> allConstraints = super.getAllConstraints();
        allConstraints.addAll(this.setOfConfReg);
        return allConstraints;
    }

    @Override
    public String toString() {
        return "RegularConstraints [nbBlocs=" + getNbBlocks() + ", nbPiles=" + getNbPiles() 
                + ", contraintes régularité=" + this.setOfConfReg.size() + "]";
    }

    public void fixedConstraintSet(){
        System.out.println("-----setOfConfReg----------");
        for(Constraint contraint : this.setOfConfReg){
            System.out.println(contraint);
        }
    }
}

