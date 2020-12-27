package com.sr.myApp.ui.base

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sr.myApp.R
import kotlinx.android.synthetic.main.toolbar.*

abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {

    val binding: T by lazy {
        DataBindingUtil.setContentView<T>(this, layoutId())
    }

    abstract fun layoutId(): Int

    abstract fun viewModel(): BaseViewModel?

    abstract fun initActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        initActivity()
    }

    fun getBinding(): Any = binding

    fun getViewModel(): Any? = viewModel()

    protected fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            val drawable: Drawable? = getDrawable(R.drawable.ic_back)
            it.setHomeAsUpIndicator(drawable)
            it.setDisplayShowTitleEnabled(false)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        toolbar.setNavigationOnClickListener { v ->
            onBackPressed()
        }

    }

    fun startActivity(cls: Class<*>, requestCode: Int = 0) {
        val intent = Intent(this, cls)
        if (requestCode == 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)

        overridePendingTransitionEnter()
    }

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun gone(view: View) {
        view.visibility = View.GONE
    }

    fun visible(view: View) {
        view.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    private fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    private fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }
}