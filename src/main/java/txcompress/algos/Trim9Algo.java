package txcompress.algos;

import txcompress.utils.StringUtils;

public class Trim9Algo extends CompressionAlgo {

    private static char[] nullTx = StringUtils.repeat("9",2673).toCharArray();
    private final  char[] buffer = new char[2673];

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

        char[] trytes = compressionResult.getTrytes().toCharArray();

        int trimPos = 2673 - trytes.length;
        System.arraycopy(nullTx, 0, buffer, 0, trimPos);
        System.arraycopy(trytes, 0, buffer, trimPos, trytes.length);

        return new DecompressionResult(new String(buffer));
    }
}
