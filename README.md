# Projet de Graphe d'Artistes en Java

## Data
nombre d'artistes : 42711
nombre de mentions : 277234

## Description
Ce projet implémente un système de graphe en Java permettant d'analyser les connexions entre différents artistes musicaux basées sur les mentions présentes dans leurs pages Wikipedia. Le programme est capable de trouver le chemin optimal entre deux artistes en utilisant différents algorithmes de recherche de chemin.

## Fonctionnalités

- Construction d'un graphe à partir des données Wikipedia d'artistes
- Analyse des mentions entre artistes pour établir des connexions pondérées
- Recherche de chemins optimaux entre deux artistes quelconques
- Catégorisation des artistes selon leurs attributs (ex: "20th_century_english_singers")
- Calcul du coût total d'un chemin basé sur la pertinence des connexions
- Affichage détaillé des résultats incluant:
    - Longueur du chemin (nombre d'artistes)
    - Coût total du chemin
    - Liste complète des artistes constituant le chemin avec leurs catégories

## Structure du projet Java

Le projet est organisé selon le modèle de conception orienté objet et comprend les classes principales suivantes:

- **Artiste.java**: Classe représentant un artiste avec son nom et ses catégories
- **Graphe.java**: Classe principale pour la construction et gestion du graphe d'artistes
- **Lien.java**: Classe représentant la connexion pondérée entre deux artistes
- **RechercheChemins.java**: Classe implémentant les algorithmes de recherche de chemin
- **Chemin.java**: Classe représentant un chemin complet entre deux artistes
- **Main.java**: Point d'entrée du programme

## Algorithme

L'algorithme principal de recherche de chemin est basé sur [spécifier l'algorithme utilisé: Dijkstra, A*, etc.] et prend en compte:
- La pertinence des connexions (poids inversement proportionnel au nombre de mentions)
- Les catégories partagées entre artistes comme facteur d'optimisation
- La minimisation du coût total du chemin

## Exemple de sortie

```
Longueur du chemin : 5
Coût total du chemin : 4.25
Chemin :
The Beatles (musical_groups_established_in_1960)
Roy Orbison (20th_century_american_singers)
Robin Gibb (20th_century_english_singers;21st_century_english_singers)
Westlife (musical_groups_established_in_1998)
Mika (singer) (21st_century_english_singers)
Kendji Girac (21st_century_french_singers)
--------------------------
Longueur du chemin : 6
Coût total du chemin : 2.3402777777777777
Chemin :
The Beatles (musical_groups_established_in_1960)
Paul McCartney (20th_century_english_singers;21st_century_english_singers)
Kanye West (21st_century_american_rappers;21st_century_american_singers)
Nicki Minaj (21st_century_american_rappers)
Ariana Grande (21st_century_american_singers)
Mika (singer) (21st_century_english_singers)
Kendji Girac (21st_century_french_singers)
```

## Prérequis

- Java JDK 11 ou supérieur
- Maven ou Gradle (pour la gestion des dépendances)

## Installation

```bash
# Cloner le dépôt
git clone https://github.com/username/graphe-artistes.git
TODO
```

## Utilisation

```bash
TODO
```


### Options disponibles


## Format des données d'entrée

Le programme s'attend à recevoir un fichier JSON structuré comme suit:

```json
{
  "artistes": [
    {
      "nom": "The Beatles",
      "categories": ["musical_groups_established_in_1960"],
      "mentions": {
        "Paul McCartney": 35,
        "Roy Orbison": 3,
        "...": "..."
      }
    },
    {
      "nom": "Paul McCartney",
      "categories": ["20th_century_english_singers", "21st_century_english_singers"],
      "mentions": {
        "The Beatles": 42,
        "Kanye West": 5,
        "...": "..."
      }
    },
    "..."
  ]
}
```

## Dépendances


- JUnit (pour les tests unitaires)

## Documentation

La documentation JavaDoc peut être générée avec:

```bash
# Avec Maven
mvn javadoc:javadoc

# Avec Gradle
gradle javadoc
```


## Contribuer

Les contributions sont les bienvenues! N'hésitez pas à ouvrir une issue ou à soumettre une pull request.

## Licence

[Spécifier la licence, ex: MIT, GPL, etc.]