package crawler.impl.dflt;

public class CountDown {
    private Integer count;

    public CountDown(Integer count) {
        this.count = count;
    }

    public synchronized void countDown() {
        if (count > 0) {
            --count;
        }
    }

    public synchronized boolean finished(){
        return count == 0;
    }

    public synchronized Integer getCount(){
        return count;
    }
}
