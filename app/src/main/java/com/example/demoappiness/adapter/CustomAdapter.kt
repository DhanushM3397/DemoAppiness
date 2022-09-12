package com.example.demoappiness.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappiness.R
import com.example.demoappiness.modelclasss.Heirarchy

class CustomAdapter(private var mList: List<Heirarchy>,context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    var context:Context=context

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_adapter, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    @SuppressLint("IntentReset")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.tx_name.text=ItemsViewModel.contactName
        holder.tx_designation.text = ItemsViewModel.designationName
        holder.tx_phone.setOnClickListener {

            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + ItemsViewModel.contactNumber)
            context.startActivity(dialIntent)



        }
        holder.tx_message.setOnClickListener {
            val smsintent=Intent(Intent.ACTION_SENDTO)
            smsintent.addCategory(Intent.CATEGORY_DEFAULT)
            smsintent.type = "vnd.android-dir/mms-sms"
            smsintent.data = Uri.parse("sms:"+ItemsViewModel.contactNumber)
            context.startActivity(smsintent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val tx_name = itemView.findViewById<TextView?>(R.id.manager_name)
        val tx_designation = itemView.findViewById<TextView?>(R.id.manager_designation)
        val tx_phone=itemView.findViewById<TextView>(R.id.tx_phone)
        val tx_message=itemView.findViewById<TextView>(R.id.tx_message)

    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: ArrayList<Heirarchy>) {
        // below line is to add our filtered
        // list in our course array list.
        mList = filterlist
        notifyDataSetChanged()
    }
}