# 실습

## 코드읽기

아래 leaderBoard에서 userProfile로 넘어갈때

네비게이션에서 데이터를 전달하는 방법이 2가지가 있는데.

하나는 기존의 방법인 `번들`을 이용해서 navigate에 번들을 함께 담아주는 방법이고

하나는 `safeArgs방식`이다.

```kotlin
//bundle
      val bundle = bundleOf("userName" to myDataset[position])
      Navigation.findNavController(holder.item).navigate(R.id.  action_leaderboard_to_userProfile,bundle)
//Safe Args.
      val action = LeaderboardDirections.actionLeaderboardToUserProfile(myDataset[position])
      Navigation.findNavController(holder.item).navigate(action)
```

```kotlin
//bundle 방식
        val name = arguments?.getString("userName") ?: "Ali Connors"

//safe args 방식

        val args : UserProfileArgs by navArgs()
        val name = args.userName
        view.findViewById<TextView>(R.id.profile_user_name).text = name


        return view
```

## 코드

1. 번들 전달 방법

```kotlin

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.item.findViewById<TextView>(R.id.user_name_text).text = myDataset[position]

        holder.item.findViewById<ImageView>(R.id.user_avatar_image)
                .setImageResource(listOfAvatars[position])

        holder.item.setOnClickListener {
            //bundle
            val bundle = bundleOf("userName" to myDataset[position])
            Navigation.findNavController(holder.item).navigate(R.id.action_leaderboard_to_userProfile,bundle)
            //Safe Args.

        }
```

```kotlin
class UserProfile : Fragment() {
    //safe args 방식
    val args : UserProfileArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)
        //bundle 방식
//        val name = arguments?.getString("userName") ?: "Ali Connors"

        //safe args 방식
        val name = args.userName

        view.findViewById<TextView>(R.id.profile_user_name).text = name
        return view
    }
}
```
