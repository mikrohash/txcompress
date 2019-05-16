package txcompress.algos;

public class Repeat9Algo extends RepeatAlgo {

    @Override
    public String getName() {
        return "Repeat-9";
    }

    @Override
    protected String compress(String trytes) {
        return compress("9", trytes);
    }

    @Override
    protected String decompress(String compressedTrytes) {
        return decompress("9", compressedTrytes);
    }
}
