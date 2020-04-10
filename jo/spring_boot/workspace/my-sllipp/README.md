# 게시판 기능 구현

## HTML page

첫 시작 화면인 index.html  

상단 네비게이션바를 통해 회원가입 진행 가능  

회원가입은 form.html에서 진행된다.

form테그 내부에서 정보를 입력 받고
action 속성으로 입력된 정보를 POST 방식으로 create페이지로 보낸다.  

보내진 정보는 이후 UserController.java에서 받는다.

## Controller
@Controller 어노테이션으로 해당 클래스가 컨트롤러임을 알린다.

내부 메서드에서는 @GetMapping, @PostMapping을 통해 원하는 url과 연결시킨뒤 return 값을 resources/templates에 있는 템플릿을 불러온다.

이때 원하는 탬플릿으로 출력값을 동적으로 할당 가능하다.  

form에서 보내진 정보는 맵핑된 매서드의 인자로 받을 수 있다. 이때 input 테그의 name 속성으로 연결시킨다.
