

public class Attack extends PingManager {
    int id;
    int ping;
    Player attacker;
    Player defender;
    Country attackerCountry;
    Country defenderCountry;
    Player winner;
    Player loser;
    int numOfAttackerHackers;
    int numOfDefenderHackers;
    PingManager pingManager;
    DiceManager roll;
    Attack(Player attacker, Player defender, Country attackersCountry, Country defendersCountry){
        this.attacker = attacker;
        this.defender = defender;
        this.attackerCountry = attackersCountry;
        this.defenderCountry = defendersCountry;
        pingManager = new PingManager();
        pingManager.setPing();
        numOfAttackerHackers = attackersCountry.hackerNumber;
        numOfDefenderHackers = defendersCountry.hackerNumber;

        if(numOfAttackerHackers >= 3 && numOfDefenderHackers>= 2){
            roll = new DiceManager(3,2, pingManager.pingLevel);
            roll.compareBiggest(numOfAttackerHackers,numOfDefenderHackers);
        }
        else if(numOfAttackerHackers >= 3 && numOfDefenderHackers == 1){
            roll = new DiceManager(3,1,pingManager.pingLevel);
            roll.compareBiggest(numOfAttackerHackers,numOfDefenderHackers);
        }

        else if(numOfAttackerHackers < 3 && numOfDefenderHackers>= 2){
            roll = new DiceManager(numOfAttackerHackers,2,pingManager.pingLevel);
            roll.compareBiggest(numOfAttackerHackers,numOfDefenderHackers);
        }
        else if(numOfAttackerHackers < 3 && numOfDefenderHackers == 1) {
            roll = new DiceManager(numOfAttackerHackers, 1, pingManager.pingLevel);
            roll.compareBiggest(numOfAttackerHackers, numOfDefenderHackers);
        }
        attackersCountry.hackerNumber = numOfAttackerHackers - roll.defenderWins;
        defendersCountry.hackerNumber = numOfDefenderHackers - roll.attackerWins;
    }
}
