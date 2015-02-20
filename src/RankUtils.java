import java.util.Arrays;

class RankUtils {
    private RankUtils() {
    }

    static int hypotheticalRank(byte[] board) {
        byte[] kerov = kerovification(board);
        int[] perm = boardToPerm(kerov);

        int ex = permEx(perm);
        int el = permL(perm);
        if (ex + el % 2 == 1)
            Main.toReport.append("odd (ex+el) found!!\n");
        return (ex + el) / 2;
    }

    private static byte[] kerovification(byte[] board) {
        int n = board.length;
        byte[] kerov = new byte[2 * n - 2];
        Arrays.fill(kerov, (byte) -1);

        for (int i = 0; i < n; i++) {
            if (board[i] == -1)
                continue;
            kerov[2 * i - 1] = (byte) (2 * board[i]);
        }
        return kerov;
    }

    private static int[] boardToPerm(byte[] board) {
        int n = board.length;
        int[] perm = new int[n];

        for (int i = 0; i < n; i++) {
            int t = i;
            for (int j = n - 1; j > 0; j--)
                if (board[j] == -1)
                    continue;
                else if (t == j)
                    t = board[j];
                else if (t == board[j])
                    t = j;
            perm[i] = t;
        }

        return perm;
    }

    private static int permEx(int[] perm) {
        int res = 0;
        for (int i = 0; i < perm.length; i++)
            if (i < perm[i])
                res++;
        return res;
    }

    private static int permL(int[] perm) {
        int res = 0;
        for (int i = 0; i < perm.length; i++)
            for (int j = 1 + i; j < perm.length; j++)
                if (perm[i] > perm[j])
                    res++;
        return res;
    }
}
