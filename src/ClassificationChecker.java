import java.util.*;

public class ClassificationChecker {
    private final List<byte[]> boardsList;
    //private final List<Integer>[] sort;
    private final Set<Integer>[] invSort;
    private final Map<byte[], Integer> map;

    private ClassificationChecker(List<byte[]> boardsList, List<Integer>[] sort) {
        this.boardsList = boardsList;
        //this.sort = sort;
        this.invSort = Sorter.inverseSort(sort);

        map = new HashMap<>();
        for (int i = 0; i < this.boardsList.size(); i++)
            map.put(this.boardsList.get(i), i);
    }

    private boolean check() {
        for (int i = 0; i < boardsList.size(); i++) {
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
        throw new UnsupportedOperationException();
    }

    private Set<Integer> getNZero(int ind) {
        throw new UnsupportedOperationException();
    }

    private Set<Integer> getNPlus(int ind) {
        throw new UnsupportedOperationException();
    }
}