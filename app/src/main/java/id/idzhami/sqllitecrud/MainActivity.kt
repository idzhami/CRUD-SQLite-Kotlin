package id.idzhami.sqllitecrud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.idzhami.sqllitecrud.dbPerson.DbAdapterPerson
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private var adapterTransaction by Delegates.notNull<RecycleViewAdapter>()
    var dbhelper = DbAdapterPerson(this).TABLE_PERSON()
    var id_data = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        try {
            GET_DATA_PERSONE()

            val edname = findViewById<EditText>(R.id.ED_NAME)
            val address = findViewById<EditText>(R.id.ED_ADDRESS)
            val birthday = findViewById<EditText>(R.id.ED_BIRTHDAY)
            val btn_add = findViewById<Button>(R.id.BTN_ADD)
//            val btn_update = findViewById<Button>(R.id.BTN_UPDATE)
//            btn_update.setOnClickListener {
//                try {

//                } catch (e: Exception) {
//                    Toast.makeText(
//                        this,
//                        "e.toString()",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
            btn_add.setOnClickListener {
                try {
                    if (id_data == "") {
                        if (edname.text.toString().trim() != "") {
                            ADD_PERSON(
                                edname.text.toString().trim(),
                                address.text.toString().trim(),
                                birthday.text.toString().trim()
                            )
                            Handler().postDelayed({
                                val edname = findViewById<EditText>(R.id.ED_NAME).setText("")
                                val address = findViewById<EditText>(R.id.ED_ADDRESS).setText("")
                                val birthday = findViewById<EditText>(R.id.ED_BIRTHDAY).setText("")
                                Toast.makeText(
                                    this,
                                    "Added successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                GET_DATA_PERSONE()
                            }, 1000)
                        } else {
                            Toast.makeText(
                                this,
                                "Please enter name",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        if (edname.text.toString().trim() != "") {
                            UPDATE_DATA(
                                id_data,
                                edname.text.toString().trim(),
                                address.text.toString().trim(),
                                birthday.text.toString().trim()
                            )
                            Handler().postDelayed({
                                val edname = findViewById<EditText>(R.id.ED_NAME).setText("")
                                val address = findViewById<EditText>(R.id.ED_ADDRESS).setText("")
                                val birthday = findViewById<EditText>(R.id.ED_BIRTHDAY).setText("")
                                val btn_add = findViewById<Button>(R.id.BTN_ADD)
                                btn_add.setText("Add")
                                id_data = ""
                                Toast.makeText(
                                    this,
                                    "Update successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                GET_DATA_PERSONE()
                            }, 1000)
                        } else {
                            Toast.makeText(
                                this,
                                "Please enter name",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    Toast.makeText(
                        this,
                        "e.toString()",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        } catch (e: Exception) {
            Log.d(" Data Error ", e.toString())
        }

    }

    private fun ADD_PERSON(
        name: String,
        address: String,
        birtday: String
    ) {
        var addperson = dbhelper.ADD_DATA_PERSON(name, address, birtday)
    }

    private fun GET_DATA_PERSONE() {
        try {
            var dataPerson = dbhelper.GET_DATA_PERSON()
            adapterTransaction = RecycleViewAdapter(
                this,
                dataPerson,
                this
            )
            val rvmain = findViewById<RecyclerView>(R.id.rv_main)

            rvmain.layoutManager = LinearLayoutManager(this@MainActivity)
            rvmain.adapter = adapterTransaction

        } catch (e: Exception) {
            Log.e("Error : ", e.toString())
        }
    }

    fun DELETE_DATA(id: String) {
        try {
            Log.d("id :", id)
            var dataPerson = dbhelper.DELETE_DATA_PERSON(id)
            Handler().postDelayed({
                Toast.makeText(
                    this,
                    "Delete successfully",
                    Toast.LENGTH_SHORT
                ).show()
                GET_DATA_PERSONE()
            }, 1000)
        } catch (e: Exception) {

        }
    }

    private fun UPDATE_DATA(
        id: String,
        name: String,
        address: String,
        birthday: String
    ) {
        var dataPerson = dbhelper.UPDATE_DATA_PERSON(id, name, address, birthday)
    }

    fun DATA_FROM_RECYCLEVIEW(
        id: String,
        name: String,
        address: String,
        birthday: String
    ) {

        id_data = id
        val btn_add = findViewById<Button>(R.id.BTN_ADD)
        btn_add.setText("Update")
        val edname = findViewById<EditText>(R.id.ED_NAME).setText(name)
        val address = findViewById<EditText>(R.id.ED_ADDRESS).setText(address)
        val birthday = findViewById<EditText>(R.id.ED_BIRTHDAY).setText(birthday)
    }

}