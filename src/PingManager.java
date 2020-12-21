public class PingManager extends Hack {
    int pingLevel;
    int attackerRange = 0;
    PingManager(){
        super();
    }
    int getDistance() {
        return distance;

    }
    void setPing(){
        pingLevel = getDistance();
        if(distance > 5)
            pingLevel = 5;
    }

    void setAttackerRange(int ping){
        attackerRange = ping;
    }

}
