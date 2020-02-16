package com.gamejam.helth

import com.badlogic.gdx.Gdx

open class DeathBlock(x : Float, y : Float, size : Float) : Block(x, y, size){
    //Use this as an enemyblock (fat cells)


    //Update each enemy's set of bullets
    var hitsound = Gdx.audio.newSound(Gdx.files.internal("hitsound.ogg"))

    fun shoot(player : Player){ //Use the player to find where the bullet should be going
        //Call this every couple of renders
        //Only shoot in player's direction initially, no homing missles

        spawnBullet(this.x, this.y, 10f , player) //bullets are 1/20th as big as their spawners
    }

    fun damage(player : Player){ //called by every enemy against the player at every render
        if (this.collision(player)){
            player.health--
            hitsound.play()
        }
        if (player.isDead()){
            player.death()
        }

    }

    fun spawnBullet(start_x : Float, start_y : Float, size: Float, player: Player){
        val bullet = BulletBlock(start_x, start_y, size, player.x, player.y, player)
        player.enemyBullets.add(bullet)
    }

    fun moveTowardsPlayer(player: Player, speed : Float){ //speed in px/frame
        //used in MeleeEnemyBlock, can be used by others too!

        var player_before_enemy_x = false //if player comes before enemy in x-coords
        var player_before_enemy_y = false //if player before enemy in y-coords
        //edge case same => stay false

        if(player.x < this.x){
            player_before_enemy_x = true //Move in this direction (multiply scalar by this)
        }
        if(player.y < this.y){
            player_before_enemy_y = true
        }

        if(player_before_enemy_x){
            if(player.x < this.x){
                this.x -= speed
            }
        }else{
            if(player.x > this.x){
                this.x += speed
            }
        }

        if(player_before_enemy_y){
            if(player.y < this.y){
                this.y -= speed
            }
        }else{
            if (player.y > this.y){
                this.y += speed
            }
        }
    }
}