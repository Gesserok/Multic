class MyThread implements Runnable {
    int a;
    public void run(){

        System.out.printf("%s started... \n", Thread.currentThread().getName());
        try{
            Thread.sleep(500);
        }
        catch(InterruptedException e){
            System.out.println("Thread has been interrupted");
        }
        System.out.printf("%s finished... \n", Thread.currentThread().getName());
    }
}

public class Program2 {

    public static void main(String[] args) {

        System.out.println("Main thread started...");
        Thread myThread = new Thread(new MyThread2(),"MyThread2");
        myThread.start();
        System.out.println("Main thread finished...");
    }
}
