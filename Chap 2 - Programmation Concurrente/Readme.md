# Chapitre 2 - Programmation Concurrente en Java

## Introduction

Ce chapitre couvre les concepts fondamentaux de la programmation concurrente en Java, mettant l'accent sur les threads et leur gestion.

## 1. Présentation et Définitions

### Qu'est-ce que la concurrence ?

La concurrence est l'exécution simultanée de plusieurs instructions/programmes. Elle peut être réalisée de deux manières :

- Via plusieurs cœurs (parallélisme réel)
- Via un mono-processeur (parallélisme simulé par alternance)

### Caractéristiques de Java

- Langage orienté objets
- Conçu nativement pour la programmation concurrente
- Support intégré du multi-tâches

## 2. Applications Pratiques

### Exemple : Le Navigateur Web

Le navigateur web illustre parfaitement la programmation concurrente en gérant simultanément :

- Vérification des mises à jour
- Connexions multiples aux serveurs
- Téléchargements en parallèle

## 3. Comparaison des Approches

### Programmation Séquentielle

```java
// Exemple d'exécution séquentielle
file.read();      // Attend la fin de la lecture
compute();        // Puis calcule
display();        // Enfin affiche
```

**Caractéristiques** :
- Instructions exécutées une par une
- Simple à comprendre et déboguer
- Peut bloquer sur des opérations lentes

### Programmation Concurrente

```java
// Exemple d'exécution concurrente
Thread readThread = new Thread(() -> file.read());
Thread computeThread = new Thread(() -> compute());
readThread.start();
computeThread.start();
```

**Avantages** :
- Exécution parallèle des tâches
- Meilleure réactivité
- Utilisation optimale du CPU

## 4. Modèles de Concurrence

### A. Programmes Séparés

**Caractéristiques** :
- Isolation complète
- Gestion simple
- Communication limitée

### B. Programme Multi-threads

**Caractéristiques** :
- Threads = tâches légères
- Mémoire partagée
- Communication facilitée
- Économie de ressources

## 5. Threads en Java

### A. Création d'un Thread

```java
// Méthode 1 : Extends Thread
class MyThread extends Thread {
    public void run() {
        for (int i = 10; i > 0; i--) {
            System.out.println("Compte à rebours : " + i);
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                // Gestion de l'interruption
            }
        }
        System.out.println("Go !");
    }
}

// Méthode 2 : Implements Runnable
class MyRunnable implements Runnable {
    public void run() {
        // Code du thread
    }
}
```

### B. États d'un Thread

1. **NEW** : Thread créé
   - État initial après création
   - Pas encore démarré

2. **RUNNABLE** : Prêt à l'exécution
   - En cours d'exécution ou prêt à être exécuté
   - Attend son tour de CPU

3. **BLOCKED** : En attente de ressource
   - Attend qu'une ressource se libère
   - Dans un bloc synchronized

4. **WAITING** : En attente de signal
   - Attend une notification
   - Sans limite de temps

5. **TIMED_WAITING** : En attente temporisée
   - Attend pour une durée définie
   - Ex: Thread.sleep()

6. **TERMINATED** : Terminé
   - A fini son exécution
   - Ne peut plus être redémarré

```java
// Exemple de gestion d'état
Thread t = new Thread();
System.out.println(t.getState()); // NEW
t.start();
System.out.println(t.getState()); // RUNNABLE
```

### C. Synchronisation

```java
// Exemple de synchronisation
synchronized(lock) {
    // Accès exclusif à une ressource
}

// Exemple de pause
Thread.sleep(1000); // Pause de 1 seconde
```

## 6. Bonnes Pratiques

### 1. Quand utiliser les threads

- Pour les tâches indépendantes
- Pour les opérations longues
- Pour garder une interface réactive

### 2. Points de vigilance

- Bien synchroniser les accès aux ressources partagées
- Gérer correctement les états des threads
- Éviter les situations de blocage (deadlocks)

### 3. Débogage

- Utiliser `Thread.getState()` pour diagnostiquer
- Logger les changements d'état importants
- Surveiller les situations de blocage

## 7. Exemple Complet

```java
public class ThreadExample {
    public static void main(String[] args) {
        // Création d'un thread avec Runnable
        Runnable task = () -> {
            try {
                System.out.println("Thread démarré");
                Thread.sleep(2000);
                System.out.println("Thread terminé");
            } catch (InterruptedException e) {
                System.out.println("Thread interrompu");
            }
        };

        // Lancement du thread
        Thread thread = new Thread(task);
        thread.start();

        // Affichage de l'état
        System.out.println("État du thread : " + thread.getState());
    }
}
```

