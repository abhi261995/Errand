package com.example.abhi.choreapp.data

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.abhi.choreapp.R
import com.example.abhi.choreapp.activity.ChoreListActivity
import com.example.abhi.choreapp.model.Chore
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.list_row.view.*
import kotlinx.android.synthetic.main.popup.view.*

class ChoreListAdapter(private val list:ArrayList<Chore>,private val context: Context):RecyclerView.Adapter<ChoreListAdapter.ViewHolder>()
{

    override fun getItemCount(): Int {

        return list.size
         }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
                //creating view from xml file
            val view=LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)
            return ViewHolder(view,context,list)
         }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

           holder?.bindView(list[position])

    }


   inner class ViewHolder(itemView: View,mContext: Context,list:ArrayList<Chore>) : RecyclerView.ViewHolder(itemView),View.OnClickListener{

            var mContext=mContext
            var mlist=list
            var choreName=itemView.listChoreName
            var assignBy=itemView.listAssignedBy
            var assignTo=itemView.listAssignTo
            var assignDate=itemView.listDate
            var deleteButton=itemView.listDeleteButton
            var editButton=itemView.listEditButton




        fun bindView(chore:Chore){

            choreName.text=chore.choreName
            assignBy.text=chore.assignBy
            assignTo.text=chore.assignTo
            assignDate.text= chore.showHumanDate(System.currentTimeMillis())
            deleteButton.setOnClickListener(this) //registering button
            editButton.setOnClickListener(this)

        }

        override fun onClick(view: View?) {
            var mPosition:Int=adapterPosition

            var chore=mlist[mPosition]
            when(view!!.id){
                deleteButton.id ->{deleteChore(chore.id!!.toInt())
                mlist.removeAt(adapterPosition)
                    notifyItemRemoved(adapterPosition)
                }
                editButton.id ->{

                    editChore(chore)


                }
            }


        }

        fun deleteChore(id:Int){
            val db:ChoreDatabaseHandler= ChoreDatabaseHandler(mContext)

            db.deleteChore(id)

        }

       fun editChore(chore:Chore) {
           val db: ChoreDatabaseHandler = ChoreDatabaseHandler(mContext)

           var dialogBuilder: AlertDialog.Builder?
           var dialog: AlertDialog?
           dialogBuilder = AlertDialog.Builder(mContext).setView(itemView)
           var view = LayoutInflater.from(context).inflate(R.layout.popup,null)
           var choreName = view.popEnterChore
           var assignBy = view.popAssignBy
           var assignTo = view.popAssignTo
           var saveButton = view.popSaveChore
           var timeAssign = null

           dialogBuilder = AlertDialog.Builder(mContext).setView(view)
           dialog = dialogBuilder!!.create()
           dialog?.show()

           saveButton!!.setOnClickListener {

               if (!TextUtils.isEmpty(choreName.text.toString().trim()) &&
                       !TextUtils.isEmpty(assignBy.text.toString().trim()) && !
                       TextUtils.isEmpty(assignTo.text.toString().trim())) {

                   chore.choreName = "Chore: ${choreName.text.toString().trim()}"
                   chore.assignBy = "Assign By: ${assignBy.text.toString().trim()}"
                   chore.assignTo = "Assign To: ${assignTo.text.toString().trim()}"
                   chore.timeAssignment =chore.timeAssignment
                   db!!.updateChore(chore)
                   notifyItemChanged(adapterPosition,chore)
                   dialog!!.dismiss()
               } else {

               }
           }






       }




    }


}
