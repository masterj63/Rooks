import java.util.Arrays;

class RankUtils {
    private RankUtils() {
    }

    static byte[] kerovification(byte[] board) {
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

    static int[] boardToPerm(byte[] board) {
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

    static int permEx(int[] perm) {
        int res = 0;
        for (int i = 0; i < perm.length; i++)
            if (i < perm[i])
                res++;
        return res;
    }

    static int permL(int[] perm) {
        int res = 0;
        for (int i = 0; i < perm.length; i++)
            for (int j = 1 + i; j < perm.length; j++)
                if (perm[i] > perm[j])
                    res++;
        return res;
    }
}
