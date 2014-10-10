class PositionMatrixComparator {
    static int compare(byte[][] a, byte[][] b) {
        boolean less = false, greater = false;
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a[i].length; j++)
                if (a[i][j] < b[i][j])
                    less = true;
                else if (a[i][j] > b[i][j])
                    greater = true;

        if (less == greater)
            return 0;
        else if (less)
            return -1;
        else
            return +1;
    }
}
