package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture

class MeleeEnemyBlock(x : Float, y : Float, size : Float,
                      val player : Player,
                      var speed : Float)
    : DeathBlock(x, y, size){
    //speed the enemy moves at
    //spawn a single one of these, starting at the bottom left corner of screen

    //assume this enemy moves left and right

    val MeleeEnemyImage = Texture("bucket.png")

    var movingLeft = true; //false => movingRight
    fun moveAround(){ //Call on every render
        //"Float" slightly towards the player
        if (this.collision(player)){
            player.health = 0;
            player.death()
            //player.usedMeleeEnemies.add(this) //This enemy is invulnerable
        }

        if(movingLeft){
            if (this.x > 0) {
                this.x -= speed
            }else{
               movingLeft = false
            }
        }else{
            if (this.x < 2220){
                this.x += speed
            }else {
                movingLeft = true
            }
        }


        //this.moveTowardsPlayer(player, speed)
    }




}