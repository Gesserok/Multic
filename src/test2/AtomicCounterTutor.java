package test2;
/*
37
        38
        39
        40
        41
        42
        43
        44
        45
        46
        47
        48
        49
        50
        51
        52
        53
        54
        55

        */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * Есть счетчик, подсчитывающий количество вызовов.
 *
 * Почему счетчик показывает разные значения и не считает до конца?
 * Как это можно исправить не используя synchronized?
 *
 * Попробуйте закомментировать обращение к yield().
 * Измениться ли значение?
 */
public class AtomicCounterTutor {
    int a;
    AtomicInteger counter = new AtomicInteger(0);


    class TestThread implements Runnable {
        String threadName;

        public TestThread(String threadName) {
            this.threadName = threadName;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                counter.getAndAdd(1);
//                Thread.yield();
            }
        }
        {}
    }

    @Test
    public void testThread() {

        List<Thread> threads = new ArrayList<>();
        List<TestThread> targets = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            TestThread target = new TestThread("t" + i);
            targets.add(target);

            Thread thread = new Thread(target);
            threads.add(thread);
        }
        System.out.println("Starting threads");
        for (Thread thread : threads) {
            thread.start();
        }
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.err.println("Counter="+counter);


        //@test is finished before ?
    }

}