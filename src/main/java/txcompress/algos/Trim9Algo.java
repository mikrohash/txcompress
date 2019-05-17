package txcompress.algos;

import java.util.Arrays;

public class Trim9Algo extends CompressionAlgo {

    private static char[] nullTx = "9".repeat(2673).toCharArray();

    @Override
    public String getName() {
        return "Trim-9";
    }

    @Override
    protected CompressionResult compress() {
        char[] trytes = getTryteString().toCharArray();
        int i = 0;
        while (trytes[i++] == '9');
        CompressionResult result = new CompressionResult(getTryteString().substring(i-1));
        return result;
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {

        char[] buffer = new char[2673];
        System.arraycopy(nullTx, 0, buffer, 0, trimPos);

        char[] trytes = compressionResult.getTrytes().toCharArray();
        int trimPos = 2673 - trytes.length;
        System.arraycopy(trytes, 0, buffer, trimPos, trytes.length);

        return new DecompressionResult(new String(buffer));
    }
}
