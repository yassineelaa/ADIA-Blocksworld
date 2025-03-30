# ADIA-Blocksworld

## Table des Matières / Table of Contents
1. [Description (FR)](#description-fr)
2. [Objectifs (FR)](#objectifs-fr)
3. [Étapes du Projet (FR)](#etapes-fr)
4. [Guide d’Utilisation du Script (FR)](#guide-script-fr)
5. [Description (EN)](#description-en)
6. [Objectives (EN)](#objectives-en)
7. [Project Phases (EN)](#phases-en)
8. [Script Usage Guide (EN)](#guide-script-en)
9. [Comment Contribuer / How to Contribute](#contribute)

---

## Description (FR) <a name="description-fr"></a>
Le projet **ADIA-Blocksworld** est une simulation destinée à illustrer des concepts d’intelligence artificielle, en particulier :
- Les algorithmes de planification
- Les problèmes de satisfaction de contraintes
- L’extraction de connaissances à partir de configurations d’un monde de blocs

Ce projet s’appuie sur le modèle classique du **monde des blocs** (Blockworld). Des blocs sont empilés en piles, et l’objectif est de passer d’une configuration initiale à une configuration finale spécifique, en utilisant un ensemble d’actions prédéfini.

---

## Objectifs (FR) <a name="objectifs-fr"></a>
1. **Planification de tâches**  
   Déterminer une séquence d’actions permettant de résoudre le problème de déplacement de blocs.

2. **Satisfaction de contraintes**  
   Résoudre des problèmes de satisfaction de contraintes où les configurations doivent respecter certaines règles de placement.

3. **Extraction de connaissances**  
   Appliquer des algorithmes pour extraire des motifs et des règles d’association à partir de configurations du monde des blocs.

---

## Étapes du Projet (FR) <a name="etapes-fr"></a>
1. **Représentation des blocs et des piles**  
   - Modélisation des blocs et des piles à l’aide de variables et de contraintes.  
   - Création d’une classe représentant un monde de blocs avec des actions (ex. déplacer un bloc d’une pile à une autre).

2. **Implémentation des contraintes**  
   - Définition des contraintes régissant la position des blocs (ex. impossibilité de placer un bloc sur un autre bloc déjà occupé, nécessité d’avoir certaines piles vides pour un déplacement).  
   - Ajout de contraintes binaires pour définir la validité des configurations (éviter les cycles, garantir l’intégrité des piles).

3. **Planification**  
   - Développement de planificateurs (ex. `DFSPlanner`, `BFSPlanner`, `AStar`, `Dijikstra`) pour résoudre des problèmes de planification.  
   - Exploration de solutions à l’aide d’algorithmes de recherche et d’optimisation.

4. **Satisfaction de contraintes**  
   - Utilisation de techniques (ex. backtracking, MACSolver) pour résoudre des problèmes où l’on doit respecter un ensemble de contraintes tout en trouvant des configurations valides.

5. **Extraction de connaissances**  
   - Application d’algorithmes d’extraction de motifs (ex. Apriori) afin de découvrir des relations entre différentes configurations et de générer des règles d’association.

---

## Guide d’Utilisation du Script (FR) <a name="guide-script-fr"></a>
Ce projet comprend un script Bash `runblockworld.sh` qui facilite la compilation et l’exécution des différentes parties de BlockWorld. Le script offre un menu interactif permettant de sélectionner et d’exécuter différentes démonstrations ou fonctionnalités du projet.

### Utilisation du Menu

1. **Sélectionnez une Option**  
   Entrez le numéro correspondant à l’option que vous souhaitez exécuter (de **1** à **6**) et appuyez sur **Entrée**.  
2. **Attendez la Compilation**  
   Le script compilera automatiquement les fichiers nécessaires.  
3. **Exécution**  
   Si la compilation réussit, le programme s’exécutera.  
4. **Retour au Menu Principal**  
   Après l’exécution, appuyez sur **Entrée** pour revenir au menu.

### Détails des Options

1. **Exécuter DemoPlanner**  
   Lance une démonstration du planificateur dans le monde des blocs.

2. **Exécuter Contrainte Croissante**  
   Exécute une démonstration mettant en œuvre la contrainte « croissante ».

3. **Exécuter Contrainte Régulière**  
   Exécute une démonstration mettant en œuvre la contrainte « régulière ».

4. **Exécuter Croissante et Régulière**  
   Exécute une démonstration combinant les contraintes « croissante » et « régulière ».

5. **Exécuter BlockworldDatamining**  
   Lance une démonstration de data mining appliquée au monde des blocs.

6. **Quitter**  
   Ferme le script.

### Résolution des Problèmes

- **La Compilation Échoue**  
  - Vérifiez que tous les fichiers Java sont correctement placés dans les dossiers correspondants.  
  - Assurez-vous que les bibliothèques `blocksworld.jar` et `bwgenerator.jar` sont présentes dans le dossier `lib`.  
  - Consultez les messages d’erreur affichés pour plus de détails.

- **Le Script Ne S’Exécute Pas**  
  - Assurez-vous d’avoir donné les permissions d’exécution au script :  
    ```bash
    chmod +x runblockworld.sh
    ```
  - Vérifiez que vous exécutez le script depuis le bon répertoire (la racine du projet).

- **Choix Invalide**  
  - Si vous entrez un choix invalide, le script vous invitera simplement à réessayer.

---

## Description (EN) <a name="description-en"></a>
The **ADIA-Blocksworld** project is a simulation designed to illustrate concepts in artificial intelligence, particularly:
- Planning algorithms
- Constraint satisfaction problems
- Knowledge extraction from blockworld configurations

This project is based on the **classic Blockworld** model, where blocks are stacked in piles. The objective is to transition from an initial configuration to a specific target configuration using a predefined set of actions.

---

## Objectives (EN) <a name="objectives-en"></a>
1. **Task Planning**  
   Determining a sequence of actions to solve the block-moving problem.

2. **Constraint Satisfaction**  
   Solving constraint satisfaction problems where configurations must respect certain placement rules.

3. **Knowledge Extraction**  
   Applying algorithms to extract patterns and association rules from block configurations.

---

## Project Phases (EN) <a name="phases-en"></a>
1. **Representation of Blocks and Piles**  
   - Modeling blocks and piles using variables and constraints.  
   - Creating a class that represents a blockworld with actions (e.g., moving a block from one pile to another).

2. **Constraint Implementation**  
   - Defining the constraints that govern block positioning (e.g., you cannot place a block on top of an occupied block, some piles must be empty to move a block).  
   - Adding binary constraints to ensure valid configurations (avoiding cycles, ensuring the integrity of piles).

3. **Planning**  
   - Developing planners (e.g., `DFSPlanner`, `BFSPlanner`) to solve planning problems.  
   - Exploring solutions with search and optimization algorithms.

4. **Constraint Satisfaction**  
   - Using techniques (e.g., backtracking) to solve problems where you must satisfy a set of constraints to find valid configurations.

5. **Knowledge Extraction**  
   - Applying pattern-extraction algorithms (e.g., Apriori) to discover relationships between various configurations and to generate association rules.

---

## Script Usage Guide (EN) <a name="guide-script-en"></a>
The project provides a Bash script named `runblockworld.sh` to facilitate compiling and running different parts of BlockWorld. It offers an interactive menu to select and run various demos or functionalities.

### Menu Usage

1. **Select an Option**  
   Enter the number corresponding to the option (1 through 6) and press **Enter**.
2. **Wait for Compilation**  
   The script will compile the necessary files.
3. **Execution**  
   If the compilation is successful, the program will run.
4. **Return to Main Menu**  
   After execution, press **Enter** to return to the menu.

### Options Details

1. **Run DemoPlanner**  
   Launches a demonstration of the planner in the block world.

2. **Run Contrainte Croissante**  
   Runs a demo implementing the “croissante” (increasing) constraint.

3. **Run Contrainte Régulière**  
   Runs a demo implementing the “régulière” (regular) constraint.

4. **Run Both Croissante and Régulière**  
   Runs a demo combining both constraints.

5. **Run BlockworldDatamining**  
   Launches a data mining demonstration in the block world.

6. **Quit**  
   Exits the script.

### Troubleshooting

- **Compilation Fails**  
  - Check that all Java files are properly placed in their respective directories.  
  - Make sure `blocksworld.jar` and `bwgenerator.jar` are in the `lib` folder.  
  - Review the error messages for more details.

- **Script Does Not Run**  
  - Verify that the script has execution permissions:
    ```bash
    chmod +x runblockworld.sh
    ```
  - Ensure you are running the script from the correct (root) directory.

- **Invalid Choice**  
  - If you enter an invalid choice, the script will prompt you to try again.

---

## Comment Contribuer / How to Contribute <a name="contribute"></a>
Les contributions sont les bienvenues ! / Contributions are welcome!

- **Fork** ce dépôt, créez une branche, et soumettez une *Pull Request* /  
  **Fork** this repository, create a branch, and submit a *Pull Request*.
- **Signalez un bug** en ouvrant une *Issue* /  
  **Report a bug** by opening an *Issue*.

Merci d’avance pour votre aide ! / Thank you in advance for your help!

---

**© 2025 ADIA-Blocksworld**
