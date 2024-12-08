package robert.paba.dbroom

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import robert.paba.dbroom.database.daftarBelanja
import robert.paba.dbroom.database.daftarBelanjaDB
import robert.paba.dbroom.database.historyBarang
import robert.paba.dbroom.database.historyBarangDB

class adapterDaftar (private val daftarBelanja : MutableList<daftarBelanja>):
        RecyclerView.Adapter<adapterDaftar.ListViewHolder>(){

    private lateinit var DB : daftarBelanjaDB

        private lateinit var onItemClickCallback : OnItemClickCallback

    interface OnItemClickCallback {
        fun delData(dtBelanja: daftarBelanja)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItemBarang)
        var _tvjumlahBarang = itemView.findViewById<TextView>(R.id.tvjumlahBarang)
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)

        var _btnEdit = itemView.findViewById<ImageView>(R.id.btnEdit)
        var _btnDelete = itemView.findViewById<ImageView>(R.id.btnDelete)
        var _btnSelesai = itemView.findViewById<Button>(R.id.btnSelesai)

    }

    fun isiData (daftar: List<daftarBelanja>){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): adapterDaftar.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list, parent,
            false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: adapterDaftar.ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]


        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvjumlahBarang.setText(daftar.jumlah)

        holder._btnEdit.setOnClickListener{
            val intent = Intent(it.context, TambahDaftarNew::class.java)
            intent.putExtra("id", daftar.id)
            intent.putExtra("addEdit",1)
            it.context.startActivity(intent)
        }
        holder._btnDelete.setOnClickListener{
            onItemClickCallback.delData(daftar)
        }

        holder._btnSelesai.setOnClickListener {
            CoroutineScope(Dispatchers.IO).async {

                onItemClickCallback.delData(daftar)

                val historyBarangDB = historyBarangDB.getDatabase(it.context)
                historyBarangDB.funhistoryBarangDAO().insert(
                    historyBarang(
                        tanggal2 = daftar.tanggal,
                        item2 = daftar.item,
                        jumlah2 = daftar.jumlah
                    )
                )

                val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
                withContext(Dispatchers.Main) {
                    isiData(daftarBelanja)
                }
            }
        }


    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }
}