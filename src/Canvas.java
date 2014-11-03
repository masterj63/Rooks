import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Canvas {
    private final JPanel jPanel;

    private final int pxBetweenNum;
    private final int pxBetweenMat;

    private int maxWidth = 0, maxHeight = 0;

    private Map<Integer, Point> indCoordMap = new HashMap<>();
    private List<JLabel> matricesJLabelList = new ArrayList<>();
    private List<JLabel> boardsJLabelList = new ArrayList<>();

    static void draw(List<List<Integer>> layers, byte[][][] matrices) {
        new Canvas(layers, matrices);
    }

    private Canvas(List<List<Integer>> layers, byte[][][] matrices) {
        int graphDepth = layers.size();
        int graphWidth = 0;
        for (List<Integer> lay : layers)
            graphWidth = Math.max(lay.size(), graphWidth);

        pxBetweenNum = 15;
        pxBetweenMat = 100 + Main.N * pxBetweenNum;

        int frameWidth = (graphDepth + 3) * pxBetweenMat;
        int frameHeight = (graphWidth + 3) * pxBetweenMat;

        JFrame jFrame = new JFrame("title");
        jFrame.setPreferredSize(new Dimension(800, 600));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel = new JPanel(null);

        JScrollPane jScrollPane = new JScrollPane(jPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        jFrame.setContentPane(jScrollPane);

        //drawMatrices(layers, matrices);
        computeCoordinates(layers);
        initializeMatricesJLabelList(matrices, layers);

        jPanel.setPreferredSize(new Dimension(maxWidth, maxHeight));

        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.repaint();
    }

    private void computeCoordinates(List<List<Integer>> layers) {
        assert indCoordMap.isEmpty();

        int x = 0;
        for (List<Integer> lay : layers) {
            int y = 0;
            for (Integer ind : lay) {
                Point coord = new Point(x * pxBetweenMat, y * pxBetweenMat);
                indCoordMap.put(ind, coord);
                y++;
            }
            x++;
        }
    }

    private void initializeMatricesJLabelList(byte[][][] matrices, List<List<Integer>> layers){
        assert matricesJLabelList.isEmpty();
        assert !indCoordMap.isEmpty();

        for(List<Integer> list : layers)
            for(int i : list)
                initializeMatrixJLabel(matrices[i], indCoordMap.get(i));
    }

    private void initializeMatrixJLabel(byte[][] mat, Point point) {
        int n = mat.length;
        int x = point.x, y = point.y;

        for (int i = 0; i < n; i++) {
            StringBuilder rowString = new StringBuilder();
            rowString.append("<html>");
            for (int j = 0; j < n; j++) {
                if (mat[i].length < j)
                    rowString.append(0);
                else if (mat[i].length == j)
                    rowString.append("<font color='#D8D8D8'>0");
                else
                    rowString.append(mat[i][j]);
                rowString.append(' ');
            }
            rowString.append("</font></html>");
            JLabel jLabel = new JLabel(rowString.toString());

            int tx = x;
            int ty = y + i * pxBetweenNum;

            final int W = 100, H = 60;//max dim of label; note this if something goes wrong
            jLabel.setBounds(tx, ty, W, H);
            maxWidth = Math.max(maxWidth, tx + W);
            maxHeight = Math.max(maxHeight, ty + H);

            matricesJLabelList.add(jLabel);
        }
        assert false;
    }
}
