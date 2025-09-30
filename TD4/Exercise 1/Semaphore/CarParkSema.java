import java.util.concurrent.Semaphore;
import java.util.Random;

public class CarParkSema {
    private Semaphore permits;

    public CarParkSema(int capacity) {
    permits = new Semaphore(capacity);
    }
    public void arrive() {
        try {
            System.out.println(Thread.currentThread().getName() + " arrives");
            permits.acquire();
            System.out.println(Thread.currentThread().getName() + " parks (places left: " + permits.availablePermits() + ")");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void depart() {
        System.out.println(Thread.currentThread().getName() + " departs");
        permits.release();
    }

    public static void main(String[] args) {
        CarParkSema carpark = new CarParkSema(4);
        Random r = new Random();

        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(r.nextInt(5) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new CarsSema("Car" + i, carpark)).start();
        }
    }
}
