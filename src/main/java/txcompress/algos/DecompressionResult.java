package txcompress.algos;

public class DecompressionResult {

    private String trytes;
    private byte[] bytes;

    public DecompressionResult(String trytes) {
        this.trytes = trytes;
    }

    public DecompressionResult(byte[] bytes) {
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
