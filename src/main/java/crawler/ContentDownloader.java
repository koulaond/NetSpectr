package crawler;

public interface ContentDownloader<S, T> {
    T downloadContent(S source);
}
