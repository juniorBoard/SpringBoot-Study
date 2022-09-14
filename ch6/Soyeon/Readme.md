## 게시글 CRUD TDD 코드 작성


*update와 delete는 생략

처음엔 entity를 return값으로 설정했는데 

엔티티를 return값으로 주지말고 dto를 통해서 리턴값을 전달하는게 좋다라는 글을 보고

이후에 Comment를 추가하여 DTO를 2개 추가했습니다. (CommentDto, RegistryDto)


---

기존 코드

BoardService → BoardDto → BoardService → BoardServiceImpl → BoardRepository → Board

<br>

이후 dto 사용 코드 추가

BoardService → CommentDto, RegistryDto → BoardService → BoardServiceImpl → CommentRepository → Comment

