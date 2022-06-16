package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private int T;
    private Percolation[] samples;
    private static double[] x;


    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }
        this.T = T;
        samples = new Percolation[T];
        x = new double[T];
        for (int i = 0; i < T; i++) {
            samples[i] = pf.make(N);
            while(!samples[i].percolates()) {
                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                if (!samples[i].isOpen(row, col)) {
                    samples[i].open(row, col);
                }
            }
            x[i] = ((double) samples[i].numberOfOpenSites()) / ((double) (N * N));
        }
    }
    public double mean() {
        double sum = 0.0;
        for (double frac : x) {
            sum += frac;
        }
        return sum/ ((double) (T));
    }

    public double stddev() {
        double mean = mean();
        double s = 0.0;
        for (double frac: x) {
            s += (frac - mean) * (frac - mean);
        }
        return Math.sqrt(s/((double) (T - 1)));
    }
    public double confidenceLow() {
        return mean() - 1.96 * stddev()/ Math.sqrt((double) (T));
    }
    public double confidenceHigh() {
        return mean() + 1.96 *stddev()/ Math.sqrt((double) (T));
    }
}
