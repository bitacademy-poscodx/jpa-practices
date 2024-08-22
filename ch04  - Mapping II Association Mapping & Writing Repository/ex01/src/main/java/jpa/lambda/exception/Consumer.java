package jpa.lambda.exception;



public class Consumer {
    public static <T> java.util.function.Consumer<T> THROWS(ThrowsProxy<T, Exception> throwsProxy) {
        return i -> {
            try {
                throwsProxy.accept(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @FunctionalInterface
    public interface ThrowsProxy<T, E extends Exception> {
        void accept(T t) throws E;
    }
}


