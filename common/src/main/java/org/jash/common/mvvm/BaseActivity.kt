package org.jash.common.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import org.jash.common.utils.observerBus
import java.lang.reflect.ParameterizedType

@Suppress("UNCHECKED_CAST")
abstract class BaseActivity<B:ViewDataBinding, VM: BaseViewModel>:AppCompatActivity() {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.errorLiveData.observe(this, ::error)
        observerBus(this, this)
        initData()
        initView()
    }
    abstract fun initData()
    abstract fun initView()

    open fun error(throwable: Throwable) {
        Toast.makeText(this, throwable.message, Toast.LENGTH_SHORT).show()
        throwable.printStackTrace()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when(item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

}