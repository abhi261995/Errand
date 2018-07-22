package com.example.abhi.choreapp.model

import java.text.DateFormat
import java.util.*

class  Chore(){
    var choreName :String? =null
    var assignBy:String?=null
    var assignTo:String?=null
    var timeAssignment:Long?=null
    var id:Int?=null

    constructor(choreName: String,aasignBy:String,assignTo:String,timeAssign:Long,id:Int):this(){

        this.choreName=choreName
        this.assignTo=assignTo
        this.assignBy=aasignBy
        this.timeAssignment=timeAssign
        this.id=id

    }

    fun showHumanDate(timeAssigned:Long):String{
        var dateFormat:java.text.DateFormat= DateFormat.getDateInstance()
        var formattedDate:String=dateFormat.format(Date(timeAssigned).time)
        return "Created: ${formattedDate}"
    }


}