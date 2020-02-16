package com.gamejam.helth

import com.badlogic.gdx.math.Rectangle

open class Block(x : Float, y : Float, val size : Float) : Rectangle(x, y, size, size) { //size of block in pixels, block is perfect square

    //Use this to find if floor is "solid" or not
    fun collision(block : Block, collisionRadius : Float = 2f) : Boolean{ //collusionRadius in pixels
        if (this.overlaps(block)){ //lazy eval
            return true
        }

        //Make a slightly bigger rectangle and check if it overlaps
        val testRec = Block(this.x, this.y, this.size) //copy construct
        testRec.setSize(testRec.width + collisionRadius, testRec.height + collisionRadius)

        if (testRec.overlaps(block)){
            return true
        }

        return false
    }


}