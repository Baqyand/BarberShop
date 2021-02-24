package com.baqynra.withbaqyand.barbercut.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.baqynra.withbaqyand.barbercut.Library
import com.baqynra.withbaqyand.barbercut.R
import com.baqynra.withbaqyand.barbercut.model.Datalocal
import com.baqynra.withbaqyand.barbercut.model.Paketpass
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_paket.view.*

class PaketAdapter(private val context: Context): RecyclerView.Adapter<PaketAdapter.PaketViewHolder>() {
    val dataArray = ArrayList<Paketpass>()
    val dataarr = mutableListOf<Datalocal>()
    private var selectposisi = RecyclerView.NO_POSITION
    private var klik: Boolean = true
    var paket : String? = null
    var cost = 0
    var totalnya = 0
    var param = 0
    val library = Library()

    fun addData(data:MutableList<Paketpass>){
        dataArray.addAll(data)
//        dataArray.sortByDescending { it.tgl_input }
        notifyDataSetChanged()
    }

    fun initData(data:MutableList<Paketpass>){
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

    inner class PaketViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val namaPaket = itemView.tv_namapaket
        val harga = itemView.tv_harga
        val collap = itemView.collapsible
        val tambah = itemView.tambah
        val kurang = itemView.kurang
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaketViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_paket, parent, false)
        return PaketViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaketViewHolder, position: Int) {
        holder.namaPaket.isSelected = selectposisi == position
        holder.namaPaket.text  = dataArray[position].nama
        holder.harga.text = library.toRupiah(dataArray[position].nilai!!.toInt())
        holder.namaPaket.setOnClickListener {
                if (holder.collap.visibility == View.GONE && holder.harga.visibility == View.GONE) {
                    holder.collap.visibility = View.VISIBLE
                    holder.harga.visibility = View.VISIBLE
                    totalnya += dataArray[position].nilai!!.toInt()
                    param += 1
                    notifyItemChanged(selectposisi)
                    val data = Intent("struk")
                    data.putExtra("harga", dataArray.get(position).nilai)
                    data.putExtra("nama_paket", dataArray.get(position).nama)
                    data.putExtra("kode", dataArray.get(position).kode_paket)
                    data.putExtra("total", totalnya)
                    data.putExtra("unit", param)
                    data.putExtra("param", "add")
                    LocalBroadcastManager.getInstance(context!!).sendBroadcast(data)

                    holder.tambah.setOnClickListener {
                        if (totalnya > 999999999) {
                            Snackbar.make(it, "Nilai Tidak boleh maksimum ", Snackbar.LENGTH_SHORT)
                                .show()
                            totalnya = 99999999

                        } else {
                            totalnya += dataArray[position].nilai!!.toInt()
                            param += 1

                            val data = Intent("struk")
                            data.putExtra("harga", dataArray.get(position).nilai)
                            data.putExtra("nama_paket", dataArray.get(position).nama)
                            data.putExtra("kode", dataArray.get(position).kode_paket)
                            data.putExtra("total", totalnya)
                            data.putExtra("unit", param)
                            data.putExtra("param", "add")
                            LocalBroadcastManager.getInstance(context!!).sendBroadcast(data)
                        }
                    }
                    holder.kurang.setOnClickListener {
                        if (param >= 1 && totalnya > 0) {
                            totalnya -= dataArray[position].nilai!!.toInt()
                            param -= 1

                            val data = Intent("struk")
                            data.putExtra("harga", dataArray.get(position).nilai)
                            data.putExtra("nama_paket", dataArray.get(position).nama)
                            data.putExtra("kode", dataArray.get(position).kode_paket)
                            data.putExtra("total", totalnya)
                            data.putExtra("unit", param)
                            data.putExtra("param", "kurang")
                            LocalBroadcastManager.getInstance(context!!).sendBroadcast(data)
                        }
                        if (totalnya <= 0){
                            Snackbar.make(it, "Nilai Tidak boleh kurang ", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                } else {
                    holder.collap.visibility = View.GONE
                    holder.harga.visibility = View.GONE

                }

            }
    }

    override fun getItemCount(): Int {
        return dataArray!!.size
    }


}