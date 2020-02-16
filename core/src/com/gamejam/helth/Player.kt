package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Actor

class Player(x : Float, y : Float, size : Float, var health : Int, var bullets : Int) : Block(x, y, size) {

    //getX() => Left Edge,  gety() => Bottom Edge

    init{ //Secondary Constructor
        this.setPosition(x,y) //xy-coord at left bottom point
    }


    //Jump
    enum class JumpState{
        STANDING, ASCENDING, DESCENDING
    }

    enum class OnPlatformState{
        ON_PLATFORM, OFF_PLATFORM, BETWEEN_PLATFORMS
    }

    var onPlatformState = OnPlatformState.OFF_PLATFORM

    val jumpSpeed = 20f //jump by 2 px each frame
    var jumpHeight = 800f //# of px to jump by, change in main

    var jumpState = JumpState.STANDING
    var jumpOrigin = 0f //y-coord (this.top) of where the jump started (lateinit)
    var jumpApex = 0f //jumpHeight + jumpOrigin (lateinit)
    var jumpApexReached = false

    val enemyBullets = ArrayList<BulletBlock>()

    fun enemyBulletsUpdate(){ //call this every render
        for (ele in enemyBullets){
            ele.update()
        }
    }


    fun isDead() : Boolean{ //Call every render and do smth if so
        if (health == 0){
            return true
        }
        return false
    }

    fun death(){
        //dead brocc
    }

    fun canShoot() : Boolean{ //Call every render and do smth if so
        if (bullets != 0){
            return true
        }
        return false
    }

    fun reload(amount : Int){
        bullets = amount
    }

    fun jump(){ //Call this in CODE (not render) to make it jump
        if (jumpState == JumpState.STANDING) {

            //onPlatformState == OnPlatformState.OFF_PLATFORM
            jumpState = JumpState.ASCENDING
            jumpOrigin = this.y  //Bottom of hitbox
            jumpApex = jumpHeight + jumpOrigin
            jumpApexReached = false //reset it

            if (onPlatformState != OnPlatformState.BETWEEN_PLATFORMS){
                onPlatformState = OnPlatformState.BETWEEN_PLATFORMS
            }

        }
    }

    fun jumpProcess(){ //Call this constantly in render
        if (jumpState == JumpState.STANDING){ //If standing
            if (onPlatformState == OnPlatformState.BETWEEN_PLATFORMS) { //if standing in midair
                this.gravity()
            }
            else { //If standing on something
                return //Do nothing, no jump processing needed
            }
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

            if (onPlatformState == OnPlatformState.BETWEEN_PLATFORMS){
                this.gravity() //TODO: replace all instance of 0f with floor value
            }

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
        if (this.collision(block, 2f)){ //if collided with a floor
            jumpState = JumpState.STANDING //stop falling

            if (onPlatformState == OnPlatformState.BETWEEN_PLATFORMS){
                onPlatformState = OnPlatformState.ON_PLATFORM
            }
            else if (onPlatformState == OnPlatformState.OFF_PLATFORM){
                onPlatformState = OnPlatformState.ON_PLATFORM
            }
        }
    }

    fun gravity(){
        jumpOrigin = 0f
        jumpState = JumpState.DESCENDING
    }

    //Background is moving, running by default

}