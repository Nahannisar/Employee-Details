package com.example.userdetail.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import com.example.userdetail.R
import com.example.userdetail.databinding.ItemUserBinding
import com.example.userdetail.model.User
import javax.inject.Inject

class UsersAdapter @Inject() constructor() :
    PagingDataAdapter<User, UsersAdapter.ViewHolder>(differCallback) {

    private lateinit var binding: ItemUserBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemUserBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
        holder.setIsRecyclable(false)
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            binding.apply {
                val name = user.firstName+" "+ if(user.maidenName.isNotBlank())user.maidenName+" " else{""}+ user.lastName
                val age = context.resources.getString(R.string.age)+" : "+user.age
                val bloodGroup =  context.resources.getString(R.string.blood_group)+" : "+user.bloodGroup
                val phone =  context.resources.getString(R.string.phone)+" : "+user.phone
                tvName.text = name
                tvAge.text = age
                tvUserPhone.text= phone
                tvBloodGroup.text= bloodGroup
                val userImageURL =  user.image
                imgUser.load(userImageURL){
                    crossfade(true)
                    placeholder(R.drawable.poster_placeholder)
                    scale(Scale.FILL)
                }
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(user)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((User) -> Unit)? = null

    fun setOnItemClickListener(listener: (User) -> Unit) {
        onItemClickListener = listener
    }

    companion object {
        val differCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == oldItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem:User): Boolean {
                return oldItem == newItem
            }
        }
    }

}