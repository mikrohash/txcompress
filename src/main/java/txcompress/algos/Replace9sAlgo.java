package txcompress.algos;

import txcompress.utils.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace9sAlgo extends CompressionAlgo {

    @Override
    public String getName() {
        return "Replace9s";
    }

    @Override
    protected CompressionResult compress() {

        String trytes = getTryteString();

        String regex = "(9+)";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(trytes);
        StringBuffer sb = new StringBuffer();
        while (m.find())
            m.appendReplacement(sb, "" +(m.group(1).length()));
        m.appendTail(sb);

        return new CompressionResult(sb.toString());
    }

    @Override
    protected DecompressionResult decompress(CompressionResult compressionResult) {

        String trytes = compressionResult.getTrytes();

        String regex = "(\\d+)";
        Pattern r = Pattern.compile(regex);
        Matcher m = r.matcher(trytes);
        StringBuffer sb = new StringBuffer();
        while (m.find())
            m.appendReplacement(sb, StringUtils.repeat("9", Integer.parseInt(m.group(1))));
        m.appendTail(sb);

        return new DecompressionResult(sb.toString());
    }

}
