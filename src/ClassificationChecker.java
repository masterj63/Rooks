import java.util.*;

import static java.util.Arrays.fill;

public class ClassificationChecker {
    private final Board[] boards;
    private final Set<Integer>[] invSort;
    private final Map<Board, Integer> boardIndMap;

    private ClassificationChecker(List<byte[]> boardsList, List<Integer>[] sort) {
        final int n = boardsList.size();

        boards = new Board[boardsList.size()];
        for (int i = 0; i < n; i++)
            boards[i] = new Board(boardsList.get(i));

        invSort = Sorter.inverseSort(sort);

        boardIndMap = new HashMap<>();
        for (int i = 0; i < n; i++)
            boardIndMap.put(boards[i], i);
    }

    static boolean check(List<byte[]> boardsList, List<Integer>[] sort) {
        ClassificationChecker checker = new ClassificationChecker(boardsList, sort);
        return checker.check();
    }

    private static boolean rookIsGreater(int a, int b, int c, int d) {
        return a > c && b < d;
    }

    private boolean check() {
        for (int i = 0; i < boards.length; i++) {
            boolean b = check(i);
            if (!b)
                return false;
        }
        return true;
    }

    private boolean check(int ind) {
        return getL(ind).equals(getN(ind));
    }

    private Set<Integer> getL(int ind) {
        return invSort[ind];
    }

    private Board getMWave(int ind) {
        Board board = boards[ind];
        final int n = board.size;
        byte[] b = new byte[n];
        fill(b, (byte) -1);
        f:
        for (int i = 0; i < n; i++) {
            byte j = board.get(i);
            if (j == -1)
                continue;
            for (int p = 0; p < n; p++) {
                byte q = board.get(p);
                if (q == -1)
                    continue;
                if (i > p && j < q)
                    continue f;
            }
            b[i] = j;
        }
        return new Board(b);
    }

    private Board getM(int ind) {
        Board b = getMWave(ind);
        final int n = b.size;
        byte[] res = new byte[n];
        fill(res, (byte) -1);

        Board d = boards[ind];
        byte[] cols = d.cols();

        f:
        for (int i = 0; i < n; i++) {
            byte j = b.get(i);
            if (j == -1)
                continue;
            for (int k = 1 + j; k < i; k++) {
                if (d.get(k) == -1 || cols[k] == -1)
                    continue f;
            }
            res[i] = j;
        }
        return new Board(res);
    }

    private Set<Integer> getNMinus(int ind) {
        Set<Integer> set = new HashSet<>();
        Board M = getM(ind);

        for (int i = 0; i < M.size; i++) {
            byte j = M.get(i);
            if (j == -1)
                continue;
            Board b = M.remove(i);
            Integer t = boardIndMap.get(b);
            set.add(t);
        }

        return set;
    }

    private Board getDRight(int ind, int i) {
        Board b = boards[ind];

        byte j = b.get(i);
        if (j == -1)
            throw new IllegalArgumentException();

        int m = -1;
        for (int k = 1 + j; k < i; k++)
            if (b.get(k) == -1) {
                m = k;
                break;
            }
        if (m == -1)
            return null;

        for (int p = 0; p < b.size; p++) {
            int q = b.get(p);
            if (q == -1)
                continue;
            if (rookIsGreater(i, j, p, q) && !rookIsGreater(i, m, p, q))
                return null;
        }

        return b.remove(i).add(i, (byte) m);
    }

    private Board getDUp(int ind, int i) {
        Board b = boards[ind];

        byte j = b.get(i);
        if (j == -1)
            throw new IllegalArgumentException();

        int m = -1;
        for (int k = i - 1; j < k; k--)
            if (b.get(k) == -1) {
                m = k;
                break;
            }
        if (m == -1)
            return null;

        for (int p = 0; p < b.size; p++) {
            int q = b.get(p);
            if (q == -1)
                continue;
            if (rookIsGreater(i, j, p, q) && !rookIsGreater(m, j, p, q))
                return null;
        }

        return b.remove(i).add(m, j);
    }

    private List<BytePair> getB(int ind, int i, int j) {
        List<BytePair> res = new ArrayList<>();
        Board board = boards[ind];

        f:
        for (int a = 0; a < board.size; a++) {
            int b = board.get(a);
            if (b == -1)
                continue;
            if (!rookIsGreater(a, b, i, j))
                continue;
            for (int p = 0; p < board.size; p++) {
                int q = board.get(p);
                if (q == -1)
                    continue;
                if (rookIsGreater(a, b, p, q) && rookIsGreater(p, q, i, j))
                    continue f;
            }
            res.add(new BytePair(a, b));
        }

        return res;
    }

    private Board getDInterchange(int ind, byte i, byte j, byte a, byte b) {
        Board board = boards[ind];
        return board.remove(i).remove(a).add(i, b).add(a, j);
    }

    private Set<Integer> getNZero(int ind) {
        throw new UnsupportedOperationException();
    }

    private List<BytePair> getC(int ind, int i, int j) {
        throw new UnsupportedOperationException();
    }

    private Board getDSplitting(int ind, int i, int j, int a, int b) {
        throw new UnsupportedOperationException();
    }

    private Set<Integer> getNPlus(int ind) {
        throw new UnsupportedOperationException();
    }

    private Set<Integer> getN(int ind) {
        Set<Integer> res = new HashSet<>();

        Set<Integer> nMinus = getNMinus(ind);
        Set<Integer> nZero = getNZero(ind);
        Set<Integer> nPlus = getNPlus(ind);

        res.addAll(nMinus);
        res.addAll(nZero);
        res.addAll(nPlus);

        return res;
    }

    private class BytePair {
        final byte b0, b1;

        public BytePair(int b0, int b1) {
            this.b0 = (byte) b0;
            this.b1 = (byte) b1;
        }
    }
}