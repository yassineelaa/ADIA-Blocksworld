#!/bin/bash

project_dir="../"

function compile_and_run() {
  main_class=$1
  cd "src"
  javac -d "../build" -cp "../lib/blocksworld.jar:../lib/bwgenerator.jar" planning/*.java modelling/*.java blocksworld/*.java cp/*.java datamining/*.java
  if [ $? -eq 0 ]; then
    java -cp "../build:../lib/blocksworld.jar:../lib/bwgenerator.jar" $main_class
  else
    echo "La compilation a échoué."
  fi
  cd "$project_dir"
}

while true; do
  echo "----------------------------------------"
  echo "      Menu du Projet BlockWorld"
  echo "----------------------------------------"
  echo "Sélectionnez une option:"
  echo "1) Exécuter DemoPlanner"
  echo "2) Exécuter Contrainte Croissante"
  echo "3) Exécuter Contariant Reguliere"
  echo "4) Exécuter Croissante et Reguliere "
  echo "5) Exécuter BlockworldDatamining"
  echo "6) Quitter"
  echo "----------------------------------------"

  read -p "Entrez votre choix [1-6]: " choice

  case $choice in
    1)
      compile_and_run "blocksworld.DemoPlanner"
      ;;
    2)
      compile_and_run "blocksworld.DemoConstraintCrois"
      ;;
    3)
      compile_and_run "blocksworld.DemoConstraintReg"
      ;;
    4)
      compile_and_run "blocksworld.DemoCspMain"
      ;;
    5)
      compile_and_run "blocksworld.DemoBlockworldDatamining"
      ;;
    6)
      echo "Au revoir!"
      exit 0
      ;;
    *)
      echo "Choix invalide, veuillez réessayer."
      ;;
  esac

  echo
  read -p "Appuyez sur Entrée pour revenir au menu principal..."
done

