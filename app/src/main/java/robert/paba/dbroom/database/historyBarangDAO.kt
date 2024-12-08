package robert.paba.dbroom.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface historyBarangDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: historyBarang)

    @Query("UPDATE historyBarang SET tanggal=:isi_tanggal2, item =:isi_item2, jumlah=:isi_jumlah2 ,status=:isi_status2 " +
            "WHERE id=:pilihid2")
    fun update(isi_tanggal2: String, isi_item2: String, isi_jumlah2: String, isi_status2: Int,
               pilihid2:Int)

    @Delete
    fun delete (daftar: historyBarang)

    @Query("SELECT * FROM historyBarang ORDER BY id asc")
    fun selectAll() : MutableList<historyBarang>

    @Query("SELECT * FROM historyBarang WHERE id=:isi_id2")
    suspend fun getItem(isi_id2 : Int) : historyBarang
}