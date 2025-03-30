package datamining;
import java.util.*;
import modelling.*;

/**
 * Classe représentant une règle d'association dans une base de données transactionnelle.
 * Une règle d'association est définie par une prémisse et une conclusion, 
 * ainsi que par des mesures de fréquence et de confiance.
 */
public class AssociationRule {

  
    private Set<BooleanVariable> premise;
    private Set<BooleanVariable> conclusion;
    private float frequency;
    private float confidence;

    /**
     * Constructeur pour initialiser une règle d'association avec sa prémisse, sa conclusion,
     * sa fréquence et sa confiance.
     *
     * @param premise    La prémisse de la règle d'association.
     * @param conclusion La conclusion de la règle d'association.
     * @param frequency  La fréquence de cette règle dans la base de données.
     * @param confidence La confiance associée à cette règle.
     */
    public AssociationRule(Set<BooleanVariable> premise, Set<BooleanVariable> conclusion,
                           float frequency, float confidence) {
        this.premise = premise;
        this.conclusion = conclusion;
        this.frequency = frequency;
        this.confidence = confidence;
    }

    // Accesseurs pour obtenir les attributs de la règle d'association

    public Set<BooleanVariable> getPremise() {
        return premise;
    }

    public Set<BooleanVariable> getConclusion() {
        return conclusion;
    }

    public float getFrequency() {
        return frequency;
    }

    public float getConfidence() {
        return confidence;
    }
    @Override
    public String toString(){
        return "premise : " + premise + ", conclusion : " + conclusion + ", frequency : " + frequency + ", confidence : " + confidence;
    }
}
