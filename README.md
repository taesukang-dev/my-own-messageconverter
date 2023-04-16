# my-own-messageconverter

- DispatcherServlet 과 MessageConverter 의 기능을 이해하기 위한 repo 입니다.
- 기본적인 MessageConverter 의 기능을 구현합니다.
  - query parameter 를 파싱합니다.
  - http body 로 들어온 json 을 파싱합니다.
  - controller 가 return 한 map 을 json 으로 파싱하고 response 로 write 합니다.

- DispatcherServlet 의 기능을 한정적으로 구현합니다.
  - 모든 request 를 받아서 url mapping 에 따른 controller 를 실행시킵니다.
  