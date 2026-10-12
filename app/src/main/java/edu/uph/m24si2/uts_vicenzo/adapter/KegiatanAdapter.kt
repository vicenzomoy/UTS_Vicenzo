package edu.uph.m24si2.uts_vicenzo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.uph.m24si2.uts_vicenzo.R
import edu.uph.m24si2.uts_vicenzo.model.Kegiatan

class KegiatanAdapter (
    private val kegiatanList: List<Kegiatan>,
    private val onHapusClick: (Kegiatan) -> Unit
) : RecyclerView.Adapter<KegiatanAdapter.KegiatanViewHolder>() {
    class KegiatanViewHolder (itemView: View):
        RecyclerView.ViewHolder(itemView) {
        val txvWaktuKegiatan: TextView = itemView.findViewById(R.id.txvWaktuKegiatan)
        val txvNamaKegiatan: TextView = itemView.findViewById(R.id.txvNamaKegiatan)
        val btnHapus: Button = itemView.findViewById(R.id.btnHapus)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): KegiatanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_kegiatan, parent, false)
        return KegiatanViewHolder(view)
    }

    override fun onBindViewHolder(holder: KegiatanViewHolder, position: Int) {
        val kegiatan = kegiatanList[position]
        holder.txvNamaKegiatan.text = kegiatan.NamaKegiatan
        holder.txvWaktuKegiatan.text = kegiatan.WaktuKegiatan
        holder.btnHapus.setOnClickListener {
            onHapusClick(kegiatan)
        }
    }

    override fun getItemCount(): Int = kegiatanList.size
}