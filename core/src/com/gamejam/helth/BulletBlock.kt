package com.gamejam.helth

class BulletBlock(x : Float, y : Float, size : Float,
                  val goal_x : Float, val goal_y : Float, val player: Player) : DeathBlock(x, y, size) {

    var usedBullet = false
    val bulletSpeed = 3f //in px/frame

    internal fun update(){ //this is internal, called by player object to update
        //make bullets travel in a line toward player
        //make bullets only move left
        this.damage(player)
        if (this.collision(player)){
            usedBullet = true
        }

        if (this.x > goal_x){ //if bullet is not there yet
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
            player.enemyBullets.remove(this)
        }
    }


}