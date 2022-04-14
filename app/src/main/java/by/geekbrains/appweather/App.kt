package by.geekbrains.appweather

import android.app.Application
import android.os.Looper
import androidx.room.Room
import by.geekbrains.appweather.room.HistoryDAO
import by.geekbrains.appweather.room.HistoryDataBase
import java.util.logging.Handler

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "HistoryDataBase.db"
        fun getHistoryDAO(): HistoryDAO {
            if (db == null) {
                synchronized(HistoryDataBase::class.java) {
                    if (db == null) {
                        if (appInstance == null)
                            throw IllegalStateException("Application is null while creating DataBase")
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext, HistoryDataBase::class.java, DB_NAME)
                            .build()
                    }
                }
            }
            return db!!.historyDAO()
        }
    }
}