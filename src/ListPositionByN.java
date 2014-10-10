import java.util.ArrayList;
import java.util.List;

class ListPositionByN {
    static List<byte[]> get() {
        ListPositionByN instance = new ListPositionByN();
        return instance.list;
    }

    private ListPositionByN() {
        this.N = Main.N;
        this.list = new ArrayList<>();
        this.arr = new byte[Main.N];
        rec(0);
    }

    private final int N;
    private final List<byte[]> list;
    private final byte[] arr;

    private void rec(final int pos) {
        if (arr.length == pos) {
            list.add(arr.clone());
            return;
        }

        arr[pos] = -1;
        rec(1 + pos);

        for (byte j = 0; j < pos; j++) {
            boolean used = false;
            for (int k = 0; k < pos; k++)
                used |= j == arr[k];
            if (used)
                continue;
            arr[pos] = j;
            rec(1 + pos);
        }
    }
}
