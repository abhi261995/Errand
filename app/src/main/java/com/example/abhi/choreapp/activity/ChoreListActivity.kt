package com.example.abhi.choreapp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.abhi.choreapp.R
import com.example.abhi.choreapp.data.ChoreDatabaseHandler
import com.example.abhi.choreapp.data.ChoreListAdapter
import com.example.abhi.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_chore_list.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListActivity : AppCompatActivity() {
    var dbHandler : ChoreDatabaseHandler?=null
    private var adapter : ChoreListAdapter?=null
    private var chorList:ArrayList<Chore>?=null
    private var choreListItems:ArrayList<Chore>?=null
    private var dialogBuilder:AlertDialog.Builder?=null

    private var dialog:AlertDialog?=null
    private var layoutManager: RecyclerView.LayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chore_list)

        chorList=ArrayList<Chore>()
        choreListItems=ArrayList()
        layoutManager= LinearLayoutManager(this)
        adapter= ChoreListAdapter(choreListItems!!,this)
        dbHandler= ChoreDatabaseHandler(this)
        // setup list =recycler View
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter

        //Load our chore
         chorList=dbHandler!!.readChore()

        for(c in chorList!!.iterator())
        {
            val chore=Chore()
            chore.choreName="Chore: ${c.choreName}"
            chore.assignBy="Assign By: ${c.assignBy}"
            chore.assignTo="Assign To: ${c.assignTo}"
            chore.id=c.id
            chore.showHumanDate(c.timeAssignment!!)
            choreListItems!!.add(chore)


        }

         adapter!!.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.top_menu,menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId ==R.id.add_menu_button){
                createPopupDialog()

        }

        return true
    }

    fun createPopupDialog(){
        var view =layoutInflater.inflate(R.layout.popup,null)
        var choreName=view.popEnterChore
        var assignBy=view.popAssignBy
        var assignTo=view.popAssignTo
        var saveButton=view.popSaveChore
        var timeAssign=null

        dialogBuilder=AlertDialog.Builder(this).setView(view)
        dialog=dialogBuilder!!.create()
        dialog!!.show()

        saveButton!!.setOnClickListener {

            if(!TextUtils.isEmpty(choreName.text.toString().trim())&&
                    !TextUtils.isEmpty(assignBy.text.toString().trim())&&!
                    TextUtils.isEmpty(assignTo.text.toString().trim()))
            {
                val chore=Chore()
                chore.choreName=choreName.text.toString().trim()
                chore.assignBy=assignBy.text.toString().trim()
                chore.assignTo=assignTo.text.toString().trim()
                chore.timeAssignment=chore.timeAssignment
                dbHandler!!.createChore(chore)
                dialog!!.dismiss()
                startActivity(Intent(this,ChoreListActivity::class.java))
                finish()
            }

            else{
                Toast.makeText(this,"Enter Chore",Toast.LENGTH_SHORT).show()

            }
        }

    }
}
