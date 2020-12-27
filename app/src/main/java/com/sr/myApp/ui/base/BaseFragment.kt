package com.sr.myApp.ui.base

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Extended BaseFragment into all the all fragments
 */
abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private lateinit var binding: ViewDataBinding

    abstract fun layoutId(): Int

    abstract fun viewModel(): BaseViewModel?

    abstract fun initFragment()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<T>(inflater, layoutId(), container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFragment()
    }

    fun getViewModel(): Any? = viewModel()

    fun getBinding(): Any = binding

    fun startActivity(cls: Class<*>, requestCode: Int = 0) {
        val intent = Intent(requireContext(), cls)
        if (requestCode == 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)
    }

    fun startActivity(cls: Class<*>, bundle: Bundle, requestCode: Int = 0) {
        val intent = Intent(requireContext(), cls)
        intent.putExtras(bundle)
        if (requestCode == 0)
            startActivity(intent)
        else
            startActivityForResult(intent, requestCode)
    }

    fun showMessage(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}