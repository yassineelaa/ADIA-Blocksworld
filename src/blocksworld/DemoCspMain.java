package blocksworld;

import modelling.*;
import cp.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import bwmodel.*;
import bwui.*;
import bwdemo.*;

public class DemoCspMain {
    public static void main(String[] args) {
        
        int nbBlocks = 6; 
        int nbPiles = 2;   

        // Initialiser les contraintes régulières
        RegularConstraints regularConstraints = new RegularConstraints(nbBlocks, nbPiles);

        // Initialiser les contraintes croissantes
        ContrainteCroissante croissanteConstraints = new ContrainteCroissante(nbBlocks, nbPiles);

        // Afficher les contraintes régulières dans la console
        System.out.println(regularConstraints);
        regularConstraints.afficheDefference();
        regularConstraints.fixedConstraintSet();
        regularConstraints.freeConstraintSet();
        regularConstraints.fixedConstraintSet(); // Affiche les contraintes de régularité

        // Afficher les contraintes croissantes dans la console
        System.out.println(croissanteConstraints);
        croissanteConstraints.afficheCroissanteConstraints();

        // Combiner toutes les contraintes
        Set<Constraint> allConstraints = new HashSet<>();
        allConstraints.addAll(regularConstraints.getAllConstraints());
        allConstraints.addAll(croissanteConstraints.getAllConstraints());

        // Récupérer toutes les variables des deux contraintes
        Set<Variable> allVariables = new HashSet<>();

        // Variables de RegularConstraints
        allVariables.addAll(regularConstraints.getOnBlocks().values());
        allVariables.addAll(regularConstraints.getFixedBlocks().values());
        allVariables.addAll(regularConstraints.getFreePiles().values());

        // Variables de ContrainteCroissante
        allVariables.addAll(croissanteConstraints.getOnBlocks().values());
        allVariables.addAll(croissanteConstraints.getFixedBlocks().values());
        allVariables.addAll(croissanteConstraints.getFreePiles().values());

        // Initialiser les solveurs avec les variables et contraintes combinées
        Solver backtrack = new BacktrackSolver(allVariables, allConstraints);
        Solver macSolver = new MACSolver(allVariables, allConstraints);

        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(false);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());

        Solver heuristicMacSolver = new HeuristicMACSolver(allVariables, allConstraints, variableHeuristic, valueHeuristic);

        // Exécuter les solveurs
        executeSolver(backtrack, "BacktrackSolver", regularConstraints, croissanteConstraints, nbBlocks);
        executeSolver(macSolver, "MACSolver", regularConstraints, croissanteConstraints, nbBlocks);
        executeSolver(heuristicMacSolver, "HeuristicMACSolver", regularConstraints, croissanteConstraints, nbBlocks);
    }

    private static void executeSolver(Solver solver, String solverName, RegularConstraints regularConstraints, ContrainteCroissante croissanteConstraints, int nbBlocks) {
        System.out.println("---------------- Execution de " + solverName + " ---------------------");

        long startTime = System.currentTimeMillis();
        Map<Variable, Object> solution = solver.solve();
        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;

        if (solution != null) {
            System.out.println("Solution trouvée : " + solution);
            // Vérifier si la solution satisfait toutes les contraintes
            boolean isValid = regularConstraints.isConfigReg(solution) && croissanteConstraints.isConfigCroissante(solution);
            System.out.println("La configuration Croissante et Reguliere est valide ? " + isValid);

            if (isValid) {
                // Visualisation de la solution
                visualizeSolution(solution, nbBlocks, solverName);
            } else {
                System.out.println("La solution ne satisfait pas toutes les contraintes.");
            }
        } else {
            System.out.println("Aucune solution trouvée.");
        }
        System.out.println("Temps de calcul : " + timeElapsed + " millisecondes.\n");
    }

    private static BWState<Integer> makeBWState(Map<Variable, Object> solution, int nbBlocks) {
        BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbBlocks);

        for (Map.Entry<Variable, Object> entry : solution.entrySet()) {
            String varName = entry.getKey().getName(); 
            int block;
            try {
                block = Integer.parseInt(varName.split(" ")[1]);
            } catch (Exception e) {
                System.out.println("Nom de variable inattendu : " + varName);
                continue;
            }

            Object on = entry.getValue();
            if (on instanceof Integer) {
                int under = (Integer) on;
                if (under >= 0) {
                    builder.setOn(block, under);
                }
            }
        }

        return builder.getState();
    }

    private static void visualizeSolution(Map<Variable, Object> solution, int nbBlocks, String solverName) {
        BWState<Integer> state = makeBWState(solution, nbBlocks);

        
        SwingUtilities.invokeLater(() -> {
            BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);
            JFrame frame = new JFrame("Configuration Régulière et Croissante - " + solverName);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(gui.getComponent(state));
            frame.pack();
            frame.setSize(1000, 1000);
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }
}
