# Exercise 1 - Modélisation d'un Parking

## 📝 Description du Problème

On souhaite modéliser un **parking** (classe `CarPark`) avec les caractéristiques suivantes :

- Le parking possède un nombre limité de places défini dans la variable `capacity`
- Chaque **voiture** est modélisée par un thread (classe `Cars`)
- Chaque voiture suit ce processus :
  1. Essaye d'entrer dans le parking (méthode `arrive`)
  2. Attend entre 0 et 20 secondes
  3. Sort du parking (méthode `depart`)

La méthode principale est définie dans la classe `CarPark`. Elle crée :
- Un parking (instance de la classe `CarPark`)
- 100 voitures (en espaçant les créations de 0 à 5 secondes)

## 🎯 Questions

### 1. Sans Sémaphore
Compléter les méthodes `arrive` et `depart` de la classe `CarPark` **sans utiliser les sémaphores**. La classe `Cars` est fournie.

### 2. Avec Sémaphore
La même question mais **avec sémaphore**.

## 🔧 Concepts Techniques Utilisés

### Synchronisation sans Sémaphore
- **`synchronized`** : permet de protéger l'accès concurrent à la variable `capacity`
- **`wait()`** : une voiture se met en attente si le parking est plein
- **`notifyAll()`** : quand une voiture sort (`depart`), toutes les voitures en attente sont réveillées et réessayent d'entrer
- **`capacity`** : 
  - Décrémente quand une voiture entre
  - Incrémente quand elle sort

## 🚗 Simulation

La simulation crée **100 voitures**, espacées de 0 à 5 secondes, qui essayent d'entrer dans un parking de **4 places**.

## 📁 Fichiers

- `CarPark.java` - Classe principale du parking
- `Cars.java` - Classe représentant les voitures (threads)

## 🚀 Exécution
Avec dossier Sync (sans sémaphore) :
```bash
javac *.java
java CarPark
```
Avec dossier Semaphore (avec sémaphore) :
```bash
javac *.java
java CarParkSema
```