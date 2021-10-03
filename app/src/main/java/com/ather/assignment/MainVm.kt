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

    fun clearData(parent: GridLayout){
        matrix =  Array(4) {Array(4) {""} }
        for(i in 0 until parent.childCount){
            val tileTV = parent.getChildAt(i) as TextView
            tileTV.text = ""
        }
    }

    /**
     * Read all the Tiles in the GridLayout and put theme into a 2D Matrix sequentially.
     *
     * This will be used to keep track of and traverse the tiles row-wise or column-wise
     * when we need to move the tiles.
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
        return (fromNumber..tillNumber-1).random()
    }

    /**
     * Populates 2 or 4 on two random tiles when the game starts
     * parent: GridLayout -> The Grid Layout used to make the Tiles View
     */
    fun initGridOnGameStart(parent: GridLayout) {
        val randomNumber = (0..matrix.size-1).random()

        loop1@ for (i in randomNumber until 4){
            for(j in randomNumber until 4){
                if(matrix[i][j].trim().equals("")){
                    val randomTwoOrFour = if ((0..1).random() == 0) 2 else 4
                    matrix[i][j] = randomTwoOrFour.toString()
                    val linearIndex = (i*4) + j
                    val tileTV = parent.getChildAt(linearIndex) as TextView
                    tileTV.text = matrix[i][j]
                    break@loop1
                }
            }
        }
        loop2@ for (i in 0 until randomNumber){
            for(j in 0 until randomNumber){
                if(matrix[i][j].trim().equals("")){
                    val randomTwoOrFour = if ((0..1).random() == 0) 2 else 4
                    matrix[i][j] = randomTwoOrFour.toString()
                    val linearIndex = (i*4) + j
                    val tileTV = parent.getChildAt(linearIndex) as TextView
                    tileTV.text = matrix[i][j]
                    break@loop2
                }
            }
        }

    }

    /**
     * Populates a 2 or 4 on a random tile
     * parent: GridLayout -> The Grid Layout used to make the Tiles View
     */
    fun populateRandomTwoOrFour(parent: GridLayout) {
        if(!isMatrixFull(matrix)){
            val randomNumber = (0..matrix.size-1).random()

            var randomNumberProduced = false

            for (i in randomNumber until 4){
                for(j in randomNumber until 4){
                    if(matrix[i][j].trim().equals("")){
                        randomNumberProduced = true
                        val randomTwoOrFour = if ((0..1).random() == 0) 2 else 4
                        matrix[i][j] = randomTwoOrFour.toString()
                        val linearIndex = (i*4) + j
                        val tileTV = parent.getChildAt(linearIndex) as TextView
                        tileTV.text = matrix[i][j]
                        return
                    }
                }
            }
            for (i in 0 until randomNumber){
                for(j in 0 until randomNumber){
                    if(matrix[i][j].trim().equals("")){
                        randomNumberProduced = true
                        val randomTwoOrFour = if ((0..1).random() == 0) 2 else 4
                        matrix[i][j] = randomTwoOrFour.toString()
                        val linearIndex = (i*4) + j
                        val tileTV = parent.getChildAt(linearIndex) as TextView
                        tileTV.text = matrix[i][j]
                        return
                    }
                }
            }

            if(!randomNumberProduced){
                populateRandomTwoOrFour(parent)
            }
        }
    }

    fun isMatrixFull(matrix: Array<Array<String>>) : Boolean{
        for(row in matrix){
            for(item in row){
                if(item.trim().equals("")){
                    return false
                }
            }
        }
        return true
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

    /**
     * For left and top swipe
     */
    fun moveEmptyItemsToEnd(array: Array<String>): Array<String> {
        var newArray =  Array(array.size) {" "}
        var nonZeroNoCount = -1

        for(i in 0 until array.size){
            if(!array[i].trim().equals("")){
                nonZeroNoCount++
                newArray[nonZeroNoCount] = array[i]
            }
        }
        for(i in 0 until newArray.size-1){
            if(!newArray[i].trim().equals("") && newArray[i].equals(newArray[i+1])){
                newArray[i] = (newArray[i].toInt()*2).toString()
                for (j in i+1 until newArray.size-1){
                    newArray[j] = newArray[j+1]
                }
                newArray[newArray.size-1] = " "
            }
        }
        return newArray
    }

    /**
     * For right and bottom swipe
     */
    fun moveEmptyItemsToBeginning(array: Array<String>): Array<String> {
        var newArray =  Array(array.size) {" "}
        var nonZeroNoCount = -1

        for(i in array.size-1 downTo 0){
            if(!array[i].trim().equals("")){
                nonZeroNoCount++
                newArray[newArray.size-nonZeroNoCount-1] = array[i]
            }
        }
        for(i in newArray.size-1 downTo 1){
            if(!newArray[i].trim().equals("") && newArray[i].equals(newArray[i-1])){
                newArray[i] = (newArray[i].toInt()*2).toString()
                for(j in i-1 downTo 1){
                    newArray[j] = newArray[j-1]
                }
                newArray[0] = " "
            }
        }
        return newArray
    }

}