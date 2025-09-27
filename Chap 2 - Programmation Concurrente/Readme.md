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

Il existe deux méthodes principales pour créer un thread :

#### Méthode 1 : Extends Thread

```java
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
```

#### Méthode 2 : Implements Runnable

```java
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
   - Ex: `Thread.sleep()`

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

## 8. Concepts clés

### Premier thread et fonction main

- Tout programme Java commence par un thread principal qui exécute `main()`
- Dans les interfaces graphiques, il existe des threads pour :
  - L'attente utilisateur
  - Le redessin des composants

### Création de threads en Java

- Création via la classe `Thread` ou l'interface `Runnable`
- Un objet `Thread` est une "télécommande" permettant de contrôler :
  - Démarrage
  - Priorité
  - Synchronisation

## 9. Interface Runnable

### Méthode essentielle

```java
public void run();
```

- La méthode `run()` contient la tâche à réaliser par le thread
- C'est l'équivalent de la méthode `main()` pour le thread

### Démarrage

- Un thread est inactif jusqu'à l'appel de `start()`
- `start()` déclenche l'exécution de `run()`

## 10. Exemples de Création et Démarrage

### Via Runnable (orienté objet)

```java
class Animation implements Runnable {
    void run() {
        while (true) {
            // Dessine les frames
            repaint();
        }
    }
}

Animation happy = new Animation("Mr.Happy");
Thread myThread = new Thread(happy);
myThread.start();
```

### Objet autonome

```java
class Animation implements Runnable {
    Thread myThread;
    
    Animation(String name) {
        myThread = new Thread(this);
        myThread.start();
    }
    
    public void run() {
        // Code d'animation
    }
}
```

### Héritage de Thread

```java
class Animation extends Thread {
    public void run() {
        // Code d'animation
    }
}

Animation fun = new Animation("fun");
fun.start();
```

## 11. Pourquoi préférer Runnable ?

**Avantages** :
- Design objet cohérent
- Possibilité d'hériter d'autres classes (`JFrame`, `JLabel`, etc.)
- `run()` peut accéder aux membres privés de la classe
- Structure objet plus flexible

## Points à retenir

1. Toujours séparer :
   - La logique d'exécution (méthode `run`)
   - Le contrôle de thread

2. Préférer l'interface `Runnable` à l'héritage de `Thread` pour :
   - Une meilleure conception orientée objet
   - Plus de flexibilité
   - Une meilleure maintenabilité

## 12. Résumé des Concepts Avancés

### 1. Processus & Threads

**Les Processus** :
- Définition : Un programme en cours d’exécution.
- Java :
  - `Runtime.getRuntime().exec()` : lance un nouveau processus.
  - `ProcessBuilder` : gestion fine (environnement, répertoire, redirections de flux).
  - Gestion des processus fils par `Process` (`waitFor()`, `destroy()`, etc.).
  - Exécution de commandes externes via `exec`.

**Les Threads (Processus légers)** :
- Plusieurs threads = plusieurs flux d’exécution au sein d’un même processus Java (un seul espace mémoire partagé).
- Contrôle sur les threads : priorité, interruption, ordonnancement via la JVM.
- Thread courant : `Thread.currentThread()` permet de manipuler le thread exécutant.

**Exemple Utilisation de Thread** :
```java
Thread t = Thread.currentThread();
t.setName("MonThread");
Thread.sleep(1000); // Pause d'une seconde
```

### 2. API Java pour la Concurrence

**`java.util.concurrent`** :
- **Executor** : déconnecte la soumission de l’exécution des tâches.
  - Implémentations : thread pool, I/O asynchrone...
  - Soumission : `void execute(Runnable command)`
  - Planification, priorités, délais.
- **ExecutorService** : contrôle avancé (planification, terminaison…).
  - Nouvelles interfaces : `Callable<T>` (task avec retour et exceptions).
  - Soumission : `submit(Callable<T> task)` ou `submit(Runnable task)`.
  - Renvoie un `Future<T>` (résultat futur d’une tâche asynchrone).

**Exemple d’exécution avec Future** :
```java
ExecutorService executor = Executors.newCachedThreadPool();
Future<Long> future = executor.submit(() -> { /* code */ return System.nanoTime(); });
executor.shutdown();
future.get(); // Récupération (bloquante)
```

- `invokeAll` : exécute plusieurs tâches, retourne leur liste de futures.
- `invokeAny` : retourne le résultat de la première qui termine sans exception.
- `shutdown()` / `shutdownNow()` : gestion de la terminaison du pool.

**Planification Avancée** :
- `ScheduledExecutorService` : planifie dans le temps (`schedule`, `scheduleAtFixedRate`, etc.).
- Gestion des délais : interface `Delayed`, unités `TimeUnit`.

### 3. Synchronisation et Accès Critique

**Section Critique & Exclusion Mutuelle** :
- Objectif : garantir l’accès exclusif aux ressources critiques.
- Mot-clé `volatile` (Java) :
  - Synchronisation mémoire pour des simples types partagés, mais ne garantit pas l’atomicité des opérations combinées (ex : `++`, `--` entre threads).

**Verrous & Synchronisation** :
- Les verrous (mutex) : permettent la protection d’une section critique.
- Exemples : `synchronized`, objets `Lock`, `ReentrantLock`.
- Sémaphores (comptent les ressources disponibles, gestion du « passage » thread).
- Moniteurs : primitives d’attente/notification (`wait()`, `notify()`) intégrées (en Java, chaque objet peut servir de moniteur avec `synchronized`, `wait()`, `notify()`).

### 4. Interblocages (Deadlocks) & Starvation

- **Définition** : Blocage mutuel où plusieurs threads attendent indéfiniment une ressource détenue par un autre.
- **Prévention** : Ordre d’acquisition des verrous, timeout, détection.
- **Famines (starvation)** : Thread jamais servi, typiquement par mauvaise politique ou par priorité mal équilibrée.

### 5. Problèmes Classiques

**Producteur-Consommateur** :
- Deux types de threads partagent un tampon : les producteurs déposent, les consommateurs retirent.
- Synchronisation nécessaire (mutex, sémaphores, moniteurs).

**Lecteurs-Rédacteurs (Readers-Writers)** :
- Plusieurs threads lecteurs possible si aucun rédacteur ; seul un rédacteur autorisé en même temps.
- Gestion d’équité et de priorité selon le scénario.

**Philosophes autour de la table (Dining Philosophers)** :
- Plusieurs processus concurrents accèdent à des ressources partagées (baguettes).
- Risques : interblocage, famine (solutions via protocoles de prise de ressources).

**Barrières & Variables de Condition** :
- Barrière : synchronisation de groupes de threads à un point d’exécution.
- Variables de condition : permettent d’attendre une condition dans une section critique (Java : `wait()`, `notify()`, `Condition` des verrous).

### 6. Divers

**Atomité & Visibilité** :
- L’atomicité des instructions est critique pour la cohérence de données partagées.
- `volatile` : visibilité, mais sans atomicité multithread.
- Pour garantir à la fois atomicité et visibilité, préférer l’utilisation de verrous (mutex) ou atomic classes (`AtomicInteger`, etc.).