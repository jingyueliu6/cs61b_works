package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean [][] arr;
    private int [][] arrNum;
    private WeightedQuickUnionUF QU;
    private int count;
    private int top;
    private int bottom;


    private int helper(int row, int col) {
        return row * N + col;
    }

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        arr = new boolean[N][N];
        arrNum = new int[N][N];
        count = 0;
        top = N*N;
        bottom = N*N +1;
        this.N = N;
        QU = new WeightedQuickUnionUF(N*(N+2));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                arr[i][j] = false;
                arrNum[i][j] = helper(i, j);
            }
        }

        for (int j = 0; j < N; j++) {
            QU.union(arrNum[0][j], top);
            QU.union(arrNum[N-1][j], bottom);
        }
    }

    private void argumentChecker(int row, int col) {
        if (row >= 0 && row < N && col >= 0 && col < N) {
            return;
        }
        throw new IndexOutOfBoundsException();
    }


    public void open(int row, int col) {
        argumentChecker(row, col);
        if (!isOpen(row, col)) {
            arr[row][col] = true;
            count += 1;
            if (row-1 >=0 && isOpen(row-1, col)) {
                QU.union(arrNum[row-1][col], arrNum[row][col]);
            }
            if (row+1 < N && isOpen(row+1, col)) {
                QU.union(arrNum[row+1][col], arrNum[row][col]);
            }
            if (col-1 >=0 && isOpen(row, col-1)) {
                QU.union(arrNum[row][col-1], arrNum[row][col]);
            }
            if (col+1 < N && isOpen(row, col+1)) {
                QU.union(arrNum[row][col+1], arrNum[row][col]);
            }
        }

    }

    public boolean isOpen(int row, int col) {
        argumentChecker(row, col);
        return arr[row][col];
    }

    public boolean isFull(int row, int col) {
        argumentChecker(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return QU.connected(arrNum[row][col], top);
    }

    public int numberOfOpenSites()
    {
        return count;
    }

    public boolean percolates() {
        return QU.connected(top, bottom);
    }

    public static void main (String[] args) {
        Percolation sample = new Percolation(5);
        sample.open(3, 4);
        sample.open(2, 4);
        sample.open(2, 2);
        sample.open(2, 3);
        sample.open(0, 2);
        sample.open(1, 2);
        System.out.println(sample.isFull(2, 2));
        System.out.println(sample.percolates());
    }

}
