### 요구사항 1 - index.html로 접속시 응답

- java inputstream, bufferedreader 용도는 알겠으나 정확한 이해가 필요
  - stream을 쓰면 자원을 반환해야 한다고 생각해서 `close()`를 하였으나 이 때문에 response가 제대로 생성되지 않았음.
  - 추측은 response를 주기도 전에 연결이 끊겨버려서 동작하지 않은 듯 하다.. close를 response 이후에 하면 문제 없긴 함.
    - 그래도 close를 할 필요는 없나?
    - 검색 해보니 close 안 해도 알아서 가비지 컬렉터로 정리는 되지만, 명시적으로 생각할 필요는 있다네

### 나머지 요구사항

- 기능 구현은 다 했으나 bufferedreader를 통해 데이터를 읽을 때 한글 인코딩이 바뀌는 문제를 해결하지 못 함
   - 의외로 검색을 해도 결과가 잘 안 나오는데..

### 느낀점

HTTP 구조를 어느 정도 알고는 있었지만 이렇게 socket을 직접 열어서 구현하니 모든 기능을 직접 만들어야 했다.    
url 분리부터 method 이름, url 마다의 기능도 일일이 if문을 만들어서 분리해야 했다. 최소한 string 다루기, 
buffer 다루기 정도는 분리 했지만 전체 구조를 잡는 부분이 좀 어렵다. 

여기에 header, cookie, 로그인 여부 체크 등 매 상태를 유지하는 것 또한 만만치 않음. static 변수, Map 활용까지

그래도 종합적으로 자바 연습을 했음에 의의를 두자. 다음 실습으로 다형성, 프로젝트 구조 잡는 법을 더 익히자


### 보완할 점

- IOStream, Reader 좀 더 깊이있게 연습해보자. 지금은 너무 날림이다.
- 그냥 Stream, 데이터 처리도 연습 필요. 
- 리팩토링과 설계 연습. 어떻게 구조를 짜서 나누는 게 괜찮을지 생각해보기


---

### 리팩토링 - 1 : 클래스 분리

현재 프로젝트 구조는
```
│── db
│   └── DataBase.java
├── model
│   └── User.java
├── util
│   ├── HttpRequestUtils.java
│   └── IOUtils.java
└── webserver
    ├── RequestHandler.java
    └── WebServer.java
```
이렇게 4가지 패키지로 구성.  
`db`, `model`의 분리는 괜찮지만 `util`, `webserver`은 그냥 있던대로 코드를 막 짰음.   
특히 `GET, POST` 여부, `라우터 차이`에 따라 `RequestHandler`에서  
if문으로 나누는 건 너무나도 보기 않 좋고 재사용성도 없음.  

if문 분기는 기본적으로 옵션, 기능이 고도화되며 나오는 자연스러운 흐름.  
하지만 이 제어의 흐름대로 코드를 짜면 필연적으로 코드는 더러워짐.    

SOLID원칙의 **DIP**에선 이런 의존 관계를 역전시키라고 한다.  
즉 고차원, 추상을 제어흐름에서 먼저 써야함. 중간에 인터페이스를 끼워넣어야함. 

이 말이 어렵다면 제어 흐름을 먼저 관찰하자.  
`WebServer의 main -> RequestHandler`이고 이 `RequestHandler`에서 분기에 따라 수많은 처리를 담당중임  
이 처리에는 공통적으로 Request와 Response가 있음. SRP에 따라 Request, Response를 분리, 클래스화하고,   
이들을 묶는 인터페이스를 RequestHandler가 참조하게 만들자.

```
WebServer main -> RequestHandler
에서

                                               ↙ HttpRequest
WebServer main -> RequestHandler -> Controller   
                                               ↖ HttpResponse
로 바뀜
```
이렇게 의존성을 바꿔보자. 그러면 어느 정도는 다형성을 확보할 수 있음.