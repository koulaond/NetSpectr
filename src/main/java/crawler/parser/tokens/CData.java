package crawler.parser.tokens;

import static crawler.parser.HtmlMarks.CDATA_END;
import static crawler.parser.HtmlMarks.CDATA_START;

public class CData extends Text {

    private CData(TokenType tokenType, String data) {
        super(tokenType, data);
    }

    @Override
    public String html() {
        return CDATA_START + super.html() + CDATA_END;
    }

    public static CDataBuilder builder(){
        return new CDataBuilder();
    }

    private static class CDataBuilder extends TextBuilder{

        public CData build() {
            return new CData(TokenType.CDATA, data);
        }
    }
}
