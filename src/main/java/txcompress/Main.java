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
            System.out.println("compression rate of '" + algo.getName() + "' is " + calcCompressionRate(algo, data));
    }

    private static double calcCompressionRate(CompressionAlgo algo, TransactionData data) {
        int compressedSize = 0, uncompressedSize = 0;
        for(String trytes : data.getTransactions()) {
            uncompressedSize += trytes.length();
            compressedSize += algo.compressedSize(trytes);
        }
        return (double)uncompressedSize / compressedSize;
    }
}
