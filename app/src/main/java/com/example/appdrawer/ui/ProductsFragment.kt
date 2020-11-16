package com.example.appdrawer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.appdrawer.R
import com.example.appdrawer.adapter.ProductsAdapter
import com.example.appdrawer.databinding.FragmentProductsBinding
import com.example.appdrawer.model.Products
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : Fragment() {
    private lateinit var binding : FragmentProductsBinding

    companion object {
        fun newInstance() = ProductsFragment()
    }

    private lateinit var viewModel: ProductsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)
        viewModel.getStatus().observe(this, Observer { t ->
            if (t ?: false) {
                listProducts.visibility = View.GONE
                tvStatus.visibility = View.VISIBLE
            } else {
                listProducts.visibility = View.VISIBLE
                tvStatus.visibility = View.GONE
            }
        })
        viewModel.setData().observe(this, Observer {
            showData(it)
        })
    }

    private fun showData(data: List<Products>) {
        listProducts.adapter = ProductsAdapter(data)
    }
}