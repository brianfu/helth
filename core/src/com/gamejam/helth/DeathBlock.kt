package com.gamejam.helth

open class DeathBlock(x : Float, y : Float, size : Float) : Block(x, y, size){
    //Use this as an enemyblock (fat cells)

    //Update each enemy's set of bullets

    fun shoot(player : Player){ //Use the player to find where the bullet should be going
        //Call this every couple of renders
        //Only shoot in player's direction initially, no homing missles

        spawnBullet(this.x, this.y, 2f , player) //bullets are 1/20th as big as their spawners
    }

    fun damage(player : Player){ //called by every enemy against the player at every render
        if (this.collision(player)){
            player.health--
        }
        if (player.isDead()){
            player.death()
        }
    }

    fun spawnBullet(start_x : Float, start_y : Float, size: Float, player: Player){
        val bullet = BulletBlock(start_x, start_y, size, player.x, player.y, player)
        player.enemyBullets.add(bullet)
    }

}