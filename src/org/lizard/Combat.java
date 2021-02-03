package org.lizard;

import java.util.Random;

public class Combat {
    Random rand;

    final int ROCK = 1;
    final int PAPER = 2;
    final int SCISS0R = 3;

    public void startCombat(Player player, Enemy enemy){
        int playerHP = player.getPlayerHP();
        int enemyHP = enemy.getEnemyHP();

        while(true){

            Random rand = new Random();
            int randPlayer = rand.nextInt(3)+1;
            int randEnemy = rand.nextInt(3)+1;


            if(randPlayer == ROCK){
                System.out.println(player.getName() + " got ROCK");
                if(randEnemy == ROCK){
                    System.out.println(enemy.getEnemyName() + " got ROCK");
                    System.out.println("It is a tie!");

                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);
                }
                else if(randEnemy == PAPER){
                    System.out.println(enemy.getEnemyName() + " got PAPER");
                    playerHP -= 10;

                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);
                }
                else if(randEnemy == SCISS0R){
                    System.out.println(enemy.getEnemyName() + " got SCISSOR");
                    enemyHP -= 10;
                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);

                }
            }
            else if(randPlayer == PAPER){
                System.out.println(player.getName() + " got PAPER");
                if(randEnemy == ROCK){
                    System.out.println(enemy.getEnemyName() + " got ROCK");
                    System.out.println(player.getName() + "won!");
                    enemyHP -= 10;

                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);
                }
                else if(randEnemy == PAPER){
                    System.out.println(enemy.getEnemyName() + " got PAPER");
                    System.out.println("It's a tie!");

                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);
                }
                else if(randEnemy == SCISS0R){
                    System.out.println(enemy.getEnemyName() + " got SCISSOR");
                    System.out.println(player.getName() + "lost! ");
                    playerHP -= 10;
                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);

                }
            }
            else if(randPlayer == SCISS0R){
                System.out.println(player.getName() + " got SCISSOR");
                if(randEnemy == ROCK){
                    System.out.println(enemy.getEnemyName() + " got ROCK");
                    System.out.println(player.getName() + "lost!");
                    playerHP -= 10;
                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);
                }
                else if(randEnemy == PAPER){
                    System.out.println(enemy.getEnemyName() + " got PAPER");
                    System.out.println(player.getName() + "won!");
                    enemyHP -= 10;
                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);
                }
                else if(randEnemy == SCISS0R){
                    System.out.println(enemy.getEnemyName() + " got SCISSOR");
                    System.out.println("It's a tie!");

                    System.out.println(player.getName() + "HP: " + playerHP);
                    System.out.println(enemy.getEnemyName() + "HP: " +enemyHP);

                }
            }

            if(playerHP == 0){
                System.out.println(player.getName() + "lost");
                break;
            }
            if(enemyHP == 0){
                System.out.println(enemy.getEnemyName() + "lost");
                break;
            }


        }


    }



}
