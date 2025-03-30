package blocksworld;

import modelling.*;
import planning.*;
import java.util.*;

import javax.swing.JFrame;

import bwmodel.*;
import bwui.*;
import bwdemo.*;

/**
 * Cette classe teste différents planificateurs dans le contexte du monde des blocs.
 */
public class DemoPlanner {
    public static void main(String[] args){
        int numBlocks = 6;
        int numPiles = 3;
        BlockWorldActions monde = new BlockWorldActions(numBlocks, numPiles);

        // Configuration de l'état initial et de l'objectif pour les planificateurs A*, BFS et Dijkstra
        Map<Variable, Object> etatInitial = createInitialState(monde);
        Map<Variable, Object> objectif = createGoalState(monde);
        Set<Action> actions = monde.getAllActions();
        Goal goal = new BasicGoal(objectif);

        // Exécution des planificateurs
        System.out.println("------------------------------------ Test plan A* ----------------------------------------------");
        BwHeuristique1 heuristique = new BwHeuristique1(goal);
        Planner aStarPlanner = new AStarPlanner(etatInitial, actions, goal, heuristique);
        List<Action> planAStar = runPlanner("A*", aStarPlanner);

        System.out.println("------------------------------------ Test plan BFS ----------------------------------------------");
        Planner bfsPlanner = new BFSPlanner(etatInitial, actions, goal);
        List<Action> planBFS = runPlanner("BFS", bfsPlanner);

        System.out.println("------------------------------------ Test plan Dijkstra ----------------------------------------------");
        Planner dijkstraPlanner = new DijkstraPlanner(etatInitial, actions, goal);
        List<Action> planDijkstra = runPlanner("Dijkstra", dijkstraPlanner);
       
       System.out.println("------------------------------------ Test plan DFS ----------------------------------------------");
        // Configuration spécifique pour le planificateur DFS avec 4 blocs et 3 piles
        int numBlocksDFS = 4;
        BlockWorldActions mondeDFS = new BlockWorldActions(numBlocksDFS, numPiles);

        Map<Variable, Object> etatInitialDFS = createInitialStateDFS(mondeDFS);
        Map<Variable, Object> objectifDFS = createGoalStateDFS(mondeDFS);
        Set<Action> actionsDFS = mondeDFS.getAllActions();
        Goal goalDFS = new BasicGoal(objectifDFS);

        Planner dfsPlanner = new DFSPlanner(etatInitialDFS, actionsDFS, goalDFS);
        List<Action> planDFS = runPlanner("DFS", dfsPlanner);

        

        // Visualisation du plan A* avec 6 bloc et 3 pile 
        visualizePlan("Blocks World - A* Plan", numBlocks, numPiles, etatInitial, planAStar);

        // Visualisation du plan A* avec 6 bloc et 3 pile 
        visualizePlan("Blocks World - BFS Plan", numBlocks, numPiles, etatInitial, planBFS);

        // Visualisation du plan Bfs avec 6 bloc et 3 pile 
        visualizePlan("Blocks World - Dijkstra Plan", numBlocks, numPiles, etatInitial, planDijkstra);

        // Visualisation du plan DFS avec 4 blocs et 
        visualizePlan("Blocks World - DFS Plan", numBlocksDFS, numPiles, etatInitialDFS, planDFS);
    }

    public static Map<Variable, Object> createInitialState(BlockWorldActions monde) {
        Map<Variable, Object> etatInitial = new HashMap<>();

        // Configuration de l'état initial pour 6 blocs
        etatInitial.put(monde.getOnBlocks().get(0), -1); // Bloc 0 est sur pile -1
        etatInitial.put(monde.getOnBlocks().get(1), -2); // Bloc 1 est sur pile -2
        etatInitial.put(monde.getOnBlocks().get(2), -3); // Bloc 2 est sur pile -3
        etatInitial.put(monde.getOnBlocks().get(3), 0);  // Bloc 3 est sur Bloc 0
        etatInitial.put(monde.getOnBlocks().get(4), 1);  // Bloc 4 est sur Bloc 1
        etatInitial.put(monde.getOnBlocks().get(5), 2);  // Bloc 5 est sur Bloc 2

        // Blocs fixés
        for (int i = 0; i < 3; i++) {
            etatInitial.put(monde.getFixedBlocks().get(i), true);
        }
        for (int i = 3; i < 6; i++) {
            etatInitial.put(monde.getFixedBlocks().get(i), false);
        }

        // Liberté des piles
        for (int i = -1; i >= -3; i--) {
            etatInitial.put(monde.getFreePiles().get(i), false);
        }

        return etatInitial;
    }

    public static Map<Variable, Object> createGoalState(BlockWorldActions monde) {
        Map<Variable, Object> objectif = new HashMap<>();

        // Configuration de l'objectif pour 6 blocs
        objectif.put(monde.getOnBlocks().get(0), -1); // Bloc 0 sur pile -1
        objectif.put(monde.getOnBlocks().get(1), 0);  // Bloc 1 sur Bloc 0
        objectif.put(monde.getOnBlocks().get(2), 1);  // Bloc 2 sur Bloc 1
        objectif.put(monde.getOnBlocks().get(3), 2);  // Bloc 3 sur Bloc 2
        objectif.put(monde.getOnBlocks().get(4), 3);  // Bloc 4 sur Bloc 3
        objectif.put(monde.getOnBlocks().get(5), 4);  // Bloc 5 sur Bloc 4

        return objectif;
    }

    
    public static Map<Variable, Object> createInitialStateDFS(BlockWorldActions monde) {
        Map<Variable, Object> etatInitial = new HashMap<>();

        etatInitial.put(monde.getOnBlocks().get(0), -1); // Bloc 0 est sur pile -1
        etatInitial.put(monde.getOnBlocks().get(1), -2); // Bloc 1 est sur pile -2
        etatInitial.put(monde.getOnBlocks().get(2), 1);  // Bloc 2 est sur Bloc 1
        etatInitial.put(monde.getOnBlocks().get(3), 0);  // Bloc 3 est sur Bloc 0

        // Blocs fixés
        etatInitial.put(monde.getFixedBlocks().get(0), true); // 0 est indeplacable
        etatInitial.put(monde.getFixedBlocks().get(1), true); // 1 est indeplacable
        etatInitial.put(monde.getFixedBlocks().get(2), false); // 2 est deplacable
        etatInitial.put(monde.getFixedBlocks().get(3), false);// 3 est deplacable

        // Liberté des piles
        etatInitial.put(monde.getFreePiles().get(-1), false); // Pile -1 est occupée
        etatInitial.put(monde.getFreePiles().get(-2), false); // Pile -2 est occupée
        etatInitial.put(monde.getFreePiles().get(-3), true);  // Pile -3 est libre

        return etatInitial;
    }

    public static Map<Variable, Object> createGoalStateDFS(BlockWorldActions monde) {
        Map<Variable, Object> objectif = new HashMap<>();

        // Configuration de l'objectif pour 4 blocs et 3 piles
        objectif.put(monde.getOnBlocks().get(0), -1); // Bloc 0 sur pile -1
        objectif.put(monde.getOnBlocks().get(1), 0);  // Bloc 1 sur Bloc 0
        objectif.put(monde.getOnBlocks().get(2), 1);  // Bloc 2 sur Bloc 1
        objectif.put(monde.getOnBlocks().get(3), 2);  // Bloc 3 sur Bloc 2

        return objectif;
    }

    public static List<Action> runPlanner(String plannerName, Planner planner) {
        planner.activateNodeCount(true);
        long startTime = System.nanoTime();
        List<Action> plan = planner.plan();
        long endTime = System.nanoTime();

        if (plan != null && !plan.isEmpty()) {
            System.out.println("Plan trouvé avec " + plannerName + ":");
            int etape = 1;
            for (Action action : plan) {
                System.out.println("Étape " + etape + ": " + action);
                etape++;
            }
        } else {
            System.out.println("Pas de plan trouvé avec " + plannerName + "!");
        }
        int nodeCount = planner.getNodeCount();
        long duration = (endTime - startTime);
        System.out.println("Nœuds visités = " + nodeCount);
        System.out.println("Temps de calcul " + plannerName + ": " + duration / 1_000_000 + " ms.");

        return plan;
    }

    public static void visualizePlan(String title, int numBlocks, int numPiles, Map<Variable, Object> etatInitial, List<Action> plan) {
        // Initialisation de l'interface graphique
        BWIntegerGUI gui = new BWIntegerGUI(numBlocks);
        JFrame frame = new JFrame(title);
        BWState<Integer> bwState = makeBWState(etatInitial, numBlocks, numPiles);
        BWComponent<Integer> component = gui.getComponent(bwState);
        frame.add(component);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);

        
        Map<Variable, Object> state = new HashMap<>(etatInitial);
        for (Action a: plan) {
            try { Thread.sleep(1000); }
            catch (InterruptedException e) { e.printStackTrace(); }
            state = a.successor(state);
            component.setState(makeBWState(state, numBlocks, numPiles));
        }
        System.out.println("Simulation du plan terminée.");
    }

    public static BWState<Integer> makeBWState(Map<Variable, Object> newInstatiation, int nbrBlocks, int nbrStacks) {
    BWStateBuilder<Integer> builder = BWStateBuilder.makeBuilder(nbrBlocks);
    BlockWorldActions etatInitialAStar = new BlockWorldActions(nbrBlocks, nbrStacks);

    for (int i = 0; i < nbrBlocks; i++) {
        System.out.println("-------------------visualisation-------------------");
        System.out.println("Création du bloc " + i);

        // Récupérer la variable `onB`
        Variable onB = etatInitialAStar.getOnBlocks().get(i);
        if (onB != null && newInstatiation.containsKey(onB)) {
            // Si la variable est présente dans `newInstatiation`
            int under = (int) newInstatiation.get(onB);
            System.out.println("Le bloc " + onB.getName() + " est sur " + under);

            
            if (under >= 0) { 
                builder.setOn(i, under);
                System.out.println("Le bloc " + onB.getName() + " est placé sur un autre bloc " + under);
            } else {
                System.out.println("Le bloc " + onB.getName() + " est placé sur la pile " + under);
            }
        } else {
            System.out.println("Variable pour le bloc " + i + " est manquante dans l'initialisation.");
        }
    }
    
    return builder.getState();
}
}

