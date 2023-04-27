# XML API

XML은 2000년대 초반에 인기가 많았습니다.
이번 실습은 레트로핏을 바닐라하게 해본다고 생각하면 됩니다.

## RSS

```
RSS는 Rich Site Summary, Really Simple Syndication, RDF Site Summary 등으로 불리며, 웹 사이트에서 제공하는 콘텐츠를 정기적으로 수집하여 한 곳에서 모아 볼 수 있는 기술입니다.

RSS는 XML 기반으로 작성되며, 블로그, 뉴스, 게시판 등의 컨텐츠에서 자주 사용됩니다. 웹 사이트에서 RSS 피드를 제공하면, 사용자는 RSS 리더기라는 프로그램을 사용하여 웹 사이트의 새로운 콘텐츠를 구독할 수 있습니다. RSS 리더기는 주기적으로 피드를 확인하여 새로운 콘텐츠가 있으면 사용자에게 알림을 줍니다.

RSS를 사용하면, 여러 웹 사이트에서 제공하는 다양한 콘텐츠를 한 곳에서 효율적으로 모아 볼 수 있습니다. 또한, 사용자는 자신이 관심 있는 콘텐츠만 선택하여 구독할 수 있으므로, 많은 정보 중에서 필요한 정보만 선택적으로 수신할 수 있습니다. 이러한 장점 때문에 RSS는 인터넷에서 정보를 수집하고 관리하는 데 매우 유용한 기술로 평가받고 있습니다.
```

## SAX, DOM

```
SAX와 DOM은 XML 문서를 파싱하기 위한 두 가지 방법입니다.

DOM(Document Object Model)은 XML 문서의 모든 내용을 메모리에 로드하고, 트리 형태로 표현합니다. 이러한 트리 구조를 통해 문서 내의 요소와 속성들에 대한 접근이 용이해집니다. 하지만, 대용량의 XML 문서를 처리하는 경우에는 메모리 부족으로 인한 성능 저하가 발생할 수 있습니다.

SAX(Simple API for XML)는 XML 문서를 한 줄씩 읽어서 처리하는 방법입니다. DOM과는 달리 문서 전체를 로드하지 않으므로 대용량의 XML 문서를 처리하는 데 용이합니다. SAX는 이벤트 기반으로 작동하며, 문서의 각 요소나 속성 등의 이벤트가 발생할 때마다 이벤트를 처리하는 핸들러를 호출합니다. 이러한 이벤트 핸들러를 통해 XML 문서의 내용을 처리할 수 있습니다.

요약하자면, DOM은 XML 문서 전체를 메모리에 로드하고, SAX는 한 줄씩 읽어서 처리하는 방식입니다. 대용량의 XML 문서를 처리하는 경우에는 SAX가 유리하며, 작은 크기의 XML 문서를 처리하는 경우에는 DOM이 유리할 수 있습니다.
```

## Virtual DOM vs DOM

```
버추얼 DOM과 그냥 DOM은 다릅니다.

DOM(Document Object Model)은 HTML, XML 등의 문서를 계층 구조로 표현한 객체 모델을 말합니다. DOM은 문서 구조가 변경될 때마다 이를 다시 렌더링하기 위해 브라우저가 화면을 갱신하는데, 이 과정에서 렌더링 성능 저하가 발생할 수 있습니다.

이러한 문제를 해결하기 위해 등장한 것이 가상 DOM(버추얼 DOM)입니다. 가상 DOM은 메모리 상에 존재하는 가상의 DOM 구조로, 브라우저가 렌더링하기 전에 이를 조작하여 필요한 부분만 렌더링합니다. 이를 통해 DOM 조작으로 인한 불필요한 렌더링을 최소화하여 성능을 향상시킬 수 있습니다.

즉, DOM은 문서 구조를 표현하는 객체 모델이고, 가상 DOM은 이를 조작하여 렌더링 성능을 개선하기 위한 기술입니다.
```

## 정리

```
DOM : XML 또는 HTML 문서 전체를 메모리에 로드
SAX : 이벤트가 발생할 때 마다 해당 이벤트에 대한 콜백
가상DOM : 화면에 보이는 부분만 렌더링

DOM과 SAX의 좋은 부분만 섞은게 가상 DOM 이구나
```

## XML Parser 1

`fold`를 사용해서 하나씩 방문하면서 값을 가져옵니다.

```kotlin
fun main() {
    var list  = arrayOf(1,2,3,4,5)

    println(list.fold(0) { acc: Int, i: Int -> acc + i }) // 1+2+3+4+5
    println(list.fold(3) { acc: Int, i: Int -> acc + i }) // 3+ 1+2+3+4+5  초기값 3

    // reduce: fold와 동일하나 초기값 사용하지 않음
    println(list.reduce { acc, i -> acc + i })
    println(list.reduceRight { acc,i -> acc + i })

    println(list.filter { it % 2 == 0 }) // 짝수만 골라내기
    println(list.filterNot { it % 2 == 0 }) // 식 이외에 요소

    // forEach: 각 요소를 람다식으로 처리
    list.forEach { print("$it") }
    println()
    list.forEachIndexed { i, value -> println("index[$i]: $value") } // 인덱스 포함
}
```

```kotlin
private fun loadAssets() {
        try {
            //open에서 읽을겁니다.
            members = MyAddressParser().parse(assets.open("address.xml"))
            //members = list
            //flod, [pre,new] 하면서 전체를 모두 순회하는 것
            statusTV.text = members.fold("") { pre, new ->
                """$pre
                            |이름: ${new.name}
                            |전화번호: ${new.phone}
                            |
                            |""".trimMargin()
            }
        } catch (e: Exception) {
            Log.e(TAG, "onCreate: assets 파일 로딩 실패", e)
        }
    }
```

XML을 실제로 비교하는 코드는 아래입니다.

```kotlin
package com.ssafy.xml.b1_xml

import android.util.Log
import android.util.Xml
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException
import java.io.InputStream

data class Member(var name: String) {
    val phone: MutableMap<String, String> = mutableMapOf()
}

private const val TAG = "MyAddressParser_싸피"
class MyAddressParser {
    // name space: 문서에는 없지만 api에서 계속 사용
    private val ns: String? = null

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Member> {
        inputStream.use {
            val parser = Xml.newPullParser()
            // 파서 내에서 namespace를 사용하지 않음
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(it, "UTF-8")
            // 내부적으로 next() 를 호출하고 START_TAG나 END_TAG를 만나면 event 발생, 만약 문제 발생 시 XmlPullParserException 발생
            parser.nextTag()
            return readAddress(parser)
        }
    }
    @Throws(XmlPullParserException::class, IOException::class)
    fun readAddress(parser: XmlPullParser): List<Member> {
        val addresses = mutableListOf<Member>()
        // 현재의 이벤트가 "주소록"이라는 태그의 시작점이라면.
        parser.require(XmlPullParser.START_TAG, ns, "주소록")
        // 다음 이벤트가 END_TAG가 아닐 때까지 읽어들임. 찾는 내용은 회원이라는 태그를 읽은 시점(시작)
        while (parser.next() != XmlPullParser.END_TAG) {
            // START_TAG가 아니면 다음 이벤트로 이동
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            // 태그의 이름이 회원이면 상세 회원 정보 획득을 위해 파싱 진행
            if (parser.name == "회원") {
                addresses.add(readMember(parser))
            }
            // 혹시 다른 태그라면 생략
            else {
                skip(parser)
            }
        }
        return addresses
    }

    @Throws(XmlPullParserException::class, IOException::class)
    fun readMember(parser: XmlPullParser): Member {
        // 이제 회원 태그가 시작 태그
        parser.require(XmlPullParser.START_TAG, ns, "회원")
        var member: Member? = null
        // 다음으로 읽은 태그가 닫는 태그가 아닐때까지.
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "name" -> member = Member(readName(parser))
                "phone" -> readPhone(parser, member!!)
                else -> skip(parser)
            }
        }
        return member!!
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readName(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "name")
        val tagTextValue = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "name")
        return tagTextValue
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun readPhone(parser: XmlPullParser, member: Member) {
        // 시작 태그는 phone으로
        parser.require(XmlPullParser.START_TAG, ns, "phone")
        // xml은 순서대로 읽기 때문에 type을 먼저 읽고 text 처리
        val type = parser.getAttributeValue(null, "type")
        val num = readText(parser)
        member.phone.put(type, num)
        parser.require(XmlPullParser.END_TAG, ns, "phone")
    }

    // 태그에서 text만 추출해내기
    @Throws(IOException::class, XmlPullParserException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    //
    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        // START_TAG가 아닌 경우 예외 발생
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        // 하위로 중첩된 태그의 처리를 위해서 flag로 depth 사용
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
```

지금 위 코드를 보면 Text를 하나하나 지정해서 파싱을 했지만,
저걸 Dto로 바꿔서 통일하면 라이브러리 하나로 가능하지 않을까?
그게 바로 Retrofit입니다.

## XML Parsing 2

```Kotlin
private fun parsing(reader: Reader) {
            parser.setInput(reader)
            var eventType = parser.eventType
            var item: HaniItem2? = null
            var id:Long = 0
            while (eventType != XmlPullParser.END_DOCUMENT) {
                var name: String? = null
                when (eventType) {
                    XmlPullParser.START_DOCUMENT -> {}
                    XmlPullParser.START_TAG -> {
                        name = parser.name
                        if (name.equals("item", ignoreCase = true)) {
                            item = HaniItem2()
                            item.id = ++id
                        } else if (item != null) {
                            if (name.equals("title", ignoreCase = true)) {
                                item.title = parser.nextText()
                            } else if (name.equals("link", ignoreCase = true)) {
                                item.link = parser.nextText()
                            } else if (name.equals("description", ignoreCase = true)) {
                                item.description = parser.nextText()
                            } else if (name.equals("pubDate", ignoreCase = true)) {
                                item.pubDate = Date(parser.nextText())
                            } else if ("dc".equals( parser.prefix, ignoreCase = true) ) {   // namespace
                                if (name.equals("subject", ignoreCase = true)) {
                                    item.subject = parser.nextText()
                                } else if (name.equals("category", ignoreCase = true)) {
                                    item.category = parser.nextText()
                                }
                            }
//                            else if (name.equalsIgnoreCase("subject")) { // namespace를 무시해도 이슈없음.
//                                item.subject = parser.nextText();
//                            }
                        }
                    }
                    XmlPullParser.END_TAG -> {
                        name = parser.name
                        if (name.equals("item", ignoreCase = true) && item != null) {
                            list.add(item)
                        }
                    }
                }
                eventType = parser.next()
            }
        }
```
