package Ex1;

public class Nombre {
    private int n;
    private int carre;

    public Nombre(int n) {
        this.n = n;
        this.carre = n * n;
    }

    /**
     * Incrémente n de 1, attend 100ms puis met à jour la valeur de carre = n * n
     */
    public synchronized void calcul() {
        // À compléter
    }

    /**
     * Ex1.Affiche les valeurs de n et carre sur la sortie standard
     */
    public synchronized void affiche() {
        // À compléter
    }
}