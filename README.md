# 내일배움캠프 3주차 과제
## 키오스크

### 실행 이미지(Main.kt)

<p align="center" width="100%">
    <image src = "https://github.com/winteryy/nbc_kiosk/blob/develop/screenshots/sc_001.png" width="30%" >
    <image src = "https://github.com/winteryy/nbc_kiosk/blob/develop/screenshots/sc_002.png" width="30%" >
</p>

- 일반적인 실행 프로세스

<p align="center" width="100%">
    <image src = "https://github.com/winteryy/nbc_kiosk/blob/develop/screenshots/sc_003.png"width="30%" >
</p>

- 생성되는 영수증 텍스트 파일

<p align="center" width="100%">
    <image src = "https://github.com/winteryy/nbc_kiosk/blob/develop/screenshots/sc_004.png"width="30%" >
    <image src = "https://github.com/winteryy/nbc_kiosk/blob/develop/screenshots/sc_005.png"width="30%" >
</p>

- 예외 상황들 (2번째 이미지는 5초마다 현재 주문 대기 수 출력하는 코드 주석처리 안 한 상태)
    

### 요구사항
- [x] 콘솔 상에서 메뉴 보여주기와 같은 키오스크 기본 기능 구현 
- [x] 메뉴 데이터들은 클래스로 관리
- [x] 상속 이용하기 
- [ ] 메뉴 리스트는 하나의 리스트 객체로 관리
- [x] 메뉴 입력 시 예외처리하기
- [x] 잔액과 가격 비교해 주문 처리하기 
- [x] 작업 종료 후, 3초 뒤 다른 작업 수행하기
- [x] 특정 시간대에 결제 진행 할 수 없도록 하기
- [x] 결제 완료 시, 완료 시각 출력
- [ ] 프로그램 종료 시까지, 5초마다 현재 주문 대기수 출력하기
  

### What to
- 콘솔 창에서 표준 입출력을 통해, 메뉴 조회 및 주문과 같은 카페의 기본적인 키오스크 기능을 이용할 수 있습니다.

- 주문이 정상적으로 종료되면, 콘솔에 결제 시각 출력과 함께 `./receipt` 디렉토리에 영수증이 텍스트 파일 형태로 출력됩니다.

- 사용할 예산을 입력 받고, 이를 이용해 주문을 제한합니다.

- 메뉴의 대분류 선택 및 세부 메뉴 선택으로 아이템을 주문 목록에 추가하며, 메뉴의 대분류 선택에서 주문/결제 단계로 진행할 수 있습니다.

- 주문 목록은 제품의 종류 및 수량의 형태로 보여집니다.

- 결제가 끝나면 3초 뒤, 처음으로 돌아갑니다.

### How to
- `ServerData` Object를 사용하여 메뉴 데이터들을 외부에서 가져온다고 가정했습니다. 현재 주문 대기 수 또한 이 곳에서 관리됩니다.
  
- 메뉴 아이템과 관련된 클래스들은 과제 의도에 따라 `open` 키워드와 상속을 이용했습니다.
  
- 상속을 받아 실제로 이용되는 말단 자식 클래스들은 `Category`라는 Interface를 Companion Object로 지니게 했습니다. (카테고리 별 명칭 및 설명을 static하게 호출하고 싶었음)

- `Exception`이 발생할 수 있는 부분의 예외처리는 `try ~ catch`로 감쌌고, `Exception`은 발생하지 않으나 예외처리가 필요한 부분은 조건식으로 처리했습니다.

- 음수 예산 입력, 주문 내역 없는 상태로 주문, 보여지는 메뉴에 없는 동작 커맨드 입력 등에 대한 핸들링을 구현했습니다.

- 시각과 관련된 동작들은 `LocalDateTime` 클래스를 사용해 처리했습니다.

- 파일 입출력과 관련된 동작들은 `java.io.File`을 이용해 기초적으로 처리했습니다. 

- 스레드와 관련된 동작들은 `Thread` 클래스를 이용해 구현했습니다.

- 주문 내역은 `Order` 클래스 내에서 `Map`으로 관리되며, 같은 항목에 대해서는 별도의 항목 추가가 아닌 수량을 더해 관리합니다.

### 기타
- 예산이 음수로 주어졌을 때, `Order` 클래스 내에서의 처리와 같이 의도적으로 `Exception`을 발생시켜 처리해 본 부분도 있습니다.

- 실제 서버가 동작하는 것은 아니기 때문에, `Order`의 고유한 값인 `idString`은 클래스 내부에서 생성하도록 했고, 그래서 값이 고유하지 않을 수 있습니다.  
해당 값을 이용하는 영수증 파일명도 중복이 발생할 수 있으나, 거기까진 처리하지 않았습니다.  

- 모든 메뉴 항목을 단일 리스트로 관리하는 요구사항은 제외했습니다. 메뉴를 단일 리스트로 관리하면, 특정 소분류(ex - Coffee) 메뉴를 보여줄 때마다 `filter`나 `map` 등을 호출해 필터링 할 필요가 있고, 데이터가 커질 경우 성능 상의 손해가 크다고 생각했습니다.  

- 5초마다 현재 대기 수를 지속적으로 출력하는 요구사항은 CLI 특성상 UX에 악영향이 큰 것 같아, 구현 후 주석처리하여 제외했습니다. `KioskClient` 클래스 내에 주석처리된 `ServerThread` 관련 코드들을 활성화하면 이용할 수 있습니다.