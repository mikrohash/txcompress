package txcompress.algos;

public abstract class RepeatAlgo extends CompressionAlgo {

    protected String compress(String atom, String trytes) {
        return trytes
                .replaceAll(atom+"{2187}", "*G")
                .replaceAll(atom+"{729}", "*F")
                .replaceAll(atom+"{243}", "*E")
                .replaceAll(atom+"{81}", "*D")
                .replaceAll(atom+"{27}", "*C")
                .replaceAll(atom+"{9}", "*B")
                .replaceAll(atom+"{3}", "*A")
                .replaceAll("\\*", atom+atom+atom)
            ;
    }

    protected String decompress(String atom, String compressedTrytes) {
        return compressedTrytes
                .replaceAll(atom+atom+atom, "*")
                .replaceAll("\\*G", "*E*E*E*E*E*E*E*E*E")
                .replaceAll("\\*F", "*D*D*D*D*D*D*D*D*D")
                .replaceAll("\\*E", "*C*C*C*C*C*C*C*C*C")
                .replaceAll("\\*D", "*B*B*B*B*B*B*B*B*B")
                .replaceAll("\\*C", "*A*A*A*A*A*A*A*A*A")
                .replaceAll("\\*B", "*A*A*A")
                .replaceAll("\\*A", atom+atom+atom)
            ;
    }
}
