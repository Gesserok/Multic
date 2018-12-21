public class Program3Lambdas {
    int a;
    public static void main(String[] args) throws InterruptedException {

        System.out.println("Main thread started...");
        Runnable r = ()->{
            System.out.printf("%s started... \n", Thread.currentThread().getName());
            try{
                Thread.sleep(500);
            }
            catch(InterruptedException e){
                System.out.println("Thread has been interrupted");
            }
            System.out.printf("%s finished... \n", Thread.currentThread().getName());
        };
        Thread myThread = new Thread(r,"MyThread2");
        myThread.start();
        myThread.join();
        System.out.println("Main thread finished...");
    }
}
