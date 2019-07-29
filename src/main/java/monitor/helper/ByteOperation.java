package main.java.monitor.helper;

import com.google.common.primitives.Bytes;

import java.math.BigInteger;
import java.util.Arrays;

public class ByteOperation {

    /**
     * Returns the bytes taking the string as argument
     *
     * @param s - String as argument
     * @return Returns byte array
     */

    public static byte[] getBytes(String s) {
        return s.getBytes();
    }

    /**
     * Returns the concatenated byte array
     *
     * @param arrays -vargs
     * @return - byte array
     */
    public static byte[] concatByteArray(byte[]... arrays) {
        return Bytes.concat(arrays);
    }

    public static byte[] getIID(long val) {
        return BigInteger.valueOf(val).toByteArray();
    }

    public static byte[] getMacHeader() {
        byte[] bytes = new byte[14];
        Arrays.fill(bytes, (byte) 'f');
        return bytes;
    }

}
