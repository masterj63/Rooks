import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static java.lang.String.format;

class Main {
    static int N = 5;

    static StringBuilder toReport = new StringBuilder();

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("log");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setPreferredSize(new Dimension(300, 300));

        JTextArea jTextArea = new JTextArea();
        printPrompt(jTextArea);
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
        } catch (Throwable throwable) {
        }

        jTextArea.append(format("Dimension set to %d.\n\n", N));

        int boardsListSize = -1;
        int maxLayerSize = -1;
        int layersNum = -1;

        long time = -System.currentTimeMillis();

        try {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            String timeStamp = dateFormat.format(cal.getTime());

            jTextArea.append(format("0/6. started at %s.\n", timeStamp));

            List<byte[]> boardsList = ListPositionByN.get();
            jTextArea.append("1/6. positions computed.\n");
            boardsListSize = boardsList.size();

            byte[][][] matrices = MatricesByListPosition.get(boardsList);
            jTextArea.append("2/6. boards computed.\n");

            List<Integer>[] sort = Sorter.sort(matrices);
            jTextArea.append("3/6. matrices sorted.\n");

            List<List<Integer>> layers = LayersBySort.get(sort);
            jTextArea.append("4/6. layers computed.\n");
            for (List<Integer> t : layers)
                maxLayerSize = Math.max(maxLayerSize, t.size());
            layersNum = layers.size();

            Canvas.draw(layers, sort, boardsList, matrices);
            jTextArea.append("5/6. i'm done.\n");

            int nonEu = Euler.get(layers, sort);
            jTextArea.append("6/6. non-eulerity is " + nonEu + " \n");
        } catch (Throwable error) {
            error.printStackTrace(System.out);
            jTextArea.append("Report may be corrupted!!\n");
        }

        time += System.currentTimeMillis();

        jTextArea.append("\nREPORT\n");
        jTextArea.append(format("Number of boards: %d \n", boardsListSize));
        jTextArea.append(format("Number of layers: %d \n", layersNum));
        jTextArea.append(format("Maximal layer size: %d \n", maxLayerSize));
        jTextArea.append(format("Time spent: %.3f sec \n", time / 1000.0d));


        if (toReport.length() > 0) {
            jTextArea.append(format("\nAdditional info:"));
            jTextArea.append(toReport.toString());
        }

        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private static void printPrompt(JTextArea jTextArea) {
        jTextArea.setEditable(false);
        jTextArea.setLineWrap(true);
        jTextArea.append("Theoretically, boards may not display properly due to technical reasons.\n");
        jTextArea.append("But I tested the app with JDK8 under Windows 7 and OS X and Ubuntu so they must be shown correctly.\n\n");
        jTextArea.append("You may double-click the panel to switch between views.\n\n");
    }
}
