package txcompress.algos;

public class Trim9Algo extends CompressionAlgo {

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
        char[] trytes = compressionResult.getTrytes().toCharArray();

        // left pad 9's
        int trimPos = 2673 - trytes.length;
        for(int i = 0; i < trimPos; i++)
            buffer[i] = '9';

        // fill with rest
        for(int i = 0; i < trytes.length; i++) {
            buffer[trimPos+i] = trytes[i];
        }

        return new DecompressionResult(new String(buffer));
    }
}
