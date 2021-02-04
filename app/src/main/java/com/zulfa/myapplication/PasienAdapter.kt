package com.zulfa.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


import com.zulfa.myapplication.Database.Pasien

import kotlinx.android.synthetic.main.adapter_pasien.view.*

class PasienAdapter (private val AllPasien: ArrayList<Pasien>, private val listener: OnAdapterListener) : RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        return PasienViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_pasien, parent, false)
        )
    }

    override fun getItemCount() = AllPasien.size

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = AllPasien[position]
        holder.view.text_nama.text = pasien.nama
        holder.view.text_nama.setOnClickListener {
            listener.onClick(pasien)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(pasien)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(pasien)
        }
    }

    class PasienViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Pasien>) {
        AllPasien.clear()
        AllPasien.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(pasien: Pasien)
        fun onDelete(pasien: Pasien)
        fun onUpdate(pasien: Pasien)
    }
}