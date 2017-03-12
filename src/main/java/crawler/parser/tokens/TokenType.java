package crawler.parser.tokens;

public enum TokenType {
    START_TAG_CLOSED,
    START_TAG_OPENED,
    SPECIAL_TAG, /** Opened tag without closing tag, i.e. meta tag**/
    END_TAG,
    ATTIBUTE,
    TEXT,
    SPACE_TEXT,
    BRACKET_OPEN,
    BRACKET_CLOSE,
    CDATA,
    DOCTYPE,
    COMMENT
}
