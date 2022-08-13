package com.baric.hacking_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class cveAdapter(private val cveList:ArrayList<cveData>) :
    RecyclerView.Adapter<cveAdapter.CveViewHolder>()
{ 
    
    private lateinit var mlistener : onItemClickListener
    interface onItemClickListener{
       fun onItemClick(position: Int)
    }
    fun setOnClickListener(listener: onItemClickListener){
        mlistener = listener
    }
    
    class CveViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val imageView: ImageView = itemView.findViewById(R.id.cardImage)
        val cveName: TextView = itemView.findViewById(R.id.cardName)
        val cveDis: TextView = itemView.findViewById(R.id.cardDis)
        val cveRelease: TextView = itemView.findViewById(R.id.cardDate)
        val cveUrl: TextView = itemView.findViewById(R.id.cardUrl)
        
        init{
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CveViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        return CveViewHolder(v, mlistener)
    }

    override fun onBindViewHolder(holder: CveViewHolder, position: Int) {
        val cve = cveList[position]
        holder.imageView.setImageResource(R.drawable.lock)
        holder.cveName.text = cve.cveName
        holder.cveDis.text = cve.cveDis
        holder.cveRelease.text = cve.cveDate
        holder.cveUrl.text = cve.cveUrl
        
       
        
       
        
    }

    override fun getItemCount(): Int {
        return cveList.size
    }

}


