package ua.com.chipsoft.j2534;

/**
 * Created by chipsoft on 29.12.14.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("CANScaner v1.0");
        byte[] dataArray = {1, 2};
        HighIOPackage highIOPackage = new HighIOPackage(1, dataArray);
        System.out.println(highIOPackage.toString());
    }
}
