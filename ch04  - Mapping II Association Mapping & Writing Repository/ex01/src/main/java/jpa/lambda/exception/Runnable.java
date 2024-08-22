package jpa.lambda.exception;

public class Runnable {
    public static java.lang.Runnable THROWS(ThrowsProxy<Exception> throwsProxy) {
        return () -> {
            try {
                throwsProxy.run();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @FunctionalInterface
    public static interface ThrowsProxy<E extends Exception> {
        void run() throws E;
    }
}
