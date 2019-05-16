package txcompress.algos;

public class RepeatTryteAlgo extends RepeatAlgo {

    private static final String TRYTES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ9";

    @Override
    public String getName() {
        return "Repeat-Tryte";
    }

    @Override
    protected String compress(String trytes) {
        for(int i = 0; i < TRYTES.length(); i++)
            trytes = compress(TRYTES.charAt(i)+"", trytes);
        return trytes;
    }

    @Override
    protected String decompress(String compressedTrytes) {
        for(int i = 0; i < TRYTES.length(); i++)
            compressedTrytes = decompress(TRYTES.charAt(TRYTES.length()-1-i)+"", compressedTrytes);
        return compressedTrytes;
    }
}
