package com.ar.sample.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ar.sample.R
import com.ar.sample.data.models.GithubContributors
import com.ar.sample.databinding.ContributerListItemBinding
import com.ar.sample.interfaces.ItemClickListener
import com.bumptech.glide.Glide


/**
 * @Author: Abdul Rehman
 */

class GithubContributorAapter(var context: Context) :
    RecyclerView.Adapter<GithubContributorAapter.ViewHolder>() {
    private var items: List<GithubContributors> = emptyList()
    private var itemClickLister: ItemClickListener<GithubContributors>? = null

    fun setClickListener(itemClickLister: ItemClickListener<GithubContributors>?) {
        this.itemClickLister = itemClickLister
    }

    class ViewHolder(binding: ContributerListItemBinding) :
        RecyclerView.ViewHolder(binding.getRoot()) {
        var binding: ContributerListItemBinding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ContributerListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.name.text =
            (context.getString(R.string.contributor_adapter_developer_name) + " " + item.name)
        holder.binding.contributions.setText(context.getString(R.string.contributor_adapter_total_commits) + " " + item.contributions)
        Glide.with(context).load(item.imageURL).into(holder.binding.avatar)
        holder.itemView.setOnClickListener {
            itemClickLister?.onItemClick(item, "ItemClick")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(dataList: List<GithubContributors>) {
        this.items = dataList
        notifyDataSetChanged()
    }
}