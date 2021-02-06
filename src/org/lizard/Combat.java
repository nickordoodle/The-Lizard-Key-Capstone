package org.lizard;

import java.util.Random;

public class Combat {
    Random rand;
//    MyJFrame frame;
    int enemyHP;
    int playerHP;
    Player player;
    Enemy enemy;

    final int ROCK = 1;
    final int PAPER = 2;
    final int SCISS0R = 3;

    public String checkGameEndingStatus(){
        if(playerHP==0){
            return "Enemy won";
        }
        else if(enemyHP==0){
            return "Player won";
        }
        else{
            return "Nobody won";
        }
    }


    public String playerTakesTurn(int randPlayer) {
        Random rand = new Random();
        int randEnemy = rand.nextInt(3) + 1;

        if (randPlayer == ROCK) {

            if (randEnemy == ROCK) {
                return (player.getName() + " got ROCK.\n\n"+enemy.getEnemyName() + " got ROCK.\n\nIt's a tie!"+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            } else if (randEnemy == PAPER) {
                playerHP -= 10;
                return (player.getName() + " got ROCK.\n\n"+enemy.getEnemyName() + " got PAPER."+"\n\n" + enemy.getEnemyName()+" won! \n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);

            } else if (randEnemy == SCISS0R) {
                enemyHP -= 10;
                return (player.getName() + " got ROCK.\n\n"+enemy.getEnemyName() + " got SCISSOR."+"\n\n"+player.getName()+ " won!"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            }

        } else if (randPlayer == PAPER) {

            if (randEnemy == ROCK) {
                enemyHP -= 10;
                return (player.getName() + " got PAPER.\n\n" +enemy.getEnemyName() + " got ROCK.\n\n" + player.getName() + " won!"+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            } else if (randEnemy == PAPER) {
                return (player.getName() + " got PAPER.\n\n"+enemy.getEnemyName() + " got PAPER.\n\nIt's a tie!"+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            } else if (randEnemy == SCISS0R) {
                playerHP -= 10;
                return (player.getName() + " got PAPER.\n\n"+enemy.getEnemyName() + " got SCISSOR.\n\n" + enemy.getEnemyName() + " won! "+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            }

        } else if (randPlayer == SCISS0R) {

            if (randEnemy == ROCK) {
                playerHP -= 10;
                return (player.getName() + " got SCISSOR.\n\n"+enemy.getEnemyName() + " got ROCK.\n\n" + enemy.getEnemyName() + " won!"+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            } else if (randEnemy == PAPER) {
                enemyHP -= 10;
                return (player.getName() + " got SCISSOR.\n\n"+enemy.getEnemyName() + " got PAPER.\n\n" + player.getName() + " won!"+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            } else if (randEnemy == SCISS0R) {
                return (player.getName() + " got SCISSOR.\n\n"+enemy.getEnemyName() + " got SCISSOR.\n\nIt's a tie!"+"\n\n"+player.getName() + "HP: " + playerHP + "\n"+enemy.getEnemyName() + "HP: " +enemyHP);
            }
        }
        return null;
    }

    public void startCombat(Player player, Enemy enemy){
        this.player = player;
        this.enemy = enemy;

        playerHP = player.getPlayerHP();
        enemyHP = enemy.getEnemyHP();
    }
}