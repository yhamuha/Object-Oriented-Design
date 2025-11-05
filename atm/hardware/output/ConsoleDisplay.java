package atm.hardware.output;

public class ConsoleDisplay implements Display {

    private String message;

    @Override
    public void showMessage(String message) {
        this.message = message;
    }

    public String getDisplayedMessage() {
        return message;
    }
}
