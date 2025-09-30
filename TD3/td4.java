

class Affiche extends Thread
{
    String txt;
    long temps;
    Affiche(String txt, long temps)
    {
        this.txt=txt;
        this.temps=temps;
    }
    public void run()
    {
        for (int i=0;i<5;i++)
        {
            try
            {
                System.out.println(txt);
                sleep(temps);
            }
            catch (InterruptedException e)
            {
                System.out.println("Erreur d'interruption");
            }
        }
    }
    public static void main(String [] args)
    {
        Affiche a1=new Affiche("premierthread ",1000);
        Affiche a2=new Affiche("secondthread ",2000);
        a1.start();
        a2.start();
        try {
            a1.join();
            a2.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Erreur d'interruption");
        }
        System.out.println("Fin des threads");
    }
    
}

class Compte
{
    int solde;
    Compte(int solde){
        this.solde=solde;
    }
    void afficheSolde()
    {
        System.out.println("Solde = "+solde);
    }
    int getSolde(){
        return solde;
    
    }
    void mofidieSolde(int somme, Thread t){
        t.yield();
        solde+=somme;
    }
}
class Mouvement extends Thread{
    int somme;
    Compte c;
    Mouvement(int somme, Compte c){
        this.c=c;
        this.somme=somme;
    }
    public void run(){
        for (int i=4;i>0;i--){
            setPriority(i);
            c.mofidieSolde(somme,currentThread);
            System.out.println("Thread "+getName()+" somme "+somme+" solde "+c.getSolde());
        }
    }
    public static void main(String [] args){
        Compte c=new Compte(500)
        system.out.println(c.getSolde());
        Mouvement m1=new Mouvement(100,c);
        Mouvement m2=new Mouvement(-50,c);
        m1.setname("plus")

        m2.setname("moins")
        m1.start();
        m2.start();
        try{
            m1.join();
            m2.join();      
        catch (InterruptedException e){
            System.out.println("Erreur d'interruption");
        }
        System.out.println("Solde final = "+c.getSolde());
}






public class ThreadA extends Thread{
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;
    public ThreadA(Semaphore mySemaphore, Semaphore nextSemaphore){
        this.mySemaphore=mySemaphore;
        this.nextSemaphore=nextSemaphore;   
}
    public void run(){
        for(int i=0;i<10;i++){
            mySemaphore.acquire();
            System.out.println(getName());
            nextSemaphore.release();
        }
    }
public class ThreadB extends Thread{
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;
    public ThreadB(Semaphore mySemaphore, Semaphore nextSemaphore){
        this.mySemaphore=mySemaphore;
        this.nextSemaphore=nextSemaphore;   
}
    public void run(){
        for(int i=0;i<10;i++){
            mySemaphore.acquire();
            System.out.println(getName());
            nextSemaphore.release();
        }
    }
}
public class ThreadC extends Thread{
    private Semaphore mySemaphore;
    private Semaphore nextSemaphore;
    public ThreadC(Semaphore mySemaphore, Semaphore nextSemaphore){         
        this.mySemaphore=mySemaphore;
        this.nextSemaphore=nextSemaphore;   
}
    public void run(){
        for(int i=0;i<10;i++){
            mySemaphore.acquire();
            System.out.println(getName());              
            