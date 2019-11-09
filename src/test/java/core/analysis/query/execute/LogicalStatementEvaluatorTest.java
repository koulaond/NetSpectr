package core.analysis.query.execute;

import core.WebPage;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

class LogicalStatementEvaluatorTest {

    @Test
    void evaluate() throws URISyntaxException, IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File webPageFile = new File(classLoader.getResource("webPage.html").getFile());
        String webPageHtml = IOUtils.toString(new FileReader(webPageFile));
        System.out.println(webPageFile);

        WebPage webPage = WebPage.builder()
                .html(webPageHtml)
                .build();

    }
}