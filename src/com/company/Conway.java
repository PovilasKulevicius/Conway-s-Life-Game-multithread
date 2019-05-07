package com.company;

import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Conway extends Thread {
    private final Grid bendras;
    private int startlength;
    private int endlength;
    private CyclicBarrier barrier;
    private int counter = 0;
    private String[][] superGrid;
    static int countLimit;

    Conway(Grid bendras, int startlength, int endlenth, CyclicBarrier barrier) {
        this.bendras = bendras;
        this.startlength = startlength;
        this.endlength = endlenth;
        this.barrier = barrier;
    }

    public void run() {
        while (true) {
            try {
                gameOfLife();
                barrier.await();
                copytoGrid(superGrid, startlength, endlength);
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (counter > countLimit)
                break;
            counter++;
        }
    }


    private void gameOfLife()
    {
        superGrid = copyfromGrid(bendras.grid);

        for (int i = startlength; i < endlength; i++)
        {
            for (int j = 0; j < bendras.grid[i].length; j++)
            {

                int life_forms = 0;
                if (bendras.grid[Math.floorMod(i - 1, bendras.grid.length)][Math.floorMod(j - 1, bendras.grid[i].length)].equals("X"))
                    life_forms++;
                if(bendras.grid[Math.floorMod(i - 1, bendras.grid.length)][j].equals("x"))
                    life_forms++;
                if(bendras.grid[Math.floorMod(i - 1, bendras.grid.length)][Math.floorMod(j + 1, bendras.grid[i].length)].equals("X"))
                    life_forms++;
                if(bendras.grid[i][Math.floorMod(j - 1, bendras.grid[i].length)].equals("x"))
                    life_forms++;
                if(bendras.grid[i][Math.floorMod(j + 1, bendras.grid[i].length)].equals("x"))
                    life_forms++;
                if(bendras.grid[Math.floorMod(i + 1, bendras.grid.length)][Math.floorMod(j - 1, bendras.grid[i].length)].equals("X"))
                    life_forms++;
                if(bendras.grid[Math.floorMod(i + 1, bendras.grid.length)][j].equals("x"))
                    life_forms++;
                if(bendras.grid[Math.floorMod(i + 1, bendras.grid.length)][Math.floorMod(j + 1, bendras.grid[i].length)].equals("X"))
                    life_forms++;

                if (bendras.grid[i][j].compareTo(".") == 0)
                {
                    // check if I can spawn a new cell
                    if (life_forms == 3)
                    {
                        superGrid[i][j] = "X";
                    }
                }
                else
                { // else current cell lives
                    // check if cell must die of over-population or under-population
                    if (life_forms < 2 || life_forms > 3)
                    {
                        superGrid[i][j] = ".";
                    }
                }
            }
        }
    }

    private String[][] copyfromGrid(String[][] grid) {
        String[][] tempGrid = new String[grid.length][grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                tempGrid[i][j] = grid[i][j];
            }
        }

        return tempGrid;
    }

    private void copytoGrid(String[][] grid, int startlength, int endlength) {
        for (int i = startlength; i < endlength; i++) {
            for (int j = 0; j < bendras.grid[i].length; j++) {
                bendras.grid[i][j] = grid[i][j];
            }
        }
    }

    static void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 0)
                    System.out.print(" ");
                else
                    System.out.print("X");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printGrid(String[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(" " + matrix[i][j] + " ");
            }

            System.out.println();
        }
    }
}