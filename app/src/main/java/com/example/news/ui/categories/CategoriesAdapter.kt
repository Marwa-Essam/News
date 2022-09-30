package  com.example.news.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.news.R
import com.example.news.model.Category
import com.google.android.material.card.MaterialCardView


class CategoriesAdapter(val categories:List<Category>):RecyclerView.Adapter<CategoriesAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(
            if (viewType==LEFT_SIDED_VIEW_TYPE) R.layout.left_sided_category
            else R.layout.right_sided_category
            ,parent,false)
        return ViewHolder(view)

    }

    val LEFT_SIDED_VIEW_TYPE=10
    val RIGHT_SIDED_VIEW_TYPE=20
    override fun getItemViewType(position: Int): Int {
        if (position%2==0)
            return LEFT_SIDED_VIEW_TYPE
        return RIGHT_SIDED_VIEW_TYPE

    }


    override fun getItemCount(): Int =categories.size

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
        val item = categories[position]
        holder.title.setText(item.titleId)
        holder.image.setImageResource(item.imageResId)
        holder.materialCard.setCardBackgroundColor(holder.itemView.context.getColor(item.backgroundColorId))
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position,item)
            }
        }
    }
    var onItemClickListener:OnItemClickListener?=null
    interface OnItemClickListener{
        fun onItemClick(pos:Int,category:Category)
    }
        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.title)
            val image: ImageView = itemView.findViewById(R.id.image)
            val materialCard: MaterialCardView = itemView.findViewById(R.id.material_card)
        }

    }