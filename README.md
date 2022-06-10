### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답

- java inputstream, bufferedreader 용도는 알겠으나 정확한 이해가 필요
  - stream을 쓰면 자원을 반환해야 한다고 생각해서 `close()`를 하였으나 이 때문에 response가 제대로 생성되지 않았음.
  - 추측은 response를 주기도 전에 연결이 끊겨버려서 동작하지 않은 듯 하다.. close를 response 이후에 하면 문제 없긴 함.
    - 그래도 close를 할 필요는 없나?
    - 검색 해보니 close 안 해도 알아서 가비지 컬렉터로 정리는 되지만, 명시적으로 생각할 필요는 있다네


### 요구사항 2 - get 방식으로 회원가입
* 

### 요구사항 3 - post 방식으로 회원가입
* 

### 요구사항 4 - redirect 방식으로 이동
* 

### 요구사항 5 - cookie
* 

### 요구사항 6 - stylesheet 적용
* 

### heroku 서버에 배포 후
* 