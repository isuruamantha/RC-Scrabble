package rapticon.tk.scrabble.exception;

import java.io.IOException;


public class ServiceUnreachableException extends IOException {

    public ServiceUnreachableException(String detailMessage) {
        super(detailMessage);
    }
}
