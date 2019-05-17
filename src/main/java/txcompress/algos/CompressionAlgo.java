package txcompress.algos;

import txcompress.utils.Trytes;

public abstract class CompressionAlgo {

    private String tryteString;
    private byte[] bytesOfTryteString;

    private CompressionResult compressionResult;
    private DecompressionResult decompressionResult;

    public void initialize(String trytes) {
        this.tryteString = trytes;
        bytesOfTryteString = Trytes.toBytes(trytes);
        compressionResult = null;
        decompressionResult = null;
    }

    public void runCompression() {
        compressionResult = compress();
    }

    public void runDecompression() {
        decompressionResult = decompress(compressionResult);
    }

    public void validate() {

        String decompressedTrytes = decompressionResult.getTrytes();
        if(decompressionResult.getBytes() != null)
            decompressedTrytes = Trytes.fromBytes(decompressionResult.getBytes(), 0 , decompressionResult.getBytes().length);

        if(!tryteString.equals(decompressedTrytes)) {
            System.err.println("original:     " + tryteString);
            System.err.println("decompressed: " + decompressedTrytes);
            throw new IllegalStateException(getName()+": decompression failed!");
        }

    }

    public int getDecompressedSize() {
        return decompressionResult.getSize();
    }

    public int getCompressedSize() {
        return compressionResult.getSize();
    }

    public abstract String getName();
    protected abstract CompressionResult compress();
    protected abstract DecompressionResult decompress(CompressionResult compressionResult);

    protected String getTryteString(){
        return new String(tryteString);
    }

    protected byte[] getBytesOfTryteString(){
        return bytesOfTryteString.clone();
    }

}
