package blocksworld;

import modelling.*;
import java.util.*;

public class ContrainteCroissante extends BlockWorldConstraints {
    private Set<Constraint> croissanteConstraints;

    public ContrainteCroissante(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
        this.croissanteConstraints = new HashSet<>();
        addCroissanteConstraints();
    }

    

    private void addCroissanteConstraints() {
        for (int b = 0; b < this.getNbBlocks(); b++) {
            Variable onb = this.getOnBlocks().get(b);
            Set<Object> allowedDomain = new HashSet<>();

            for (Object value : onb.getDomain()) {
                int val = (Integer) value;
                if (val < 0 || val < b) {
                    // Si la valeur est une pile ou un bloc de numéro inférieur à b, on l'autorise
                    allowedDomain.add(val);
                }
            }

            Constraint c = new UnaryConstraint(onb, allowedDomain);
            this.croissanteConstraints.add(c);
        }
    }

    /**
     * Vérifie si une configuration est croissante.
     */
    public boolean isConfigCroissante(Map<Variable, Object> instantiation) {
        if (!super.isConfigSatisfyConstraints(instantiation)) {
            return false;
        }
        for (Constraint constraint : this.croissanteConstraints) {
            if (!constraint.isSatisfiedBy(instantiation)) {
                System.out.println("Contrainte de croissance non satisfaite : " + constraint);
                return false;
            }
        }
        return true;
    }

    public Set<Constraint> getCroissanteConstraints() {
        return this.croissanteConstraints;
    }

    /**
     * Récupère toutes les contraintes, y compris celles de croissance.
     */
    @Override
    public Set<Constraint> getAllConstraints() {
        Set<Constraint> allConstraints = super.getAllConstraints();
        allConstraints.addAll(this.croissanteConstraints);
        return allConstraints;
    }

    public void afficheCroissanteConstraints() {
        System.out.println("-----Contraintes Croissantes----------");
        for (Constraint constraint : this.croissanteConstraints) {
            System.out.println(constraint);
        }
    }
}




