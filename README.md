# my own spring mvc

- <a href="https://liltdevs.tistory.com/189">블로그 </a>

- Spring mvc 가 제공하는 기능을 이해하기 위한 repo 입니다.

- Reflection 을 사용하여 mapping 된 controller 를 확인합니다.
  - boilerplate 코드의 축소를 위해 Reflections 라이브러리를 사용합니다.

- 간단한 HanlderMappping 의 기능을 구현합니다.
  - Map 을 하나 생성하고 해당하는 handler 를 할당합니다.

- 기본적인 MessageConverter 의 기능을 구현합니다.
  - query parameter 를 파싱합니다.
  - http body 로 들어온 json 을 파싱합니다.
  - controller 가 return 한 map 을 json 으로 파싱하고 response 로 write 합니다.

- DispatcherServlet 의 기능을 한정적으로 구현합니다.
  - 모든 request 를 받아서 url mapping 에 따른 controller 를 실행시킵니다.
  
