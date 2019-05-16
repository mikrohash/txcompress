package txcompress;

import txcompress.algos.*;
import txcompress.utils.Trytes;

import java.io.File;

public class Main {

    public static void main(String[] args) {

        TransactionData data = new TransactionData(new File("src/main/resources/tx_trytes.txt"));

        CompressionAlgo[] algos = new CompressionAlgo[] {
                //new RepeatTryteAlgo(),
                //new Repeat9Algo(),
                //new Trim9Algo(),
                new LZ4Algo(),
                new FastPFOR()
        };

        for(CompressionAlgo algo : algos)
            System.out.println(calculateMetrics(algo, data));
    }

    private static Result calculateMetrics(CompressionAlgo algo, TransactionData data) {
        long startTime = System.nanoTime();
        int compressedSize = 0, uncompressedSize = 0;
        for(String trytes : data.getTransactions()) {
            uncompressedSize += Trytes.toBytes(trytes).length;
            compressedSize += algo.compressedSize(trytes);
        }
        return new Result(algo.getName(),  (double) uncompressedSize / compressedSize, (System.nanoTime() - startTime) / 10);
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
            return name + " time: "+time + "ns , compression ratio: " +compressionRatio ;
        }

    }
}
