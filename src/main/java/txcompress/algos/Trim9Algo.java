package txcompress.algos;

public class Trim9Algo extends CompressionAlgo {

    @Override
    public String getName() {
        return "Trim-9";
    }

    @Override
    protected CompressionResult compress() {
        return new CompressionResult(getTryteString().replaceAll("^9*", ""));
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {
        char[] leftPad = new char[2673 - compressionResult.getTrytes().length()];
        for(int i = 0; i < leftPad.length; i++)
            leftPad[i] = '9';
        return new DecompressionResult(new String(leftPad) + compressionResult.getTrytes());
    }
}
