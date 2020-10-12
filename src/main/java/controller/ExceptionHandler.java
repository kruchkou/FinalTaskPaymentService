package controller;

public class ExceptionHandler {
    
    private final static ExceptionHandler instance = new ExceptionHandler();
    
    private ExceptionHandler() {
    }

    public static ExceptionHandler getInstance() {
        return instance;
    }
    
    
    
    
    
}
