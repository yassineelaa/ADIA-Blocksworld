package blocksworld;

import java.util.*;
import bwgeneratordemo.Demo;
import datamining.*;
import modelling.BooleanVariable;

/**
 * Classe pour gérer les variables booléennes et extraire les données du monde des blocs.
 * Elle étend BlocksWorld.
 */
public class BlocksWorldDatamining extends BlocksWorld {

    private Set<BooleanVariable> allBooleanVariables = new HashSet<>();
    private Map<String, BooleanVariable> variableMap = new HashMap<>();

    /**
     * Constructeur pour initialiser le monde des blocs avec le nombre de blocs et de piles.
     *
     * @param nbBlocs Le nombre de blocs
     * @param nbPiles Le nombre de piles
     */
    public BlocksWorldDatamining(int nbBlocs, int nbPiles) {
        super(nbBlocs, nbPiles);
        initOnBlockVariables();
        initOnTableVariables();
    }

    /**
     * Génère les variables pour vérifier si un bloc est au-dessus d'un autre.
     */
    private void initOnBlockVariables() {
        for (Integer blockA : super.getOnBlocks().keySet()) {
            for (Integer blockB : super.getFixedBlocks().keySet()) {
                if (!blockA.equals(blockB)) {
                    String name = blockA + "_" + blockB;
                    BooleanVariable onAB = new BooleanVariable("on_" + name);
                    allBooleanVariables.add(onAB);
                    variableMap.put(name, onAB);
                }
            }
        }
    }

    /**
     * Génère les variables indiquant si un bloc est sur la table dans une pile spécifique.
     */
    private void initOnTableVariables() {
        for (Integer blockIndex : super.getOnBlocks().keySet()) {
            for (Integer pileIndex : super.getFreePiles().keySet()) {
                String name = blockIndex + "_" + pileIndex;
                BooleanVariable onTable = new BooleanVariable("onTable_" + name);
                allBooleanVariables.add(onTable);
                variableMap.put(name, onTable);
            }
        }
    }

    /**
     * Récupère les variables booléennes actives dans un état donné.
     *
     * @param state Représentation d'un état du monde des blocs (transaction)
     * @return Ensemble des variables booléennes actives dans cet état.
     */
    public Set<BooleanVariable> getActiveVariables(List<List<Integer>> state) {
        Set<BooleanVariable> activeVars = new HashSet<>();

        for (int stackIndex = 0; stackIndex < state.size(); stackIndex++) {
            List<Integer> stack = state.get(stackIndex);

            if (stack.isEmpty()) {
                String name = Integer.toString(-stackIndex - 1);
                BooleanVariable isFree = getOrCreateVariable("free_" + name, name);
                activeVars.add(isFree);
            }

            for (int position = 0; position < stack.size(); position++) {
                int block = stack.get(position);

                if (position == 0) {
                    String name = block + "_" + (-stackIndex - 1);
                    BooleanVariable onTable = getOrCreateVariable("onTable_" + name, name);
                    activeVars.add(onTable);
                }

                if (position < stack.size() - 1) {
                    int blockAbove = stack.get(position + 1);
                    String nameOn = blockAbove + "_" + block;
                    String nameFixed = String.valueOf(block);
                    BooleanVariable onTop = getOrCreateVariable("on_" + nameOn, nameOn);
                    BooleanVariable isFixed = getOrCreateVariable("fixed_" + nameFixed, nameFixed);
                    activeVars.add(onTop);
                    activeVars.add(isFixed);
                }
            }
        }
        return activeVars;
    }

    /**
     * Génère une base de données booléenne en utilisant la bibliothèque BWGenerator.
     *
     * @param nbTransactions Le nombre de transactions à générer
     * @return Base de données booléenne avec les transactions générées.
     */
    public BooleanDatabase generateBooleanDatabase(int nbTransactions) {
        BooleanDatabase booleanDatabase = new BooleanDatabase(allBooleanVariables);
        for (int i = 0; i < nbTransactions; i++) {
            List<List<Integer>> state = Demo.getState(new Random());
            Set<BooleanVariable> transaction = getActiveVariables(state);
            booleanDatabase.add(transaction);
        }
        return booleanDatabase;
    }

    /**
     * Récupère une variable existante ou en crée une nouvelle si nécessaire.
     *
     * @param varName Nom de la variable
     * @param varKey  Clé de la variable dans la map
     * @return La variable booléenne existante ou nouvellement créée
     */
    private BooleanVariable getOrCreateVariable(String varName, String varKey) {
        return variableMap.computeIfAbsent(varKey, k -> new BooleanVariable(varName));
    }

    /**
     * Retourne l'ensemble de toutes les variables booléennes.
     *
     * @return Ensemble de toutes les variables booléennes.
     */
    public Set<BooleanVariable> getAllBooleanVariables() {
        return allBooleanVariables;
    }

    /**
     * Retourne la map de correspondance nom-variable.
     *
     * @return Map de correspondance nom-variable.
     */
    public Map<String, BooleanVariable> getVariableMap() {
        return variableMap;
    }

    @Override
    public String toString() {
        return "BlocksWorldDatamining{" +" allBooleanVariables=" + getAllBooleanVariables() +" }";
    }
}
