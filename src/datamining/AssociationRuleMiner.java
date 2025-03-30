package datamining;
import modelling.*;
import java.util.*;


/**
 * Interface définissant les opérations pour un mineur de règles d'association.
 * Un mineur de règles d'association est responsable de l'extraction des règles d'association
 * fréquentes et fiables à partir d'une base de données transactionnelle,
 * en fonction de seuils de fréquence et de confiance minimaux.
 */


public interface AssociationRuleMiner {
    BooleanDatabase getDatabase();
    Set<AssociationRule> extract(float minFrequency, float minConfidence);
}
