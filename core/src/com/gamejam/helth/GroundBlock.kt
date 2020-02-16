package com.gamejam.helth

import com.badlogic.gdx.math.Rectangle

class GroundBlock(x : Float, y : Float, width : Float, height : Float) : Block(x, y, width) {

    init{
        this.height = height
    }


    //Use this to find if floor is "solid" or not
    override fun collision(block : Block, collisionRadius : Float) : Boolean{ //collusionRadius in pixels


        //Make a slightly bigger rectangle and check if it overlaps
        val testRec = GroundBlock(this.x, this.y + 210, this.width, this.height) //copy construct
//        testRec.setSize(testRec.width + collisionRadius, testRec.height + collisionRadius)
        testRec.setSize(testRec.width + collisionRadius, testRec.height + collisionRadius)


        if (testRec.overlaps(block)){
            return true
        }

        return false
    }


}