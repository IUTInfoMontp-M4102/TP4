package Ex2;

import java.util.Random;

public class ATache implements Runnable {
    int nom;    // nom de la tâche
    int index;  // index de la boucle d’affichage

    public ATache(int ident) {
        this.nom = ident;
        this.index = 1;
    }

    public void run() {
        System.out.println("début tâche T" + this.nom);
        Random rand = new Random();
        while (index <= 30) {
            try {
                System.out.println("T" + nom + ": " + index);
                Thread.sleep(rand.nextInt(300));    // pause avec un délai aléatoire entre 0 et 300 ms
                index++;
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Fin tâche T" + this.nom);
    }
}
