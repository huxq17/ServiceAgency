package com.buyi.huxq17.serviceagency;

public class AgencyException extends RuntimeException {
    public AgencyException(String message) {
        super(message);
    }

    public AgencyException(Throwable cause) {
        super(cause);
    }
}
