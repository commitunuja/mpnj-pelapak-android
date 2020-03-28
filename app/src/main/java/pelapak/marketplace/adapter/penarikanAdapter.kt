package id.poter.PLCMonitoring.apdater

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import pelapak.marketplace.R
import pelapak.marketplace.model.r_penarikan
import java.util.*


class penarikanAdapter(private val context: Context, results: ArrayList<r_penarikan>, adapterCallback: AdapterCallback) :
    RecyclerView.Adapter<penarikanAdapter.ItemViewHolder>() {
    private var mAdapterCallback: AdapterCallback? = null

    private var Items = ArrayList<r_penarikan>()

    init {
        this.Items = results
        this.mAdapterCallback = adapterCallback;

    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nomer : TextView
        val pemilik : TextView
        val nominal : TextView
        val status : TextView
        val tanggal : TextView
        init {
            nomer = view.findViewById(R.id.cvp_rekening)
            pemilik = view.findViewById(R.id.cvp_pemilik)
            nominal = view.findViewById(R.id.cvp_nominal)
            status = view.findViewById(R.id.cvp_status)
            tanggal = view.findViewById(R.id.cvp_tanggal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.costume_view_penarikan, null)

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val myHolder = holder
        val result = Items[position]
        myHolder.pemilik.text = result.atas_nama
        myHolder.nomer.text = result.nomor_rekening+" / "+result.nama_bank
        myHolder.nominal.text = result.nominal
        myHolder.status.text = result.status
        myHolder.tanggal.text = result.tgl
        myHolder.itemView.setOnClickListener {
            mAdapterCallback!!.onRowAdapterClicked(result)

        }
    }

    override fun getItemCount(): Int {
        return Items.size
    }
    interface AdapterCallback {

        fun onRowAdapterClicked(position: r_penarikan)
    }
}