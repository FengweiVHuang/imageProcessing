package jobmanager;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RobotTests {

    @Test
    public void test_constructor1() {
        try {
            new Robot(0);
            new Robot(1);
            new Robot(Integer.MAX_VALUE);
        }
        catch (Exception e) {
            fail();
        }
    }

    @Test
    public void test_constructor2() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {new Robot(-1);});
        assertEquals(e.getMessage(), "Robot id must be strictly positive or 0 for a Null Robot");
    }

    @Test
    public void test_isNull1() {
        Robot robot = new Robot(0);
        assertTrue(robot.isNull());
    }

    @Test
    public void test_isNull2() {
        Robot robot = new Robot(1);
        assertFalse(robot.isNull());
    }

    @Test
    public void test_equals1() {
        Robot r1 = new Robot(1);
        Robot r2 = new Robot(1);
        assertEquals(r1, r2);
    }

    @Test
    public void test_equals2() {
        Robot r1 = new Robot(1);
        Robot r2 = new Robot(2);
        assertNotEquals(r1, r2);
    }

    @Test
    public void test_equals3() {
        Robot r1 = new Robot(0);
        Robot r2 = new Robot(1);
        assertNotEquals(r1, r2);
    }

    @Test
    public void test_equals4() {
        Robot r1 = new Robot(1);
        Robot r2 = new Robot(0);
        assertNotEquals(r1, r2);
    }

    @Test
    public void test_equals5() {
        Robot r1 = new Robot(0);
        Robot r2 = new Robot(0);
        assertNotEquals(r1, r2);
    }

    @Test
    public void test_hashCode1() {
        int id = 1;
        Robot r = new Robot(id);
        assertEquals(id, r.hashCode());
    }

    @Test
    public void test_hashCode2() {
        int id = 0;
        Robot r = new Robot(id);
        assertEquals(id, r.hashCode());
    }
}
