import java.util.*;

public class ClassificationChecker {
    private final Board[] boards;
    private final Set<Integer>[] invSort;
    private final Map<Board, Integer> boardIndMap;

    static boolean check(List<byte[]> boardsList, List<Integer>[] sort) {
        ClassificationChecker checker = new ClassificationChecker(boardsList, sort);
        return checker.check();
    }

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

    private Board getM(int ind) {
        Board b = getMWave(ind);
        final int n = b.size;
        byte[] res = new byte[n];
        Arrays.fill(res, (byte) -1);
        byte[] cols = new byte[n];
        Arrays.fill(cols, (byte) -1);

        Board d = boards[ind];
        for(byte i = 0; i < n; i++){
            byte j = d.get(i);
            if(j == -1)
                continue;
            cols[j] = i;
        }

        f:for(int i = 0; i < n; i++){
            byte j = b.get(i);
            if(j == -1)
                continue;
            for(int k = 1 + j; k < i; k++){
                if(d.get(k) == -1 || cols[k] == -1)
                    continue f;
            }
            res[i] = j;
        }
        return new Board(res);
    }

    private Board getMWave(int ind) {
        Board board = boards[ind];
        final int n = board.size;
        byte[] b = new byte[n];
        for(int i = 0; i < b.length; i++)
            b[i] = -1;
        f:for(int i = 0; i < n; i++){
            byte j = board.get(i);
            if(j == -1)
                continue;
            for(int p = 0; p < n; p++){
                byte q = board.get(p);
                if(q == -1)
                    continue;
                if(i > p && j < q)
                    continue f;
            }
            b[i] = j;
        }
        return new Board(b);
    }

    private Set<Integer> getNZero(int ind) {
        throw new UnsupportedOperationException();
    }

    private Set<Integer> getNPlus(int ind) {
        throw new UnsupportedOperationException();
    }
}