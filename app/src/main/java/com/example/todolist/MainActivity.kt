package com.example.todolist

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var item: EditText
    lateinit var add : Button
    lateinit var listView : ListView

    var itemList = ArrayList<String>()

    var fileHelper = FileHelper() //to be able to access the FileHelper class from the mainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item = findViewById(R.id.editText)
        add = findViewById(R.id.button)
        listView = findViewById(R.id.List)

        //the fileHelper readData method called from the FileHelper Class
        itemList = fileHelper.readData(this)

        //defining the adapter for taking the data and displaying items onto the ListView view
        var arrayAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1, itemList) //for a basic listView
        listView.adapter = arrayAdapter

        add.setOnClickListener {

            var itemName : String = item.text.toString()
            itemList.add(itemName)
            item.setText("")
            fileHelper.writeData(itemList, applicationContext)
            arrayAdapter.notifyDataSetChanged()
        }

        listView.setOnItemClickListener { adapterView, view, position, l ->

            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you really wanna delete this item from the list ?")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, i ->

                itemList.removeAt(position)
                arrayAdapter.notifyDataSetChanged()
                fileHelper.writeData(itemList, applicationContext)
            })

            alert.create().show()
        }
    }
}