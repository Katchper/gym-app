package datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gymapp.R
import com.example.gymapp.datasource.RoomDatabaseII
import com.example.gymapp.model.WorkoutDao
import com.example.gymapp.model.classgroup.Workout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Workout::class], version = 1)
abstract class WorkoutsRoomDatabase : RoomDatabase(), RoomDatabaseII {

    abstract override fun workoutsDao(): WorkoutDao

    override fun closeDb() {
        instance?.close()
        instance = null
    }

    companion object {
        private var instance: WorkoutsRoomDatabase? = null
        private val coroutineScope = CoroutineScope(Dispatchers.IO)

        @Synchronized
        fun getDatabase(context: Context): WorkoutsRoomDatabase? {
            synchronized(this) {
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            WorkoutsRoomDatabase::class.java,
                            "workouts_db"
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
        private fun populateDatabase(context: Context, instance: WorkoutsRoomDatabase) {

            val dao = instance.workoutsDao()
            dao.insertOneWorkout(Workout(0,"Monday","Biceps", R.drawable.icons8_biceps.toString(), 0, ""))
        }

    }
}