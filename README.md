### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답

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
