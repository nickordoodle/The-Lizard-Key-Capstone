package org.lizard;

public class Enemy {

    String enemyName;
    int enemyHP = 50;

    Enemy(String enemyName){
        this.enemyName = enemyName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getEnemyHP() {
        return enemyHP;
    }

    public void setEnemyHP(int enemyHP) {
        this.enemyHP = enemyHP;
    }

}
