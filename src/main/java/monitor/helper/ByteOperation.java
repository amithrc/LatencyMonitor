package main.java.monitor.helper;

import com.google.common.primitives.Bytes;

public class ByteOperation {
    /**
     * Takes hexa String as an argument returns a byte array
     *
     * @param s - String in hexadecimal
     * @return return byte array representation of the Hexa string passed
     */
    public static byte[] hexToByte(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    /**
     * Returns the concatenated byte array
     *
     * @param a - Byte array 1
     * @param b - Byte array 2
     * @return - concatenated of a and b
     */
    public static byte[] concatByteArray(byte[] a, byte[] b) {
        return Bytes.concat(a, b);
    }

}
