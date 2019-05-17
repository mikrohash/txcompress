package txcompress.algos;

public class FieldTrim9Algo extends CompressionAlgo {

    private final byte[] txBuffer = new byte[2673/3*2];
    private final byte[] nullTx = new byte[2673/3*2];
    private static final byte DELIMITER_BYTE = (byte)255;
    private static final byte NULL_BYTE = (byte)0;

    @Override
    public String getName() {
        return "FieldTrim9Algo";
    }

    @Override
    protected CompressionResult compress() {
        byte[] input = getBytesOfTryteString();
        int outputWriteOffset = 0;

        for(Field field : Field.values()) {
            int trimPos;
            for(trimPos = field.byteLength-1; trimPos > 1 && input[field.byteOffset+field.byteLength-trimPos] == NULL_BYTE && input[field.byteOffset+field.byteLength-trimPos-1] == NULL_BYTE; trimPos-=2);
            System.arraycopy(input, field.byteOffset, txBuffer, outputWriteOffset, trimPos);
            txBuffer[outputWriteOffset] = DELIMITER_BYTE;
            outputWriteOffset += trimPos+1;
            System.out.println("trimmed away " + (field.byteLength-trimPos-1) + " from " + field.name());
        }

        byte[] output = new byte[outputWriteOffset-1];
        System.arraycopy(txBuffer, 0, output, 0, output.length);
        return new CompressionResult(output);
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {
        byte[] compressed = compressionResult.getBytes();
        System.arraycopy(nullTx, 0, txBuffer, 0, txBuffer.length);

        Field[] fields = Field.values();
        int fieldIndex = 0;

        int compressedFieldOffset = 0;
        for(int i = 0; i < compressed.length; i++) {
            if(compressed[i] == DELIMITER_BYTE) {
                Field field = fields[fieldIndex];
                System.arraycopy(compressed, compressedFieldOffset, txBuffer, field.byteOffset, i-compressedFieldOffset);
                fieldIndex++;
                compressedFieldOffset = i+1;
            }
        }

        return new DecompressionResult(txBuffer);
    }

    public enum Field {
       SIGNATURE_FRAGMENTS(6561, null),
                EXTRA_DATA_DIGEST(243, SIGNATURE_FRAGMENTS),
                ADDRESS(243, EXTRA_DATA_DIGEST),
                VALUE(81, ADDRESS),
                ISSUANCE_TIMESTAMP(27, VALUE),
                TIMELOCK_LOWER_BOUND(27, ISSUANCE_TIMESTAMP),
                TIMELOCK_UPPER_BOUND(27, TIMELOCK_LOWER_BOUND),
                BUNDLE_NONCE(81, TIMELOCK_UPPER_BOUND),
                TRUNK_HASH(243, BUNDLE_NONCE),
                BRANCH_HASH(243, TRUNK_HASH),
                TAG(81, BRANCH_HASH),
                ATTACHMENT_TIMESTAMP(27, TAG),
                ATTACHMENT_TIMESTAMP_LOWER_BOUND(27, ATTACHMENT_TIMESTAMP),
                ATTACHMENT_TIMESTAMP_UPPER_BOUND(27, ATTACHMENT_TIMESTAMP_LOWER_BOUND),
                NONCE(81, ATTACHMENT_TIMESTAMP_UPPER_BOUND);

        public final int tritOffset, tritLength, tryteOffset, tryteLength, byteOffset, byteLength;

        private Field(int length, Field previousField) {
            assert length > 0;
            assert length % 3 == 0;
            this.tritLength = length;
            this.tritOffset = previousField == null ? 0 : previousField.tritOffset + previousField.tritLength;
            this.tryteOffset = tritOffset / 3;
            this.tryteLength = tritLength / 3;
            this.byteOffset = tryteOffset / 3 * 2;
            this.byteLength = tryteLength / 3 * 2;
        }
    }
}
