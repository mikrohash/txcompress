package txcompress.algos;

import net.jpountz.lz4.LZ4Compressor;
import net.jpountz.lz4.LZ4Factory;
import net.jpountz.lz4.LZ4FastDecompressor;

public class LZ4Algo extends CompressionAlgo {

    private LZ4Factory factory = LZ4Factory.fastestInstance();
    private int decompressedLength;

    @Override
    public String getName() {
        return "LZ4";
    }

    @Override
    protected CompressionResult compress() {
        byte[] bytesOfTryteString = getBytesOfTryteString();
        decompressedLength = bytesOfTryteString.length;

        LZ4Compressor compressor = factory.fastCompressor();
        int maxCompressedLength = compressor.maxCompressedLength(decompressedLength);
        byte[] compressed = new byte[maxCompressedLength];
        compressor.compress(bytesOfTryteString, 0, decompressedLength, compressed, 0, maxCompressedLength);

        return new CompressionResult(compressed);
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {

        LZ4FastDecompressor decompressor = factory.fastDecompressor();
        byte[] restored = new byte[decompressedLength];
        decompressor.decompress(compressionResult.getBytes(), 0, restored, 0, decompressedLength);

        return new DecompressionResult(restored);
    }

}