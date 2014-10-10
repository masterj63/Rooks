import java.util.List;

class PositionByListPosition {
    static byte[][][] get(List<byte[]> list) {
        byte[][][] res = new byte[list.size()][Main.N][];
        for (int i = 0; i < list.size(); i++) {

            byte[] a = list.get(i);

            byte[][] t = new byte[Main.N][];
            for (int j = 0; j < Main.N; j++)
                t[j] = new byte[j];

            if (a[Main.N - 1] == 0)
                t[Main.N - 1][0] = 1;

            for (int d = 0; d < Main.N - 1; d++) {
                final int j1 = Main.N - d - 1, j2 = d;

                for (int j = j1; d < j; j--) {
                    boolean bOk = 1 + j < Main.N;
                    boolean lOk = 0 < j2;

                    byte left = lOk ? t[j][j2 - 1] : 0;
                    byte bottom = bOk ? t[1 + j][j2] : 0;
                    byte leftBottom = lOk && bOk ? t[1 + j][j2 - 1] : 0;

                    t[j][j2] = (byte) (left + bottom - leftBottom);
                    if (a[j] == j2)
                        t[j][j2]++;
                }

                for (int j = j2; j < j1; j++) {
                    boolean bOk = 1 + j1 < Main.N;
                    boolean lOk = 0 < j;

                    byte left = lOk ? t[j1][j - 1] : 0;
                    byte bottom = bOk ? t[1 + j1][j] : 0;
                    byte leftBottom = lOk && bOk ? t[1 + j1][j - 1] : 0;

                    t[j1][j] = (byte) (left + bottom - leftBottom);
                    if (a[j1] == j)
                        t[j1][j]++;
                }
            }

            res[i] = t;
        }

        return res;
    }
}
