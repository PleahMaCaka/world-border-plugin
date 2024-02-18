# World Border Survival

- 1.20.1

> [!IMPORTANT]  
> 한번만 쓰고 버릴 용도로 작성했습니다. 코드를 뜯어보지 마세요. 인생에 도움이 되지 않는 구조를 가지고 있습니다.
> 하지만 work-well

## Commands

- `game`
    - start : 게임을 시작합니다. 상남자 전용이라 중지는 안됩니다.
    - config : 별 쓸대없는 배율들을 설정합니다.
    - give : 전용 아이템을 지급합니다.
- `clean`
    - 플레이어 채팅창을 더러운 방식으로 깨끗하게 만듭니다.

## Configuration

- `./.server/config/*`
    - rewards.yml : 확장시 랜덤 보상 ItemStack 정보
    - gamestatus.yaml : 진행 상황 때려박혀 있음

## Development

- Requirements
    - Python3

아주 오래된 서버 실행 스크립트인 `run.py`를 실행하세요.

```bash
> python3 run.py
```

# License

[MIT License](LICENSE)