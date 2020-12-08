package hu.andras.mobilbeadando.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ Income::class, Expense::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {

    abstract val transactionDatabaseDao : TransactionDatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: TransactionDatabase? = null

        fun getInstance(context: Context): TransactionDatabase {
            var instance = INSTANCE

            if(instance == null){
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                    "transaction_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }
            return instance
        }
    }



}