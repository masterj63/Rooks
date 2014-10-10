import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class LayersBySortAndPosition {
    static List<List<Integer>> get(List<Integer>[] sort) {
        LayersBySortAndPosition instance = new LayersBySortAndPosition(sort);
        return instance.LAYERS;
    }

    private LayersBySortAndPosition(List<Integer>[] sort) {
        this.SORT = sort;
        this.LAYERS = new ArrayList<>();
        this.LAYERS_SET = new ArrayList<>();

        boolean[] pointed = new boolean[sort.length];
        for (List<Integer> list : sort)
            for (int i : list)
                pointed[i] = true;

        int min = -1;
        for (int i = 0; i < pointed.length; i++)
            if (!pointed[i]) {
                min = i;
                break;
            }

        dfs(min, 0);
    }

    private final List<Integer>[] SORT;
    private final List<List<Integer>> LAYERS;
    private final List<Set<Integer>> LAYERS_SET;

    private void dfs(int pos, int layer) {
        while (LAYERS_SET.size() <= layer)//btw while is replaceable by just if, but let's let it be for the sake of safety
            LAYERS_SET.add(new HashSet<Integer>());

        while (LAYERS.size() <= layer)//btw while is replaceable by just if, but let's let it be for the sake of safety
            LAYERS.add(new ArrayList<Integer>());

        if (LAYERS_SET.get(layer).contains(pos))
            return;

        LAYERS.get(layer).add(pos);
        LAYERS_SET.get(layer).add(pos);
        for (int next : SORT[pos])
            dfs(next, 1 + layer);
    }
}
