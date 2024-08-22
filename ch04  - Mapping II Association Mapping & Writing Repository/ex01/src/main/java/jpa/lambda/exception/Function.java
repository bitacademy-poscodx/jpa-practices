package jpa.lambda.exception;

public class Function {
    public static <T, R> java.util.function.Function<T, R> THROWS(ThrowsProxy<T, R, Exception> throwsProxy) {
        return i -> {
            try {
                return throwsProxy.apply(i);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    @FunctionalInterface
    public interface ThrowsProxy<T, R, E extends Exception> {
        R apply(T t) throws E;
    }
}
