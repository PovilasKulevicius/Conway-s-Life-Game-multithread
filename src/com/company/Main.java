package com.company;

import java.util.concurrent.CyclicBarrier;

class Main {
    static int threadCount; //1:18 2:11 4:9

    public static void main(String[] args) throws InterruptedException {
        threadCount = Integer.parseInt(args[0]);
        Conway.countLimit = Integer.parseInt(args[1]);
//        Conway.countLimit = 20000;
        //System.out.println("Number of processors: " + Runtime.getRuntime().availableProcessors());
        //System.out.println("Number of threads: " + threadCount);
        final Grid bendras = new Grid();
        //System.out.println("Original Generation");
        //Conway.printGrid(bendras.grid);
        Conway thread[] = new Conway[threadCount];
        int startlegth = 0;
        int endlength = 0;
        int lengthpart = bendras.grid.length / threadCount;
        CyclicBarrier barrier = new CyclicBarrier(threadCount);
        for (int i = 0; i < threadCount; i++) {
            //counts last endlength for last uneven thread
            if(i == 2 && threadCount == 3) endlength += 1;
            if((i == 5 && threadCount == 6)||(i == 11 && threadCount == 12)) endlength += 4;
            if((i == 6 && threadCount == 7) || (i == 10 && threadCount == 11)) endlength += 6;
            if(i == 8 && threadCount == 9) endlength += 7;
            endlength += lengthpart;
            //System.out.println("startlegth: " + startlegth);
            //System.out.println("endlength: " + endlength);
            thread[i] = new Conway(bendras, startlegth, endlength, barrier);
            startlegth += lengthpart;
        }
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < threadCount; ++i) {
            thread[i].start();
        }
        for (int i = 0; i < threadCount; ++i) {
            thread[i].join();
        }
        long endTime = System.currentTimeMillis();
        //Conway.printGrid(bendras.grid);
        System.out.println(threadCount + " threads took: " + (endTime - startTime)/1000 + " seconds");
    }
}


