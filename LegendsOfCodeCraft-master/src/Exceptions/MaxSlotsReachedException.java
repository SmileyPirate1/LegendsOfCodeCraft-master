package Exceptions;

public class MaxSlotsReachedException extends RuntimeException {
    public MaxSlotsReachedException(String message) {
        super(message);
    }
}
