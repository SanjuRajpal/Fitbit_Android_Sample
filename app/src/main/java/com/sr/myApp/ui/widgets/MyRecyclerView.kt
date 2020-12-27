package com.sr.myApp.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sr.myApp.R

class MyRecyclerView : FrameLayout {
    private var recyclerView: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    private var tvNoData: TextView? = null
    var isLoading = false
        private set
    private var isShowNoData = false

    constructor(context: Context) : super(context) {
        init(null, -1)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?
    ) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs, defStyleAttr)
    }

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs, defStyleAttr)
    }

    private fun init(attrs: AttributeSet?, defStyleAttr: Int) {
        if (attrs != null && defStyleAttr != -1) {
            val a = context.obtainStyledAttributes(
                attrs,
                R.styleable.MyRecyclerView,
                defStyleAttr,
                0
            )
            isLoading = a.getBoolean(R.styleable.MyRecyclerView_showProgress, false)
            isShowNoData = a.getBoolean(R.styleable.MyRecyclerView_showNoData, false)
            a.recycle()
        }
        val view =
            View.inflate(context, R.layout.recyclerview, null)
        recyclerView = view.findViewById(R.id.recycler_view)
        progressBar = view.findViewById(R.id.progress_bar)
        tvNoData = view.findViewById(R.id.tv_no_data)
        addView(view)
        if (isLoading) {
            progressBar?.visibility = View.VISIBLE
        }
    }

    fun dismissLoading() {
        isLoading = false
        progressBar!!.visibility = View.GONE
    }

    fun showLoading() {
        isLoading = true
        progressBar!!.visibility = View.VISIBLE
    }

    fun setRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView!!.adapter = adapter
        if (isShowNoData) {
            if (adapter.itemCount == 0) tvNoData!!.visibility =
                View.VISIBLE else tvNoData!!.visibility = View.GONE
        }
        dismissLoading()
    }
}