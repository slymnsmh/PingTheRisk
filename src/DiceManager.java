import java.util.ArrayList;

public class DiceManager {
    int numOfAttackerDice;
    int numOfDefenderDice;
    int winner_id;
    int loser_id;
    int attackerWins;
    int defenderWins;
    ArrayList<Integer> attackersScores;
    ArrayList<Integer> defendersScores;

    DiceManager(int numOfAttackerDice, int numOfDefenderDice, int attackerRange){
        this.attackerWins = 0;
        this.defenderWins = 0;
        this.winner_id = getWinner();
        this.loser_id = getLoser();
        this.numOfAttackerDice = numOfAttackerDice;
        this.numOfDefenderDice = numOfDefenderDice;
        attackersScores = new ArrayList<Integer>();
        defendersScores = new ArrayList<Integer>();
        rollDice(attackerRange);
    }

    void rollDice(int attackerRange) {

        int temp = 0;
        for (int i = 0; i < numOfAttackerDice; i++) {
            temp = (int) ((6.0 * Math.random() + 1));
            if (i == 0){
                if(temp - attackerRange <= 0){
                    attackersScores.add(1);
                }
                else
                    attackersScores.add(temp - attackerRange);
            }


            else if (i == 1) {
                if (temp >= attackersScores.get(i - 1)) {
                    attackersScores.add(attackersScores.get(i - 1));
                    attackersScores.set(i - 1, temp);
                } else if (temp < attackersScores.get(i - 1)) {
                    attackersScores.add(i, temp);
                }
            } else {
                if (i == 2 && temp >= attackersScores.get(i - 2)) {
                    attackersScores.add(attackersScores.get(i - 1));
                    attackersScores.set(i - 1, attackersScores.get(i - 2));
                    attackersScores.set(i - 2, temp);
                    break;
                } else if (i == 2 && temp >= attackersScores.get(i - 1)) {
                    attackersScores.add(attackersScores.get(i - 1));
                    attackersScores.set(i - 1, temp);
                } else
                    attackersScores.add(temp);
            }
        }
        if (numOfDefenderDice == 2) {
            temp = (int) ((6.0 * Math.random() + 1));
            defendersScores.add(temp);
            temp = (int) ((6.0 * Math.random() + 1));
            if (temp >= defendersScores.get(0)) {
                defendersScores.add(defendersScores.get(0));
                defendersScores.set(0,temp);
            } else
                defendersScores.add(temp);
        }
        else {
            temp = (int) ((6.0 * Math.random() + 1));
            defendersScores.add(temp);
        }
    }


    void compareBiggest(int attackerDiceNum, int defenderDiceNum){
        if(attackersScores.get(0) > defendersScores.get(0)) {
            attackerWins++;
        }
        else if (attackersScores.get(0) <= defendersScores.get(0) ) {
            defenderWins++;
        }
        if(defenderDiceNum > 1 && attackerDiceNum > 1) {
            if (attackersScores.get(1) > defendersScores.get(1)) {
                attackerWins++;
            }
            else if (attackersScores.get(1) <= defendersScores.get(1)) {
                defenderWins++;
            }
        }
    }

    int getWinner(){
        return winner_id;
    }

    int getLoser(){
        return loser_id;
    }
}
