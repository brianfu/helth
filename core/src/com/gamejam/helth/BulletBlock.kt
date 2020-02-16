package com.gamejam.helth

import com.badlogic.gdx.graphics.Texture

class BulletBlock(x : Float, y : Float, size : Float,
                  val goal_x : Float, val goal_y : Float, val player: Player) : DeathBlock(x, y, size) {

    val bulletImage = Texture("bullet.png")

    var usedBullet = false
    val bulletSpeed = 5f //in px/frame

    internal fun update(){ //this is internal, called by player object to update
        //make bullets travel in a line toward player
        //make bullets only move left
        this.damage(player)
        if (this.collision(player)){
            usedBullet = true
        }

        if (this.x + 20f > 0){ //if bullet is not there yet
            this.x -= bulletSpeed //bullets move toward player's original position
        }else{
            usedBullet = true
        }

        if (this.y > goal_y){
            this.y -= bulletSpeed
        }else{
            this.y += bulletSpeed //move bullet more towards player
        }


        if (usedBullet){
            player.usedBullets.add(this)
        }
    }


}