package txcompress.algos;

public abstract class CompressionAlgo {

    public final int compressedSize(String trytes) {
        String compressed = compress(trytes);
        String decompressed = decompress(compressed);
        /*
        System.out.println();
        System.out.println(compressed);
        System.out.println(decompressed);
        */
        if(!trytes.equals(decompressed)) {
            System.err.println("original:     " + trytes);
            System.err.println("compressed:   " + compressed);
            System.err.println("decompressed: " + decompressed);
            throw new IllegalStateException("Decompression failed!");
        }

        return compressed.length();
    }

    public abstract String getName();

    protected abstract String compress(String trytes);

    protected abstract String decompress(String compressedTrytes);
}
