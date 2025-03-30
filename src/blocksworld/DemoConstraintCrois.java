package blocksworld;

import modelling.*;
import cp.*;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;


import bwmodel.*;
import bwui.*;
import bwdemo.*;




public class DemoConstraintCrois {
    public static void main(String[] args) {
        
        int nbBlocks = 6; 
        int nbPiles = 2;   
        
        ContrainteCroissante bwConstraint = new ContrainteCroissante(nbBlocks, nbPiles);
        Set<Constraint> constraints = bwConstraint.getAllConstraints();

        Set<Variable> variables = new HashSet<>(bwConstraint.getOnBlocks().values());

        Solver backtrack = new BacktrackSolver(variables, constraints);
        Solver macSolver = new MACSolver(variables, constraints);

        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(false);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());

        Solver heuristicMacSolver = new HeuristicMACSolver(variables, constraints, variableHeuristic, valueHeuristic);

        executeSolver(backtrack, "BacktrackSolver", bwConstraint, nbBlocks);
        executeSolver(macSolver, "MACSolver", bwConstraint, nbBlocks);
        executeSolver(heuristicMacSolver, "HeuristicMACSolver", bwConstraint, nbBlocks);
    }

    private static void executeSolver(Solver solver, String solverName, ContrainteCroissante bwConstraint, int nbBlocks) {
        System.out.println("---------------- Execution de " + solverName + " pour une configuration Croissante ---------------------");

        long startTime = System.currentTimeMillis();
        Map<Variable, Object> solution = solver.solve();
        long endTime = System.currentTimeMillis();

        long timeElapsed = endTime - startTime;

        if (solution != null) {
            System.out.println("Solution trouvée : " + solution);
            // Visualisation de la solution
            visualizeSolution(solution, bwConstraint, nbBlocks, solverName);
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

    
    private static void visualizeSolution(Map<Variable, Object> solution, ContrainteCroissante bwConstraint, int nbBlocks, String solverName) {
        BWState<Integer> state = makeBWState(solution, nbBlocks);
        
       
        SwingUtilities.invokeLater(() -> {
            BWIntegerGUI gui = new BWIntegerGUI(nbBlocks);
            JFrame frame = new JFrame("Configuration Croissante - " + solverName);
            frame.setSize(1000,1000);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.add(gui.getComponent(state));
            frame.pack();
            frame.setSize(1000,1000);
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);
        });
    }
}