package com.example.abhi.choreapp.activity

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.example.abhi.choreapp.R
import com.example.abhi.choreapp.data.ChoreDatabaseHandler
import com.example.abhi.choreapp.data.ChoreListAdapter
import com.example.abhi.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var dbHandler :ChoreDatabaseHandler?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        dbHandler = ChoreDatabaseHandler(this)
        checkDB()

        saveChore.setOnClickListener {


            if (!TextUtils.isEmpty(choreId.text.toString()) &&
                    !TextUtils.isEmpty(asignById.text.toString())
                    && !TextUtils.isEmpty(assignToId.text.toString())) {
                    progressBar.visibility=View.VISIBLE


                //save to db
                var chore = Chore()
                chore.choreName = choreId.text.toString()
                chore.assignBy = asignById.text.toString()
                chore.assignTo = assignToId.text.toString()
                chore.timeAssignment=chore.timeAssignment
                saveToDb(chore)
                   progressBar.visibility=View.GONE
                startActivity(Intent(this,ChoreListActivity::class.java))


            }
            else
            {
                Toast.makeText(this,"Please enter the chore",Toast.LENGTH_SHORT).show()
            }


        }
    }
            fun checkDB(){
                if(dbHandler!!.getChoresCount()>0){
                    startActivity(Intent(this,ChoreListActivity::class.java))

                }
            }
        fun saveToDb(chore: Chore)
        {
            dbHandler!!.createChore(chore)
        }
    }
