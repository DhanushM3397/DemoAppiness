package com.example.demoappiness

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoappiness.adapter.CustomAdapter
import com.example.demoappiness.modelclasss.Heirarchy
import com.example.demoappiness.modelclasss.ModelClass
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var listroot: List<Heirarchy> = ArrayList<Heirarchy>()
    private var recyclerView: RecyclerView? = null

    lateinit var customAdapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recyclerView = findViewById(R.id.recyclerView)
        loginDetails()

    }

    private fun loginDetails() {
        val retroClient = RetroClient()
        val api = retroClient.apiService
        api.getleaddata().enqueue(object : Callback<ModelClass?> {
            override fun onResponse(call: Call<ModelClass?>, response: Response<ModelClass?>) {
                if (response.isSuccessful()) {
                    assert(response.body() != null)
                    listroot =
                        response.body()?.dataObject?.get(0)?.myHierarchy?.get(0)?.heirarchyList!!
                    customAdapter = CustomAdapter(listroot, this@MainActivity)
                    recyclerView?.setLayoutManager(LinearLayoutManager(this@MainActivity))
                    recyclerView?.setAdapter(customAdapter)
                    customAdapter.notifyDataSetChanged()

                    Toast.makeText(
                        this@MainActivity,
                        "successfully  bind the data to Recyclerview",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this@MainActivity, " Data is Not Binding to Recyclerview .. Please Check your Connection..", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ModelClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Please Check your Internet connection..", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // below line is to get our inflater
        val inflater = menuInflater

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.menu, menu)

        // below line is to get our menu item.
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView: SearchView = searchItem.getActionView() as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Heirarchy> = ArrayList()

        // running a for loop to compare elements.
        for (item in listroot) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.contactName.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            customAdapter.filterList(filteredlist)
        }
    }

}