package id.idzhami.sqllitecrud.dbPerson

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.core.content.contentValuesOf
import id.idzhami.sqllitecrud.modelPerson.Person


class DbAdapterPerson(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_PERSON($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_NAME TEXT , $COL_ADDRESS TEXT , $COL_BIRTHDAY TEXT);")
    }

    private fun dropDb(db: SQLiteDatabase) {
        db.execSQL("DROP TABLE $TABLE_PERSON;")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        this.dropDb(db)
        onCreate(db)
    }

    inner class TABLE_PERSON {
        fun ADD_DATA_PERSON(caller_name: String, caller_address: String, caller_birthday: String) {
            val db = writableDatabase;
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_NAME, caller_name)
            newContentoffline.put(COL_ADDRESS, caller_address)
            newContentoffline.put(COL_BIRTHDAY, caller_birthday)
            db.insert(TABLE_PERSON, null, newContentoffline)
        }

        fun DELETE_DATA_PERSON(id: String): Int {
            val db = writableDatabase
            return db.delete(TABLE_PERSON, "caller_id = ?", arrayOf(id))
        }

        fun GET_DATA_PERSON(): ArrayList<Person> {
            val db = writableDatabase
            val res = db.rawQuery("SELECT * FROM ${TABLE_PERSON}", null)
            val useList = ArrayList<Person>()
            if (res.moveToFirst()) {
                while (!res.isAfterLast()) {
                    val model =
                        Person(
                            res.getString(0),
                            res.getString(1),
                            res.getString(2),
                            res.getString(3)
                        )

                    useList.add(model)
                    res.moveToNext()
                }
            }
            res.close()
            return useList
        }

        fun UPDATE_DATA_PERSON(id: String, name: String, address: String, birthday: String) {
            val db = writableDatabase
            val newContentoffline = ContentValues()
            newContentoffline.put(COL_ID, id)
            newContentoffline.put(COL_NAME, name)
            newContentoffline.put(COL_ADDRESS, address)
            newContentoffline.put(COL_BIRTHDAY, birthday)
            db.update(TABLE_PERSON, newContentoffline, "$COL_ID = ?", arrayOf(id))
        }

    }

    companion object {
        val DATABASE_NAME = "Person.db"
        val TABLE_PERSON = "tblPerson"
        val COL_ID = "caller_id"
        val COL_NAME = "caller_name"
        val COL_ADDRESS = "caller_address"
        val COL_BIRTHDAY = "caller_birthday"
    }

}

