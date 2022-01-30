public class Partage extends Thread {
    private static String chaine = "";
    private static int cpt = 0;
    private String nom;

    public Partage(String s) {
        nom = s;
    }

    public void run() {
        for (int i = 0; i < 10; i++) {
            maj(nom);
            try {
                Thread.sleep(100); // milliseconds
            } catch (InterruptedException ignored) {
            }
        }
    }

    public void maj(String nn) {
        chaine = chaine + nn;
        cpt++;
    }

    // mises a jour
    public static void main(String[] args) {
        Thread T1 = new Partage("T1 ");
        Thread T2 = new Partage("T2 ");
        Thread T3 = new Partage("T3 ");
        T1.start();
        T2.start();
        T3.start();
        try {
            T1.join();
            T2.join();
            T3.join();
        } catch (InterruptedException ignored) {
        }
        System.out.println(chaine);
        System.out.println(cpt);
    }
}