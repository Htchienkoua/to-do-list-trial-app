package com.example.todolist

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


//this class will be useful for saving the data entered in the TODO list in the phone memory for later usage

@Suppress("UNCHECKED_CAST")
class FileHelper {

    val FILENAME = "listinfo.dat"

    //method to write data to this file
    fun writeData(item : ArrayList<String>, context: Context){

        //the file output stream method takes two parameters , the filename constant and the Context(private in this case means accessible only within the app)

        var fos : FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)

        var oas = ObjectOutputStream(fos)
        oas.writeObject(item) //the 'item' parameter is the string in the field to be saved in the memory via the FileOutput Stream
        oas.close() //to close the file after saving the data , since it can cause some crashing problems if not closed
    }


    //this method will read the saved data from the memory

    //but we need to define a try-and-catch option if there is no initial file saved to the phone memory
    fun readData(context: Context): ArrayList<String>{

        var itemList : ArrayList<String>

        try{
            var fis : FileInputStream = context.openFileInput(FILENAME)
            var ois = ObjectInputStream(fis)
            itemList  = ois.readObject() as ArrayList<String>
        }catch (e : FileNotFoundException){
            itemList = ArrayList()//we define an empty item arraylist if we dont see any items saved to the phone memomry as the scope of the catch bloc
        }


        return itemList
    }
}