package crawler.parser;

import reactor.core.support.StringUtils;

import static java.util.Objects.requireNonNull;

public class TextValueAdjuster {
    public String adjust(String toAdjust, String... valuesToTake){
        requireNonNull(toAdjust);
        String adjusted = toAdjust;
        for (String valueToTake : valuesToTake){
            adjusted = StringUtils.replace(adjusted, valueToTake, "");
        }
        return adjusted;
    }
}
