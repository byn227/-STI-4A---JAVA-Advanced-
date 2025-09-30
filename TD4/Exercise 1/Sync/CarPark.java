package Sync 1;
import java.util.Random;
public class CarPark{
    private int capacity;
    public CarPark(int capacity){
        this.capacity = capacity;
    }
    public synchronized void arrive(){
        System.out.println(Thread.currentThread().getName() + " arrives");
        while (capacity == 0){
            try {
                wait();
                System.out.println(Thread.currentThread().getName() + " waits");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + " parks" + " (places res: " + (capacity-1) +")");
        capacity--;
    }
    public synchronized void depart(){
        System.out.println(Thread.currentThread().getName() + " departs");
        capacity++;
        notify();
    }
    public static void main(String args[]){
        CarPark carpark=new CarPark(4);
        Random r= new Random();
        for (int i=0;i<100;i++){
            try {
                Thread.sleep(r.nextInt(5)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            new Thread(new Cars("Car"+i,carpark)).start();
        }
    }
}
