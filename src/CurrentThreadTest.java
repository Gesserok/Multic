import java.util.Date;

public class CurrentThreadTest {
    int a;
    public static void main(String[] args) {

        MyMonitorThread monitorThread = new MyMonitorThread(Thread.currentThread());

        monitorThread.start();


        for (int i = 0; i < 5; i++) {
            Thread current  = Thread.currentThread();
            try {
//                System.out.printf("id %s, name %s, state %s\n",
//                        current.getId(), current.getName(), current.getState());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.printf("%1$tM - %1$tS\n", new Date());
        }

        monitorThread.interrupt();
    }
}

class MyMonitorThread extends Thread {
    private Thread subject;

    public MyMonitorThread(Thread subject) {
        this.subject = subject;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(99999);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.err.printf("id %s, name %s, state %s\n",
                    subject.getId(), subject.getName(), subject.getState());
        }

    }
}