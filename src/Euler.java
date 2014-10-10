import java.util.Arrays;
import java.util.List;

class Euler {
    private List<List<Integer>> layers;
    private List<Integer>[] sort;

    private int[] rho = new int[2];
    private int destInd, destLay;
    private int checkInd = 0;
    private int[] used;

    Euler(List<List<Integer>> layers, List<Integer>[] sort) {
        this.layers = layers;
        this.sort = sort;
        this.used = new int[sort.length];
    }

    private void run() {
        int nonEulerity = 0;

        for (int i0 = 0; i0 < layers.size(); i0++)
            for (int j0 = 1 + i0; j0 < layers.size(); j0++)
                for (int i1 = 0; i1 < layers.get(i0).size(); i1++)
                    for (int j1 = 0; j1 < layers.get(j0).size(); j1++) {
                        rho[0] = 0;
                        rho[1] = 0;
                        destInd = layers.get(j0).get(j1);
                        destLay = j0;
                        Arrays.fill(used, 0);

                        dfs(layers.get(i0).get(i1), i0);
                        if (rho[0] != rho[1]) {
                            nonEulerity++;
                        }
                    }

        System.out.println(nonEulerity);
    }

    private boolean dfs(int posInd, int posLay) {
        if (used[posInd] == +1)
            return true;
        if (used[posInd] == -1)
            return false;

        if (posInd == destInd) {
            rho[posLay % 2]++;
            used[posInd] = +1;
            return true;
        }

        if (destLay < posLay) {
            used[posInd] = -1;
            return false;
        }

        boolean onTheWay = false;
        for (int next : sort[posInd]) {
            onTheWay |= dfs(next, 1 + posLay);
        }
        if (onTheWay) {
            rho[posLay % 2]++;
            used[posInd] = +1;
            return true;
        } else {
            used[posInd] = -1;
            return false;
        }
    }

    static void get(List<List<Integer>> layers, List<Integer>[] sort) {
        new Euler(layers, sort).run();
    }
}
