// 데이터 모델 클래스
data class Fruit(val name: String, val image: Int)

// 액티비티 클래스
class MainActivity : AppCompatActivity() {

    // 데이터 리스트 생성
    private val fruitList = listOf(
        Fruit("사과", R.drawable.apple),
        Fruit("바나나", R.drawable.banana),
        Fruit("체리", R.drawable.cherry),
        Fruit("포도", R.drawable.grape),
        Fruit("오렌지", R.drawable.orange),
        Fruit("파인애플", R.drawable.pineapple),
        Fruit("수박", R.drawable.watermelon)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 어댑터 생성
        val adapter = FruitAdapter(this, R.layout.fruit_item, fruitList)

        // ListView 설정
        val listView: ListView = findViewById(R.id.listView)
        listView.adapter = adapter
    }
}

// 어댑터 클래스
class FruitAdapter(context: Context, private val resourceId: Int, private val data: List<Fruit>)
    : ArrayAdapter<Fruit>(context, resourceId, data) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder

        // 재사용 가능한 뷰가 있는 경우
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(resourceId, parent, false)
            viewHolder = ViewHolder()
            viewHolder.fruitImage = view.findViewById(R.id.fruitImage)
            viewHolder.fruitName = view.findViewById(R.id.fruitName)
            view.tag = viewHolder // 뷰 홀더를 저장
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder // 저장된 뷰 홀더를 사용
        }

        // 데이터 설정
        val fruit = data[position]
        viewHolder.fruitImage.setImageResource(fruit.image)
        viewHolder.fruitName.text = fruit.name

        return view
    }

    // 뷰 홀더 클래스
    private class ViewHolder {
        lateinit var fruitImage: ImageView
        lateinit var fruitName: TextView
    }
}
