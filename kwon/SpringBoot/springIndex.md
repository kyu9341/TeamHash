
## Index 페이지와 파비곤
- **웰컴 페이지** : 애플리케이션 root로 요청을 했을 때 보여주는 페이지. 정적 페이지로 index를 보여주는 방법은 기본 리소스 위치에(resources/static/ 등..) index.html파일을 두면 애플리케이션이 해당 html을 반환한다.


- 루트 페이지 방문 시
<div style="width: 600px; height: 80px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/static4.png" style="width: 600px
    ; height: 80px;">
</div>

- **파비콘(Favicon)** : 파비콘은 웹페이지에 접속했을때, 상단 탭에 보여지는 아이콘을 일컫는다. 이 아이콘은 즐겨찾기에 웹페이지를 등록할때도 사용된다. 웹사이트를 대표하는 로고(logo)의 개념과 비슷하며, 사이트의 성격을 드러내기도 한다.

<https://favicon.io/>
위의 웹 사이트에서 파비콘을 만들 수 있다.

- 만든 파비콘을 index.html이 있는 경로에 같이 둔다.(/static/favicon.ico)

<div style="width: 600px; height: 150px;">
    <img src="https://github.com/kyu9341/TeamHash_Practice/blob/master/kwon/image/static5.png" style="width: 600px
    ; height: 150px;">
</div>

- 만약 파비콘이 제대로 나오지 않는다면 ide를 재시작 해보고, http://localhost:8080/favicon.ico 로 직접 요청 후에 서버를 재시작하면 제대로 동작할 수도 있다.
- 그래도 안된다면 모든 브라우저를 닫고 다시 시작하여 접속해보자. (나는 이렇게 해서 됐다.)




> 참조
> <https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8/dashboard>
> <https://webdir.tistory.com/337>
