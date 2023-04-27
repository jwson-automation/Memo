package com.ssafy.a3_recylerview_swipe

import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore.Audio.Playlists.Members.moveItem
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import java.util.*

private const val TAG = "HelloListView_싸피"
class HelloListView : AppCompatActivity(){
    private val adapter: MyAdapter by lazy {
        MyAdapter()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = COUNTRIES
        // submitList로 데이터를 제공한다.
        // 기존의 목록과 새로운 목록을 비교하기 때문에, 두 목록의 reference는 달라야 한다. toMutableList로 새로 생성.
        adapter.submitList(data.toMutableList())
        val rview = findViewById<RecyclerView>(R.id.recyclerView)
        rview.layoutManager = LinearLayoutManager(this,  LinearLayoutManager.VERTICAL, false)
        rview.adapter = adapter

        //구분선 추가.
        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        rview.addItemDecoration(dividerItemDecoration)

        adapter.itemClickListener = object : MyAdapter.ItemClickListener {
            override fun onClick(view: View, data: String, position: Int) {
                Toast.makeText(this@HelloListView, "item clicked...${data}", Toast.LENGTH_SHORT).show()
            }
        }

        adapter.deleteListener = object : MyAdapter.DeleteListener {
            override fun delete(position: Int) {
                Log.d(TAG, "delete: $position")
                data.removeAt(position)
                //data 의 reference가 바뀌어야 ListAdapter가 워킹함.
                adapter.submitList(data.toMutableList())
                //adapter.notifyDataSetChanged() //필요없어짐. 
            }
        }

        val itemTouchCallBack = ItemTouchHelper(MyAdapter.ItemTouchCallBack())
        itemTouchCallBack.attachToRecyclerView(rview)
    }


    override fun onContextItemSelected(item: MenuItem): Boolean {
        if(item.itemId == 1){
            Toast.makeText(this, "contextmenu selected -  firstItem : ${item}", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "contextmenu selected", Toast.LENGTH_SHORT).show()
        }
        return super.onContextItemSelected(item)
    }

    //ListAdapter에서 목록관리하므로, collection으로 받을 필요 없음.
    class MyAdapter : ListAdapter<String, MyAdapter.CustomViewHolder>(StringComparator){
        companion object StringComparator: DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                // Item의 id 값으로 비교한다. object에 id가 있으면 그 id를 사용하여 비교.
                return System.identityHashCode(oldItem) == System.identityHashCode(newItem)
            }

        }
        inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnCreateContextMenuListener {
            init{
                //long click 하면 contextmenu가 동작하게 되어 화면이 이상하게 보일 수 있음.
                itemView.setOnCreateContextMenuListener(this)
            }

            var name:TextView = itemView.findViewById<TextView>(R.id.name)
            fun bindInfo(data:String){
                name.setText(data)
                itemView.setOnClickListener{
                    itemClickListener.onClick(it, data, layoutPosition )
                }
            }

            override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
                val selected = itemView.findViewById<TextView>(R.id.name).text.toString()
                Log.d(TAG, "onCreateContextMenu: ${selected}")

                val menuItem = menu.add(0, 0 , 0, "delete");
                menuItem?.setOnMenuItemClickListener {
                    deleteListener.delete(layoutPosition)
                    Toast.makeText(itemView.context, "Hello:$selected", Toast.LENGTH_LONG).show()
                    true
                }
            }
        }

        inner class ItemTouchCallBack():ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                moveItem(viewHolder.layoutPosition,target.layoutPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                removeItem(viewHolder.layoutPosition)
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                when(actionState){
                    ItemTouchHelper.ACTION_STATE_DRAG,ItemTouchHelper.ACTION_STATE_SWIPE ->
                        (viewHolder as MyAdapter.CustomViewHolder).setBackground(Color.LTGRAY)

                }
            }

            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)
                (viewHolder as MyAdapter.CustomViewHolder).setBackground(Color.WHITE)
            }
        }

        interface ItemClickListener{
            fun onClick( view:View, data:String, position:Int)
        }

        lateinit var itemClickListener: ItemClickListener

        interface DeleteListener{
            fun delete( position:Int)
        }

        lateinit var deleteListener: DeleteListener

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CustomViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
            Log.d(TAG, "inflate")

            return CustomViewHolder(view)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.apply{
                //getItem : 위치의 값을 가져옴.
                bindInfo(getItem(position))
            }
        }

        // 자리이동.현재 데이터 가져와서 바꿔치기한다. 위치가 한칸씩 바뀔때 마다 호출된다.
        fun moveItem(fromPosition:Int, toPosition:Int){
            val newList = currentList.toMutableList()
            Collections.swap(newList,fromPosition,toPosition)
            submitList(newList)
        }

        // 삭제. 해당위치의 아이템을 삭제한다.
        fun removeItem(position: Int){
            val newList = currentList.toMutableList()
            newList.removeAt(position)
            submitList(newList)
        }
    }


    companion object {
        val COUNTRIES = arrayListOf(
            "Afghanistan", "Albania",
            "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
            "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia",
            "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
            "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin",
            "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
            "Botswana", "Bouvet Island", "Brazil",
            "British Indian Ocean Territory", "British Virgin Islands",
            "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cote d'Ivoire",
            "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
            "Central African Republic", "Chad", "Chile", "China",
            "Christmas Island", "Cocos (Keeling) Islands", "Colombia",
            "Comoros", "Congo", "Cook Islands", "Costa Rica", "Croatia",
            "Cuba", "Cyprus", "Czech Republic",
            "Democratic Republic of the Congo", "Denmark", "Djibouti",
            "Dominica", "Dominican Republic", "East Timor", "Ecuador", "Egypt",
            "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",
            "Ethiopia", "Faeroe Islands", "Falkland Islands", "Fiji",
            "Finland", "Former Yugoslav Republic of Macedonia", "France",
            "French Guiana", "French Polynesia", "French Southern Territories",
            "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",
            "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala",
            "Guinea", "Guinea-Bissau", "Guyana", "Haiti",
            "Heard Island and McDonald Islands", "Honduras", "Hong Kong",
            "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
            "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
            "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos",
            "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
            "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Madagascar",
            "Malawi", "Malaysia", "Maldives", "Mali", "Malta",
            "Marshall Islands", "Martinique", "Mauritania", "Mauritius",
            "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia",
            "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia",
            "Nauru", "Nepal", "Netherlands", "Netherlands Antilles",
            "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria",
            "Niue", "Norfolk Island", "North Korea", "Northern Marianas",
            "Norway", "Oman", "Pakistan", "Palau", "Panama",
            "Papua New Guinea", "Paraguay", "Peru", "Philippines",
            "Pitcairn Islands", "Poland", "Portugal", "Puerto Rico", "Qatar",
            "Reunion", "Romania", "Russia", "Rwanda", "Sqo Tome and Principe",
            "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia",
            "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines",
            "Samoa", "San Marino", "Saudi Arabia", "Senegal", "Seychelles",
            "Sierra Leone", "Singapore", "Slovakia", "Slovenia",
            "Solomon Islands", "Somalia", "South Africa",
            "South Georgia and the South Sandwich Islands", "South Korea",
            "Spain", "Sri Lanka", "Sudan", "Suriname",
            "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland",
            "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand",
            "The Bahamas", "The Gambia", "Togo", "Tokelau", "Tonga",
            "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan",
            "Turks and Caicos Islands", "Tuvalu", "Virgin Islands", "Uganda",
            "Ukraine", "United Arab Emirates", "United Kingdom",
            "United States", "United States Minor Outlying Islands", "Uruguay",
            "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam",
            "Wallis and Futuna", "Western Sahara", "Yemen", "Yugoslavia",
            "Zambia", "Zimbabwe"
        )
    }
}