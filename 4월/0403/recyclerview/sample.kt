package com.ssafy.ui.a4_listview_recylerview

import android.content.ClipData.Item
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.ui.a4_listview_recylerview.databinding.ActivityMainBinding
import com.ssafy.ui.a4_listview_recylerview.databinding.ItemRowBinding


private const val TAG = "HelloListView_싸피"
class HelloListView : AppCompatActivity(){
    val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//      setContentView(R.layout.activity_main)
        setContentView(binding.root)

        val adapter = MyAdapter(COUNTRIES)
//        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        //아래 한줄은 xml에서 해도되고, 자바에서 해도됩니다.
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.adapter = adapter

        val deco = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        binding.recyclerView.addItemDecoration(deco)

        adapter.itemClickListener = object : MyAdapter.ItemClickListener{
            override fun onClick(view:View, position: Int, data:String) {
                Toast.makeText(this@HelloListView,"선택됨...${position},${data}",Toast.LENGTH_SHORT).show()

            }

        }

    }

    class MyAdapter(var list : ArrayList<String>) : RecyclerView.Adapter<MyAdapter.CustomViewHolder>() {
            // class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            inner class CustomViewHolder(binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener{
                init{
                    itemView.setOnCreateContextMenuListener(this)
                }
            private var name = binding.name
            // [아래는 추가적으로 만드는 것] private 대신에 아래처럼 setter만들어줌
            fun bindInfo(data:String){
                name.text = data
                itemView.setOnClickListener{
                    itemClickListener.onClick(it, layoutPosition, name.text.toString())
                }
            }
                override fun onCreateContextMenu(
                    menu: ContextMenu?,
                    p1: View?,
                    p2: ContextMenu.ContextMenuInfo?
                ) {
                    // java로 달기, xml로 달기 2가지 방법이 있습니다.
                    val menuItem = menu?.add(0,0,0,"context menu...")
                    val selected = name.text

                    menuItem?.setOnMenuItemClickListener {
                        Toast.makeText(itemView.context,selected, Toast.LENGTH_SHORT).show()

                        false
                        // true : 여기서 끝, false : 다음 진행
                    }

//                    Log.d(TAG, "onCreateContextMenu: $selected")
                }

            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            // view를 생성 -> holder의 파라미터로 넣어줌. 바로 위에 보면 class가 그렇게 생겼잖아
//            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent,false)
            // atatchToRoot : <ListView> 여기에 넣을까요? 라는 뜻! </ListView>
            val binding: ItemRowBinding = ItemRowBinding.inflate(LayoutInflater.from(parent.context))
            return CustomViewHolder(binding)
        }

        //외부 주입용 interface , ( inner를 써줘야함 위에 )

        lateinit var itemClickListener: ItemClickListener

        interface ItemClickListener{
            fun onClick(view:View, position: Int, data:String)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            // 데이터와 ViewHOlder를 연결해주는 친구임
            // holder는 위에 만든 CustomViewHolder임

            // 그냥 고치고 싶으면 아래 방법을 사용!
            // holder.name.text = list[position]
            //세터처럼 쓰고 싶으면 아래 방법을 사용!
            holder.bindInfo(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
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