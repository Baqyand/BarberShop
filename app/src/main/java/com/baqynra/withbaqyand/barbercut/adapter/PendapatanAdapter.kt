package com.baqynra.withbaqyand.barbercut.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.model.DataTransaksi
import kotlinx.android.synthetic.main.item_transaksi.view.*

class PendapatanAdapter(private val context: Context):RecyclerView.Adapter<PendapatanAdapter.PendapatanViewHolder>() {
    private val dataArray = mutableListOf<DataTransaksi>()

    fun addData(data:MutableList<DataTransaksi>){
        dataArray.addAll(data)
//        dataArray.sortByDescending { it.tgl_input }
        notifyDataSetChanged()
    }

    fun initData(data:MutableList<DataTransaksi>){
        dataArray.clear()
        dataArray.addAll(data)
        notifyDataSetChanged()
    }

    fun clearData(){
        dataArray.clear()
        notifyDataSetChanged()
    }

    fun subtractData(){
        dataArray.removeAt(dataArray.size-1)
        notifyDataSetChanged()
    }
    inner class PendapatanViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val seri = itemView.tv_seri
        val harga = itemView.tv_Harga
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendapatanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaksi, parent, false)
        return PendapatanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PendapatanViewHolder, position: Int) {
        holder.seri.text = dataArray[position].no_bukti
        holder.harga.text = dataArray[position].total
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }


}