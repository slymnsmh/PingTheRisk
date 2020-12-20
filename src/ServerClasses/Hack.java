package ServerClasses;

import javafx.scene.input.MouseEvent;

public class Hack extends TurnManager implements TurnPart {

    int distance;
    boolean timesOut = false;
    boolean attackIsSuccessful = false;
    Player one;
    Player  two;
    public Hack(){
        this.distance = distance;
        boolean timesOut = false;
        boolean attackIsSuccessful = false;

    }


    @Override
    public int countDown() {
        return 0;
    }

    @Override
    public void setPartNameLabelText(String text) {

    }

    @Override
    public void chooseBase(MouseEvent mouseEvent) {
        System.out.println("mouse click detected! " );
        baseCountry = (Country) mouseEvent.getSource();
    }

    @Override
    public boolean ifTimeIsOut() {
        return false;
    }

    @Override
    public void endPart() {

    }

    @Override
    public int chooseHackerNum() {
        return 0;
    }

    void chooseTarget(MouseEvent mouseEvent){
        System.out.println("mouse click detected! " );
        targetCountry = (Country) mouseEvent.getSource();
    }

    void calculateDistance(){
        int temp1 = Integer.parseInt(baseCountry.location.substring(0, 1)) - Integer.parseInt(targetCountry.location.substring(0, 1));
        int temp2 = Integer.parseInt(baseCountry.location.substring(2, 3)) - Integer.parseInt(targetCountry.location.substring(2, 3));
        distance = (int) Math.sqrt(Math.pow(temp1, 2) + Math.pow(temp2, 2));
    }
    void startHack(){
        Attack atak = new Attack(one ,two, baseCountry, targetCountry);
    }

    void giveCard(){
        //if(attackIsSuccessful)
        //turnManager.turnOwner

    }

}
