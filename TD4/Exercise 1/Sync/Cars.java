package Sync;
import java.util.Random;
public class Cars implements Runnable {
    private CarPark carpark;
    private Random r;
    private String nom;

    public Cars(String nom, CarPark carpark) {
        this.nom = nom;
        this.carpark = carpark;
        r = new Random();
    }
    public void run (){
        Thread.currentThread().setName(nom);
        carpark.arrive();
        try {
            Thread.sleep(r.nextInt(10)*1000);
        } catch (InterruptedException e) {
            return;
    }
        carpark.depart();
    }
}
