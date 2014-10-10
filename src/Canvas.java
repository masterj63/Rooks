import javax.swing.*;
import java.awt.*;
import java.util.List;

class Canvas {
    private final JPanel jPanel;

    private final int pxBetweenNum;
    private final int pxBetweenMat;

    private final int frameWidth;
    private final int frameHeight;

    private int maxWidth = 0, maxHeight = 0;

    static void draw(List<List<Integer>> layers, byte[][][] position) {
        new Canvas(layers, position);
    }

    private Canvas(List<List<Integer>> layers, byte[][][] position) {
        int graphDepth = layers.size();
        int graphWidth = 0;
        for (List<Integer> lay : layers)
            graphWidth = Math.max(lay.size(), graphWidth);

        pxBetweenNum = 15;
        pxBetweenMat = 100 + Main.N * pxBetweenNum;

        frameWidth = (graphDepth + 3) * pxBetweenMat;
        frameHeight = (graphWidth + 3) * pxBetweenMat;

        JFrame jFrame = new JFrame("title");
        jFrame.setPreferredSize(new Dimension(800, 600));
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jPanel = new JPanel(null);

        JScrollPane jScrollPane = new JScrollPane(jPanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        jFrame.setContentPane(jScrollPane);

        drawMatrices(layers, position);

        jPanel.setPreferredSize(new Dimension(maxWidth, maxHeight));

        jFrame.pack();
        jFrame.setVisible(true);
        jFrame.repaint();

        //Shape shape = Temp.createArrowShape(new Point(100, 100), new Point(300, 400));
        //Graphics g = jFrame.getGraphics();
        //Graphics2D g2 = (Graphics2D) g;
        //g2.draw(shape);
        //jFrame.repaint();
    }

    private void drawMatrices(List<List<Integer>> layers, byte[][][] position) {
        int x = 0;
        for (List<Integer> lay : layers) {
            int y = 0;
            for (Integer ind : lay) {
                drawMatrix(position[ind], x * pxBetweenMat, y * pxBetweenMat);
                y++;
            }
            x++;
        }
    }

    private void drawMatrix(byte[][] mat, int x, int y) {
        int n = mat.length;
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

            final int W = 100, H = 60;
            jLabel.setBounds(tx, ty, W, H);
            maxWidth = Math.max(maxWidth, tx + W);
            maxHeight = Math.max(maxHeight, ty + H);

            jPanel.add(jLabel);
        }
        assert false;
    }
}
