import java.util.concurrent.locks.ReentrantLock;
public class BankAccount {
    private final int accountId;
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();
    public BankAccount(int accountId, int initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }
    public void deposit(int amount){
        lock.lock();
        try {
            balance += amount;
            System.out.println("Account " + accountId + ": Deposited " + amount + ", New Balance: " + balance);
        } finally {
            lock.unlock();
        }
    }
    public void withdraw(int amount){
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println("Account " + accountId + ": Withdrew " + amount + ", New Balance: " + balance);
            } else {
                System.out.println("Account " + accountId + ": Insufficient funds for withdrawal of " + amount);
            }
        } finally {
            lock.unlock();
        }
    }
    public int getBalance() {
        lock.lock();
        try {
            return balance;
        } finally {
            lock.unlock(); 
        }
    }
    public static void transfer(BankAccount from, BankAccount to, int amount) {
        if (from.accountId == to.accountId) {
            System.out.println("Transfer failed: Cannot transfer to the same account");
            return;
        }

        BankAccount firstLock = from.accountId < to.accountId ? from : to;
        BankAccount secondLock = from.accountId < to.accountId ? to : from;

        firstLock.lock.lock();
        try {
            secondLock.lock.lock();
            try {
                if (from.balance >= amount) {
                    from.balance -= amount;
                    to.balance += amount;
                    System.out.println("Transferred " + amount + " from Account " + from.accountId + " to Account " + to.accountId);
                    System.out.println("Account " + from.accountId + " new balance: " + from.balance);
                    System.out.println("Account " + to.accountId + " new balance: " + to.balance);
                } else {
                    System.out.println("Transfer failed: Insufficient funds in Account " + from.accountId + " (Balance: " + from.balance + ", Required: " + amount + ")");
                }
            } finally {
                secondLock.lock.unlock();
            }
        } finally {
            firstLock.lock.unlock();
        }
    }
}
