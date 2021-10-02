package com.ather.assignment

import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ather.assignment.util.OnSwipeTouchListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val DEBUG_TAG = "DEBUG_TAG_MAIN"
    private val viewModel: MainVm by viewModels()
    lateinit var mGridLayout: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUI()
        observeLiveData()
    }

    private fun setUI() {
        mGridLayout = findViewById(R.id.gl_board) as GridLayout
        handleSwipes()
        initUIForFirstTime()
        viewModel.makeMatrix(mGridLayout)
    }

    private fun initUIForFirstTime() {
        val randomNumber1 = viewModel.getRandomWithinGridSize(0, 8)
        val randomNumber2 = viewModel.getRandomWithinGridSize(9,16)
        when(randomNumber1){
            0 -> tv_tile1.text = viewModel.getRandomTwoOrFour()
            1 -> tv_tile2.text = viewModel.getRandomTwoOrFour()
            2 -> tv_tile3.text = viewModel.getRandomTwoOrFour()
            3 -> tv_tile4.text = viewModel.getRandomTwoOrFour()
            4 -> tv_tile5.text = viewModel.getRandomTwoOrFour()
            5 -> tv_tile6.text = viewModel.getRandomTwoOrFour()
            6 -> tv_tile7.text = viewModel.getRandomTwoOrFour()
            7 -> tv_tile8.text = viewModel.getRandomTwoOrFour()
            8 -> tv_tile9.text = viewModel.getRandomTwoOrFour()
            9 -> tv_tile10.text = viewModel.getRandomTwoOrFour()
            10 -> tv_tile11.text = viewModel.getRandomTwoOrFour()
            11 -> tv_tile12.text = viewModel.getRandomTwoOrFour()
            12 -> tv_tile13.text = viewModel.getRandomTwoOrFour()
            13 -> tv_tile14.text = viewModel.getRandomTwoOrFour()
            14 -> tv_tile15.text = viewModel.getRandomTwoOrFour()
            15 -> tv_tile16.text = viewModel.getRandomTwoOrFour()
        }
        when(randomNumber2){
            0 -> tv_tile1.text = viewModel.getRandomTwoOrFour()
            1 -> tv_tile2.text = viewModel.getRandomTwoOrFour()
            2 -> tv_tile3.text = viewModel.getRandomTwoOrFour()
            3 -> tv_tile4.text = viewModel.getRandomTwoOrFour()
            4 -> tv_tile5.text = viewModel.getRandomTwoOrFour()
            5 -> tv_tile6.text = viewModel.getRandomTwoOrFour()
            6 -> tv_tile7.text = viewModel.getRandomTwoOrFour()
            7 -> tv_tile8.text = viewModel.getRandomTwoOrFour()
            8 -> tv_tile9.text = viewModel.getRandomTwoOrFour()
            9 -> tv_tile10.text = viewModel.getRandomTwoOrFour()
            10 -> tv_tile11.text = viewModel.getRandomTwoOrFour()
            11 -> tv_tile12.text = viewModel.getRandomTwoOrFour()
            12 -> tv_tile13.text = viewModel.getRandomTwoOrFour()
            13 -> tv_tile14.text = viewModel.getRandomTwoOrFour()
            14 -> tv_tile15.text = viewModel.getRandomTwoOrFour()
            15 -> tv_tile16.text = viewModel.getRandomTwoOrFour()
        }
    }


    private fun observeLiveData(){
        viewModel.tileLiveData.observe(this, {
            it.let {
                it.tileView.text = it.numberInString
            }
        })
    }


    private fun handleSwipes() {

        cl_root.setOnTouchListener(object: OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {

                /*val x1: Float
                val y1: Float
                val x2: Float
                val y2: Float
                x1 = viewModel.getXCoordinate(tv_tile1)
                y1 = viewModel.getYCoordinate(tv_tile1)
                x2 = viewModel.getXCoordinate(tv_tile2)
                y2 = viewModel.getYCoordinate(tv_tile2)

                val x_displacement = x2 - x1
                val y_displacement = y2 - y1

                tv_tile1.animate().xBy(x_displacement).yBy(y_displacement)
                tv_tile2.animate().xBy(-x_displacement).yBy(-y_displacement)*/

                // viewModel.populateRandomTwoOrFour(mGridLayout)
                viewModel.traverseAndSlideTilesLeft(mGridLayout)
                super.onSwipeLeft()
            }

            override fun onSwipeRight() {
                viewModel.traverseAndSlideTilesRight(mGridLayout)
                super.onSwipeRight()
            }

            override fun onSwipeTop() {
                viewModel.traverseAndSlideTilesTop(mGridLayout)
                super.onSwipeTop()
            }

            override fun onSwipeBottom() {
                viewModel.traverseAndSlideTilesBottom(mGridLayout)
                super.onSwipeBottom()
            }
        })

    }
}