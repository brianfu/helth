package com.gamejam.helth

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor

class Player(x : Float, y : Float, size : Float, var health : Int, var bullets : Int) : Block(x, y, size) {

    //getX() => Left Edge,  gety() => Bottom Edge
    var hitsound = Gdx.audio.newSound(Gdx.files.internal("jump_sound.wav"))
    init{ //Secondary Constructor
        this.setPosition(x,y) //xy-coord at left bottom point

    }

    lateinit internal var characterImage : Texture //set in gameScreen

    internal var floorHeight = 150f;

    //Jump
    enum class JumpState{
        STANDING, ASCENDING, DESCENDING
    }

    enum class OnPlatformState{
        ON_PLATFORM, OFF_PLATFORM, BETWEEN_PLATFORMS
    }

    enum class InputState{ //Is the input being continuously given (by .isTouched, etc)
        HELD, NONE
    }

    var inputState = InputState.NONE //change to held while held
    var onPlatformState = OnPlatformState.OFF_PLATFORM

    val jumpSpeed = 20f //jump by 2 px each frame
    var jumpHeight = 800f //# of px to jump by, change in main

    var jumpState = JumpState.STANDING
    var jumpOrigin = floorHeight //y-coord (this.top) of where the jump started (lateinit)
    var jumpApex = 0f //jumpHeight + jumpOrigin (lateinit)
    var jumpApexReached = false
    var leggo = "ez"

    val enemyBullets = ArrayList<BulletBlock>()
    val usedBullets = ArrayList<BulletBlock>()

    //val enemyMeleeEnemies = ArrayList<MeleeEnemyBlock>()
    //val usedMeleeEnemies = ArrayList<MeleeEnemyBlock>() //similar to bullet, except random spawn point

    //empty the used enemy buckets upon every update


    fun enemyBulletsUpdate(){ //call this every render
        for (ele in enemyBullets){
            ele.update()
        }

        //To fix concurrent modification exception
        for (ele in usedBullets){
            enemyBullets.remove(ele)
        }
        usedBullets.clear()
    }

    fun isDamaged() : Boolean{
        if (health <= 5){
            return true
        }
        return false
    }

    fun damage(){
        if (isDamaged()){
            characterImage = Texture("damaged_brocc.png")
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
        if (isDead()){
         characterImage = Texture("dead_brocc.png")
        }
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


            hitsound.play();
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
            if(inputState == InputState.HELD) { //If currently ascending and .isTouched()
                //Do as normal
                if (this.y < jumpApex) {
                    this.y += jumpSpeed
                } else { //delays descention by a frame, should be fine
                    jumpState = JumpState.DESCENDING
                }
            }else{ //If currently ascending and not .IsTouched()
                jumpState == JumpState.DESCENDING
                this.gravity()
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

    fun jumpCollider(block : GroundBlock){ //Call in GameScreen after jumpProcess
        //Loop through all blocks in GameScreen, check for collisions, if any, stop descending the jump
        if (block.collision(this, 2f)){ //if collided with a floor
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
        jumpOrigin = floorHeight
        jumpState = JumpState.DESCENDING

        if (this.y == floorHeight){
            onPlatformState = OnPlatformState.OFF_PLATFORM
            jumpState = JumpState.STANDING
        }
    }

    //Background is moving, running by default

}