# TD3 : Programmation Java Avancée — Corrigés

---

## EXERCICE 1 : Thread Affiche

**Objectif :**
Créer un thread nommé `Affiche` qui affiche un texte puis attend un certain temps. Ajouter une méthode `main` pour instancier deux objets et tester leur exécution.

**Correction :**

```java
class Affiche extends Thread {
    String txt; // le texte à afficher
    long temps; // le temps d'attente

    Affiche(String txt, long temps) {
        this.txt = txt;
        this.temps = temps;
    }

    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println(txt);
                sleep(temps);
            } catch (Exception e) {
                System.out.println("error");
            }
        }
    }

    public static void main(String[] args) {
        Affiche Monthread1 = new Affiche("premierthread\n", 2000);
        Affiche Monthread2 = new Affiche("deuxiemethread\n", 5000);
        Monthread1.start();
        Monthread2.start();

        try {
            Monthread1.join();
            Monthread2.join();
        } catch (Exception e) {}

        System.out.println("L'execution des threads est terminee");
    }
}
```

---

## EXERCICE 2 : Compte bancaire et Mouvement

**Objectif :**  
Pour simuler des mouvements sur un compte bancaire, on souhaite écrire deux classes : Compte et Mouvement.

La classe Compte représente un compte bancaire avec une valeur initiale stockée dans l'attribut Solde. Elle a les méthodes suivantes : afficheSolde pour afficher le solde du compte, getSolde pour retourner le solde du compte et modifSolde pour permettre la modification du compte en ajoutant une somme spécifiée par un thread donné.

La classe Mouvement est un thread représentant un mouvement financier. Chaque mouvement agit sur un compte en ajoutant ou en retirant une somme spécifique. Chaque thread Mouvement exécute un cycle de mouvements (spécifié par la boucle for) en utilisant la méthode modifSolde de la classe Compte. À chaque itération, le thread doit afficher son nom, sa priorité et le solde du compte après la modification.

1. Ecrivez la classe Compte
2. Ecrivez la classe Mouvement
3. Dans la méthode main de la classe Mouvement, créez une instance de la classe Compte avec une valeur initiale de 500. Ensuite, créez deux threads Mouvement : m1 qui ajoute 100 au compte et m2 qui retire 50 du compte. Démarrez ces threads et attendez leur fin avant d'afficher le solde final du compte.

### 1. Classe Compte

```java
class Compte {
    int solde;
    
    // Constructeur
    Compte(int solde) {
        this.solde = solde;
    }

    void afficheSolde() {
        System.out.println(solde);
    }

    int getSolde() {
        return solde;
    }

    void modifSolde(int somme, Thread t) {
        t.yield();
        solde += somme;
    }
}
```

### 2. Classe Mouvement

```java
class Mouvement extends Thread {
    int somme;
    Compte c;
    
    // Constructeur
    Mouvement(int somme, Compte c) {
        this.somme = somme;
        this.c = c;
    }
    
    // Méthode run
    public void run() {
        for (int i = 4; i > 0; i--) {
            // Donner une priorité au Thread
            setPriority(i);
            // Modifier le contenu du compte
            c.modifSolde(somme, currentThread());
            // Afficher le nom du Thread
            System.out.println("Nom: " + getName());
            // Afficher la priorité du Thread
            System.out.println("Priority: " + getPriority());
            // Afficher le contenu du compte
            System.out.println("Solde: " + c.getSolde());
        }
    }

    public static void main(String[] args) {
        Compte c = new Compte(500); // Instance de la classe Compte
        System.out.println(c.getSolde()); // Solde avant modification

        Mouvement m1 = new Mouvement(100, c); // Mouvement ajoutant une somme = 100
        Mouvement m2 = new Mouvement(-50, c); // Mouvement ajoutant une somme = -50

        m1.setName("plus"); // Initialiser le nom du Thread
        m2.setName("moins"); // Initialiser le nom du Thread
        
        // Début d'exécution des Threads
        m1.start();
        m2.start();
        
        try {
            m1.join();
            m2.join();
        } catch (Exception e) {
            System.out.println("error");
        }
        
        // L'affichage de la valeur finale du compte ne sera exécuté qu'après la fin des Threads m1 et m2
        System.out.println("Solde Final: " + c.getSolde());
    }
}
```

---

## EXERCICE 3 : Gestion du jardin avec threads

**Objectif :**  
On souhaite gérer le nombre de personnes visitant un jardin à l'aide des threads en Java.

La classe Compteur gère le nombre total de personnes dans le jardin. Elle possède deux méthodes synchronisées :
- `incrementer(int n)` : pour incrémenter le nombre de personnes lors de l'entrée.
- `decrementer(int n)` : pour décrémenter le nombre de personnes lors de la sortie.

Les classes Entre et Sortie représentent les portes d'entrée et de sortie du jardin respectivement. Chaque classe gère un certain nombre de passants, ajoutant ou retirant ces passants du compteur de personnes total (Compteur) à travers l'une des méthodes suivantes :
- Entre gère l'entrée des personnes en ajoutant un nombre aléatoire de passants.
- Sortie gère la sortie des personnes en retirant un nombre aléatoire de passants.

1. Ecrivez les classes Compteur, Entre et Sortie
2. Ecrivez la classe Jardin et démarrez tous les threads pour simuler l'entrée et la sortie des personnes.

### 1. Classes Compteur, Entre, Sortie

```java
class Compteur {
    int nbrepersonne;
    
    Compteur(int nbrepersonne) {
        this.nbrepersonne = nbrepersonne;
    }
    
    synchronized void increment(int n) {
        nbrepersonne += n;
    }
    
    synchronized void decrementer(int n) {
        if (n >= nbrepersonne)
            System.out.println("Erreur");
        else
            nbrepersonne -= n;
    }
}

class Entre extends Thread {
    int nbretotal = 0;
    int nbrepassant;
    String nomporte;
    Compteur c;
    
    Entre(String nomporte, Compteur c) {
        super(nomporte);
        this.c = c;
    }
    
    public void run() {
        for (int i = 0; i < 10; i++) {
            nbrepassant = (int) (Math.random() * 10);
            c.increment(nbrepassant);
            nbretotal += nbrepassant;
            System.out.println("Porte " + getName() + " nbretotal " + nbretotal + " nbrepersonne " + c.nbrepersonne);
        }
    }
}

class Sortie extends Thread {
    int nbretotal = 0;
    int nbrepassant;
    String nomporte;
    Compteur c;
    
    Sortie(String nomporte, Compteur c) {
        super(nomporte);
        this.nomporte = nomporte;
        this.c = c;
    }
    
    public void run() {
        for (int i = 0; i < 10; i++) {
            nbrepassant = (int) (Math.random() * 10);
            c.decrementer(nbrepassant);
            nbretotal += nbrepassant;
            System.out.println("Porte " + getName() + " nbretotal " + nbretotal + " nbrepersonne " + c.nbrepersonne);
        }
    }
}
```

### 2. Classe Jardin

```java
class Jardin {
    public static void main(String[] argv) {
        Compteur c = new Compteur(0);
        Entre e1 = new Entre("porte1", c);
        Entre e2 = new Entre("porte2", c);
        Sortie s1 = new Sortie("porte3", c);
        Sortie s2 = new Sortie("porte4", c);
        
        e1.start();
        e2.start();
        s1.start();
        s2.start();
    }
}
```

---

## EXERCICE 4 : Synchronisation avec Semaphore

Soit la classe Semaphore fournie ci-dessous:

```java
class Semaphore {
    private int permits;
    
    public Semaphore(int initialpermits) {
        permits = initialpermits;
    }
    
    public synchronized void acquire() {
        permits--;
        while (permits <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void release() {
        permits++;
        if (permits <= 0)
            notify();
    }
}
```

Utilisez la classe Semaphore pour compléter les classes Main, ThreadA, ThreadB et ThreadC fournies. Les classes ThreadA, ThreadB et ThreadC affichent respectivement cinq A, cinq B et cinq C. Synchronisez vos threads à l'aide de sémaphores de sorte que l'affichage effectué par l'application soit : ACBACBACBACBACB.

```java
public class ThreadA extends Thread {
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;
    
    public ThreadA(Semaphore mySemaphore, Semaphore nextSemaphore) {
        this.mySemaphore = mySemaphore;
        this.nextSemaphore = nextSemaphore;
    }
    
    public void run() {
        for (int i = 0; i < 5; i++) {
            mySemaphore.acquire();
            System.out.print("A");
            nextSemaphore.release();
        }
    }
}

public class ThreadB extends Thread {
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;
    
    public ThreadB(Semaphore mySemaphore, Semaphore nextSemaphore) {
        this.mySemaphore = mySemaphore;
        this.nextSemaphore = nextSemaphore;
    }
    
    public void run() {
        for (int i = 0; i < 5; i++) {
            mySemaphore.acquire();
            System.out.print("B");
            nextSemaphore.release();
        }
    }
}

public class ThreadC extends Thread {
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;
    
    public ThreadC(Semaphore mySemaphore, Semaphore nextSemaphore) {
        this.mySemaphore = mySemaphore;
        this.nextSemaphore = nextSemaphore;
    }
    
    public void run() {
        for (int i = 0; i < 5; i++) {
            mySemaphore.acquire();
            System.out.print("C");
            nextSemaphore.release();
        }
    }
}

public class Main {
    public static void main(String args[]) {
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(0);
        Semaphore semaphoreC = new Semaphore(0);
        
        new ThreadA(semaphoreA, semaphoreC).start();
        new ThreadB(semaphoreB, semaphoreA).start();
        new ThreadC(semaphoreC, semaphoreB).start();
    }
}
```