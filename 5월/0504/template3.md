# 실습 3

## 실습 하기

RecyclereView > ListView

- notifyDataSetChanged를 호출 할 필요가 없어졌습니다.
- DiffUtil의 itemCallBack을 줘서 알아서 비교하도록 해줍니다

## 코드 읽기

- viewModel과 함께 사용 할 때, `ListAdapter.submitList()` 를 해주면 변화를 알아서 찾아준다.
  ( 이전에는 Mutable을 따로 만들어 줘야 했습니다. )

- 그 후에 아예 viewModel을 DataBinding 해서 recyclerview에 집어 넣어버립니다.

- 이렇게 되면 observer도 xml이 해주도록 보낼 수 있다.

`옵저브 -> 서브밋리스트`

`xml 데이터 변경 -> 자동 갱신(데이터바인딩) -> adapter의 데이터 변경 -> 변경된 정보가 리스트 어댑터에서 변경 -> 새로 갱신되어서 뿌려짐`

## 코드

변경 전

```kotlin
private const val TAG = "SearchFragment_싸피"
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()

    //adapter구성
    var mAdapter = MyAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        //adapter 세팅
        binding.searchFragRecyclerView.apply{
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        // IME 키패드에서 검색 버튼 클릭 이벤트
        binding.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> { // 검색버튼 클릭
                    val keyword = binding.searchFragEt.text.toString()
                    showLoadingDialog(requireContext())
                    searchViewModel.searchBoard(keyword)

                    //keyboard  내리기
                    val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.searchFragEt.getWindowToken(), 0)

                    true
                }

                else -> false //기타키 동작 없음.
            }
        }
    }
    private fun registerObserver(){
        searchViewModel.searchResult.observe(viewLifecycleOwner){
            if(it.size > 0){
                Log.d(TAG, "registerObserver: $it")
                mAdapter.list = it
                mAdapter.notifyDataSetChanged()
            }
            dismissLoadingDialog()
        }
    }


    class MyAdapter: RecyclerView.Adapter<MyAdapter.CustomViewHolder>(){
        var list = mutableListOf<SearchResult>()

        // no databinding
        class CustomViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bindInfo(data: SearchResult) {
                itemView.findViewById<TextView>(R.id.no).text = data.no.toString()
                itemView.findViewById<TextView>(R.id.title).text = data.title
                itemView.findViewById<TextView>(R.id.regDate).text = data.regtime
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
            return CustomViewHolder(view)
        }
        // no databinding

        // databinding
//        class CustomViewHolder (private val binding: ListItemDatabindingBinding): RecyclerView.ViewHolder(binding.root) {
//            fun bindInfo(data: SearchResult){
//                binding.apply{
//                    searchResult = data
//                }
//            }
//        }
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
//            val binding:ListItemDatabindingBinding = ListItemDatabindingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//
//            return CustomViewHolder(binding).apply{
//                binding.root.setOnClickListener{
//                    Toast.makeText(parent.context, "onCreateViewHolder: adapterPosition:${adapterPosition}, layoutPosition: ${layoutPosition}", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
        // databinding

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: ${list.get(position)}")
            holder.bindInfo(list.get(position))
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }
}

```

변경 후

```kotlin
class SearchFragmentWithListAdapter : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    private val searchViewModel: SearchViewModel by viewModels()

    //adapter구성
    var mAdapter = MyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        //adapter 세팅
        binding.searchFragRecyclerView.apply{
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        // IME 키패드에서 검색 버튼 클릭 이벤트
        binding.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> { // 검색버튼 클릭
                    val keyword = binding.searchFragEt.text.toString()
                    showLoadingDialog(requireContext())
                    searchViewModel.searchBoard(keyword)

                    //keyboard  내리기
                    val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.searchFragEt.getWindowToken(), 0)

                    true
                }

                else -> false //기타키 동작 없음.
            }
        }
    }
    private fun registerObserver(){
        searchViewModel.searchResult.observe(viewLifecycleOwner){
            if(it.size > 0){
                Log.d(TAG, "registerObserver: $it")
                //데이터 변화가 있으면 submitList 한다.
                mAdapter.submitList(it)
//                mAdapter.list = it
//                mAdapter.notifyDataSetChanged()
            }
            dismissLoadingDialog()
        }
    }

    class MyListAdapter: ListAdapter<SearchResult, MyListAdapter.CustomViewHolder>(SearchResultComparator){
//        var list = mutableListOf<SearchResult>()
        companion object SearchResultComparator: DiffUtil.ItemCallback<SearchResult>() {
            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return oldItem.no == newItem.no
            }
        }

        class CustomViewHolder (val binding: ListItemDatabindingBinding): RecyclerView.ViewHolder(binding.root) {

            fun bindInfo(data: SearchResult){
                binding.apply{
                    searchResult = data
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val binding:ListItemDatabindingBinding = ListItemDatabindingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return CustomViewHolder(binding).apply{
                binding.root.setOnClickListener{
                    Toast.makeText(parent.context, "onCreateViewHolder: adapterPosition:${adapterPosition}, layoutPosition: ${layoutPosition}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//            Log.d(TAG, "onBindViewHolder: ${list.get(position)}")
//            holder.bindInfo(list.get(position))
            Log.d(TAG, "onBindViewHolder: ${getItem(position)}")
            holder.bindInfo(getItem(position))
        }

//        override fun getItemCount(): Int {
//            return list.size
//        }
    }
}

```

마지막

```kotlin
private const val TAG = "SearchFragment_싸피"
class SearchFragmentWithListAdapterAndDataBinding : BaseFragment<FragmentSearchDatabindingBinding>(FragmentSearchDatabindingBinding::bind, R.layout.fragment_search_databinding) {

    private val searchViewModel: SearchViewModel by viewModels()

    //adapter구성
    var mAdapter = MyListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        registerObserver() // databinding이므로 observe 필요없음.

        // !! 필수 !!
        // databinding의 lifycycle을 현재 fragment의 viewModel과 lifecyle을 맞춤
        binding.lifecycleOwner = viewLifecycleOwner

        //binding에 viewModel assign!!
        binding.viewModel = searchViewModel

        //adapter 세팅
        binding.searchFragRecyclerView.apply{
            this.adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        }

        // IME 키패드에서 검색 버튼 클릭 이벤트
        binding.searchFragEt.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> { // 검색버튼 클릭
                    val keyword = binding.searchFragEt.text.toString()
//                    showLoadingDialog(requireContext())
                    searchViewModel.searchBoard(keyword)

                    //keyboard  내리기
                    val imm = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(binding.searchFragEt.getWindowToken(), 0)

                    true
                }

                else -> false //기타키 동작 없음.
            }
        }
    }
    //databinding되어 있으므로.. 필요없어짐.
//    private fun registerObserver(){
//        searchViewModel.searchResult.observe(viewLifecycleOwner){
//            if(it.size > 0){
//                Log.d(TAG, "registerObserver: $it")
//                mAdapter.submitList(it)
////                mAdapter.list = it
////                mAdapter.notifyDataSetChanged()
//            }
//            dismissLoadingDialog()
//        }
//    }

    class MyListAdapter: ListAdapter<SearchResult, MyListAdapter.CustomViewHolder>(SearchResultComparator){
        companion object SearchResultComparator: DiffUtil.ItemCallback<SearchResult>() {
            override fun areItemsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: SearchResult, newItem: SearchResult): Boolean {
                return oldItem.no == newItem.no
            }
        }

        class CustomViewHolder (val binding: ListItemDatabindingBinding): RecyclerView.ViewHolder(binding.root) {

            fun bindInfo(data: SearchResult){
                binding.apply{
                    searchResult = data
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            val binding:ListItemDatabindingBinding = ListItemDatabindingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

            return CustomViewHolder(binding).apply{
                binding.root.setOnClickListener{
                    Toast.makeText(parent.context, "onCreateViewHolder: adapterPosition:${adapterPosition}, layoutPosition: ${layoutPosition}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            Log.d(TAG, "onBindViewHolder: ${getItem(position)}")
            holder.bindInfo(getItem(position))
        }
    }
}

```
