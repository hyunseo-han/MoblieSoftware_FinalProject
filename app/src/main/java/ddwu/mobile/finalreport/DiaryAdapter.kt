package ddwu.mobile.finalreport

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DiaryAdapter(val db: ArrayList<DiaryDto>)
    : RecyclerView.Adapter<DiaryAdapter.DiaryViewHolder>(){

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

    lateinit var listener: OnItemClickListener

    fun setOnItemClickListener(listener: DiaryAdapter.OnItemClickListener){
        this.listener = listener
    }

    interface OnItemLongClickListener{
        fun onItemLongClick(view: View, position: Int)
    }

    lateinit var listener2: OnItemLongClickListener

    fun setOnItemLongClickListener(listener2: DiaryAdapter.OnItemLongClickListener){
        this.listener2 = listener2
    }

    override fun getItemCount() = db.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DiaryAdapter.DiaryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_view, parent, false)
        return DiaryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiaryAdapter.DiaryViewHolder, position: Int) {
        val dbHelper = db[position]

        if(position == 0) {
            holder.imageView.setImageResource(R.mipmap.paris)
        } else if (position == 1){
            holder.imageView.setImageResource(R.mipmap.cztree)
        }else if (position == 2){
            holder.imageView.setImageResource(R.mipmap.rome)
        }else if (position == 3){
            holder.imageView.setImageResource(R.mipmap.finland)
        }else if (position == 4){
            holder.imageView.setImageResource(R.mipmap.hackathon)
        }else if (position == 5){
            holder.imageView.setImageResource(R.mipmap.cherryblossom)
        }else if (position == 6){
            holder.imageView.setImageResource(R.mipmap.mt)
        } else {
            holder.imageView.setImageResource(R.mipmap.finland)
        }
        holder.bind(dbHelper)
    }


    inner class DiaryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        val locationTextView: TextView = itemView.findViewById(R.id.tv_location)
        val dateTextView: TextView = itemView.findViewById(R.id.tv_date)
        val imageView: ImageView = itemView.findViewById(R.id.iv_thumbnail)

        fun bind(db: DiaryDto){
            titleTextView.text = db.title
            locationTextView.text = db.location
            dateTextView.text = db.date
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(itemView, adapterPosition)
            }

            itemView.setOnLongClickListener{
                listener2.onItemLongClick(itemView, adapterPosition)
                true
            }
        }
    }
}