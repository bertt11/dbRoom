package robert.paba.dbroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class historyBarang(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id2: Int = 0,

    @ColumnInfo(name = "tanggal")
    var tanggal2: String? = null,

    @ColumnInfo(name = "item")
    var item2: String? = null,

    @ColumnInfo(name = "jumlah")
    var jumlah2: String? = null,

    @ColumnInfo(name = "status")
    var status2: Int = 0,
)

