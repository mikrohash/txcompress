package txcompress.algos;

public class Trim9Algo extends CompressionAlgo {

    @Override
    public String getName() {
        return "Trim-9";
    }

    @Override
    protected String compress(String trytes) {
        return trytes.replaceAll("^9*", "");
    }

    @Override
    protected String decompress(String compressedTrytes) {
        char[] leftPad = new char[2673 - compressedTrytes.length()];
        for(int i = 0; i < leftPad.length; i++)
            leftPad[i] = '9';
        return new String(leftPad) + compressedTrytes;
    }
}
