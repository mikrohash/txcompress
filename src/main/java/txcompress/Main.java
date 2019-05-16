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
        long duration = 0;

        for(String trytes : data.getTransactions()) {

            algo.initialize(trytes);
            long startTime = System.currentTimeMillis();
            algo.run();
            long endTime = System.currentTimeMillis();
            algo.validate();

            uncompressedSize += algo.getDecompressedSize();
            compressedSize += algo.getCompressedSize();
            duration += endTime - startTime;

        }

        double compressionRatio = (double) uncompressedSize / compressedSize;
        return new Metric(algo.getName(),  compressionRatio, duration);
    }

    static class Metric {

        private String name;
        private double compressionRatio;
        private long duration;

        public Metric(String name, double compressionRatio, long duration) {
            this.name = name;
            this.compressionRatio = compressionRatio;
            this.duration = duration;
        }

        @Override
        public String toString() {
            return name + ", "+duration + "ns, ratio: " +compressionRatio;
        }

    }

}
