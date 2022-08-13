package com.baric.hacking_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class CveEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cve_edit)
        
        var cveName : TextView = findViewById(R.id.cveName)
        cveName.text = intent.getStringExtra("passedCveName")
        var cveDate : TextView = findViewById(R.id.cveDate)
        cveDate.text = intent.getStringExtra("passedCveDate")
        var cveDis : TextView = findViewById(R.id.cveDis)
        cveDis.text = intent.getStringExtra("passedCveDis")
        var cveUrl : TextView = findViewById(R.id.cveUrl)
        cveUrl.text = intent.getStringExtra("passedCveUrl")
        
        var openInBrowser:ImageView = findViewById(R.id.openToBrowser)
        openInBrowser.setOnClickListener(View.OnClickListener {
            val openURL = Intent(Intent.ACTION_VIEW)
            openURL.data = Uri.parse(intent.getStringExtra("passedCveUrl"))
            startActivity(openURL)
        })
        var edit:ImageView = findViewById(R.id.edit)
        edit.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@CveEditActivity, "edit not designed yet", Toast.LENGTH_SHORT).show()
        })
        var delete:ImageView = findViewById(R.id.delete)
        delete.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@CveEditActivity, "delete not designed yet", Toast.LENGTH_SHORT).show()
        })
        
    }
}