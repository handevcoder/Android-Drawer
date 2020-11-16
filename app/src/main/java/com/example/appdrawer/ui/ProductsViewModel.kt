package com.example.appdrawer.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appdrawer.model.Products
import com.example.appdrawer.network.NetworkConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsViewModel : ViewModel() {
    private var data = MutableLiveData<List<Products>>()
    private var status = MutableLiveData<Boolean>()

    init {
        getData()
    }

    private fun getData() {
        status.value = true
        NetworkConfig().api().GetAllProducts().enqueue(object : Callback<List<Products>>{
            override fun onFailure(call: Call<List<Products>>, t: Throwable) {
                status.value = true
            }
            override fun onResponse(call: Call<List<Products>>, response: Response<List<Products>>) {
                status.value = false
                if (response.isSuccessful){
                    data.value = response.body()?.let { it }
                } else {
                    status.value = true
                }
            }
        })
    }

    fun setData() : MutableLiveData<List<Products>> {
        return  data
    }

    fun getStatus() : MutableLiveData<Boolean>{
        status.value = true
        return status
    }
}

