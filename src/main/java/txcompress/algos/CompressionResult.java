package txcompress.algos;

public class CompressionResult {

    private String trytes;
    private byte[] bytes;

    public CompressionResult(String trytes) {
        this.trytes = trytes;
    }

    public CompressionResult(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getTrytes() {
        return trytes;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public int getSize() {
        if(trytes == null)
            return bytes.length;
        return trytes.length();
    }

}
