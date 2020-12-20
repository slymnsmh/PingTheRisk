package ServerClasses;
import javafx.scene.input.MouseEvent;

public interface TurnPart {
    int countDown();
    void setPartNameLabelText(String text);

    void chooseBase(MouseEvent mouseEvent);

    boolean ifTimeIsOut();
    void endPart();
    int chooseHackerNum();
}