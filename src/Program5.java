class JThread3 extends Thread {

    JThread3(String name){
        super(name);
    }
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        int counter=1; // счетчик циклов
        while(!isInterrupted()){

            System.out.println("Loop " + counter++);
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}
public class Program5 {
    int a;
    public static void main(String[] args) {

        System.out.println("Main thread started...");
        Thread t = new JThread3("JThread");
        t.start();
        try{
            Thread.sleep(150);
            t.interrupt();

            Thread.sleep(150);
        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.println("Main thread finished...");
    }
}