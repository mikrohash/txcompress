package txcompress;

import txcompress.algos.*;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TransactionData data = new TransactionData(new File("src/main/resources/tx_trytes.txt"));

        CompressionAlgo[] algos = new CompressionAlgo[] {
                new RepeatTryteAlgo(),
                new Repeat9Algo(),
                new Trim9Algo(),
                new LZ4Algo()
        };

        for(CompressionAlgo algo : algos)
            System.out.println(calculateMetrics(algo, data));
    }

    private static Result calculateMetrics(CompressionAlgo algo, TransactionData data) {
        long startTime = System.currentTimeMillis();
        int compressedSize = 0, uncompressedSize = 0;
        for(String trytes : data.getTransactions()) {
            uncompressedSize += trytes.length();
            compressedSize += algo.compressedSize(trytes);
        }
        return new Result(algo.getName(),  (double) uncompressedSize / compressedSize, System.currentTimeMillis() - startTime);
    }

    static class Result {

        String name;
        double compressionRatio;
        long time;

        public Result(String name, double compressionRatio, long time) {
            this.name = name;
            this.compressionRatio = compressionRatio;
            this.time = time;
        }

        @Override
        public String toString() {
            return name + " time: "+time + "ms, compression ratio: " +compressionRatio ;
        }

    }
}
