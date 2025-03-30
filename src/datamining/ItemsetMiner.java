package datamining;
import modelling.*;
import java.util.*;


 /** 
 * Interface définissant les opérations pour un mineur d'itemsets fréquents.
 * Un itemset miner est chargé d'extraire les itemsets fréquents d'une base de données
 * transactionnelle en fonction d'une fréquence minimale spécifiée.
 */


public interface ItemsetMiner{

BooleanDatabase getDatabase();
Set<Itemset> extract(float minFrequency);




}