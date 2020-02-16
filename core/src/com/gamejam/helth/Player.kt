package com.gamejam.helth

import com.badlogic.gdx.scenes.scene2d.Actor

class Player(x : Float, y : Float, size : Float, var health : Int, var bullets : Int) : Block(x, y, size) {

    //getX() => Left Edge,  gety() => Bottom Edge, getTop() => Top Edge

    init{ //Secondary Constructor
        this.setPosition(x,y) //xy-coord at left bottom point
    }


    //Jump
    enum class JumpState{
        STANDING, ASCENDING, DESCENDING
    }


    val jumpSpeed = 2f //jump by 2 px each frame
    var jumpHeight = 10f //# of px to jump by, change in main

    var jumpState = JumpState.STANDING
    var jumpOrigin = 0f //y-coord (this.top) of where the jump started (lateinit)
    var jumpApex = 0f //jumpHeight + jumpOrigin (lateinit)
    var jumpApexReached = false


    fun isDead() : Boolean{
        if (health == 0){
            return true
        }
        return false
    }

    fun canShoot() : Boolean{
        if (bullets != 0){
            return true
        }
        return false
    }

    fun reload(amount : Int){
        bullets = amount
    }

    fun jump(){ //Call this in CODE (not render) to make it jump
        jumpState = JumpState.ASCENDING
        jumpOrigin = this.y  //Bottom of hitbox
        jumpApex = jumpHeight + jumpOrigin
        jumpApexReached = false //reset it
    }

    fun jumpProcess(){ //Call this constantly in render
        if (jumpState == JumpState.STANDING){
            return
        }
        else if (jumpState == JumpState.ASCENDING){
            if (this.y < jumpApex){
                this.y += jumpSpeed
            }
            else{ //delays descention by a frame, should be fine
                jumpState = JumpState.DESCENDING
            }
        }
        else if (jumpState == JumpState.DESCENDING){
            if(this.y > jumpOrigin){
                this.y -= jumpSpeed
            }
            else{
                jumpState = JumpState.STANDING
            }
        }
    }

    fun jumpCollider(block : Block){ //Call in GameScreen after jumpProcess
        //Loop through all blocks in GameScreen, check for collisions, if any, stop descending the jump
        if (this.collision(block)){ //if collided with a floor
            jumpState = JumpState.STANDING //stop falling
        }
    }

//    fun jump(height : Float, speed: Float){ //height is how high to jump (and how much to lower after jump)
//        // speed is how fast to jump
//        //simply call this in main render loop and everything should work
//
//        val jumpHeight = height + this.top
//
//        var topReached = false //If jump has reached the apex
//        if (this.top < jumpHeight && !topReached){ //if not finished jumping up
//            this.moveBy(0f, 2f) //jump by 2 px at a time
//        }else{ //elif top >= jumpHeight
//            topReached = true
//        }
//
//        if(topReached){
//            this.moveBy(0f, -2f)
//        }
//
//    }

    //Background is moving, running by default

}