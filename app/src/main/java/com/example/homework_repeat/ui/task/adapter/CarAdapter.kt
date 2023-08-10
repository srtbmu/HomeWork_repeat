package com.example.homework_repeat.ui.task.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homework_repeat.databinding.ItemCarBinding
import com.example.homework_repeat.model.Car


class CarAdapter() : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {

    private val list = arrayListOf<Car>()

    fun addCar(cars: List<Car>) {
        list.clear()
        list.addAll(cars)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        return CarViewHolder(
            ItemCarBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        holder.bind(list[position])

    }

    inner class CarViewHolder(private val binding: ItemCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.tvBrand.text = car.brand
            binding.tvModel.text = car.model
        }
    }
}