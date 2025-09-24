

## 1. Vue d’ensemble de la JVM (Java Virtual Machine)
La JVM est une machine virtuelle qui exécute les programmes Java compilés. Elle offre une couche d’abstraction entre le code Java et le matériel/système d’exploitation réels, garantissant la portabilité du code sur toutes les plateformes où la JVM est installée.

## 2. Architecture de la JVM
La JVM est composée de quatre zones principales : les registres, la pile (stack), le tas (heap) pour la mémoire dynamique gérée par le garbage collector, et la zone des méthodes (stockant le bytecode).
La pile conserve l’état de chaque appel de fonction et le tas gère l’allocation des objets dynamiques, automatiquement récupérée par le garbage collector.

## 3. Bytecode & Fichiers .class
Le bytecode est le langage intermédiaire entre le code source Java et le code machine, généré par le compilateur javac.
Les fichiers .class contiennent ce bytecode avec une structure standard (magic number, constant pool, flags d’accès, définitions de classe, fields, methods, attributes...).

## 4. ClassLoader (Chargeur de classes)
Le ClassLoader permet de charger dynamiquement des classes en mémoire.
On distingue trois types principaux : Bootstrap (charge les classes de base du JDK), Extension Loader (charge les extensions), System Loader (classes du classpath utilisateur).
La délégation s’effectue de l’enfant vers le parent afin d’assurer la cohérence et éviter la duplication des classes.

## 5. Processus de chargement et d'initialisation des classes
Lorsqu’une classe est nécessaire, la JVM vérifie si elle est déjà chargée, puis la recherche, la charge, la vérifie, la lie et l’initialise (exécution du bloc static).

## 6. Méthodes principales du ClassLoader
loadClass(name), findClass(name), defineClass(...)
Ces méthodes permettent la personnalisation du chargement des classes (depuis des fichiers, réseau, base de données…).

## 7. Exemples de Custom ClassLoader
On retrouve des exemples de chargeurs personnalisés qui lisent des classes à partir de sources non standards, avec une gestion des erreurs telles que ClassNotFoundException.

## 8. Mémoire Java : Stack & Heap
La pile (stack) gère les variables locales et chaque thread possède sa propre pile.
Le tas (heap) héberge les objets ; il est partagé et géré par le garbage collector.
La gestion de la mémoire automatique réduit fortement les risques de fuites de mémoire.

## 9. Garbage Collection (GC)
Le garbage collector recycle automatiquement la mémoire occupée par les objets non référencés (à laquelle aucune variable ne pointe encore).
Techniques de GC : Reference Counting (rarement utilisé à cause des références cycliques), Mark-and-Sweep (tracé des objets vivants à partir des racines), Compactage, Copying GC.

## 10. Optimisation avancée du GC
Mark-and-Sweep : marquage et suppression des objets, activation des finaliseurs si besoin.
Le compactage consiste à regrouper les objets vivants pour limiter la fragmentation.

## 11. Mécanismes d’exécution dans la JVM
Interpréteur (lit et exécute le bytecode directement, plus lent), JIT Compiler (compilation à la volée pour accélérer l’exécution), JNI (interface pour appeler du code natif).

## 12. JDK, JRE et JVM
JDK inclut le JRE ainsi que les outils de développement (javac, debugger…).
JRE contient la JVM et les bibliothèques standard.
La JVM est le cœur qui exécute le bytecode Java.