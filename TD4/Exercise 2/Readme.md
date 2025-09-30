## TD4 — Exercice 2 : Simulation de comptes bancaires concurrents

Implémenter une simulation de trois comptes bancaires. Plusieurs threads réalisent en parallèle des opérations de dépôt, retrait et transfert. L’objectif est d’assurer la cohérence des soldes malgré la concurrence grâce à des mécanismes d’exclusion mutuelle.

### Objectifs
- Rendre thread-safe les opérations de `BankAccount` (dépôt, retrait, transfert).
- Orchestrer l’exécution concurrente via `Callable` et `ExecutorService`.
- Garantir la conservation du solde total (invariant) à la fin des opérations.

### Prérequis
- JDK 8+ installé (`java`, `javac` disponibles dans le PATH).
- Système: Windows (exemples de commandes en `cmd`).

### Structure
- `BankAccount.java` : Compte bancaire avec `ReentrantLock`, opérations thread-safe et méthode statique `transfer` avec ordre de verrouillage pour éviter l’interblocage.
- `BankTestTP.java` : Programme principal. Crée 3 comptes, soumet 3 tâches concurrentes via un pool fixe, attend la fin et affiche les soldes finaux et le total.



### Compilation et exécution
Exécuter depuis la racine du dépôt:

```cmd
cd "TD4\Exercise 2"
javac *.java
java BankTestTP
```

