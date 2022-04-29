package id.idzhami.sqllitecrud

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import id.idzhami.sqllitecrud.dbPerson.DbAdapterPerson
import id.idzhami.sqllitecrud.modelPerson.Person

class RecycleViewAdapter(

    private val context: Context,
    private var resultTransaction: List<Person>,
    private val activity: MainActivity

) : RecyclerView.Adapter<RecycleViewAdapter.ViewHolderTransaction>() {
    private val TAG = javaClass.simpleName

    companion object {
        private const val VIEW_TYPE_DATA = 0;
        private const val VIEW_TYPE_PROGRESS = 1;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTransaction {
        return when (viewType) {
            VIEW_TYPE_DATA -> {//inflates row layout
                val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_list, parent, false)
                ViewHolderTransaction(view)
            }
            VIEW_TYPE_PROGRESS -> {//inflates progressbar layout
                val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.support_simple_spinner_dropdown_item, parent, false)
                ViewHolderTransaction(view)
            }
            else -> throw IllegalArgumentException("Different View type")
        }
    }

    override fun getItemCount(): Int = resultTransaction.size

    fun refreshAdapter(resultTransaction: List<Person>) {
        this.resultTransaction = resultTransaction
        notifyItemRangeChanged(0, this.resultTransaction.size)
    }


    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(
        holder: RecycleViewAdapter.ViewHolderTransaction,
        position: Int
    ) {
        val txtName: TextView
        val txtBirthday: TextView
        val txtAddress: TextView
        val BTN_DELETE: ImageView
        val BTN_UPDATE: Button
        txtName = holder.itemView.findViewById(R.id.TXT_NAME) as TextView
        txtBirthday = holder.itemView.findViewById(R.id.TXT_BIRTHDAY) as TextView
        txtAddress = holder.itemView.findViewById(R.id.TXT_ADDRESS) as TextView
        BTN_DELETE = holder.itemView.findViewById(R.id.BTN_DELETE) as ImageView
        BTN_UPDATE = holder.itemView.findViewById(R.id.BTN_UPDATE) as Button
        if (holder.itemViewType == VIEW_TYPE_DATA) {
            val resultItem = resultTransaction[position]
            txtName.text = resultItem.name
            txtBirthday.text = resultItem.birthday
            txtAddress.text = resultItem.address

            BTN_DELETE.setOnClickListener {
                try {
                    activity.DELETE_DATA(resultItem.id)
                } catch (e: Exception) {
                    Log.e("error : ", e.toString())
                }
            }
            BTN_UPDATE.setOnClickListener {
                try {
                    activity.DATA_FROM_RECYCLEVIEW(resultItem.id,resultItem.name,resultItem.address,resultItem.birthday)
                } catch (e: Exception) {
                    Log.e("error : ", e.toString())
                }
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (resultTransaction[position] == null) {
            VIEW_TYPE_PROGRESS
        } else {
            VIEW_TYPE_DATA
        }
    }

    inner class ViewHolderTransaction(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

}