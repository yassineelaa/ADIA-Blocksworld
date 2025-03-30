package blocksworld;

import modelling.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Initialiser le nombre de blocs et de piles
        int nbBlocks = 7;
        int nbPiles = 3;

        // Créer une instance de BlockWorldConstraints pour obtenir les variables
        BlockWorldConstraints baseConstraints = new BlockWorldConstraints(nbBlocks, nbPiles);

        // Créer des instances des classes de contraintes
        ContrainteCroissante cc = new ContrainteCroissante(nbBlocks, nbPiles);
        RegularConstraints rc = new RegularConstraints(nbBlocks, nbPiles);

        // Récupérer les variables
        Map<Integer, Variable> onBlocks = baseConstraints.getOnBlocks();
        Map<Integer, BooleanVariable> fixedBlocks = baseConstraints.getFixedBlocks();
        Map<Integer, BooleanVariable> freePiles = baseConstraints.getFreePiles();

        // Liste pour stocker les configurations
        List<Map<Variable, Object>> configurations = new ArrayList<>();

        // --- Configuration 1: Contraintes de croissance et de régularité satisfaites ---
        Map<Variable, Object> config1 = new HashMap<>();
        config1.put(onBlocks.get(0), -1); // Bloc 0 sur pile -1
        config1.put(onBlocks.get(1), 0);  // Bloc 1 sur bloc 0
        config1.put(onBlocks.get(2), 1);  // Bloc 2 sur bloc 1
        config1.put(onBlocks.get(3), 2);  // Bloc 2 sur bloc 1
        config1.put(onBlocks.get(4), 3);  // Bloc 2 sur bloc 1
        config1.put(onBlocks.get(5), 4);  // Bloc 2 sur bloc 1
        config1.put(onBlocks.get(6), -2); // Bloc 3 sur pile -2


        // Blocs fixés pour config1
        config1.put(fixedBlocks.get(0), true);
        config1.put(fixedBlocks.get(1), true);
        config1.put(fixedBlocks.get(2), true);
        config1.put(fixedBlocks.get(3), true);
        config1.put(fixedBlocks.get(4), true);
        config1.put(fixedBlocks.get(5), false);
        config1.put(fixedBlocks.get(6), false);

        // Piles libres pour config1
        
        baseConstraints.updateFreePiles(config1);
        configurations.add(config1);
        
        // --- Configuration 2: Contraintes de croissance non satisfaites, régularité satisfaites ---
        Map<Variable, Object> config2 = new HashMap<>();
        config2.put(onBlocks.get(3), -1); 
        config2.put(onBlocks.get(2), 3);  
        config2.put(onBlocks.get(1), 2);  
        config2.put(onBlocks.get(4), -2); 
        config2.put(onBlocks.get(5), 4);
        config2.put(onBlocks.get(6), 5);
        config2.put(onBlocks.get(0), -3);

        

        // Blocs fixés pour config2
        config2.put(fixedBlocks.get(2), true);
        config2.put(fixedBlocks.get(1), false);
        config2.put(fixedBlocks.get(0), false);
        config2.put(fixedBlocks.get(3), true);
        config2.put(fixedBlocks.get(4), true);
        config2.put(fixedBlocks.get(5), true);
        config2.put(fixedBlocks.get(6), false);


        // Piles libres pour config2
        baseConstraints.updateFreePiles(config2);

        
        configurations.add(config2);

        // --- Configuration 3: Contraintes de croissance satisfaites, régularité non satisfaites ---
        Map<Variable, Object> config3 = new HashMap<>();
        config3.put(onBlocks.get(0), -1); 
        config3.put(onBlocks.get(2), 0);  
        config3.put(onBlocks.get(3), 2);  
        config3.put(onBlocks.get(1), -2);  
        config3.put(onBlocks.get(6), -3);  
        config3.put(onBlocks.get(4), 1);  
        config3.put(onBlocks.get(5), 4);  

        // Blocs fixés pour config3
        config3.put(fixedBlocks.get(0), true);
        config3.put(fixedBlocks.get(1), true);
        config3.put(fixedBlocks.get(2), true);
        config3.put(fixedBlocks.get(3), false);
        config3.put(fixedBlocks.get(5), false);
        config3.put(fixedBlocks.get(6), false);
        config3.put(fixedBlocks.get(4), true);

        // Piles libres pour config3
        baseConstraints.updateFreePiles(config3);

        configurations.add(config3);
        
        // --- Tester les configurations ---
        int configNum = 1;
        for (Map<Variable, Object> config : configurations) {
            System.out.println("Test de la configuration " + configNum + " :");

            // Afficher la configuration
            for (Map.Entry<Variable, Object> entry : config.entrySet()) {
                System.out.println(entry.getKey().getName() + " = " + entry.getValue());
            }

            // Vérifier les contraintes de base
            boolean isValidBase = baseConstraints.isConfigSatisfyConstraints(config);
            System.out.println("Contraintes de base satisfaites : " + isValidBase);

            // Vérifier les contraintes de croissance
            boolean isValidCroissante = cc.isConfigCroissante(config);
            System.out.println("Contraintes de croissance satisfaites : " + isValidCroissante);

            // Vérifier les contraintes de régularité
            boolean isValidReguliere = rc.isConfigReg(config);
            System.out.println("Contraintes de régularité satisfaites : " + isValidReguliere);

            System.out.println();
            configNum++;
        }
    }
}






