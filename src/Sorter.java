import java.util.ArrayList;
import java.util.List;

class Sorter {
    static List<Integer>[] sort(byte[][][] a) {
        int n = a.length;

        @SuppressWarnings("unchecked")
        List<Integer>[] res = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            res[i] = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                int c = PositionMatrixComparator.compare(a[i], a[j]);
                if (c != -1)
                    continue;
                boolean betweenExists = false;
                for (byte[][] pos1 : a) {
                    int c1 = PositionMatrixComparator.compare(a[i], pos1);
                    int c2 = PositionMatrixComparator.compare(pos1, a[j]);

                    if (c1 == -1 && c2 == -1) {
                        betweenExists = true;
                        break;
                    }
                }
                if (!betweenExists)
                    res[i].add(j);
            }
        }

        return res;
    }
}
