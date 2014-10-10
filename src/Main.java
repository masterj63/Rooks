import java.util.List;

class Main {
    static int N;

    public static void main(String[] args) {
        try {
            N = Integer.parseInt(args[0]);
        } catch (Throwable throwable) {
            N = 5;
        }

        long time = -System.currentTimeMillis();

        System.out.println("0/5. started.");

        List<byte[]> list = ListPositionByN.get();
        System.out.println("1/5. positions computed.");

        byte[][][] position = PositionByListPosition.get(list);
        System.out.println("2/5. boards computed.");

        List<Integer>[] sort = Sorter.sort(position);
        System.out.println("3/5. matrices sorted.");

        List<List<Integer>> layers = LayersBySortAndPosition.get(sort);
        System.out.println("4/5. layers computed.");

        Canvas.draw(layers, position);
        System.out.println("5/5. i'm done.");

        Euler.get(layers, sort);
        System.out.println("6/5. eulerity checked.");

        time += System.currentTimeMillis();

        int maxLayerSize = 0;
        for (List<Integer> t : layers)
            maxLayerSize = Math.max(maxLayerSize, t.size());
        int layersNum = layers.size();
        System.out.println();
        System.out.println("REPORT");
        System.out.printf("Number of boards: %d \n", list.size());
        System.out.printf("Number of layers: %d \n", layersNum);
        System.out.printf("Maximal layer size: %d \n", maxLayerSize);
        System.out.printf("Time spent: %.3f sec \n", time / 1000.0d);
    }
}
