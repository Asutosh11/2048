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
        startGame()
    }

    fun setUI(){
        mGridLayout = findViewById(R.id.gl_board) as GridLayout
        restart_btn.setOnClickListener{
            startGame()
        }
    }

    fun startGame(){
        setData()
        observeLiveData()
    }

    private fun setData() {
        viewModel.clearData(mGridLayout)
        viewModel.makeMatrix(mGridLayout)
        viewModel.initGridOnGameStart(mGridLayout)
        handleSwipes()
    }

    private fun observeLiveData(){
        viewModel.tileLiveData.observe(this, {
            it.let {
                it.tileView.text = it.numberInString
            }
        })
        viewModel.scoreLiveData.observe(this, {
            it.let {
                score_tv.text = getString(R.string.score_label_to_show) + " " + it.toString()
            }
        })
    }


    private fun handleSwipes() {

        cl_root.setOnTouchListener(object: OnSwipeTouchListener(this@MainActivity) {
            override fun onSwipeLeft() {
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