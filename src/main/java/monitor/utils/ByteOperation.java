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

    public static long getLongID(byte[] bytes) {
        return new BigInteger(bytes).longValue();
    }

    public static String getHexValue(byte[] byteVal) {
        StringBuilder sb = new StringBuilder(byteVal.length * 2);
        for (byte b : byteVal) {
            sb.append(String.format("%02x", b));
        }
        return "0x" + sb.toString();
    }

    public static String getEtherType(byte[] packetHeader) {
        byte[] etherSlice = Arrays.copyOfRange(packetHeader, 12, 14);
        return ByteOperation.getHexValue(etherSlice);
    }


}
