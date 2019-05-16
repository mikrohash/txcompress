package txcompress.algos;

import txcompress.utils.Trytes;

public abstract class CompressionAlgo {

    public final int compressedSize(String trytes) {

        byte[] compressed = compress(Trytes.toBytes(trytes));
        byte[] decompressed = decompress(compressed);
        String result = Trytes.fromBytes(decompressed, 0 , decompressed.length);

        if(!trytes.equals(result)) {
            System.err.println("original:     " + trytes);
            System.err.println("decompressed: " + result);
            throw new IllegalStateException("Decompression failed!");
        }

        return compressed.length;

    }

    public abstract String getName();

    protected abstract byte[] compress(byte[] bytes);
    protected abstract byte[] decompress(byte[] compressedBytes);

}
