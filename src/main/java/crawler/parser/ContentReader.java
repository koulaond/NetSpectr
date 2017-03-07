package crawler.parser;

import static java.util.Objects.requireNonNull;

public class ContentReader {
    private static final char EOF = (char) -1;
    private int position;
    private char[] content;
    private int length;

    ContentReader(String input) {
        requireNonNull(input);
        this.content = input.toCharArray();
        this.length = content.length;
    }

    public int nextIndexOf(char ch) {
        for (int i = position; i < length; i++) {
            if (content[i] == ch) {
                return i;
            }
        }
        return -1;
    }

    public char readNext() {
        return position >= length ? EOF : content[position++];
    }

    public char actual(){
        return position >= length ? EOF : content[position];
    }

    public char[] readTo(char ch) {
        if (position > length) {
            return new char[]{EOF};
        }
        int nextIndexOf = nextIndexOf(ch);
        if (nextIndexOf != -1) {
            return readInterval(position, nextIndexOf);
        } else {
            return readInterval(position, length);
        }
    }

    public char[] readTo(String sequence) {
        requireNonNull(sequence);
        char[] sequenceArr = sequence.toCharArray();
        OUTER:
        for (int i = position; i < length; i++) {
            if (content[i] == sequenceArr[0]) {
                int curr = 1;
                INNER:
                while (curr < sequenceArr.length) {
                    if (content[i + curr] == sequenceArr[curr]) {
                        ++curr;
                        continue INNER;
                    } else {
                        continue OUTER;
                    }
                }
                return readInterval(position, i);
            }
        }
        return readInterval(position, length);
    }

    public char[] readInterval(int start, int end) {
        if (start < 0) {
            start = 0;
        }
        if (end > length) {
            end = length;
        }
        char[] sequence = new char[end - start];
        int i = 0;
        while (i + start < end) {
            sequence[i] = content[i + start];
            ++i;
        }
        return sequence;
    }

    public char[] readWhileNotEquals(char... chars) {
        int shift = position;
        OUTER:
        for (int i = position; i < length; i++) {
            char curr = content[i];
            INNER:
            for (char ch : chars) {
                if (curr == ch) {
                    break OUTER;
                }
            }
            shift = i;
        }
        if (shift == position) {
            return new char[0];
        } else {
            char[] data = readInterval(position, shift + 1);
            position = shift + 1;
            return data;
        }
    }

    public char[] readData() {
        return readWhileNotEquals(HtmlCharacters.LEFT_BRACKET, HtmlCharacters.REFERENCE, HtmlCharacters.NULL_CHAR);
    }

    public char[] readTagName() {
        return readWhileNotEquals(HtmlCharacters.SPACE);
    }

    public char[] readAttributeName(){
        return readWhileNotEquals(HtmlCharacters.ASSIGN);
    }

    public char[] readAttributeValue(){
        return readWhileNotEquals(HtmlCharacters.QUOTE);
    }
}
