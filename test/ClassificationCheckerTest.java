import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClassificationCheckerTest {
    private static List<ClassificationChecker> classificationCheckerList = new ArrayList<>();
    private static List<String> boardNotesStringList = new ArrayList<>();

    private static List<Method> getMWaveMethodList = new ArrayList<>();
    private static List<Board> mWaveBoardList = new ArrayList<>();
    private static List<Board> mBoardList = new ArrayList<>();

    private static class TestAdder {
        final List<Integer>[] sort = new List[0];
        String testNote = null;
        byte[] board = null;
        byte[] mWaveBoard = null;
        byte[] mBoard = null;

        void add() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
                InstantiationException {
            if(testNote == null || board == null || mWaveBoard == null || mBoard == null)
                throw new IllegalStateException();

            boardNotesStringList.add(testNote);

            List<byte[]> list = new ArrayList<>();
            list.add(board);

            Constructor<ClassificationChecker> constructor =
                    ClassificationChecker.class.getDeclaredConstructor(List.class, List[].class);
            constructor.setAccessible(true);
            ClassificationChecker checker = constructor.newInstance(list, sort);
            classificationCheckerList.add(checker);

            Method getMWave = ClassificationChecker.class.getDeclaredMethod("getMWave", Integer.TYPE);
            getMWave.setAccessible(true);
            getMWaveMethodList.add(getMWave);

            Board mWave = new Board(mWaveBoard);
            mWaveBoardList.add(mWave);

            Board m = new Board(mBoard);
            mBoardList.add(m);
        }
    }

    @BeforeClass
    public static void makeClassificationCheckers() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        List<Integer>[] sort = new List[0];

        {
            TestAdder test = new TestAdder();
            test.testNote = "Paper2, p. 14/944, D";
            test.board = new byte[]{-1, -1, 0, -1, 3, 1, 2, 5};
            test.mWaveBoard = new byte[]{-1, -1, 0, -1, 3, -1, -1, 5};
            test.mBoard = new byte[]{-1, -1, -1, -1, 3, -1, -1, -1};
            test.add();
        }

        {
            TestAdder test = new TestAdder();
            test.testNote = "Paper2, p. 14/944, D 62 54";
            test.board = new byte[]{-1, -1, 0, -1, 1, 3, 2, 5};
            test.mWaveBoard = new byte[]{-1, -1, 0, -1, 1, 3, -1, 5};
            test.mBoard = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1};
            test.add();
        }

        {
            TestAdder test = new TestAdder();
            test.testNote = "Paper2, p. 14/944, D 62 up";
            test.board = new byte[]{-1, -1, 0, 1, 3, -1, 2, 5};
            test.mWaveBoard = new byte[]{-1, -1, 0, 1, 3, -1, -1, 5};
            test.mBoard = new byte[]{-1, -1, -1, 1, 3, -1, -1, -1};
            test.add();
        }

        {
            TestAdder test = new TestAdder();
            test.testNote = "Paper2, p. 14/944, D 73 right";
            test.board = new byte[]{-1, -1, 0, -1, 3, 1, 4, 5};
            test.mWaveBoard = new byte[]{-1, -1, 0, -1, 3, -1, 4, 5};
            test.mBoard = new byte[]{-1, -1, -1, -1, 3, -1, 4, -1};
            test.add();
        }

        {
            TestAdder test = new TestAdder();
            test.testNote = "Paper2, p. 15/945, D 33 62";
            test.board = new byte[]{-1, -1, 1, 0, 3, 2};
            test.mWaveBoard = new byte[]{-1, -1, 1, -1, 3, -1};
            test.mBoard = new byte[]{-1, -1, 1, -1, 3, -1};
            test.add();
        }

        {
            TestAdder test = new TestAdder();
            test.testNote = "my test";
            test.board = new byte[]{-1, -1, -1, 2, 0, 1};
            test.mWaveBoard = new byte[]{-1, -1, -1, 2, -1, -1};
            test.mBoard = new byte[]{-1, -1, -1, 2, -1, -1};
            test.add();
        }
    }

    @Test
    public void checkListSizes() {
        assertEquals(classificationCheckerList.size(),
                boardNotesStringList.size());

        assertEquals(classificationCheckerList.size(),
                getMWaveMethodList.size());

        assertEquals(classificationCheckerList.size(),
                mWaveBoardList.size());

        assertEquals(classificationCheckerList.size(),
                mBoardList.size());
    }

    @Test
    public void testGetMWave() throws InvocationTargetException, IllegalAccessException {
        int n = classificationCheckerList.size();
        for (int i = 0; i < n; i++) {
            ClassificationChecker checker = classificationCheckerList.get(i);
            Method getMWave = getMWaveMethodList.get(i);
            Board board = mWaveBoardList.get(i);

            assertEquals(boardNotesStringList.get(i),
                    getMWave.invoke(checker, 0),
                    board);
        }
    }
}