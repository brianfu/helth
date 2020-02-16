package com.gamejam.helth

import com.badlogic.gdx.math.Rectangle

class GroundBlock(x : Float, y : Float, width : Float, height : Float) : Rectangle(x, y, width, height) {

    //Use this to find if floor is "solid" or not
    fun collision(block : Block, collisionRadius : Float = 2f) : Boolean{ //collusionRadius in pixels
        if (this.overlaps(block)){ //lazy eval
            return true
        }

        //Make a slightly bigger rectangle and check if it overlaps
        val testRec = GroundBlock(this.x, this.y, this.width, this.height) //copy construct
        testRec.setSize(testRec.width + collisionRadius, testRec.height + collisionRadius)

        if (testRec.overlaps(block)){
            return true
        }

        return false
    }

}