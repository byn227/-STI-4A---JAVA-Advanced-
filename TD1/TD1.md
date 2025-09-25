# TD 1 Programmation Java Avancée – La JVM

## Objectifs

Ce TD permet de comprendre le fonctionnement interne de la JVM :

- Analyse du bytecode
- Rôle des Class Loaders
- Exécution des blocs statiques et constructeurs
- Stockage des objets en mémoire JVM
- Gestion automatique de la mémoire (Garbage Collector)
- JIT Compiler pour l’optimisation des performances
- JNI pour l’appel de code natif

---

## Exercice 1 – Bytecode et fichiers `.class` (15 min)

### Code

```java
public class Hello {
    public static void main (String[] args) {
        // Affiche Hello JVM ! dans la console
        System.out.println("Hello JVM !");
    }
}
```

### Instructions

- Compilez : `javac Hello.java`
- Analysez : `javap -c Hello`

### Questions

1. Que contient le fichier `.class` ?
2. Quelle instruction bytecode correspond à l'affichage ?

### Réponses

1. Le fichier `.class` contient :
   - Le bytecode Java compilé
   - La table des constantes (strings, références)
   - Les métadonnées de la classe
   - Les définitions des méthodes

2. Les instructions bytecode pour l'affichage sont :
   ```
   getstatic     #2  // Référence à System.out
   ldc           #3  // Charge "Hello JVM !"
   invokevirtual #4  // Appelle println()
   ```

### Mémo

- `.class` contient le bytecode et la table des constantes.
- Instructions clés :  
  - `getstatic` → récupère System.out  
  - `ldc` → charge "Hello JVM !"  
  - `invokevirtual` → appelle println()  
  - `return` → termine main

---

## Exercice 2 – Class Loader et initialisation (10 min)

### Code

```java
// A.java
public class A {
    static { System.out.println("Classe A chargée"); }
}

// B.java
public class B {
    public static void main (String[] args) {
        new A();
    }
}
```

### Questions

1. Quand la classe A est-elle chargée et initialisée ?
2. Quel est le rôle du Class Loader ?

### Mémo

- Classe A : chargée à l’instanciation (`new A()`)
- Bloc statique : exécuté une fois lors du chargement
- Types de Class Loader : Bootstrap, Extension, Application
- Bloc statique = initialisation globale  
- Constructeur = initialisation par objet

---

## Exercice 3 – Mémoire JVM (15 min)

### Code

```java
public class MemoryTest {
    public static void main (String[] args) {
        int[] numbers = new int[10]; // Tableau d'entiers
        String s = new String("test"); // Objet String
    }
}
```

### Questions

1. Où sont stockés `numbers` et `s` dans la JVM ?
2. Où se trouve la méthode main ?

### Réponses

1. Localisation en mémoire :
   - `numbers` : tableau stocké dans le Heap
   - `s` : objet String stocké dans le Heap
   - Les références sont sur la Stack

2. La méthode `main` :
   - Stockée dans la Method Area/Metaspace
   - Avec le bytecode et les métadonnées de la classe

### Mémo

- `numbers` et `s` : Heap (mémoire partagée pour objets)
- Références : Stack (pile locale de main)
- Méthode main : Method Area / Metaspace (code et métadonnées)

---

## Exercice 4 – JIT Compiler (10 min)

### Code

```java
public class JITTest {
    public static void main (String[] args) {
        for (int i = 0; i < 1_000_000; i++) {
            Math.sin(i); // Calcul intensif
        }
    }
}
```

### Questions

1. Qu'est-ce que le JIT Compiler ?
2. Pourquoi l'exécution devient-elle plus rapide après plusieurs itérations ?

### Réponses

1. Le JIT Compiler est :
   - Un compilateur qui transforme le bytecode en code natif
   - S'exécute pendant l'exécution du programme
   - Optimise les performances

2. L'exécution s'accélère car :
   - Le JIT identifie les méthodes "hot" (fréquemment exécutées)
   - Ces méthodes sont compilées en code natif
   - Les appels suivants utilisent le code optimisé

### Mémo

- JIT = Just-In-Time Compiler : transforme le bytecode en code natif
- Compile les méthodes "hot" → optimisation des performances
- Utilisez `-XX:+PrintCompilation` pour voir les méthodes compilées

---

## Exercice 5 – JNI (Java Native Interface) (10 min)

### Code

```java
public class JNIExample {
    static { System.loadLibrary("nativeLib"); }
    public native void sayHello();
    public static void main (String[] args) {
        new JNIExample().sayHello();
    }
}
```

### Questions

1. Que fait System.loadLibrary("nativeLib") ?
2. Que signifie native ?

### Réponses

1. `System.loadLibrary("nativeLib")` :
   - Charge une bibliothèque native (.dll sous Windows, .so sous Linux)
   - Établit le lien entre Java et le code natif
   - Permet d'accéder aux fonctions natives

2. Le mot-clé `native` :
   - Indique une méthode implémentée en code natif (C/C++)
   - Pas de corps de méthode en Java
   - Permet d'appeler du code spécifique à la plateforme

### Mémo

- Charge une bibliothèque C/C++ pour la méthode `sayHello`
- `native` = méthode implémentée en code natif, pas en Java

---

## Bonus – Exercices pratiques (20 min)

### Bloc statique vs constructeur

```java
public class Test {
    static { System.out.println("Bloc statique"); }
    public Test() { System.out.println("Constructeur"); }
    public static void main (String[] args) { new Test(); }
}
```

**Mémo** :

- Bloc statique = exécuté une fois
- Constructeur = exécuté à chaque instanciation

---

### Tableau d’1 million d’objets

```java
MyObject[] arr = new MyObject[1_000_000];
for (int i = 0; i < arr.length; i++)
    arr[i] = new MyObject();
```

**Mémo** :

- `arr` → référence sur Stack
- Tableau → objet Heap avec références
- Objets → Heap

---