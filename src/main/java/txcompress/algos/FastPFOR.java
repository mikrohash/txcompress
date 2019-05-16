package txcompress.algos;

import me.lemire.integercompression.differential.IntegratedIntCompressor;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

public class FastPFOR extends CompressionAlgo {

    private IntegratedIntCompressor iic = new IntegratedIntCompressor();

    @Override
    public String getName() {
        return "FastPFOR";
    }

    @Override
    protected byte[] compress(byte[] bytes) {

        // fill up to 1784 bytes, to have exactly 446 integers
        bytes = addByte(bytes, (byte) 0);
        bytes = addByte(bytes, (byte) 0);

        // bytes to int
        int[] data = bytesToInt(bytes);

        // compress and return as bytes
        int[] compressed = iic.compress(data);
        return integersToBytes(compressed);

    }

    @Override
    protected byte[] decompress(byte[] compressedBytes) {
        int[] recov = iic.uncompress(bytesToInt(compressedBytes));
        byte[] bytes = integersToBytes(recov);
        // remove last 2 bytes
        bytes = Arrays.copyOf(bytes, bytes.length - 2);
        return bytes;
    }


    public int[] bytesToInt(byte[] bytes) {
        IntBuffer intBuf = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        int[] data = new int[intBuf.remaining()];
        intBuf.get(data);
        return data;
    }

    byte[] integersToBytes(int[] values)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        for(int i=0; i < values.length; ++i) {
            try {
                dos.writeInt(values[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return baos.toByteArray();
    }

    private static byte[] addByte(byte[] a, byte e) {
        a  = Arrays.copyOf(a, a.length + 1);
        a[a.length - 1] = e;
        return a;
    }

}
