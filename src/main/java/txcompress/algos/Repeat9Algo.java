package txcompress.algos;

public class Repeat9Algo extends RepeatAlgo {

    @Override
    public String getName() {
        return "Repeat-9";
    }

    @Override
    protected CompressionResult compress() {
        return new CompressionResult(compress("9", getTryteString()));
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {
        return new DecompressionResult(decompress("9", compressionResult.getTrytes()));
    }
}
