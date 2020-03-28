package id.poter.PLCMonitoring.apdater

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import pelapak.marketplace.R
import pelapak.marketplace.model.r_rekening
import java.util.*


class rekeningAdapter(private val context: Context, results: ArrayList<r_rekening>, adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<rekeningAdapter.ItemViewHolder>() {
    private var mAdapterCallback: AdapterCallback? = null

    private var Items = ArrayList<r_rekening>()

    init {
        this.Items = results
        this.mAdapterCallback = adapterCallback;

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomer : TextView
        val bank : TextView
        val pemilik : TextView

        init {
            nomer = view.findViewById(R.id.cr_nomor_rekening)
            bank = view.findViewById(R.id.cr_nama_bank)
            pemilik = view.findViewById(R.id.cr_nama_pemilik)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costume_rekening, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.pemilik.text = result.atas_nama
        myHolder.nomer.text = result.nomor_rekening
        myHolder.bank.text = result.nama_bank
    }

    override fun getItemCount(): Int {
        return Items.size
    }
    interface AdapterCallback {

        fun onRowAdapterClicked(position: Int)
    }
}