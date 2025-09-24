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

**Code**:
public class Hello {
public static void main (String[] args) {
// Affiche Hello JVM ! dans la console
System.out.println("Hello JVM !");
}
}
**Instructions** :
- Compilez : `javac Hello.java`
- Analysez : `javap -c Hello`

**Questions** :
1. Que contient le fichier `.class` ?
2. Quelle instruction bytecode correspond à l’affichage ?

**Mémo** :
- `.class` contient le bytecode et la table des constantes.
- Instructions clés :  
  - `getstatic` → récupère System.out  
  - `ldc` → charge "Hello JVM !"  
  - `invokevirtual` → appelle println()  
  - `return` → termine main

---

## Exercice 2 – Class Loader et initialisation (10 min)

**Code**:
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

**Questions** :
1. Quand la classe A est-elle chargée et initialisée ?
2. Quel est le rôle du Class Loader ?

**Mémo** :
- Classe A : chargée à l’instanciation (`new A()`)
- Bloc statique : exécuté une fois lors du chargement
- Types de Class Loader : Bootstrap, Extension, Application
- Bloc statique = initialisation globale  
- Constructeur = initialisation par objet

---

## Exercice 3 – Mémoire JVM (15 min)

**Code**:
public class MemoryTest {
public static void main (String[] args) {
int[] numbers = new int; // Tableau d’entiers
String s = new String("test"); // Objet String
}
}

**Questions** :
1. Où sont stockés `numbers` et `s` dans la JVM ?
2. Où se trouve la méthode main ?

**Mémo** :
- `numbers` et `s` : Heap (mémoire partagée pour objets)
- Références : Stack (pile locale de main)
- Méthode main : Method Area / Metaspace (code et métadonnées)

---

## Exercice 4 – JIT Compiler (10 min)

**Code**:
public class JITTest {
public static void main (String[] args) {
for (int i = 0; i < 1_000_000; i++) {
Math.sin(i); // Calcul intensif
}
}
}

**Questions** :
1. Qu’est-ce que le JIT Compiler ?
2. Pourquoi l’exécution devient-elle plus rapide après plusieurs itérations ?

**Mémo** :
- JIT = Just-In-Time Compiler : transforme le bytecode en code natif
- Compile les méthodes "hot" → optimisation des performances
- Utilisez `-XX:+PrintCompilation` pour voir les méthodes compilées

---

## Exercice 5 – JNI (Java Native Interface) (10 min)

**Code**:
public class JNIExample {
static { System.loadLibrary("nativeLib"); }
public native void sayHello();
public static void main (String[] args) {
new JNIExample().sayHello();
}
}

**Questions** :
1. Que fait System.loadLibrary("nativeLib") ?
2. Que signifie native ?

**Mémo** :
- Charge une bibliothèque C/C++ pour la méthode `sayHello`
- `native` = méthode implémentée en code natif, pas en Java

---

## Bonus – Exercices pratiques (20 min)

### Bloc statique vs constructeur

**Code**:
public class Test {
static { System.out.println("Bloc statique"); }
public Test() { System.out.println("Constructeur"); }
public static void main (String[] args) { new Test(); }
}

**Mémo** :
- Bloc statique = exécuté une fois
- Constructeur = exécuté à chaque instanciation

### Tableau d’1 million d’objets

**Code**:
MyObject[] arr = new MyObject[1_000_000];
for (int i = 0; i < arr.length; i++)
arr[i] = new MyObject();

**Mémo** :
- `arr` → référence sur Stack
- Tableau → objet Heap avec références
- Objets → Heap

---

