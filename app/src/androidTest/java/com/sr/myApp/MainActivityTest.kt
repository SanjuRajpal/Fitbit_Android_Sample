package com.sr.myApp

import android.widget.TextView
import com.sr.myApp.ui.home.MainActivity
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var mainAct: MainActivity

    @Before
    fun setup() {
        mainAct = Robolectric.buildActivity(MainActivity::class.java)
            .create()
            .resume()
            .get()
    }

    @Test
    fun doHelloWorldButtonClickTest() {
//        val textView = mainAct.findViewById(R.id.tv) as TextView
//        assertThat(textView.text.toString(), equalTo("Hello World!"))
    }

}