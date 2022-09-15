# _@Controller_ & _@RestController_

## _@RestController_
_@RestController_ 안에 _@Controller_와 _@ResponseBody_가 포함되어 있음.

### _@ResponseBody_
응답을 JSON 형식으로 바꿔주는 역할.

_@RestController_는 응답으로 객체를 전달할 때 JSON 형식으로 변환하고 헤더의 Content-Type을 application/json으로 설정해 전달.