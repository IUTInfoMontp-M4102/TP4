# TP4 - Synchronisation et sémaphores

Lien GitHub Classroom pour [faire un fork privé du TP](https://classroom.github.com/a/jqj0L3df).

Dans ce TD, nous allons voir comment les moniteurs et sémaphores peuvent être utilisés en Java pour réaliser l’exclusion mutuelle et / ou pour synchroniser les tâches.

## Exclusions mutuelles avec `synchronized` et coopérations

Nous allons partager deux informations : un nombre `n` et son carré `carre` entre deux _threads_. Le premier incrémente `n` et calcule son carré dans `carre`; le second _thread_ se contente d’afficher `n` et le contenu de `carre`.

Ici, les deux informations (`n` et `carre`) sont regroupées dans un objet `nombre` de type `Nombre`. Cette classe dispose de deux méthodes :
- `void calcul()` qui incrémente `n`, fait une pause de 100 ms et calcule la valeur de `carre` ;
- `void affiche()` qui affiche les valeurs de `n` et de `carre`.

On crée de deux _threads_ de deux classes différentes :
- `calc` de la classe `Calcul` qui appelle, à son rythme (`sleep(100)`), la méthode `calcul()` de `Nombre`
- `aff` de la classe `Affiche` qui appelle à son rythme (différent de celui de `calc`, par exemple `sleep(150)`), la méthode `affiche()` de `Nombre`.

Les deux _threads_ sont lancés par le `main()` et s’arrêtent après 10 itérations.

1. Implémentez et exécutez le programme décrit ci-dessus. Expliquez les résultats.

2. Pour réaliser des exclusions, mettez les méthodes `calcul()` et `affiche()` de `Nombre` en `synchronized`. Testez les classes et expliquez les résultats.

Jusqu’à présent, les deux _threads_ n’étaient pas vraiment synchronisés. On modifie maintenant le code pour que les deux _threads_ soient coordonnés, malgré leurs rythmes différents (i.e. on effectue alternativement une incrémentation et un calcul). Pour ce faire, on utilisera les méthodes `wait()` et `notifyAll()`, ainsi qu’un indicateur booléen `pret` permettant aux deux _threads_ de communiquer entre eux.

3. Modifiez le code précédent afin de synchroniser les deux _threads_.


## Sémaphores pour la synchronisation

Dans cet exercice on considère la classe `ATache` (cf. fichier joint).

On veut contrôler les exécutions des trois tâches _T1_, _T2_ et _T3_ réalisées par trois instances distinctes de la classe `ATache` pour garantir que pour chaque valeur de l’indice, les tâches sont exécutées dans l’ordre _T1_, _T2_, _T3_ : (_T1_, _i_ = 1), (_T2_, _i_ = 1), (_T3_, _i_ = 1), (_T1_, _i_ = 2), (_T2_, _i_ = 2), (_T3_, _i_ = 2), (_T1_, _i_ = 3), etc.

Pour réaliser cet objectif de synchronisation, nous allons utiliser la classe `Semaphore`[^1].

Les méthodes `acquire()` et `release()` sur un objet de la classe `Semaphore` sont équivalentes aux primitives `P()` et `V()` vues en première année.

Le constructeur de la classe `Semaphore` prend deux paramétres : un entier pour la valeur initiale du sémaphore et un booléen. La valeur `true` permet une gestion _FIFO_ des tâches en attente sur ce sémaphore.

Exemple d’utilisation :
```java
Semaphore sem1;
sem1 = new Semaphore(0, true); // arret immédiat de la tache au premier P() 
sem1.acquire();                // P() une valeur peut etre donnée en paramètre
sem1.release();                // V() une valeur peut etre donnée en paramètre
```

Les fonctions `acquire()` et `release()` peuvent également recevoir un entier comme argument. `acquire(2)` ne passe que si la valeur du sémaphore est au moins 2 (la valeur est alors décrémentée de 2), `release(2)` ajoute 2 à la valeur courante du sémaphore. Si l’on ne donne pas d’argument les fonctions se comportent comme si l’argument était 1.

**Remarque :** Plusieurs tâches peuvent partager un même objet créé préalablement si celui-ci est passé en argument à la création de l’instance correspondant à la tâche. Les données d’un tel objet ne sont pas perdues lorsque la tâche se termine.

Deux paramètres vont être ajoutés au constructeur de la classe `ATache`. Le premier sémaphore (`prive`) sera celui que la tâche doit surveiller pour savoir quand elle a le droit de poursuivre son exécution. Le second (`voisin`) sera celui que la tâche suivante attend pour continuer.

```java
public ATache (int nom, Semaphore prive, Semaphore voisin)
```

1. En utilisant des sémaphores, modifiez votre programme pour garantir que l’exécution des trois tâches _T1_, _T2_ et _T3_ se fasse de manière régulièrement intercalée.

   **Indication :** Il faut créer trois sémaphores dans la fonction `main` (un pour chaque tâche), puis donner à chaque tâche son sémaphore et celui de la tâche suivante au moment de sa création.

    À chaque itération de la boucle dans la fonction `run` de la classe `ATache`, il faut commencer par demander si le sémaphore est disponible, exécuter une itération de la boucle puis libérer le sémaphore de la tâche suivante (pour qu’elle puisse à son tour effectuer une itération de la boucle).

   Attention á l’intialisation de chacun des trois sémaphores...

2. Modifiez le programme pour que les tâches s’alternent dans l’ordre inverse : _T3_, _T2_ puis _T1_.

3. Que se passe-t-il si le groupe de tâches est initialisé avec une seule tâche exécutable à la fois ?

4. Ajoutez un sémaphore `semfin` pour que l’affichage « fin tâche principale » se trouve aprés les affichages des trois tâches.


## Pertes de mises à jour, exclusion mutuelle

Dans ce dernier exercice, nous allons examiner les problèmes causés par l’utilisation concurrente des ressources. Pour cela, on prend un exemple simple : plusieurs _threads_ d’un programme modifient en parallèle des objets (une chaîne et un entier) statiques partagés.

1. Lisez et exécutez le code de la classe `Partage`. Expliquez les résultats.

2. Modifiez le programme en assurant l’exécution exclusive de la section critique en utilisant une méthode `synchronized`.

3. Réalisez maintenant l’exclusion mutuelle avec des sémaphores.

[^1]: Pour utiliser la classe `Semaphore` : `import java.util.concurrent.Semaphore;`
