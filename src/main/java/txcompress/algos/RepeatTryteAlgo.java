package txcompress.algos;

public class RepeatTryteAlgo extends RepeatAlgo {

    private static final String TRYTES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ9";

    @Override
    public String getName() {
        return "Repeat-Tryte";
    }

    @Override
    protected CompressionResult compress() {
        String trytes = getTryteString();
        for(int i = 0; i < TRYTES.length(); i++)
            trytes = compress(TRYTES.charAt(i)+"", trytes);
        return new CompressionResult(trytes);
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {
        String compressedTrytes = compressionResult.getTrytes();
        for(int i = 0; i < TRYTES.length(); i++)
            compressedTrytes = decompress(TRYTES.charAt(TRYTES.length()-1-i)+"", compressedTrytes);
        return new DecompressionResult(compressedTrytes);
    }
}
