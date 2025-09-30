import java.util.concurrent.*;

public class BankTestTP {
    public static void main(String[] args) throws Exception {
        BankAccount acc1 = new BankAccount(1, 500);
        BankAccount acc2 = new BankAccount(2, 300);
        BankAccount acc3 = new BankAccount(3, 200);

        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        Callable<Void> t1 = () -> {
            for (int i = 0; i < 10; i++) {
                BankAccount.transfer(acc1, acc2, 50);
                Thread.sleep(100);
            }
            return null;
        };
        
        Callable<Void> t2 = () -> {
            for (int i = 0; i < 10; i++) {
                BankAccount.transfer(acc2, acc3, 30);
                Thread.sleep(100);
            }
            return null;
        };
        
        Callable<Void> t3 = () -> {
            for (int i = 0; i < 10; i++) {
                BankAccount.transfer(acc3, acc1, 20);
                Thread.sleep(100);
            }
            return null;
        };
        Future<Void> f1 = executor.submit(t1);
        Future<Void> f2 = executor.submit(t2);
        Future<Void> f3 = executor.submit(t3);

        f1.get();
        f2.get();
        f3.get();

        executor.shutdown();

        System.out.println("Solde compte 1: " + acc1.getBalance());
        System.out.println("Solde compte 2: " + acc2.getBalance());
        System.out.println("Solde compte 3: " + acc3.getBalance());
        System.out.println("Solde total: " + (acc1.getBalance() + acc2.getBalance() + acc3.getBalance()));
    }
}