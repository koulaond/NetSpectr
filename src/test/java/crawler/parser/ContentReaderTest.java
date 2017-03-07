package crawler.parser;

import org.junit.Assert;
import org.junit.Test;

public class ContentReaderTest {
    @Test
    public void nextIndexOf() throws Exception {
        String input = "abcdefghijabcABC";
        ContentReader reader = new ContentReader(input);
        Assert.assertEquals(0, reader.nextIndexOf('a'));
        Assert.assertEquals(1, reader.nextIndexOf('b'));
        Assert.assertEquals(2, reader.nextIndexOf('c'));
        Assert.assertEquals(3, reader.nextIndexOf('d'));
        Assert.assertEquals(4, reader.nextIndexOf('e'));
        Assert.assertEquals(5, reader.nextIndexOf('f'));
        Assert.assertEquals(6, reader.nextIndexOf('g'));
        Assert.assertEquals(7, reader.nextIndexOf('h'));
        Assert.assertEquals(8, reader.nextIndexOf('i'));
        Assert.assertEquals(9, reader.nextIndexOf('j'));
        Assert.assertEquals(13, reader.nextIndexOf('A'));
        Assert.assertEquals(14, reader.nextIndexOf('B'));
        Assert.assertEquals(15, reader.nextIndexOf('C'));
        Assert.assertNotEquals(8, reader.nextIndexOf('h'));
    }

    @Test
    public void readNext() throws Exception {
        String input = "abcdefghijabcABC";
        ContentReader reader = new ContentReader(input);
        Assert.assertEquals('a', reader.readNext());
        Assert.assertEquals('b', reader.readNext());
        Assert.assertEquals('c', reader.readNext());
        Assert.assertEquals('d', reader.readNext());
        Assert.assertEquals('e', reader.readNext());
        Assert.assertEquals('f', reader.readNext());
        Assert.assertEquals('g', reader.readNext());
        Assert.assertEquals('h', reader.readNext());
        Assert.assertEquals('i', reader.readNext());
        Assert.assertEquals('j', reader.readNext());
        Assert.assertEquals('a', reader.readNext());
        Assert.assertEquals('b', reader.readNext());
        Assert.assertEquals('c', reader.readNext());
        Assert.assertEquals('A', reader.readNext());
        Assert.assertEquals('B', reader.readNext());
        Assert.assertEquals('C', reader.readNext());
        Assert.assertEquals((char) -1, reader.readNext());
    }

    @Test
    public void readToChar() throws Exception {
        String input = "mlRg\"hello45Av[]{}\\";
        ContentReader reader = new ContentReader(input);
        // Read to non-existing character - it should read to end of content
        Assert.assertArrayEquals("mlRg\"hello45Av[]{}\\".toCharArray(), reader.readTo('b'));
        // Read to specific char
        Assert.assertArrayEquals("mlRg\"hello45Av".toCharArray(), reader.readTo('['));
        // Advance reading
        reader.readNext();
        // Read to specific char
        Assert.assertArrayEquals("lRg\"hello45Av".toCharArray(), reader.readTo('['));
    }

    @Test
    public void readToString() throws Exception {
        String input = "mlRg\"hello45Av[]{}\\";
        ContentReader reader = new ContentReader(input);
        // Read to specific string
        Assert.assertArrayEquals("mlRg\"".toCharArray(), reader.readTo("hello"));
        Assert.assertArrayEquals("mlRg\"".toCharArray(), reader.readTo("h"));
        // Advance reading
        reader.readNext();
        reader.readNext();
        reader.readNext();
        // Read to specific string
        Assert.assertArrayEquals("g\"".toCharArray(), reader.readTo("hello"));
        Assert.assertArrayEquals("g\"hell".toCharArray(), reader.readTo("o45"));
        Assert.assertArrayEquals("g\"".toCharArray(), reader.readTo("h"));
        Assert.assertArrayEquals("g\"hello45Av[]{}\\".toCharArray(), reader.readTo("b"));
    }

    @Test
    public void readInterval() throws Exception {
        String input = "mlRg\"hello45Av[]{}\\";
        ContentReader reader = new ContentReader(input);
        Assert.assertArrayEquals("mlRg\"hello45Av[]{}\\".toCharArray(), reader.readInterval(-1, 20));
        Assert.assertArrayEquals("mlRg\"hello45Av[]{}\\".toCharArray(), reader.readInterval(-1, 19));
        Assert.assertArrayEquals("mlRg\"hello45Av[]{}\\".toCharArray(), reader.readInterval(0, 20));
        Assert.assertArrayEquals("mlRg\"hello45Av[]{}\\".toCharArray(), reader.readInterval(0, 19));
        Assert.assertArrayEquals("mlRg\"hello45Av[]{}".toCharArray(), reader.readInterval(-1, 18));
        Assert.assertArrayEquals("\"hello45Av[]".toCharArray(), reader.readInterval(4, 16));
    }

    @Test
    public void readData() throws Exception {
        String input = "Text in div tag mlRg\"hello45Av[]{}\\</div>";
        ContentReader reader = new ContentReader(input);
        Assert.assertArrayEquals("Text in div tag mlRg\"hello45Av[]{}\\".toCharArray(), reader.readData());
        input = "Text in div tag mlRg\"hello45Av[]";
        reader = new ContentReader(input);
        Assert.assertArrayEquals("Text in div tag mlRg\"hello45Av[]".toCharArray(), reader.readData());
        input = "</div>";
        reader = new ContentReader(input);
        Assert.assertArrayEquals(new char[0], reader.readData());
        input = "Text in div tag mlRg\"hello45Av[]{}\\</div>gh;[rAF45[00f</div>";
        reader = new ContentReader(input);
        Assert.assertArrayEquals("Text in div tag mlRg\"hello45Av[]{}\\".toCharArray(), reader.readData());
        // Advance reading
        for (int i = 0; i < 6; i++) {
            reader.readNext();
        }
        Assert.assertArrayEquals("gh;[rAF45[00f".toCharArray(), reader.readData());
    }

    @Test
    public void readTagName() throws Exception {
        String input = "<div class=\"main\"> body </div>";
        ContentReader reader = new ContentReader(input);
        reader.readNext();
        Assert.assertArrayEquals("div".toCharArray(), reader.readTagName());
    }

    @Test
    public void readAttributeName() throws Exception {
        String input = "class=\"main\"> body </div>";
        ContentReader reader = new ContentReader(input);
        Assert.assertArrayEquals("class".toCharArray(), reader.readAttributeName());
    }

    @Test
    public void readAttributeValue() throws Exception {
        String input = "\"main\"> body </div>";
        ContentReader reader = new ContentReader(input);
        Assert.assertArrayEquals("\"main\"".toCharArray(), reader.readAttributeValue());
        input = "\"main\" id=\"35\"> body </div>";
        reader = new ContentReader(input);
        Assert.assertArrayEquals("\"main\"".toCharArray(), reader.readAttributeValue());
    }
}