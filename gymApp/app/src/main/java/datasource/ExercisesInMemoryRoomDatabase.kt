package datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gymapp.datasource.RoomDatabaseI
import com.example.gymapp.model.ExercisesDao
import com.example.gymapp.model.classgroup.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The database for exercises data,
 */

@Database(entities = [Exercise::class], version = 1)
abstract class ExercisesRoomDatabase : RoomDatabase(), RoomDatabaseI {

    abstract override fun exercisesDao(): ExercisesDao

    override fun closeDb() {
        instance?.close()
        instance = null
    }

    companion object {
        private var instance: ExercisesRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        @Synchronized
        fun getDatabase(context: Context): ExercisesRoomDatabase? {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            ExercisesRoomDatabase::class.java,
                            "exercises_db"
                        )
                            .allowMainThreadQueries()
                            .addCallback(roomDatabaseCallback(context))
                            .build()
                }
                return instance!!
            }
        }

        private fun roomDatabaseCallback(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    coroutineScope.launch {
                        populateDatabase(context, getDatabase(context)!!)
                    }
                }
            }
        }
        private fun populateDatabase(context: Context, instance: ExercisesRoomDatabase) {
            val dao = instance.exercisesDao()
        }

    }
}