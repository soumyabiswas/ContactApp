package com.travelyaari.tycorelib.orm;

/**
 * Created by Triode on 5/10/2016.
 */
public final class Exceptions {


    /**
     * Exception will be raised against the deletion of content
     */
    public static class DeleteException extends Exception{
        /**
         * Constructs a new {@code Exception} that includes the current stack trace.
         */
        public DeleteException() {
            super();
        }

        /**
         * Constructs a new {@code Exception} with the current stack trace and the
         * specified detail message.
         *
         * @param detailMessage the detail message for this exception.
         */
        public DeleteException(String detailMessage) {
            super(detailMessage);
        }

        /**
         * Constructs a new {@code Exception} with the current stack trace, the
         * specified detail message and the specified cause.
         *
         * @param detailMessage the detail message for this exception.
         * @param throwable
         */
        public DeleteException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        /**
         * Constructs a new {@code Exception} with the current stack trace and the
         * specified cause.
         *
         * @param throwable the cause of this exception.
         */
        public DeleteException(Throwable throwable) {
            super(throwable);
        }
    }


    /**
     * Exception raised against an error happened while qurying the
     * database
     o */
    public static class QueryException extends Exception{
        /**
         * Constructs a new {@code Exception} with the current stack trace and the
         * specified detail message.
         *
         * @param detailMessage the detail message for this exception.
         */
        public QueryException(String detailMessage) {
            super(detailMessage);
        }

        /**
         * Constructs a new {@code Exception} that includes the current stack trace.
         */
        public QueryException() {
            super();
        }

        /**
         * Constructs a new {@code Exception} with the current stack trace, the
         * specified detail message and the specified cause.
         *
         * @param detailMessage the detail message for this exception.
         * @param throwable
         */
        public QueryException(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        /**
         * Constructs a new {@code Exception} with the current stack trace and the
         * specified cause.
         *
         * @param throwable the cause of this exception.
         */
        public QueryException(Throwable throwable) {
            super(throwable);
        }
    }
}
