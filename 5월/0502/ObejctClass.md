# Object Class vs View Model

`keyword` = 안드로이드 메모리 누수 패턴

https://blog.kmshack.kr/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C%EC%9D%98-%EB%A9%94%EB%AA%A8%EB%A6%AC-%EB%88%84%EC%88%98-%ED%8C%A8%ED%84%B4/

1. Obejct Class는 Static에 저장합니다.

2. Static 메모리에 저장한다는 것은, Heap이 아닌 곳에 데이터를 저장한 다는 것으로, 삭제되지 않는 데이터를 쌓는 다는 것을 의미합니다.

3. 따라서, View Model이 필요해서 생긴 것이 아니라, Object Class의 사용 자체가 지양되고 써서는 안되는 방법이기 때문에, 더 자유롭고 안전한 View Model을 사용하는 것이 옳다.

4. 예전에는 안드로이드 스태틱 변수를 쓰지 말라는 가이드 라인이 있었습니다.

5. 스태틱 변수의 누수를 쉽게 설명하는 자료가 별도로 있으니 찾아보면 좋습니다.

6. 스택이 크고, 힙이 작은데 거기에 메모리를 엄청 사용하는 방향으로 넣어버리면 애초에 근본과 다른 잘못된 방향이다!

7. " 가비지 콜렉션 " 이 발생해서 누수를 제거해주는 안드로이드 시스템이 동작하지 못하도록 막는 잘못된 코딩 방법이다.

```kotlin
object DB {
    var count = 0
}
```

```java
package com.ssafy.jetpack;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(
   mv = {1, 6, 0},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b¨\u0006\t"},
   d2 = {"Lcom/ssafy/jetpack/DB;", "", "()V", "count", "", "getCount", "()I", "setCount", "(I)V", "live_data_4_debug"}
)
public final class DB {
   private static int count;
   @NotNull
   public static final DB INSTANCE;

   public final int getCount() {
      return count;
   }

   public final void setCount(int var1) {
      count = var1;
   }

   private DB() {
   }

   static {
      DB var0 = new DB();
      INSTANCE = var0;
   }
}
```
