import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[] openSites;
    private int openSpaces = 0;
    private int n;
    private WeightedQuickUnionUF grid;
    private int totalNumberOfSites;

    public Percolation(int n)
    {
        if (n < 1)
        {
            throw new IllegalArgumentException();
        }
        grid = new WeightedQuickUnionUF(n * n + 2);
        this.n = n;
        this.totalNumberOfSites = n * n;
        openSites = new boolean[n * n + 2];
        openSites[0] = true;
        openSites[n*n + 1] = true;
    }

    public void open(int row, int col)
    {
        if (row > n || col > n || row < 1 || col < 1)
        {
            throw new IllegalArgumentException();
        }

        var siteNumber = coordinateToNumber(row, col);
        openSite(siteNumber);
        if (row == 1)
        {
            grid.union(0, siteNumber);
        }
        else if (row == n)
        {
            grid.union(totalNumberOfSites+ 1, siteNumber);
        }

        if (col < n)
        {
            if (openSites[siteNumber + 1])
            {
                grid.union(siteNumber, siteNumber + 1);
            }
        }

        if (col > 1)
        {
            if (openSites[siteNumber - 1])
            {
                grid.union(siteNumber, siteNumber - 1);
            }
        }

        if (row < n)
        {
            if (openSites[siteNumber + n])
            {
                grid.union(siteNumber, siteNumber + n);
            }
        }

        if (row > 1)
        {
            if (openSites[siteNumber - n])
            {
                grid.union(siteNumber, siteNumber - n);
            }
        }
    }

    public boolean isOpen(int row, int col)
    {
        if (row > n || col > n || row < 1 || col < 1)
        {
            throw new IllegalArgumentException();
        }

        var siteNumber = coordinateToNumber(row, col);
        return openSites[siteNumber];
    }

    public boolean isFull(int row, int col)
    {
        if (row > n || col > n || row < 1 || col < 1)
        {
            throw new IllegalArgumentException();
        }

        var siteNumber = coordinateToNumber(row, col);
        return grid.find(0) == grid.find(siteNumber);
    }

    public int numberOfOpenSites()
    {
        return openSpaces;
    }

    public boolean percolates()
    {
        return grid.find(0) == grid.find(n * n + 1);
    }

    public static void main(String[] args)
    {
        var percolation = new Percolation(4);
        //percolation.open(1, 1);
        /*var doesPercolate = percolation.percolates();
        percolation.open(2, 1);
        percolation.open(3, 1);
        percolation.open(4, 1);
        doesPercolate = percolation.percolates();*/
        System.out.println(percolation.isFull(1,1));
    }

    private void openSite(int siteNumber)
    {
        openSites[siteNumber] = true;
        openSpaces++;
    }

    private int coordinateToNumber(int row, int col)
    {
        return n *  (row - 1) + (col - 1) + 1;
    }
}
