import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

class Main {
    static int N;

    static StringBuilder toReport = new StringBuilder();

    public static void main(String[] args) {
        try {
            N = Integer.parseInt(args[0]);
        } catch (Throwable throwable) {
            N = 8;
        }

        int boardsListSize = -1;
        int maxLayerSize = -1;
        int layersNum = -1;

        long time = -System.currentTimeMillis();

        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String timeStamp = dateFormat.format(cal.getTime());

            System.out.printf("0/5. started at %s.\n", timeStamp);

            List<byte[]> boardsList = ListPositionByN.get();
            System.out.println("1/5. positions computed.");
            boardsListSize = boardsList.size();

            byte[][][] matrices = MatricesByListPosition.get(boardsList);
            System.out.println("2/5. boards computed.");

            List<Integer>[] sort = Sorter.sort(matrices);
            System.out.println("3/5. matrices sorted.");

            List<List<Integer>> layers = LayersBySort.get(sort);
            System.out.println("4/5. layers computed.");
            for (List<Integer> t : layers)
                maxLayerSize = Math.max(maxLayerSize, t.size());
            layersNum = layers.size();

            Canvas.draw(layers, boardsList, matrices);
            System.out.println("5/5. i'm done.");

            Euler.get(layers, sort);
            System.out.println("6/5. eulerity checked.");
        }catch (Throwable error){
            error.printStackTrace(System.out);
            System.out.println(">>>>>>Report may be corrupted!");
        }

        time += System.currentTimeMillis();

        System.out.println();
        System.out.println("REPORT");
        System.out.printf("Number of boards: %d \n", boardsListSize);
        System.out.printf("Number of layers: %d \n", layersNum);
        System.out.printf("Maximal layer size: %d \n", maxLayerSize);
        System.out.printf("Time spent: %.3f sec \n", time / 1000.0d);


        if(toReport.length() > 0){
            System.out.println("\nAdditional info:");
            System.out.println(toReport);
        }
    }
}
