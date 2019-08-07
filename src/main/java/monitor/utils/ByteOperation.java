package main.java.monitor.utils;

import com.google.common.primitives.Bytes;

import java.math.BigInteger;
import java.nio.ByteBuffer;
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
        return ByteBuffer.allocate(8).putLong(val).array();
    }

    /**
     * Returns the 14 byte ethernet header
     *
     * @param sourceMac - Takes the source interface MAc address
     * @return - returns the 14 bytes Ether net header
     */
    public static byte[] getMacHeader(byte[] sourceMac) {
        byte[] destinationMac = new byte[6];
        Arrays.fill(destinationMac, (byte) 'f');
        byte[] ethertype = new byte[2];
        Arrays.fill(ethertype, (byte) 'f');
        return concatByteArray(destinationMac, sourceMac, ethertype);
    }

    public static long getInteger(byte[] bytes) {
        return new BigInteger(bytes).longValue();

    }


}
