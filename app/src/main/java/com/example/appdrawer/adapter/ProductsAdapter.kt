package com.example.appdrawer.adapter

import android.content.Context
import android.content.Context.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appdrawer.databinding.ItemProductsBinding
import com.example.appdrawer.model.Products

class ProductsAdapter(private val context: Context):RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    private var datas = listOf<Products>()

    fun setData(data: List<Products>){
        datas = data
        notifyDataSetChanged()
    }



    inner class ViewHolder (private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun binData(letak:Products){
            binding.nameProducts.text = letak.title
            binding.categProducts.text = letak.category
            binding.descProducts.text = letak.description
            binding.priceProducts.text = letak.price.toString()
            Glide.with(binding.root).load(letak.image).into(binding.imgProducts)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemProductsBinding.inflate(LayoutInflater.from(context), parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binData(datas[position])
    }

    override fun getItemCount(): Int = datas.size
}

/*
* interface TodoListener {
    fun onClick(todo: String)
    fun onDelete(todo: String)
}

class TodoAdapter(
    private val context: Context, private val listener: TodoListener
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemTodoBinding,
        private val listener: TodoListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(todo: String) {
            binding.tvTodo.text = todo

            binding.root.setOnClickListener { listener.onClick(todo) }
            binding.ivTodo.setOnClickListener { listener.onDelete(todo) }
        }
    }

    private var todo = setOf<String>()

    fun setData(data: Set<String>) {
        todo = data
        notifyDataSetChanged()
    }

    fun addData(data: String) {
        todo = todo.toMutableList().apply { add(0, data) }.sorted().toSet()
        notifyDataSetChanged()
    }

    fun deleteData(data: String) {
        val index = todo.toMutableList().indexOf(data)
        todo = todo.toMutableList().apply { remove(data) }.toSet()
        notifyItemRemoved(index)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoAdapter.ViewHolder {
        return ViewHolder(
            ItemTodoBinding.inflate(LayoutInflater.from(context), parent, false),
            listener
        )
    }

    override fun onBindViewHolder(holder: TodoAdapter.ViewHolder, position: Int) {
        holder.bindData(todo.toMutableList()[position])
    }

    override fun getItemCount(): Int = todo.size
}*/