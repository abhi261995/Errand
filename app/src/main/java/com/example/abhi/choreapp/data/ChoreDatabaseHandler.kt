package com.example.abhi.choreapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.abhi.choreapp.model.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChoreDatabaseHandler(context:Context) :SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //SQL-Structured Query Language
        var CREATE_CHORE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_CHORE_NAME + " TEXT," + KEY_CHORE_ASSIGNED_BY +
                " TEXT," + KEY_CHORE_ASSIGNED_TO + " TEXT," + KEY_CHORE_ASSIGNED_TIME + " LONG" + ")"

        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

        //CREATE TABLE AGAIN
        onCreate(db)

    }

    /***
     * CRUD -Create ,Read,Update,Delete
     */
    fun createChore(chore: Chore) {
        var db: SQLiteDatabase = writableDatabase
        var values: ContentValues = ContentValues() //holds key value pair
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)
        Log.d("DATA INSERTED ", "SUCCESS")
        db.close()
    }

    fun readChore():ArrayList<Chore> {
        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<Chore>? = null
        list = ArrayList()
        var selectAll = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(selectAll, null)



        if (cursor.moveToFirst()) {
            do {
                var chore = Chore()
                chore.id=cursor.getInt(cursor.getColumnIndex(KEY_ID))
                chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
                chore.assignBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))
                chore.assignTo = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
                chore.timeAssignment = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))

                list.add(chore)

            } while (cursor.moveToNext())




        }
        return list
    }


    fun updateChore(chore: Chore):Int{
        var db:SQLiteDatabase=writableDatabase

        var values:ContentValues= ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())


        //update a row

        return db.update(TABLE_NAME,values, KEY_ID + "=?", arrayOf(chore.id.toString()) )


    }

    fun deleteChore(id: Int){

        var db:SQLiteDatabase=writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))
        db.close()
    }

    fun getChoresCount():Int{
        var db:SQLiteDatabase=readableDatabase
        var countQuery ="SELECT * FROM "+ TABLE_NAME
        var cursor:Cursor=db.rawQuery(countQuery,null)
        return cursor.count
    }
}