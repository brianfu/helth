package com.gamejam.helth

class MeleeEnemyBlock(x : Float, y : Float, size : Float,
                      val player : Player,
                      var speed : Float = 5f, val goal_x : Float = player.x, val goal_y : Float = player.y)
    : DeathBlock(x, y, size){
    //speed the enemy moves at

    //assume this enemy just floats around

    fun moveAround(){ //Call on every render
        //"Float" slightly towards the player
        if (this.collision(player)){
            this.damage(player)
            player.usedMeleeEnemies.add(this)
            return
        }



    }



}