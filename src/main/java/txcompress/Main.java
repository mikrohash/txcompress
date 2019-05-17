package txcompress;

import txcompress.algos.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TransactionData data = new TransactionData(new File("src/main/resources/tx_trytes.txt"));

        CompressionAlgo[] algos = new CompressionAlgo[] {
                new RepeatTryteAlgo(),
                new FastPFOR(),
                new Repeat9Algo(),
                new Trim9Algo(),
                new LZ4Algo(),
        };

        for(CompressionAlgo algo : algos)
            System.out.println(run(algo, data));
    }

    private static Metric run(CompressionAlgo algo, TransactionData data) {

        int compressedSize = 0, uncompressedSize = 0;
        long nanosDecompression = 0, nanosCompression = 0;

        for(String trytes : data.getTransactions()) {

            algo.initialize(trytes);
            long startTime = System.nanoTime();
            algo.runCompression();
            long compressionTime = System.nanoTime();
            algo.runDecompression();
            long decompressionTIme = System.nanoTime();
            algo.validate();

            uncompressedSize += algo.getDecompressedSize();
            compressedSize += algo.getCompressedSize();
            nanosDecompression += decompressionTIme - compressionTime;
            nanosCompression += compressionTime - startTime;

        }

        nanosCompression /= data.getTransactions().size();
        nanosDecompression /= data.getTransactions().size();

        double compressionRatio = (double) uncompressedSize / compressedSize;
        return new Metric(algo.getName(),  compressionRatio, nanosCompression, nanosDecompression);
    }

    static class Metric {

        private String name;
        private double compressionRatio;
        private long nanosCompression, nanosDecompression;

        public Metric(String name, double compressionRatio, long nanosCompression, long nanosDecompression) {
            this.name = name;
            this.compressionRatio = compressionRatio;
            this.nanosCompression = nanosCompression;
            this.nanosDecompression = nanosDecompression;
        }

        @Override
        public String toString() {
            return padRight(name, 14)
                    +" C: "+padLeft(nanosCompression /1000+"µs", 8) + ","
                    +" D: "+padLeft(nanosDecompression/1000+"µs", 8)+ ","
                    +" ratio: " +compressionRatio;
        }

        private static String padLeft(String string, int length) {
            return " ".repeat(length-string.length()) + string;
        }

        private static String padRight(String string, int length) {
            return string + " ".repeat(length-string.length());
        }
    }
}
