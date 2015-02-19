import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Sorter {
    static List<Integer>[] sort(byte[][][] a) {
        final int n = a.length;

        @SuppressWarnings("unchecked")
        List<Integer>[] res = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            res[i] = new ArrayList<>();

            for (int j = 0; j < n; j++) {
                int c = MatrixComparator.compare(a[i], a[j]);
                if (c != -1)
                    continue;
                boolean betweenExists = false;
                for (byte[][] pos1 : a) {
                    int c1 = MatrixComparator.compare(a[i], pos1);
                    int c2 = MatrixComparator.compare(pos1, a[j]);

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

    static Set<Integer>[] inverseSort(List<Integer>[] sort) {
        final int n = sort.length;

        Set<Integer>[] res = new HashSet[n];
        for (int i = 0; i < n; i++)
            res[i] = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (Integer j : sort[i])
                res[j].add(i);
        }

        return res;
    }
}
