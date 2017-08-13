package crawler;

import crawler.api.*;
import crawler.api.SubscriberContainer.SubscriberContainerBuilder;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import reactor.fn.Consumer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static crawler.api.SubscriberContainer.builder;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SubscriberContainerTest {

    private static final TestConsumer[] CONSUMERS_FOR_NEW_LINKS_EVENT = {
            new TestConsumer(NewLinksAvailableEvent.class),
            new TestConsumer(NewLinksAvailableEvent.class),
            new TestConsumer(NewLinksAvailableEvent.class),
            new TestConsumer(NewLinksAvailableEvent.class)
    };

    private static final TestConsumer[] CONSUMERS_FOR_CONTENT_TO_PROCESS_EVENT = {
            new TestConsumer(ContentToProcessEvent.class),
            new TestConsumer(ContentToProcessEvent.class),
            new TestConsumer(ContentToProcessEvent.class)
    };

    private static final TestConsumer[] CONSUMERS_FOR_STATE_EVENT = {
            new TestConsumer(CrawlerStateChangedEvent.class),
            new TestConsumer(CrawlerStateChangedEvent.class)
    };

    private static final Map<Class<? extends CrawlerEvent>, TestConsumer[]> CONSUMERS = new HashMap<>();

    @BeforeClass
    public static void setup() {
        CONSUMERS.put(NewLinksAvailableEvent.class, CONSUMERS_FOR_NEW_LINKS_EVENT);
        CONSUMERS.put(ContentToProcessEvent.class, CONSUMERS_FOR_CONTENT_TO_PROCESS_EVENT);
        CONSUMERS.put(CrawlerStateChangedEvent.class, CONSUMERS_FOR_STATE_EVENT);
    }

    @Test
    public void getEvents() throws Exception {
        SubscriberContainer container = containerBuilder().build();
        Assert.assertEquals(CONSUMERS.size(), container.getEvents().size());
    }

    @Test
    public void testBuildNewContainer_correctCase() throws Exception {
        SubscriberContainer container = containerBuilder().build();
        CONSUMERS.keySet().forEach(eventClass -> container.getSubscribersFor(eventClass).forEach(consumer -> Assert.assertEquals(eventClass, ((TestConsumer) consumer).getEventClass())));
    }

    @Test(expected = NullPointerException.class)
    public void testBuildNewContainer_addNullValues() throws Exception {
        SubscriberContainerBuilder builder = containerBuilder();
        builder.add(null, null);
    }

    @Test
    public void testEquals_identical() {
        SubscriberContainerBuilder leftBuilder = builder();
        SubscriberContainerBuilder rightBuilder = builder();

        CONSUMERS.forEach((eventClass, testConsumers) ->
                Stream.of(testConsumers).forEach(testConsumer -> {
                    leftBuilder.add(eventClass, testConsumer);
                    rightBuilder.add(eventClass, testConsumer);
                }));

        SubscriberContainer leftContainer = leftBuilder.build();
        SubscriberContainer rightContainer = rightBuilder.build();
        assertTrue(leftContainer.equals(rightContainer));
    }

    @Test
    public void testEquals_differentEvents() {
        SubscriberContainerBuilder leftBuilder = builder();
        SubscriberContainerBuilder rightBuilder = builder();

        CONSUMERS.forEach((eventClass, testConsumers) ->
                Stream.of(testConsumers).forEach(testConsumer -> {
                    leftBuilder.add(eventClass, testConsumer);
                    if (!eventClass.equals(CrawlerStateChangedEvent.class)) {
                        rightBuilder.add(eventClass, testConsumer);
                    }
                }));

        SubscriberContainer leftContainer = leftBuilder.build();
        SubscriberContainer rightContainer = rightBuilder.build();
        assertFalse(leftContainer.equals(rightContainer));
    }

    @Test
    public void testEquals_sameEvents_differentConsumers() {
        SubscriberContainerBuilder leftBuilder = builder();
        SubscriberContainerBuilder rightBuilder = builder();

        CONSUMERS.forEach((eventClass, testConsumers) ->
                Stream.of(testConsumers).forEach(testConsumer -> {
                    leftBuilder.add(eventClass, testConsumer);
                    if (!testConsumer.equals(CONSUMERS_FOR_STATE_EVENT[0])) {
                        rightBuilder.add(eventClass, testConsumer);
                    }
                }));

        SubscriberContainer leftContainer = leftBuilder.build();
        SubscriberContainer rightContainer = rightBuilder.build();
        assertFalse(leftContainer.equals(rightContainer));
    }

    private SubscriberContainerBuilder containerBuilder() {
        SubscriberContainerBuilder builder = builder();
        CONSUMERS.forEach((eventClass, testConsumers) -> Stream.of(testConsumers).forEach(testConsumer -> builder.add(eventClass, testConsumer)));
        return builder;
    }


    private static class TestConsumer implements Consumer<CrawlerEvent> {

        private Class<? extends CrawlerEvent> eventClass;

        public TestConsumer(Class<? extends CrawlerEvent> eventClass) {
            this.eventClass = eventClass;
        }

        @Override
        public void accept(CrawlerEvent event) {
        }

        public Class<? extends CrawlerEvent> getEventClass() {
            return eventClass;
        }
    }
}