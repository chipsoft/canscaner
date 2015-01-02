package ua.com.chipsoft.j2534;

import java.util.Arrays;

/**
 * Created by chipsoft on 29.12.14.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("CANScaner v1.0");
        byte[] dataArray = {0, 1, 2, 0, 1, 0, 3, 0, 1, 2};
        HighIOPackage io1 = null;
        try {
            //io1 = HighIOPackage.createInstance(0x100, dataArray.length, 1, 3, dataArray);
            io1 = HighIOPackage.fromByteArray(dataArray);
            System.out.println(io1.toString());
            System.out.println(Arrays.toString(io1.toByteArray()));
        } catch (IllegalHighIOPackage illegalHighIOPackage) {
            illegalHighIOPackage.printStackTrace();
        }
//        HighIOPackage highIOPackage = new HighIOPackage(1, dataArray);

    }
}
