package com.baric.hacking_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var cveList: ArrayList<cveData>
    private lateinit var searchList : ArrayList<cveData>
    private lateinit var cveAdapter: cveAdapter
    private lateinit var dbRef : DatabaseReference
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        cveList = ArrayList()
        searchList = ArrayList()
        
        getCveData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        
        menuInflater.inflate(R.menu.menu_item, menu)
        val item = menu?.findItem(R.id.searchAction)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Toast.makeText(this@MainActivity, "Search already current", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                searchList.clear()
                val searchtext = p0!!.toLowerCase(Locale.getDefault())
                if(searchtext.isNotEmpty()){
                    cveList.forEach{
                        if(it.cveDate.lowercase().contains(searchtext) ||
                            it.cveName!!.lowercase().contains(searchtext) ||
                            it.cveDis!!.lowercase().contains(searchtext) ){
                            
                            searchList.add(it)
                        }
                    }
                    
                    recyclerView.adapter!!.notifyDataSetChanged()
                }else{
                    searchList.clear()
                    searchList.addAll(cveList)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
                
                return false
            }

        })
        
        return super.onCreateOptionsMenu(menu)
    }
    
    private fun getCveData() {
        dbRef = FirebaseDatabase.getInstance().getReference("restricted_access/secret_document")
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(cveDta in snapshot.children){
                        for(c in cveDta.children) {
                            val cdb = c.getValue(cveData::class.java)
                            cveList.add(cdb!!)
                            //Log.d("TAG", cveList.toString())
                        }  
                    }
                    
                    searchList.addAll(cveList)
                    //cveList.sortBy { it.cveDate }
                    cveAdapter = cveAdapter(searchList)
                    recyclerView.adapter = cveAdapter
                    cveAdapter.setOnClickListener(object : cveAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            Toast.makeText(this@MainActivity, searchList[position].cveName, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@MainActivity,CveEditActivity::class.java) 
                            intent.putExtra("passedCveName", searchList[position].cveName)
                            intent.putExtra("passedCveDate", searchList[position].cveDate)
                            intent.putExtra("passedCveDis", searchList[position].cveDis)
                            intent.putExtra("passedCveUrl", searchList[position].cveUrl)
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                
            }

        })
    }
}