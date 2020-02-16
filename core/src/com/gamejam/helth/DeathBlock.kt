package com.gamejam.helth

open class DeathBlock(x : Float, y : Float, size : Float) : Block(x, y, size){

    fun kill(player : Player){
        player.health = 0;
    }

}