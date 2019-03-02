package com.travelyaari.tycorelib.orm;

/**
 * Created by Triode on 5/10/2016.
 */
public class TableModificationException extends Exception {

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified detail message.
     *
     * @param detailMessage the detail message for this exception.
     */
    public TableModificationException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace, the
     * specified detail message and the specified cause.
     *
     * @param detailMessage the detail message for this exception.
     * @param throwable
     */
    public TableModificationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    /**
     * Constructs a new {@code Exception} with the current stack trace and the
     * specified cause.
     *
     * @param throwable the cause of this exception.
     */
    public TableModificationException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Constructs a new {@code Exception} that includes the current stack trace.
     */
    public TableModificationException() {
        super();
    }
}
