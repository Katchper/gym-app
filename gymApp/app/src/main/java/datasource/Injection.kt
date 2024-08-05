package datasource

import android.content.Context
import com.example.gymapp.datasource.RoomDatabaseI

object Injection {
    fun getDatabase(context: Context): RoomDatabaseI =
        ExercisesRoomDatabase.getDatabase(context)!!
}