import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import java.lang.Math;

public class PercolationStats {

    private int gridLengthHeight;
    private int numberOfTrials;
    private double[] trials;

    public PercolationStats(int gridLengthHeight, int numberOfTrials)
    {
        if (gridLengthHeight < 1 || numberOfTrials < 1)
        {
            throw new IllegalArgumentException();
        }

        this.numberOfTrials = numberOfTrials;
        this.gridLengthHeight = gridLengthHeight;
        this.trials = new double[numberOfTrials];
        var siteNumbers = new int[gridLengthHeight * gridLengthHeight];
        for (var i = 0; i < siteNumbers.length; i++)
        {
            siteNumbers[i] = i + 1;
        }

        for (var i = 0; i < this.numberOfTrials; i++)
        {
            StdRandom.shuffle(siteNumbers);
            trials[i] = percolationTrial(siteNumbers, gridLengthHeight);
        }
    }

    public double mean()
    {
        return StdStats.mean(trials);
    }

    public double stddev()
    {
        return StdStats.stddev(trials);
    }

    public double confidenceLo()
    {
        return mean() - (1.96f * stddev()) / Math.sqrt(trials.length);
    }

    public double confidenceHi()
    {
        return mean() + (1.96f * stddev()) / Math.sqrt(trials.length);
    }

    public static void main(String[] args)
    { ;
        var percolationStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        var mean = String.format("mean\t= %f", percolationStats.mean());
        var standardDeviation = String.format("stddev\t= %f", percolationStats.stddev());
        var confidenceInterval = String.format("95%% confidence interval\t = [ %f , %f ]", percolationStats.confidenceLo(), percolationStats.confidenceHi());
        StdOut.println(mean);
        StdOut.println(standardDeviation);
        StdOut.println(confidenceInterval);
    }

    private double percolationTrial(int[] shuffledSiteNumbers, int n)
    {
        var percolation = new Percolation(n);
        for (var siteNumber: shuffledSiteNumbers)
        {
            var rowAndColumn = getRowAndColumn(siteNumber);
            percolation.open(rowAndColumn[0], rowAndColumn[1]);
            if (percolation.percolates())
            {
                return (double)percolation.numberOfOpenSites() / (n * n);
            }
        }
        return 0;
    }

    private int[] getRowAndColumn(int siteNumber)
    {
        var row = (siteNumber - 1) / gridLengthHeight + 1;
        var column = siteNumber % gridLengthHeight;
        if (column == 0) column = gridLengthHeight;
        return new int[] {row, column};
    }
}
