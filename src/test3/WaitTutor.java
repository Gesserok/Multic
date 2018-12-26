package test3;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Как сделать так, чтобы потоки вызывались по очереди?
 *
 * Часто необходимо упорядочить потоки, т.к. результат одного потока
 * понадобится другому, и нужно дождаться, когда первый поток сделает свою работу.
 *
 * Задача: добавьте еще один поток, который будет выводить в лог сообщения о
 * 	значениях счетчика, кратных 10, например 10, 20, 30...
 * При этом такие сообщения должны выводиться после того, как все потоки преодолели
 * кратность 10, но до того, как какой-либо поток двинулся дальше.
 */
public class WaitTutor {
    Thread t1, t2;
    final Object monitor = new Object();
    int runningThreadNumber = 1;
    AtomicInteger t1Counter = new AtomicInteger(0), t2Counter = new AtomicInteger(0);
    private Thread counterThread;
    //int maxCounter = 0;

    class TestThread implements Runnable {
        String threadName;
        int n;

        public TestThread(String threadName, int n) {
            this.threadName = threadName;
            this.n = n;
        }

        @Override
        public void run() {
            for (int i=0;i<100;i++) {
                System.out.println(threadName+":"+i);
                synchronized(monitor) {
                    if (n==1) t1Counter.set(i);
                    if (n==2) t2Counter.set(i);
                    monitor.notifyAll();
                    Thread.yield();
                    try {
                        if (n==1) {
                            if (i>t2Counter.get()) {
                                System.out.println("t1 is ahead with i="+i+", wait for t2Counter="+t2Counter);
                                monitor.wait();
                            }
                            if (t1Counter.get() == t2Counter.get() && t1Counter.get() % 10 == 0) {
                                monitor.wait();
                            }
                        }
                        if (n==2) {
                            if (i>t1Counter.get()) {
                                System.out.println("t2 is ahead with i="+i+", wait for t1Counter="+t1Counter);
                                monitor.wait();
                            }
                            if (t2Counter.get() == t1Counter.get() && t2Counter.get() % 10 == 0) {
                                monitor.wait();
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Thread.yield();
            }
        }
    }

    @Test
    public void testThread() {
        counterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    synchronized (monitor) {
                        while (t1Counter.get() != t2Counter.get()) {
                            try {
                                monitor.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        if (t1Counter.get() % 10 == 0 && t2Counter.get() % 10 ==0  && t1Counter.get() > 0 && t2Counter.get() > 0) {
                            System.err.printf("Counter %s = %s\n", t1Counter, t2Counter);
                            try {
                                monitor.notifyAll();
                                monitor.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });
        t1 = new Thread(new TestThread("t1", 1));
        t2 = new Thread(new TestThread("t2", 2));
        System.out.println("Starting threads");
        counterThread.setDaemon(true);
        counterThread.start();
        t1.start();
        t2.start();

        System.out.println("Waiting for threads");
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}