package org.lizard;

import java.util.Random;

public class Combat {
    Random rand;
    //    MyJFrame frame;
    int enemyHP;
    int playerHP;
    Player player;
    Room battleRoom;
    Enemy enemy;
    Board board;
    boolean bossTime = false;
    final int ROCK = 1;
    final int PAPER = 2;
    final int SCISS0R = 3;

    // Checks the game ending conditions
    // Depends on player HP.  Gives back
    // the game ending message to be displayed
    public String checkGameEndingStatus() {
        if (playerHP == 0) {
            return "Enemy won";
        } else if (enemyHP == 0) {
            return "You defeated the monster!";
        } else {
            return "Nobody won";
        }
    }


    public String playerTakesTurn(String playerInputDecision) {
        Random rand = new Random();
        int randEnemy = rand.nextInt(3) + 1;
        int playerNumberAnswer = -1;
        // validate player entered correct input
        if(!isValidCombatInput(playerInputDecision)){
            return ("You entered an invalid answer. " +
                    "\nPlease choose 1, 2, or 3" +
                    " for ROCK, PAPER, or SCISSORS");
        }

        playerNumberAnswer = Integer.parseInt(playerInputDecision);

        if (playerNumberAnswer == ROCK) {

            if (randEnemy == ROCK) {
                return (player.getName() + " got ROCK.\n\n" + enemy.getEnemyName() + " got ROCK.\n\nIt's a tie!" + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            } else if (randEnemy == PAPER) {
                playerHP -= 10;
                player.playerHP -= 10;
                return (player.getName() + " got ROCK.\n\n" + enemy.getEnemyName() + " got PAPER." + "\n\n" + enemy.getEnemyName() + " won! \n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);

            } else if (randEnemy == SCISS0R) {
                enemyHP -= 10;
                if (enemyHP == 0) {
                    battleRoom.setEnemy(null);
                    bossTime = board.enemyDied();
                }
                return (player.getName() + " got ROCK.\n\n" + enemy.getEnemyName() + " got SCISSOR." + "\n\n" + player.getName() + " won!" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            }

        } else if (playerNumberAnswer == PAPER) {

            if (randEnemy == ROCK) {
                enemyHP -= 10;
                if (enemyHP == 0) {
                    battleRoom.setEnemy(null);
                    bossTime = board.enemyDied();

                }
                return (player.getName() + " got PAPER.\n\n" + enemy.getEnemyName() + " got ROCK.\n\n" + player.getName() + " won!" + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            } else if (randEnemy == PAPER) {
                return (player.getName() + " got PAPER.\n\n" + enemy.getEnemyName() + " got PAPER.\n\nIt's a tie!" + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            } else if (randEnemy == SCISS0R) {
                playerHP -= 10;
                player.playerHP -= 10;

                return (player.getName() + " got PAPER.\n\n" + enemy.getEnemyName() + " got SCISSOR.\n\n" + enemy.getEnemyName() + " won! " + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            }

        } else if (playerNumberAnswer == SCISS0R) {

            if (randEnemy == ROCK) {
                playerHP -= 10;
                player.playerHP -= 10;

                return (player.getName() + " got SCISSOR.\n\n" + enemy.getEnemyName() + " got ROCK.\n\n" + enemy.getEnemyName() + " won!" + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            } else if (randEnemy == PAPER) {
                enemyHP -= 10;
                if (enemyHP == 0) {
                    battleRoom.setEnemy(null);
                    bossTime = board.enemyDied();

                }
                return (player.getName() + " got SCISSOR.\n\n" + enemy.getEnemyName() + " got PAPER.\n\n" + player.getName() + " won!" + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            } else if (randEnemy == SCISS0R) {
                return (player.getName() + " got SCISSOR.\n\n" + enemy.getEnemyName() + " got SCISSOR.\n\nIt's a tie!" + "\n\n" + player.getName() + "HP: " + playerHP + "\n" + enemy.getEnemyName() + "HP: " + enemyHP);
            }
        }

        return null;
    }

    public boolean isValidCombatInput(String input) {
        try {
            int numInputFromString = Integer.parseInt(input);
            // Check for valid input which is the integer range 1-3 inclusive
            if (numInputFromString < 1 || numInputFromString > 3){
                return false;
            }
            return true;
        } catch (NumberFormatException exception) {
            // This exception will be caught if the string cannot
            // be converted to an integer
            return false;
        }
    }

    public void startCombat(Player player, Room battleRoom, Board board) {
        this.player = player;
        this.enemy = battleRoom.getEnemy();
        this.battleRoom = battleRoom;
        this.board = board;
        playerHP = player.getPlayerHP();
        enemyHP = enemy.getEnemyHP();
    }
}