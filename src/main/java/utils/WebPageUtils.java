package utils;

import java.net.MalformedURLException;
import java.net.URL;

public final class WebPageUtils {

    private WebPageUtils() {
        throw new IllegalStateException("No instance of " + this.getClass().getName());
    }

    public static URL cleanUrl(URL toClean) {
        String ref = toClean.getRef();
        String fullPath = toClean.toExternalForm();
        if (ref != null) {
            ref = "#" + ref;
            fullPath = fullPath.replace(ref, "");
        }
        if (fullPath.endsWith("/")) {
            fullPath = fullPath.substring(0, fullPath.length() - 1);
        }
        try {
            return new URL(fullPath);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
