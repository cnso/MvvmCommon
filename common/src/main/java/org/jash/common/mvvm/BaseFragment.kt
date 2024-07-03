package org.jash.common.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.jash.common.mvvm.BaseViewModel
import org.jash.common.utils.observerBus
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseFragment<B:ViewDataBinding, VM: BaseViewModel>:Fragment() {
    private val types by lazy { (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments }
    val binding:B by lazy {
        val clazz = types[0] as Class<B>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as B
    }
    val viewModel:VM by lazy {
        val clazz = types[1] as Class<VM>
        ViewModelProvider(this)[clazz]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.errorLiveData.observe(viewLifecycleOwner, ::error)
        observerBus(viewLifecycleOwner, this)
        initData()
        initView()
    }

    abstract fun initData()
    abstract fun initView()

    open fun error(throwable: Throwable) {
        Toast.makeText(requireContext(), throwable.message, Toast.LENGTH_SHORT).show()
        throwable.printStackTrace()
    }

}