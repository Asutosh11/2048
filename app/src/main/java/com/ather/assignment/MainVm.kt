package com.ather.assignment

import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.ather.assignment.base.BaseViewModel
import com.ather.assignment.data.DoubleTile
import com.ather.assignment.data.Tile

class MainVm : BaseViewModel() {

    private val DEBUG_TAG = "DEBUG_TAG_MAIN_VM"

    private var matrix =  Array(4) {Array(4) {""} }

    var tileLiveData: MutableLiveData<Tile> = MutableLiveData()
    var gameStartTilesLiveData: MutableLiveData<DoubleTile> = MutableLiveData()

    /**
     * Get X coordinate of a View
     *
     * view: View -> Any generic View
     */
    fun getXCoordinate(view: View): Float {
        return view.getX()
    }

    /**
     * Get Y coordinate of a View
     *
     * view: View -> Any generic View
     */
    fun getYCoordinate(view: View): Float {
        return view.getY()
    }

    /**
     * Read all the Tiles in the GridLayout and put theme into a HashMap sequentially.
     * HashMap keys: 1 to 16, sequentially
     * HashMap values: tags of tiles, as defined in XML layout
     *
     * This will be used to keep track of and traverse the tiles row-wise or column-wise
     * when we need to move the tiles. Even if the position of tiles change after sliding/mering,
     * this HashMap will keep track of their positions
     *
     * parent: GridLayout -> The Grid Layout used to make the Tiles View
     */
    fun makeMatrix(parent: GridLayout) {

        for (i in 0 until 16) {
            val tileTV = parent.getChildAt(i) as TextView
            if(i < 4){
                matrix[0][i%4] = tileTV.text.toString()
            }
            else if(i < 8){
                matrix[1][i%4] = tileTV.text.toString()
            }
            else if(i < 12){
                matrix[2][i%4] = tileTV.text.toString()
            }
            else if(i < 16){
                matrix[3][i%4] = tileTV.text.toString()
            }

            val name = ""
        }
    }

    fun getRandomTwoOrFour() : String{
        return if ((0..1).random() == 0) "2" else "4"
    }

    fun getRandomWithinGridSize(fromNumber: Int, tillNumber: Int) : Int {
        return (fromNumber..tillNumber).random()
    }

    /**
     * Populates a 2 or 4 on a random tile
     * parent: GridLayout -> The Grid Layout used to make the Tiles View
     */
    fun populateRandomTwoOrFour(parent: GridLayout) {
        val sizeOfGrid: Int = parent.getChildCount()
        val randomNumber = (0..sizeOfGrid).random()

        for (i in randomNumber until sizeOfGrid) {
            val tileTV = parent.getChildAt(i) as TextView
            if (tileTV.text.toString().trim().equals("")) {
                val randomTwoOrFour = if ((0..1).random() == 0) 2 else 4
                matrix[randomNumber/4][randomNumber%4] = randomTwoOrFour.toString()
                tileLiveData.postValue(Tile(tileTV, randomTwoOrFour.toString()))
                return
            }
        }
        for (i in 0 until randomNumber) {
            val tileTV = parent.getChildAt(i) as TextView
            if (tileTV.text.toString().trim().equals("")) {
                val randomTwoOrFour = if ((0..1).random() == 0) 2 else 4
                matrix[randomNumber/4][randomNumber%4] = randomTwoOrFour.toString()
                tileLiveData.postValue(Tile(tileTV, randomTwoOrFour.toString()))
                return
            }
        }
    }

    fun traverseAndSlideTilesLeft(parent: GridLayout){
        val ListRow1 = moveEmptyItemsToEnd(arrayOf(
            matrix[0][0], matrix[0][1],matrix[0][2], matrix[0][3]))
        val ListRow2 = moveEmptyItemsToEnd(arrayOf(
            matrix[1][0], matrix[1][1],matrix[1][2], matrix[1][3]))
        val ListRow3 = moveEmptyItemsToEnd(arrayOf(
            matrix[2][0], matrix[2][1],matrix[2][2], matrix[2][3]))
        val ListRow4 = moveEmptyItemsToEnd(arrayOf(
            matrix[3][0], matrix[3][1],matrix[3][2], matrix[3][3]))

        for(i in 0 until 4){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[0][i] = ListRow1[i]
            tileTV.text = ListRow1[i]
        }
        for(i in 4 until 8){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[1][i-4] = ListRow2[i-4]
            tileTV.text = ListRow2[i-4]
        }
        for(i in 8 until 12){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[2][i-8] = ListRow3[i-8]
            tileTV.text = ListRow3[i-8]
        }
        for(i in 12 until 16){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[3][i-12] = ListRow4[i-12]
            tileTV.text = ListRow4[i-12]
        }
        populateRandomTwoOrFour(parent)
    }

    fun traverseAndSlideTilesRight(parent: GridLayout){
        val ListRow1 = moveEmptyItemsToBeginning(arrayOf(
            matrix[0][0], matrix[0][1],matrix[0][2], matrix[0][3]))
        val ListRow2 = moveEmptyItemsToBeginning(arrayOf(
            matrix[1][0], matrix[1][1],matrix[1][2], matrix[1][3]))
        val ListRow3 = moveEmptyItemsToBeginning(arrayOf(
            matrix[2][0], matrix[2][1],matrix[2][2], matrix[2][3]))
        val ListRow4 = moveEmptyItemsToBeginning(arrayOf(
            matrix[3][0], matrix[3][1],matrix[3][2], matrix[3][3]))

        for(i in 0 until 4){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[0][i] = ListRow1[i]
            tileTV.text = ListRow1[i]
        }
        for(i in 4 until 8){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[1][i-4] = ListRow2[i-4]
            tileTV.text = ListRow2[i-4]
        }
        for(i in 8 until 12){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[2][i-8] = ListRow3[i-8]
            tileTV.text = ListRow3[i-8]
        }
        for(i in 12 until 16){
            val tileTV = parent.getChildAt(i) as TextView
            matrix[3][i-12] = ListRow4[i-12]
            tileTV.text = ListRow4[i-12]
        }
        populateRandomTwoOrFour(parent)
    }

    fun traverseAndSlideTilesTop(parent: GridLayout){

        val ListColumn1 = moveEmptyItemsToEnd(arrayOf(
            matrix[0][0], matrix[1][0],matrix[2][0], matrix[3][0]))
        val ListColumn2 = moveEmptyItemsToEnd(arrayOf(
            matrix[0][1], matrix[1][1],matrix[2][1], matrix[3][1]))
        val ListColumn3 = moveEmptyItemsToEnd(arrayOf(
            matrix[0][2], matrix[1][2],matrix[2][2], matrix[3][2]))
        val ListColumn4 = moveEmptyItemsToEnd(arrayOf(
            matrix[0][3], matrix[1][3],matrix[2][3], matrix[3][3]))

        for(i in 0 until 4){
            val tileTV = parent.getChildAt(i*4) as TextView
            matrix[i][0] = ListColumn1[i]
            tileTV.text = ListColumn1[i]

            val index2 = (i*4)+1
            val tileTV2 = parent.getChildAt(index2) as TextView
            matrix[i][1] = ListColumn2[i]
            tileTV2.text = ListColumn2[i]

            val index3 = (i*4)+2
            val tileTV3 = parent.getChildAt(index3) as TextView
            matrix[i][2] = ListColumn3[i]
            tileTV3.text = ListColumn3[i]

            val index4 = (i*4)+3
            val tileTV4 = parent.getChildAt(index4) as TextView
            matrix[i][3] = ListColumn4[i]
            tileTV4.text = ListColumn4[i]
        }
        var name = ""
        populateRandomTwoOrFour(parent)
    }

    fun traverseAndSlideTilesBottom(parent: GridLayout){

        val ListColumn1 = moveEmptyItemsToBeginning(arrayOf(
            matrix[0][0], matrix[1][0],matrix[2][0], matrix[3][0]))
        val ListColumn2 = moveEmptyItemsToBeginning(arrayOf(
            matrix[0][1], matrix[1][1],matrix[2][1], matrix[3][1]))
        val ListColumn3 = moveEmptyItemsToBeginning(arrayOf(
            matrix[0][2], matrix[1][2],matrix[2][2], matrix[3][2]))
        val ListColumn4 = moveEmptyItemsToBeginning(arrayOf(
            matrix[0][3], matrix[1][3],matrix[2][3], matrix[3][3]))


        for(i in 0 until 4){
            val tileTV = parent.getChildAt(i*4) as TextView
            matrix[i][0] = ListColumn1[i]
            tileTV.text = ListColumn1[i]

            val index2 = (i*4)+1
            val tileTV2 = parent.getChildAt(index2) as TextView
            matrix[i][1] = ListColumn2[i]
            tileTV2.text = ListColumn2[i]

            val index3 = (i*4)+2
            val tileTV3 = parent.getChildAt(index3) as TextView
            matrix[i][2] = ListColumn3[i]
            tileTV3.text = ListColumn3[i]

            val index4 = (i*4)+3
            val tileTV4 = parent.getChildAt(index4) as TextView
            matrix[i][3] = ListColumn4[i]
            tileTV4.text = ListColumn4[i]
        }
        populateRandomTwoOrFour(parent)
    }

    fun moveEmptyItemsToEnd(array: Array<String>): Array<String> {
        var count = 0
        var temp: String

        for (i in 0 until array.size) {
            if (!array[i].trim().equals("")) {

                temp = array[count]
                array[count] = array[i]
                array[i] = temp
                count = count + 1
            }
        }
        return array
    }

    fun moveEmptyItemsToBeginning(array: Array<String>): Array<String> {
        var k = 0
        for (i in 0 until array.size) {
            if (array[i].trim().equals("")) {
                val temp: String = array[i]
                array[i] = array.get(k)
                array[k] = temp
                k++
            }
        }
        return array
    }

    /*fun traverseGrid(mlayout: GridLayout) {
        val count: Int = mlayout.getChildCount()
        for (i in 0 until count) {
            val child = mlayout. as TextView
            Log.d(DEBUG_TAG, child.text.toString())
        }
    }*/
}