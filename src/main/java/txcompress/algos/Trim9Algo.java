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
        char[] leftPad = new char[2673 - compressionResult.getTrytes().length()];
        for(int i = 0; i < leftPad.length; i++)
            leftPad[i] = '9';
        return new DecompressionResult(new String(leftPad) + compressionResult.getTrytes());
    }
}
