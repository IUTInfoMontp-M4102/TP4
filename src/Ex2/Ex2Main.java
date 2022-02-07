package Ex2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ex2Main {
    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(3);
        ATache t1 = new ATache(1);
        ATache t2 = new ATache(2);
        ATache t3 = new ATache(3);

        es.execute(t1);
        es.execute(t2);
        es.execute(t3);

        es.shutdown();
        System.out.println("Fin tâche principale");
    }
}
