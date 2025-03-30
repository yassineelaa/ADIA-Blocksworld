package blocksworld;

import planning.*;
import modelling.*;
import java.util.*;

/**
 * Cette classe étend BlockWorldConstraints et définit les actions possibles
 * dans le problème BlockWorld, en respectant les contraintes définies.
 */
public class BlockWorldActions extends BlockWorldConstraints {

    public BlockWorldActions(int nbBlocks, int nbPiles) {
        super(nbBlocks, nbPiles);
    }
    
    /**
     * Vérifie si une combinaison de trois blocs est valide.
     * 
     * @param b1 Identifiant du premier bloc.
     * @param b2 Identifiant du deuxième bloc.
     * @param b3 Identifiant du troisième bloc.
     * @return true si la combinaison est valide, false sinon.
     */
    private boolean isValidBlockCombination(int b1, int b2, int b3) {
        return b1 != b2 && b1 != b3 && b2 != b3;
    }
    
    /**
     * Génère toutes les actions permettant de déplacer un bloc d'un autre bloc vers un autre bloc.
     */
    public Set<Action> moveBlockFromBlockToBlock() {
        Set<Action> actions = new HashSet<>();
        int nbBlocks = super.getNbBlocks();

        for (int b1 = 0; b1 < nbBlocks; b1++) {
            for (int b2 = 0; b2 < nbBlocks; b2++) {
                if (b1 == b2) continue;

                for (int b3 = 0; b3 < nbBlocks; b3++) {
                    if (!isValidBlockCombination(b1, b2, b3)) continue;

                    Map<Variable, Object> precondition = Map.of(
                        super.getOnBlocks().get(b1), b2,
                        super.getFixedBlocks().get(b1), false,
                        super.getFixedBlocks().get(b2), true,
                        super.getFixedBlocks().get(b3), false
                    );

                    Map<Variable, Object> effect = Map.of(
                        super.getOnBlocks().get(b1), b3,
                        super.getFixedBlocks().get(b1), false,
                        super.getFixedBlocks().get(b2), false,
                        super.getFixedBlocks().get(b3), true
                    );

                    actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
        return actions;
    }
    
    /**
     * Génère toutes les actions permettant de déplacer un bloc d'un autre bloc vers une pile libre.
     */
    public Set<Action> moveBlockFromBlockToStack() {
        Set<Action> actions = new HashSet<>();
        int nbBlocks = super.getNbBlocks();
        int nbPiles = super.getNbPiles();

        for (int b1 = 0; b1 < nbBlocks; b1++) {
            for (int b2 = 0; b2 < nbBlocks; b2++) {
                if (b1 == b2) continue;

                for (int p = -nbPiles; p < 0; p++) {
                    Map<Variable, Object> precondition = Map.of(
                        super.getOnBlocks().get(b1), b2,
                        super.getFixedBlocks().get(b1), false,
                        super.getFixedBlocks().get(b2), true,
                        super.getFreePiles().get(p), true
                    );

                    Map<Variable, Object> effect = Map.of(
                        super.getOnBlocks().get(b1), p,
                        super.getFixedBlocks().get(b1), false,
                        super.getFixedBlocks().get(b2), false,
                        super.getFreePiles().get(p), false
                    );

                    actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
        return actions;
    }
   
    /**
     * Génère toutes les actions permettant de déplacer un bloc d'une pile vers un autre bloc.
     */
    public Set<Action> moveBlockFromStackToBlock() {
        Set<Action> actions = new HashSet<>();
        int nbBlocks = super.getNbBlocks();
        int nbPiles = super.getNbPiles();

        for (int b1 = 0; b1 < nbBlocks; b1++) {
            for (int p = -nbPiles; p < 0; p++) {
                for (int b2 = 0; b2 < nbBlocks; b2++) {
                    if (b1 == b2) continue;

                    Map<Variable, Object> precondition = Map.of(
                        super.getOnBlocks().get(b1), p,
                        super.getFixedBlocks().get(b1), false,
                        super.getFreePiles().get(p), false,
                        super.getFixedBlocks().get(b2), false
                    );

                    Map<Variable, Object> effect = Map.of(
                        super.getOnBlocks().get(b1), b2,
                        super.getFixedBlocks().get(b1), false,
                        super.getFreePiles().get(p), true,
                        super.getFixedBlocks().get(b2), true
                    );

                    actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
        return actions;
    }
    
    /**
     * Génère toutes les actions permettant de déplacer un bloc d'une pile vers une autre pile.
     */
    public Set<Action> moveBlockFromStackToStack() {
        Set<Action> actions = new HashSet<>();
        int nbBlocks = super.getNbBlocks();
        int nbPiles = super.getNbPiles();

        for (int b = 0; b < nbBlocks; b++) {
            for (int p = -nbPiles; p < 0; p++) {
                for (int px = -nbPiles; px < 0; px++) {
                    if (p == px) continue;

                    Map<Variable, Object> precondition = Map.of(
                        super.getOnBlocks().get(b), p,
                        super.getFreePiles().get(p), false,
                        super.getFreePiles().get(px), true
                    );

                    Map<Variable, Object> effect = Map.of(
                        super.getOnBlocks().get(b), px,
                        super.getFreePiles().get(p), true,
                        super.getFreePiles().get(px), false
                    );

                    actions.add(new BasicAction(precondition, effect, 1));
                }
            }
        }
        return actions;
    }
    
    /**
     * Récupère toutes les actions possibles dans le problème BlockWorld.
     */
    public Set<Action> getAllActions() {
        Set<Action> actions = new HashSet<>();
        actions.addAll(moveBlockFromStackToStack());
        actions.addAll(moveBlockFromStackToBlock());
        actions.addAll(moveBlockFromBlockToStack());
        actions.addAll(moveBlockFromBlockToBlock());
        return actions;
    }
}
