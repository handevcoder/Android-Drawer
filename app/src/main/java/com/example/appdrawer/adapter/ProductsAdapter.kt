package com.example.appdrawer.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appdrawer.R
import com.example.appdrawer.model.Products
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_products.view.*

class ProductsAdapter(data: List<Products>) :
    RecyclerView.Adapter<ProductsAdapter.ProductsHolder>() {
    private var datas = listOf<Products>()

    fun setData(data: List<Products>){
        datas = data
        notifyDataSetChanged()
    }

    class ProductsHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Products) {
            with(itemView) {
                nameProducts.text = item.title
                categProducts.text = item.category
                descProducts.text = item.description
                //priceProducts.text = item.price.toString()
                Picasso.get().load(item.image)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(imgProducts)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = ProductsHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_products, parent, false))


    override fun onBindViewHolder(holder: ProductsAdapter.ProductsHolder, position: Int) = holder.bind(datas[position])

    override fun getItemCount(): Int = datas.size

}