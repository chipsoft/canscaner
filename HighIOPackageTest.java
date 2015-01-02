package ua.com.chipsoft.j2534;

import org.junit.Test;

import static org.junit.Assert.*;

public class HighIOPackageTest {

//    @Test
//    public void testIsCRCValid() throws Exception {
//        byte[] dataArray = {1, 2, 3};
//        HighIOPackage highIOPackage = new HighIOPackage(1, dataArray);
//        assertEquals(6, highIOPackage.getCrc());
//    }

    @Test
    public void testToByteArray() throws Exception {
        byte[] dataArray = {1, 2, 3};
        HighIOPackage highIOPackage = new HighIOPackage(1, dataArray);
        byte[] expected = {1, 0, 3, 0, 0, 0, 6, 0, 1, 2, 3};
        assertArrayEquals (expected, highIOPackage.toByteArray());

    }
}