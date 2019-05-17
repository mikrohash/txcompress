package txcompress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class TransactionData {

    private Set<String> txTrytes = new HashSet<>();

    public TransactionData(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            int i = 0;
            String line;

            while((line = reader.readLine()) != null && i < 50000) {

                if(line.length() != 2673)
                    line = line.substring(82);
                txTrytes.add(line);

                i++;

            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Set<String> getTransactions() {
        return txTrytes;
    }

}
