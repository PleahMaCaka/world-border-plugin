# World Border Survival

인생의 리버스 역작이라고 할 수 있겠습니다. 월드 보더 확장 서바이벌 플러그인.

- 1.20.1

> [!IMPORTANT]  
> 한번 쓰고 버릴 용도로 작성했습니다. 코드를 뜯어보지 마세요. (엄중)  
> 인생에 도움이 되지 않는 구조를 가지고 있습니다.  
> 하지만 work-well

## Commands

- `game`
    - start : 게임을 시작합니다. 상남자 전용이라 중지는 안됩니다.
    - config : 상점 아이템과 랜덤 보상을 설정합니다.
    - give : 전용 아이템을 지급합니다.
- `shop`
    - 더럽게 코딩된 천박한 상점을 엽니다.
- `clean`
    - 플레이어 채팅창을 더러운 방식으로 깨끗하게 만듭니다.

## Configuration

- `./.server/config/*`
    - rewards.yml : 확장시 랜덤 보상 ItemStack 정보
    - gamestatus.yaml : 진행 상황 때려박혀 있음

## Development

- Requirements
    - Python3
    - JDK 17

아주 오래된 스크립트인 `run.py`를 실행하세요.

```bash
> python3 run.py
```

# License

[MIT License](LICENSE)