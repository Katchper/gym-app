package datasource

import android.content.Context
import com.example.gymapp.datasource.RoomDatabaseII

object WorkoutInjection {
    fun getDatabase(context: Context): RoomDatabaseII =
        WorkoutsRoomDatabase.getDatabase(context)!!
}