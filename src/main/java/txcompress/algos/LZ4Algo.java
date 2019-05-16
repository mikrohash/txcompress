package txcompress.algos;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class LZ4Algo extends CompressionAlgo {

    private LZ4Factory factory = LZ4Factory.fastestInstance();
    private int decompressedLength;

    @Override
    public String getName() {
        return "LZ4";
    }

    @Override
    protected String compress(String trytes) {

        byte[] data = null;
        try {
            data = trytes.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        decompressedLength = data.length;


        LZ4Compressor compressor = factory.fastCompressor();
        int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
        byte[] compressed = new byte[maxCompressedLength];
        compressor.compress(data, 0, decompressedLength, compressed, 0, maxCompressedLength);

        return Base64.getEncoder().encodeToString(compressed);

    }

    @Override
    protected String decompress(String compressedTrytes) {

        byte[] compressed = Base64.getDecoder().decode(compressedTrytes);

        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        byte[] restored = new byte[decompressedLength];
        decompressor.decompress(compressed, 0, restored, 0, decompressedLength);

        return new String(restored);
    }

}
