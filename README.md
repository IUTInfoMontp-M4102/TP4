# TD4 - Synchronisation et sémaphores

Lien GitHub Classroom pour [faire un fork privé du TP](https://classroom.github.com/a/jqj0L3df).

Dans ce TD, nous allons voir comment les moniteurs et les sémaphores peuvent être utilisés en Java pour réaliser l’exclusion mutuelle et/ou pour synchroniser des tâches.

## Exercice 1 : Exclusions mutuelles avec `synchronized` et coopération

Dans cet exercice, on considère une classe `Nombre` possédant deux attributs `n` et `carre` de type `int`. La classe dispose également de deux méthodes :
- `void calcul()` qui incrémente `n`, fait une pause de 100 ms et met à jour la valeur de `carre = n * n` ;
- `void affiche()` qui affiche les valeurs de `n` et de `carre` sur la sortie standard (`System.out`)

1. Complétez la classe `Nombre` selon les spécifications précédentes.

Nous allons exécuter deux _threads_ en parallèle qui travaillent sur le même objet de type `Nombre`. Le premier va appeler la méthode `calcul()` toutes les 150ms, tandis que le second va appeler la méthode `affiche()` toutes les 100ms. Chaque _thread_ exécute 10 itérations.

2. Écrivez les classes `Calcul` et `Affiche` pour exécuter ces tâches (en implémentant l'interface `Runnable`).

   **Remarque :** Les deux tâches doivent travailler sur un même objet de type `Nombre`. 

3. Écrivez la méthode `main` de la classe `Ex1Main` qui lance une tâche `Calcul` et une tâche `Affiche` sur le même objet de type `Nombre` (utilisez un `ExecutorService` pour exécuter les tâches).

4. Exécutez le programme et expliquez les résultats observés.

5. Pour réaliser des exclusions mutuelles, mettez les méthodes `calcul()` et `affiche()` de `Nombre` en `synchronized`. Exécutez le programme et expliquez les résultats.

Jusqu'ici, bien que le mot clé `synchronized` permette de protéger les sections critiques, les deux _threads_ ne sont pas vraiment coordonnés (ils travaillent à des rythmes indépendants).

6. En utilisant les méthodes `wait()` et `notifyAll()` (de la classe `Object`), modifiez votre programme pour que les deux _threads_ avancent de manière coordonnée (en alternant les incrémentations et les affichages) malgré leurs délais différents.

   **Indication :** En plus des méthodes `wait()` et `notifyAll()` qui doivent être appelées dans les méthodes synchronisées `calcul()` et `affiche()`, vous pouvez ajouter un booléen `pret` qui indique laquelle des deux tâches doit exécuter son itération.


## Exercice 2 : Synchronisation à l'aide de _sémaphores_

1. Regardez le code de la classe `ATache` fournie et du programme `Ex2Main`. Exécutez le programme et analysez le résultat. 

On veut contrôler les exécutions des trois tâches `t1`, `t2` et `t3` réalisées par trois instances distinctes de la classe `ATache` pour garantir que pour chaque valeur de l’indice, les tâches sont exécutées dans l’ordre `t1`, `t2`, `t3` :
```
T1: 1
T2: 1
T3: 1
T1: 2
T2: 2
T3: 2
T1: 3
...
```

Pour réaliser cet objectif de synchronisation, nous allons utiliser la classe `Semaphore`[^1].

Deux sémaphores vont être passés en argument à la création d'un objet `ATache` (dans le constructeur). Le premier (`prive`) sera celui que la tâche doit surveiller pour savoir quand elle a le droit de poursuivre son exécution. Le second (`voisin`) sera celui que la tâche suivante attend pour continuer :
```java
public ATache (int nom, Semaphore prive, Semaphore voisin)
```

2. Modifiez le programme (classe `ATache` et `Ex2Main`) pour garantir que l’exécution des trois tâches `t1`, `t2` et `t3` se fasse de manière régulièrement intercalée.

   **Indication :** Il faut créer trois sémaphores dans la fonction `main` (un pour chaque tâche), puis donner à chaque tâche son sémaphore et celui de la tâche suivante au moment de sa création. À chaque itération de la boucle dans la fonction `run` de la classe `ATache`, il faut commencer par demander si le sémaphore est disponible, exécuter une itération de la boucle puis libérer le sémaphore de la tâche suivante (pour qu’elle puisse à son tour effectuer une itération de la boucle).

5. Modifiez le programme pour que les tâches s’alternent dans l’ordre inverse : `t3`, `t2` puis `t1`.

6. Que se passe-t-il si les tâches sont exécutées par un `ExecutorService` qui n'autorise que l'exécution de 2 tâches en parallèle (par exemple `Executors.newFixedThreadPool(2)`) ?

7. En utilisant un nouveau sémaphore `semFin`, faites en sorte que l’affichage « fin tâche principale » se trouve après les affichages des trois tâches.


## Exercice 3 : Pertes de mises à jour, exclusion mutuelle

Dans ce dernier exercice, nous allons examiner les problèmes causés par l’utilisation concurrente des ressources. On s'intéresse à un exemple très simple : plusieurs _threads_ d’un programme modifient en parallèle des variables (une chaîne de caractères et un entier) statiques partagés.

1. Lisez et exécutez le code de la classe `Partage`. Expliquez les résultats.

2. Modifiez le programme en assurant l’exécution exclusive de la section critique en utilisant une méthode `synchronized`.

3. Réalisez maintenant l’exclusion mutuelle avec des sémaphores.

[^1]: Pour utiliser la classe `Semaphore` : `import java.util.concurrent.Semaphore;`