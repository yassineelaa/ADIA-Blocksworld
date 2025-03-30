package blocksworld;

import datamining.*;


/**
 * Classe de démonstration pour l'extraction de motifs fréquents et de règles d'association
 * dans le monde des blocs. Elle génère une base de données d'états, extrait les motifs fréquents
 * et les règles d'association, et affiche les résultats.
 */
import java.util.*;

public class DemoBlockworldDatamining {

    public static void main(String[] args) {
        int numberOfBlocks = 4; 
        int numberOfPiles = 2;  
        BlocksWorldDatamining extractor = new BlocksWorldDatamining(numberOfBlocks, numberOfPiles);
        
        // Définir le nombre de transactions (états) à générer
        int nbTransactions = 10000;
        // Générer une base de données booléenne contenant les états possibles
        BooleanDatabase database = extractor.generateBooleanDatabase(nbTransactions);
		System.out.println(database);

        // Définir la fréquence minimale pour qu'un motif soit considéré comme fréquent
        float minFrequency = 2.0f / 3.0f;
        Apriori miner = new Apriori(database);
        Set<Itemset> frequentItemsets = miner.extract(minFrequency);
       
        // Définir la confiance minimale pour qu'une règle soit pertinente
        float minConfidence = 95.0f / 100.0f;
        // Utiliser l'algorithme BruteForceAssociationRuleMiner pour extraire les règles d'association
        BruteForceAssociationRuleMiner ruleMiner = new BruteForceAssociationRuleMiner(database);
        Set<AssociationRule> associationRules = ruleMiner.extract(minFrequency, minConfidence);

        // Affichage des motifs fréquents
		System.out.println("MinFrequency :" + minFrequency + "        |           MinConfidence :" + minConfidence);
        System.out.println("----------------------------------------- Frequent Itemsets : ---------------------------------------------------------");
        for (Itemset itemset : frequentItemsets) {
            System.out.println(itemset);
        }

        //Affichage des règles d'association
        System.out.println("\n---------------------------------------- Association Rules : ---------------------------------------------------");
        for (AssociationRule rule : associationRules) {
            System.out.println(rule);
        }
    }
}
