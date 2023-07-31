package randomizedtest;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */

public class TestBuggyAList {
   @Test
    /*Adds the same value to both the correct and buggy AList implementations,
    * then checks that the results to three subsequent removeLast() are the same.*/
  public void testThreeAddThreeRemove() {
      AListNoResizing<Integer> noResizingAList = new AListNoResizing<>();
      BuggyAList<Integer> buggyAList = new BuggyAList<>();
      for (int i = 4; i < 7; i++) {
          noResizingAList.addLast(i);
          buggyAList.addLast(i);
      }
      for (int i = 0; i < 3; i++) {
          assertEquals(noResizingAList.removeLast(), buggyAList.removeLast());
      }
  }

  @Test
    /*Makes random calls to both implementations and verify they always return the same value.*/
    public void randomizedTest() {
        AListNoResizing<Integer> correct = new AListNoResizing<>();
        BuggyAList<Integer> broken = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                //addLast
                int randVal = StdRandom.uniform(0, 100);
                correct.addLast(randVal);
                broken.addLast(randVal);
            } else if (operationNumber == 1) {
                //size
                int correctSize = correct.size();
                int brokenSize = broken.size();
                assertEquals("Size should always be the same", correctSize, brokenSize);
            } else if (operationNumber == 2) {
                //removeLast
                if (correct.size() == 0) {
                    continue;
                }
                int correctRemovedLast = correct.removeLast();
                int brokenRemoveLast = broken.removeLast();
                assertEquals("Removed last should always be the same", correctRemovedLast, brokenRemoveLast);
            } else if (operationNumber == 3) {
                //getLast
                if (correct.size() == 0) {
                    continue;
                }
                int correctLast = correct.getLast();
                int brokenLast = broken.getLast();
                assertEquals("The last item should always be the same", correctLast, brokenLast);
            }
        }
  }
}
