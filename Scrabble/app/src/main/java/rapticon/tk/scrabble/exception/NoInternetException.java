package rapticon.tk.scrabble.exception;

import java.io.IOException;

public class NoInternetException extends IOException {

    public NoInternetException(String detailMessage) {
        super(detailMessage);
    }
}
