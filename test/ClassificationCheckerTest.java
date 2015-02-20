import org.junit.Before;
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

    @BeforeClass
    public static void makeClassificationCheckers() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        List<Integer>[] sort = new List[0];

        {
            boardNotesStringList.add("Paper2, p. 14/944, D");
            byte[] board = {-1, -1, 0, -1, 3, 1, 2, 5};
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

            byte[] waveBoard = {-1, -1, 0, -1, 3, -1, -1, 5};
            Board mWave = new Board(waveBoard);
            mWaveBoardList.add(mWave);
        }

        {
            boardNotesStringList.add("Paper2, p. 14/944, D 62 54");
            byte[] board = {-1, -1, 0, -1, 1, 3, 2, 5};
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

            byte[] waveBoard = {-1, -1, 0, -1, 1, 3, -1, 5};
            Board mWave = new Board(waveBoard);
            mWaveBoardList.add(mWave);
        }

        {
            boardNotesStringList.add("Paper2, p. 14/944, D 62 up");
            byte[] board = {-1, -1, 0, 1, 3, -1, 2, 5};
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

            byte[] waveBoard = {-1, -1, 0, 1, 3, -1, -1, 5};
            Board mWave = new Board(waveBoard);
            mWaveBoardList.add(mWave);
        }

        {
            boardNotesStringList.add("Paper2, p. 14/944, D 73 right");
            byte[] board = {-1, -1, 0, -1, 3, 1, 4, 5};
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

            byte[] waveBoard = {-1, -1, 0, -1, 3, -1, 4, 5};
            Board mWave = new Board(waveBoard);
            mWaveBoardList.add(mWave);
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