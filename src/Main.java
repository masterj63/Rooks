import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

class Main {
    static int N = 5;

    static StringBuilder toReport = new StringBuilder();

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("log");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(300, 270));

        JTextArea jTextArea = new JTextArea();
        jTextArea.setEditable(false);
        jFrame.add(jTextArea);

        JScrollPane jScrollPane = new JScrollPane(jTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jFrame.setContentPane(jScrollPane);

        jFrame.pack();
        jFrame.setVisible(true);

        try {
            String answer = JOptionPane.showInputDialog(null, "What is the dimension?", Integer.toString(N));
            N = Integer.parseInt(answer);
        } catch (Throwable throwable) {}

        jTextArea.append(String.format("Dimension set to %d.\n", N));

        int boardsListSize = -1;
        int maxLayerSize = -1;
        int layersNum = -1;

        long time = -System.currentTimeMillis();

        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String timeStamp = dateFormat.format(cal.getTime());

            jTextArea.append(String.format("0/5. started at %s.\n", timeStamp));

            List<byte[]> boardsList = ListPositionByN.get();
            jTextArea.append("1/5. positions computed.\n");
            boardsListSize = boardsList.size();

            byte[][][] matrices = MatricesByListPosition.get(boardsList);
            jTextArea.append("2/5. boards computed.\n");

            List<Integer>[] sort = Sorter.sort(matrices);
            jTextArea.append("3/5. matrices sorted.\n");

            List<List<Integer>> layers = LayersBySort.get(sort);
            jTextArea.append("4/5. layers computed.\n");
            for (List<Integer> t : layers)
                maxLayerSize = Math.max(maxLayerSize, t.size());
            layersNum = layers.size();

            Canvas.draw(layers, boardsList, matrices);
            jTextArea.append("5/5. i'm done.\n");

            int nonEu = Euler.get(layers, sort);
            jTextArea.append("6/5. non-eulerity is " + nonEu + " \n");
        }catch (Throwable error){
            error.printStackTrace(System.out);
            jTextArea.append("Report may be corrupted!!\n");
        }

        time += System.currentTimeMillis();

        jTextArea.append("\nREPORT\n");
        jTextArea.append(String.format("Number of boards: %d \n", boardsListSize));
        jTextArea.append(String.format("Number of layers: %d \n", layersNum));
        jTextArea.append(String.format("Maximal layer size: %d \n", maxLayerSize));
        jTextArea.append(String.format("Time spent: %.3f sec \n", time / 1000.0d));


        if(toReport.length() > 0){
            jTextArea.append(String.format("\nAdditional info:"));
            jTextArea.append(toReport.toString());
        }

        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}
