import java.util.Random;

public class ATache implements Runnable {
    int nom; // nom de la tâche
    int index; // index de la boucle d’affichage

    public ATache(int ident) {
        this.nom = ident;
        this.index = 1;
    }

    public void run() {
        System.out.println("début tâche T" + this.nom);
        Random rand = new Random();
        int pause;
        while (index <= 30) {
            try {
                System.out.println("indice: " + index + ", tâche T" + this.nom);
                pause = rand.nextInt(150, 300);
                Thread.sleep(100);
                index++;
            } catch (InterruptedException ignored) {}
        }
        System.out.println("Fin tâche T" + this.nom);
    }
}
