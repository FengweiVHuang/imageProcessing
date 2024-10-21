package jobmanager;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class JobManagerTests {

    @Test
    public void test_constructor1() {
        assertDoesNotThrow(() -> {
            new JobManager(1);
            new JobManager(10);
        });
    }

    @Test
    public void test_constructor2() {
        assertThrows(IllegalArgumentException.class, () -> new JobManager(0));
        assertThrows(IllegalArgumentException.class, () -> new JobManager(-1));
    }

    @Test
    public void test_constructor3() {
        JobManager jm = new JobManager(3);
        assertFalse(jm.isAssigned(1));
    }

    @Test
    public void test_addRobot1() {
        JobManager jm = new JobManager(1);
        assertTrue(jm.addRobot(new Robot(1)));
    }

    @Test
    public void test_addRobot2() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.addRobot(new Robot(0)));
    }

    @Test
    public void test_addRobot3() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.addRobot(new Robot(1)));
    }

    @Test
    public void test_addRobot4() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.addRobot(null));
    }

    @Test
    public void test_removeRobot1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.removeRobot(new Robot(1)));
    }

    @Test
    public void test_removeRobot2() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.removeRobot(new Robot(1)));
    }

    @Test
    public void test_removeRobot3() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.removeRobot(new Robot(0)));
    }

    @Test
    public void test_removeRobot4() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.removeRobot(null));
    }

    @Test
    public void test_removeRobot5() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 3);
        jm.removeRobot(new Robot(1));
        assertFalse(jm.isAssigned(3));
        assertFalse(jm.isAssigned(2));
        assertFalse(jm.isAssigned(1));
    }

    @Test
    public void test_hasRobot1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.hasRobot(new Robot(1)));
    }

    @Test
    public void test_hasRobot2() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.hasRobot(new Robot(1)));
    }

    @Test
    public void test_hasRobot3() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(1));
        assertTrue(jm.hasRobot(new Robot(1)));
    }

    @Test
    public void test_hasRobot4() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.removeRobot(new Robot(1));
        assertFalse(jm.hasRobot(new Robot(1)));
    }

    @Test
    public void test_hasRobot5() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.removeRobot(new Robot(1));
        jm.removeRobot(new Robot(1));
        assertFalse(jm.hasRobot(new Robot(1)));
    }

    @Test
    public void test_hasRobot6() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.hasRobot(new Robot(0)));
    }

    @Test
    public void test_hasRobot7() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.hasRobot(null));
    }

    @Test
    public void test_assignJobs1() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.assignJobs(new Robot(1), 1));
    }

    @Test
    public void test_assignJobs2() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.assignJobs(new Robot(0), 1));
    }

    @Test
    public void test_assignJobs3() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.assignJobs(null, 1));
    }

    @Test
    public void test_assignJobs4() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.assignJobs(new Robot(1), 1));
    }

    @Test
    public void test_assignJobs5() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.assignJobs(new Robot(1), 2));
    }

    @Test
    public void test_assignJobs6() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        assertTrue(jm.assignJobs(new Robot(1), 3));
    }

    @Test
    public void test_assignJobs7() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        jm.assignJobs(new Robot(1), 3);
        assertTrue(jm.assignJobs(new Robot(1), 3));
    }

    @Test
    public void test_assignJobs8() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        assertTrue(jm.assignJobs(new Robot(1), 0));
    }

    @Test
    public void test_assignJobs9() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertTrue(jm.isAssigned(1));
        assertEquals(new Robot(1), jm.getRobot(1));
    }

    @Test
    public void test_isAssigned1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertTrue(jm.isAssigned(1));
    }

    @Test
    public void test_isAssigned2() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.isAssigned(1));
    }

    @Test
    public void test_isAssigned3() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 2);
        assertTrue(jm.isAssigned(1) && jm.isAssigned(2));
    }

    @Test
    public void test_isAssigned4() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 2);
        assertFalse(jm.isAssigned(3));
    }

    @Test
    public void test_isAssigned5() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertFalse(jm.isAssigned(0));
    }

    @Test
    public void test_isAssigned6() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertFalse(jm.isAssigned(-1));
    }

    @Test
    public void test_getRobot1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertEquals(new Robot(1), jm.getRobot(1));
    }

    @Test
    public void test_getRobot2() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.getRobot(1).isNull());
    }

    @Test
    public void test_getRobot3() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.getRobot(0).isNull());
    }

    @Test
    public void test_getRobot4() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.getRobot(-1).isNull());
    }

    @Test
    public void test_getRobot5() {
        JobManager jm = new JobManager(1);
        assertTrue(jm.getRobot(1).isNull());
    }

    @Test
    public void test_moveJobsNoId1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), new Robot(2)));
    }

    @Test
    public void test_moveJobsNoId2() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(2), new Robot(1)));
    }

    @Test
    public void test_moveJobsNoId3() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.moveJobs(new Robot(1), new Robot(1)));
    }

    @Test
    public void test_moveJobsNoId4() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.moveJobs(new Robot(1), new Robot(1)));
    }

    @Test
    public void test_moveJobsNoId5() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertTrue(jm.moveJobs(new Robot(1), new Robot(1)));
    }

    @Test
    public void test_moveJobsNoId6() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        assertTrue(jm.moveJobs(new Robot(1), new Robot(2)));
    }

    @Test
    public void test_moveJobsNoId7() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        jm.assignJobs(new Robot(1), 3);
        assertTrue(jm.moveJobs(new Robot(1), new Robot(2)));
    }

    @Test
    public void test_moveJobsNoId8() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(0), new Robot(1)));
    }

    @Test
    public void test_moveJobsNoId9() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), new Robot(0)));
    }

    @Test
    public void test_moveJobsNoId10() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), new Robot(0)));
    }

    @Test
    public void test_moveJobsNoId11() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(null, new Robot(1)));
    }

    @Test
    public void test_moveJobsNoId12() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), null));
    }

    @Test
    public void test_moveJobsNoId13() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        jm.assignJobs(new Robot(1), 2);
        jm.moveJobs(new Robot(1), new Robot(2));
        assertTrue(jm.isAssigned(1));
        assertTrue(jm.isAssigned(2));
        assertFalse(jm.isAssigned(3));
        assertEquals(new Robot(2), jm.getRobot(1));
        assertEquals(new Robot(2), jm.getRobot(2));
        assertTrue(jm.getRobot(3).isNull());
    }

    @Test
    public void test_moveJobsWithId1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), new Robot(2), 1));
    }

    @Test
    public void test_moveJobsWithId2() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(2), new Robot(1),1));
    }

    @Test
    public void test_moveJobsWithId3() {
        JobManager jm = new JobManager(1);
        assertFalse(jm.moveJobs(new Robot(1), new Robot(1), 1));
    }

    @Test
    public void test_moveJobsWithId4() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertTrue(jm.moveJobs(new Robot(1), new Robot(1),1));
    }

    @Test
    public void test_moveJobsWithId5() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertTrue(jm.moveJobs(new Robot(1), new Robot(1), 1));
    }

    @Test
    public void test_moveJobsWithId6() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        assertTrue(jm.moveJobs(new Robot(1), new Robot(2), 1));
    }

    @Test
    public void test_moveJobsWithId7() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        jm.assignJobs(new Robot(1), 3);
        assertTrue(jm.moveJobs(new Robot(1), new Robot(2), 3));
    }

    @Test
    public void test_moveJobsWithId8() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(0), new Robot(1), 1));
    }

    @Test
    public void test_moveJobsWithId9() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), new Robot(0),1));
    }

    @Test
    public void test_moveJobsWithId10() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), new Robot(0),1));
    }

    @Test
    public void test_moveJobsWithId11() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(null, new Robot(1)));
    }

    @Test
    public void test_moveJobsWithId12() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertFalse(jm.moveJobs(new Robot(1), null));
    }


    @Test
    public void test_moveJobsWithId13() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.addRobot(new Robot(2));
        jm.assignJobs(new Robot(1), 2);
        jm.moveJobs(new Robot(1), new Robot(2), 2);
        assertTrue(jm.isAssigned(1));
        assertTrue(jm.isAssigned(2));
        assertFalse(jm.isAssigned(3));
        assertEquals(new Robot(2), jm.getRobot(1));
        assertEquals(new Robot(2), jm.getRobot(2));
        assertTrue(jm.getRobot(3).isNull());
    }

    @Test
    public void test_getHighestPriorityJob1() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        assertEquals(0, jm.getHighestPriorityJob(new Robot(1), 1));
    }

    @Test
    public void test_getHighestPriorityJob2() {
        JobManager jm = new JobManager(1);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 1);
        assertEquals(1, jm.getHighestPriorityJob(new Robot(1), 1));
    }

    @Test
    public void test_getHighestPriorityJob3() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 3);
        assertEquals(3, jm.getHighestPriorityJob(new Robot(1), 3));
    }

    @Test
    public void test_getHighestPriorityJob4() {
        JobManager jm = new JobManager(3);
        jm.addRobot(new Robot(1));
        jm.assignJobs(new Robot(1), 3);
        assertEquals(2, jm.getHighestPriorityJob(new Robot(1), 2));
        assertEquals(1, jm.getHighestPriorityJob(new Robot(1), 1));
        assertEquals(0, jm.getHighestPriorityJob(new Robot(1), 0));
    }

    @Test
    public void test_equals1() {
        JobManager jm1 = new JobManager(3);
        JobManager jm2 = new JobManager(3);
        assertEquals(jm1, jm2);
    }

    @Test
    public void test_equals2() {
        JobManager jm1 = new JobManager(3);
        jm1.addRobot(new Robot(1));
        JobManager jm2 = new JobManager(3);
        jm2.addRobot(new Robot(1));
        assertEquals(jm1, jm2);
    }

    @Test
    public void test_equals3() {
        JobManager jm1 = new JobManager(2);
        jm1.addRobot(new Robot(1));
        jm1.addRobot(new Robot(2));
        jm1.assignJobs(new Robot(1), 1);
        jm1.assignJobs(new Robot(2), 2);
        JobManager jm2 = new JobManager(2);
        jm2.addRobot(new Robot(1));
        jm2.addRobot(new Robot(2));
        jm2.assignJobs(new Robot(1), 1);
        jm2.assignJobs(new Robot(2), 2);
        assertEquals(jm1, jm2);
    }

    @Test
    public void test_equals4() {
        JobManager jm1 = new JobManager(3);
        jm1.addRobot(new Robot(1));
        jm1.addRobot(new Robot(2));
        jm1.assignJobs(new Robot(1), 1);
        jm1.assignJobs(new Robot(2), 2);
        JobManager jm2 = new JobManager(2);
        jm2.addRobot(new Robot(1));
        jm2.addRobot(new Robot(2));
        jm2.assignJobs(new Robot(1), 1);
        jm2.assignJobs(new Robot(2), 2);
        assertNotEquals(jm1, jm2);
    }

    @Test
    public void test_hashCode1() {
        JobManager jm1 = new JobManager(3);
        assertTrue(jm1.hashCode() == 3);
    }

    @Test
    public void test_hashCode2() {
        JobManager jm1 = new JobManager(3);
        jm1.addRobot(new Robot(2));
        assertTrue(jm1.hashCode() == 3);
    }

    @Test
    public void test_hashCode3() {
        JobManager jm1 = new JobManager(3);
        jm1.addRobot(new Robot(2));
        jm1.assignJobs(new Robot(2), 1);
        assertTrue(jm1.hashCode() == 5);
    }
}
