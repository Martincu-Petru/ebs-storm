package utils;

public final class Filenames {
    public static final String PUBLICATIONS1 = getFileName("publications_1");
    public static final String PUBLICATIONS2 = getFileName("publications_2");
    public static final String PUBLICATIONS3 = getFileName("publications_3");

    public static final String SUBSCRIPTIONS1 = getFileName("subscriptions_1");
    public static final String SUBSCRIPTIONS2 = getFileName("subscriptions_2");
    public static final String SUBSCRIPTIONS3 = getFileName("subscriptions_3");

    private static String getFileName(String fileName) {
        return String.format("./src/main/java/utils/%s.json", fileName);
    }
}
