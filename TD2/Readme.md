# TD 2 - Programmation Java Avancée – Notions de Bases


## Objectif général

Apprendre à :
- Créer et organiser des classes en Java
- Définir des méthodes
- Utiliser l'héritage (superclasse/sous-classe)
- Manipuler des attributs et des objets
- Appliquer le polymorphisme et l'interface
- Écrire des programmes modulaires, structurés et réutilisables

## Exercice 1 : Classe Point

### Question

1. Créer une classe `Point` avec :
- Deux attributs `x` et `y` de type `double` représentant les coordonnées
- Un constructeur prenant deux paramètres pour initialiser les coordonnées
- Une méthode `affiche()` qui affiche le point sous la forme `P=(x,y)`
- Une méthode `translate(dx, dy)` qui déplace le point selon les valeurs reçues

2. Écrire une méthode `main` (dans la classe `Point` ou une classe `TestPoint`) qui :
- Construit un point de coordonnées `(3,5)`.
- Affiche les coordonnées de ce point.
- Translate ce point de `(2,3)`.
- Réaffiche les coordonnées de ce point.
### Solution

```java
class Point {
    double x;
    double y;
    
    Point(double a, double b) {
        x = a;
        y = b;
    }
    
    void affiche() {
        System.out.println("P=(" + x + "," + y + ")");
    }
    
    void translate(double dx, double dy) {
        x = x + dx;
        y = y + dy;
    }
    
    public static void main(String[] args) {
        Point P = new Point(3, 5);
        P.affiche();      // P=(3,5)
        P.translate(2, 5);
        P.affiche();      // P=(5,10)
    }
}
```

## Exercice 2 : Héritage et polymorphisme

### Question

Écrire une hiérarchie de classes avec :
1. Une classe de base `Personne`
2. Deux classes dérivées `Forgeron` et `Menuisier`
3. Une méthode `affiche()` dans chaque classe pour afficher les informations
4. Un programme de test utilisant le polymorphisme

### Programme de test

```java
class testMetiers {
    public static void main (String[] args) {
        Personne[] personnes = new Personne[4];
        personnes[0] = new Personne("Salah");
        personnes[1] = new Forgeron("Ali");
        personnes[2] = new Menuisier("Mohamed");
        personnes[3] = new Forgeron("Amor");
        
        for (int i = 0; i < personnes.length; i++) {
            personnes[i].affiche();
        }
    }
}
```

### Sortie attendue
```
Je suis Salah
Je suis Ali le forgeron
Je suis Mohamed le menuisier
Je suis Amor le forgeron
```

### Solution

```java
public class Personne {
    String prenom;

    Personne(String prenom) {
        this.prenom = prenom;
    }

    void affiche() {
        System.out.println("je suis " + prenom);
    }
}

public class Forgeron extends Personne {
    Forgeron(String prenom) {
        super(prenom);
    }

    void affiche() {
        System.out.println("je suis " + prenom + " le forgeron");
    }
}

public class Menuisier extends Personne {
    Menuisier(String prenom) {
        super(prenom);
    }

    void affiche() {
        System.out.println("je suis " + prenom + " le menuisier");
    }
}
```

## Exercice 3 : Interfaces et Classes

### Question

Créer la hiérarchie suivante :

1. Interface `Homme` avec :
   - Méthode `void identite()` qui affiche les informations

2. Classe `Personne` implémentant `Homme` avec :
   - Attributs privés : `nom`, `prenom`
   - Constructeur paramétré
   - Implémentation de `identite()`

3. Classe `Client` héritant de `Personne` avec :
   - Attribut privé supplémentaire : `numero`
   - Constructeur paramétré
   - Redéfinition de `identite()`

4. Classe `Peuple` avec :
   - Tableau d'`Homme` limité à 100 éléments
   - Méthode `naissance(Homme h)` pour ajouter
   - Méthode `explorer()` pour afficher tous les hommes

### Solution

```java
public interface Homme {
    void identite();
}

public class Personne implements Homme {
    private String nom, prenom;
    
    Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public void identite() {
        System.out.println("Nom : " + nom);
        System.out.println("Prenom : " + prenom);
    }
}

public class Client extends Personne implements Homme {
    private int numero;
    
    Client(String nom, String prenom, int numero) {
        super(nom, prenom);
        this.numero = numero;
    }
    
    public void identite() {
        super.identite();
        System.out.println("Numero Client : " + numero);
    }
}

public class Peuple {
    private Homme pays[] = new Homme[100];
    private int nbHommes = 0;
    
    void naissance(Homme h) {
        pays[nbHommes++] = h;
    }
    
    void explorer() {
        for (int i = 0; i < nbHommes; i++) {
            pays[i].identite();
        }
    }
    
    public static void main(String[] args) {
        Peuple p = new Peuple();
        p.naissance(new Personne("xx", "yy"));
        p.naissance(new Client("xx", "zz", 11));
        p.naissance(new Client("ss", "zz", 123));
        p.naissance(new Personne("AA", "BB"));
        p.explorer();
    }
}
```

## Exercice 4 : Packages et Visibilité

### Question

Analysez les deux fichiers source suivants et répondez aux questions :

1. Quel doit être l'emplacement physique de chaque fichier ?
2. Quelles sont les instructions erronées ?
3. Pourquoi ces erreurs se produisent-elles ?

```java
// C1.java
package exercicesJava.paquet1;

public class C1 {
    public int entierPublique;
    protected int entierProtege;
    int entierDefaut;
    private int entierPrive;
}

// C2.java
package exercicesJava.paquet2;
import exercicesJava.paquet1.C1;

public class C2 {
    C1 c = new C1();
    int entier;
    
    C2() {
        entier = c.entierPublique;
        // entier = c.entierProtege;  // Erreur : pas accessible
        // entier = c.entierDefaut;   // Erreur : pas accessible
        // entier = c.entierPrive;    // Erreur : pas accessible
    }
}

class C11 extends C1 {
    int entier;
    
    C11() {
        entier = entierPublique;
        entier = entierProtege;
        // entier = entierDefaut;    // Erreur : pas accessible
        // entier = entierPrive;     // Erreur : jamais accessible
    }
}
```

### Solution

1. **Emplacement des fichiers** :
   - `C1.java` → `../exercicesJava/paquet1/`
   - `C2.java` → `../exercicesJava/paquet2/`

2. **Instructions erronées** :
   - Dans la classe `C2` :
     - Accès refusé à `entierProtege` : non accessible hors package
     - Accès refusé à `entierDefaut` : non accessible hors package
     - Accès refusé à `entierPrive` : jamais accessible
   - Dans la classe `C11` :
     - Accès refusé à `entierDefaut` : non accessible hors package
     - Accès refusé à `entierPrive` : jamais accessible

3. **Explication** :
   - Les modificateurs d'accès suivent les règles de visibilité Java
   - La classe `C2` est dans un package différent sans héritage
   - La classe `C11` hérite mais est dans un package différent

## Exercice 5 : Correction de Programme

### Question

Le programme suivant ne compile pas. Expliquez pourquoi et corrigez-le sans :
- Ajouter de nouvelles méthodes
- Modifier le droit d'accès des attributs

```java
package exercicesJava.paquet1;

class Personne {
    private String nom, prenom;
    
    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }
    
    public void affiche() {
        System.out.println("Nom: " + nom);
        System.out.println("Prenom: " + prenom);
    }
}

class Etudiant extends Personne {
    private String filiere;
    private float moyenne;
    
    public Etudiant(String nom, String prenom, String filiere, float moyenne) {
        super(nom, prenom);
        this.filiere = filiere;
        this.moyenne = moyenne;
    }
    
    void affiche() {
        super.affiche();
        System.out.println("Filiere: " + filiere);
        System.out.println("Moyenne: " + moyenne);
    }
}
```

### Solution

Les problèmes sont :
1. Niveau d'accès de `affiche()` réduit dans `Etudiant`
2. On ne peut pas réduire le niveau d'accès lors de la redéfinition

Version corrigée de la méthode dans `Etudiant` :
```java
@Override
public void affiche() {
    super.affiche();
    System.out.println("Filiere: " + filiere);
    System.out.println("Moyenne: " + moyenne);
}
```

## Exercice 6 : Tableau Trié

### Question

Créer une classe représentant un tableau trié d'entiers avec :

1. **Attributs** :
   - Un tableau d'entiers (privé)
   - Un compteur d'éléments

2. **Méthodes** :
   - Constructeur pour allouer la capacité
   - `inserer(int entier)` : ajoute en conservant l'ordre croissant
   - `retirer(int entier)` : supprime une occurrence
   - `afficher()` : montre le contenu

3. **Programme de test** qui :
   - Crée un tableau de taille 10
   - Insère plusieurs valeurs
   - Affiche l'état à chaque étape

### Solution

```java
class TableauTrie {
    private int[] tab;
    private int nbElem = 0;
    
    TableauTrie() {
        tab = new int[100];
    }
    
    void inserer(int entier) {
        int i = 0;
        
        if (nbElem < tab.length) {
            // Chercher la position d'insertion
            while ((i < nbElem) && (tab[i] < entier)) {
                i++;
            }
            // Décaler les éléments
            for (int j = nbElem; j > i; j--) {
                tab[j] = tab[j-1];
            }
            // Insérer l'élément
            tab[i] = entier;
            nbElem++;
        }
    }
    
    void retirer(int entier) {
        int i;
        boolean trouve = false;
        
        // Rechercher l'élément
        for (i = 0; (i < nbElem) && (!trouve); i++) {
            if (tab[i] == entier) {
                trouve = true;
            }
        }
        
        if (trouve) {
            // Décaler à gauche
            while (i < nbElem) {
                tab[i-1] = tab[i];
                i++;
            }
            nbElem--;
        }
    }
    
    void affiche() {
        System.out.println("Nouveau affichage:");
        for (int i = 0; i < nbElem; i++) {
            System.out.println(tab[i]);
        }
    }
}
```

## Exercice 7 : Manipulation de Chaînes

### Question

Créer une classe `UtilChaine` permettant de :

1. **Constructeur** :
   - Prend une chaîne en paramètre
   - Initialise l'attribut `s`

2. **Méthodes** :
   - `occurenceCar(char c, int i)` : compte les occurrences après position i
   - `existeCar(char c, int deb, int fin)` : cherche dans un intervalle
   - `occurenceMultiple()` : compte les caractères répétés

3. **Programme de test** avec la chaîne "saluts mes etudiants"

### Solution

```java
public class UtilChaine {
    String s;
    
    // Constructeur
    UtilChaine(String s) {
        this.s = s;
    }
    
    // Compte les occurrences d'un caractère
    int occurenceCar(char c, int i) {
        int occ = 0;
        for (int j = i; j < s.length(); j++) {
            if (s.charAt(j) == c) {
                occ++;
            }
        }
        return occ;
    }
    
    // Recherche un caractère dans un intervalle
    boolean existeCar(char c, int deb, int fin) {
        boolean trouve = false;
        for (int i = deb; (i < fin) && (!trouve); i++) {
            if (s.charAt(i) == c) {
                trouve = true;
            }
        }
        return trouve;
    }
    
    // Compte les caractères avec plusieurs occurrences
    int occurenceMultiple() {
        int nb = 0;
        for (int i = 0; i < s.length(); i++) {
            // Si le caractère apparaît plus d'une fois et n'a pas été traité
            if (occurenceCar(s.charAt(i), i) > 1 && 
                !existeCar(s.charAt(i), 0, i)) {
                nb++;
            }
        }
        return nb;
    }
    
    public static void main(String[] args) {
        String s = "saluts mes etudiants";
        UtilChaine u = new UtilChaine(s);
        System.out.println(
            "Nombre de caractères avec plusieurs occurrences : " + 
            u.occurenceMultiple()
        );
    }
}
```

### Explication du résultat

Pour la chaîne "saluts mes etudiants" :
- Le programme trouve 3 caractères répétés :
  - 's' (dans "saluts" et "mes")
  - 't' (dans "saluts" et "etudiants")
  - 'a' (dans "saluts" et "etudiants")

---
