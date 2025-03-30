-----------------------------------------------
 README - BlockWorld
 ----------------------------------------------- 
 
 ------------------------------------------------
    Guide d'Utilisation du Script d'Exécution:
-------------------------------------------------

Ce projet comprend un script Bash runblockworld.sh qui facilite la compilation et l'exécution des différentes parties de BlockWorld. Le script offre un menu interactif permettant de sélectionner et d'exécuter différentes démonstrations ou fonctionnalités du projet.

Utilisation du Menu

    Sélectionnez une Option : Entrez le numéro correspondant à l'option que vous souhaitez exécuter (de 1 à 6) et appuyez sur Entrée.
    Attendez la Compilation : Le script compilera les fichiers nécessaires.
    Exécution : Si la compilation réussit, le programme s'exécutera.
    Retour au Menu Principal : Après l'exécution, appuyez sur Entrée pour revenir au menu.

Détails des Options

    Exécuter DemoPlanner
        Lance une démonstration du planificateur dans le monde des blocs.

    Exécuter Contrainte Croissante
        Exécute une démonstration mettant en œuvre la contrainte croissante.

    Exécuter Contrainte Régulière
        Exécute une démonstration mettant en œuvre la contrainte régulière.

    Exécuter Croissante et Régulière
        Exécute une démonstration combinant les contraintes croissante et régulière.

    Exécuter BlockworldDatamining
        Lance une démonstration relative au data mining dans le monde des blocs.

    Quitter
        Ferme le script.

----------------------------------------
     Résolution des Problèmes
----------------------------------------

    La Compilation Échoue :
        Vérifiez que tous les fichiers Java sont correctement placés dans les dossiers correspondants.
        Assurez-vous que les bibliothèques blocksworld.jar et bwgenerator.jar sont présentes dans le dossier lib.
        Consultez les messages d'erreur affichés pour plus de détails.

    Le Script Ne S'Exécute Pas :
        Assurez-vous d'avoir donné les permissions d'exécution au script (chmod +x runblockworld.sh).
        Vérifiez que vous exécutez le script depuis le bon répertoire (la racine).

    Choix Invalide :
        Si vous entrez un choix invalide, le script vous invitera à réessayer
