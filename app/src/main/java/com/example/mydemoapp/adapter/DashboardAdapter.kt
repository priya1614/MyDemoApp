package com.example.mydemoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mydemoapp.data.model.User
import com.example.mydemoapp.databinding.ItemNewsLayoutBinding

class DashboardAdapter(
    private val users: ArrayList<User>
) : RecyclerView.Adapter<DashboardAdapter.DataViewHolder>() {

    private lateinit var binding: ItemNewsLayoutBinding

    class DataViewHolder(
        val binding: ItemNewsLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        binding = ItemNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }


    override fun getItemCount(): Int = users.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])

    }

    fun addData(list: List<User>) {
        users.addAll(list)
    }

}