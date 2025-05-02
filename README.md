# GameBox 

## 1. 프로젝트 개요

**프로젝트명**: GameBox  
**설명**: JSP 기반의 게임 판매, 커뮤니티 기능, 고객 지원을 제공하는 웹 애플리케이션  
**개발 기간**: 2024년 12월 25일 ~ 2025년 1월 6일  
**저장소**: [kumugu/PJ_GameBox](https://github.com/kumugu/PJ_GameBox)
            [kumugu/B_Prototype_GameBox](https://github.com/kumugu/B_Prototype_GameBox)

![image](11.png)

## 기술 스택

**백엔드**:
- Java (JSP, JSTL, EL)
- Oracle DB XE
- Tomcat 9

**프론트엔드**:
- HTML
- CSS
- JavaScript

**라이브러리 및 도구**:
- ojdbc6.jar
- commons-fileupload
- json-simple
- commons-io

## 2. 주요 기능

### 사용자 관리
- 역할 기반 접근 제어(관리자/일반 사용자)
- 회원가입 및 로그인 기능
- 세션 기반 인증

### 게임 상점
- 게임 검색 및 상세 정보 페이지
- 장바구니 및 구매 기능(카카오페이)
- 리뷰 및 별점 기능

### 커뮤니티
- 사용자 간 게시글 작성 및 토론 기능
- 커뮤니티 콘텐츠 관리

### 관리자 페이지
- 회원, 게임, 리뷰, 문의, 커뮤니티 관리 (CRUD)
- 콘텐츠 관리 도구

### 마이페이지
- 구매 내역 및 사용자 정보 관리
- 계정 설정

### 고객지원
- 사용자 문의 등록 및 관리
- 지원 티켓 시스템

## 3. 프로젝트 구조

```
GameBox/
├── src/
│   ├── com/gamebox/controller/    # FrontController 및 Command 패턴
│   ├── com/gamebox/dao/           # 데이터베이스 접근 객체
│   ├── com/gamebox/dto/           # 데이터 전송 객체
│   ├── com/gamebox/action/        # 비즈니스 로직 구현
│   └── resources/mapping.properties # 라우팅 설정 파일
├── web/
│   ├── WEB-INF/
│   │   ├── views/                 # JSP 뷰 페이지
│   │   └── web.xml                # 서블릿 설정
│   ├── static/                    # CSS, JavaScript, 이미지 파일
│   └── index.jsp                  # 초기 로드 페이지
└── README.md
```

## 4. 데이터베이스 설계

주요 테이블: USERS, GAMES, REVIEWS, CART_ITEMS, ORDERS, PAYMENTS

### 데이터베이스 설정 예시

```sql
CREATE TABLE USERS (
    user_id NUMBER PRIMARY KEY,
    email VARCHAR2(255) UNIQUE,
    password VARCHAR2(255),
    name VARCHAR2(255),
    role VARCHAR2(50),
    created_at DATE DEFAULT SYSDATE
);

-- 추가 테이블은 DB 설정 문서 참고
```

## 5. 구현 과제와 해결 방법

### 과제 1: 동적 라우팅 구현
**해결**: FrontController 패턴 적용 및 properties 파일 기반 라우팅 설정

```java
@WebServlet("*.do")
public class FrontController extends HttpServlet {
    private HashMap<String, Command> commandMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        // Command 등록

        // 관리자 계정 메뉴
        commandMap.put("/admin_home.do", new com.gamebox.action.AdminHomeCommand());             // 관리자 메뉴 진입
        commandMap.put("/manage_users.do", new com.gamebox.action.ManageUsersCommand());         // 관리자 메뉴 - 회원관리(CRUD)
        commandMap.put("/add_user_form.do", new com.gamebox.action.AddUserFormCommand());        // 관리자 메뉴 - 회원관리 - 회원추가 진입
        commandMap.put("/add_user.do", new com.gamebox.action.AddUserCommand());                 // 관리자 메뉴 - 회원관리 - 회원추가 처리
        commandMap.put("/edit_user.do", new com.gamebox.action.EditUserCommand());               // 관리자 메뉴 - 회원관리 - 회원수정 진입
        commandMap.put("/manage_games.do", new com.gamebox.action.ManageGamesCommand());         // 관리자 메뉴 - 게임 관리(CRUD)
        commandMap.put("/manage_reviews.do", new com.gamebox.action.ManageReviewsCommand());     // 관리자 메뉴 - 리뷰 관리
        commandMap.put("/manage_community.do", new com.gamebox.action.ManageCommunityCommand()); // 관리자 메뉴 - 커뮤니티 관리
        commandMap.put("/manage_support.do", new com.gamebox.action.ManageSupportCommand());     // 관리자 메뉴 - 문의 관리

        // 일반 메뉴
        commandMap.put("/user_home.do", new com.gamebox.action.UserHomeCommand());               // 메인 페이지
        commandMap.put("/shop.do", new com.gamebox.action.ShopCommand());                        // 상점 페이지
        commandMap.put("/gameDetail.do", new com.gamebox.action.GameDetailCommand());            // 상점 상세 페이지
        commandMap.put("/review.do", new com.gamebox.action.ReviewCommand());                    // 상점 상세 페이지 - 리뷰

        commandMap.put("/addToCart.do", new com.gamebox.action.AddToCartCommand());              // 장바구니 - 추가
        commandMap.put("/viewCart.do", new com.gamebox.action.ViewCartCommand());                // 장바구니 - 페이지
        commandMap.put("/deleteCartItem.do", new com.gamebox.action.DeleteCartItemCommand());    // 장바구니 - 삭제

        commandMap.put("/checkout.do", new com.gamebox.action.CheckoutCommand());                // 결제 - 결제 진행
        commandMap.put("/paymentResult.do", new com.gamebox.action.PaymentResultCommand());      // 결제 - 결제 결과
        commandMap.put("/verifyPayment.do", new com.gamebox.action.VerifyPaymentCommand());      // 결제 - 결제 검증
        commandMap.put("/paymentSuccess.do", new com.gamebox.action.PaymentSuccessCommand());    // 결제 - 결제 완료

        commandMap.put("/mypage.do", new com.gamebox.action.MypageCommand());                    // 마이 페이지
        commandMap.put("/community.do", new com.gamebox.action.CommunityCommand());              // 커뮤니티
        commandMap.put("/support.do", new com.gamebox.action.SupportCommand());                  // 지원

        // 회원가입 및 로그인
        commandMap.put("/signup_form.do", new com.gamebox.action.SignupFormCommand());           // 회원가입 진입
        commandMap.put("/signup.do", new com.gamebox.action.SignupCommand());                    // 회원가입 처리
        commandMap.put("/login.do", new com.gamebox.action.LoginCommand());                      // 로그인
        commandMap.put("/logout.do", new com.gamebox.action.LogoutCommand());                    // 로그아웃
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath();
        Command command = commandMap.get(path);

        if (command != null) {
            String view = command.execute(request, response);
            if (view != null) {
                RequestDispatcher dispatcher = request.getRequestDispatcher(view);
                dispatcher.forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
```

### 과제 2: 사용자 권한 관리
**해결**: 세션 기반의 역할(Role) 인증 구현으로 관리자 및 사용자 페이지 구분

```java
public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserDAO userDAO = new UserDAO();
        UserDTO user = userDAO.loginUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            if ("ADMIN".equalsIgnoreCase(user.getRole())) {
                return "admin_home.do";
            } else {
                return "user_home.do";
            }
        } else {
            request.setAttribute("error", "로그인 정보가 유효하지 않습니다.");
            return "/login.jsp";
        }
    }
}
```

### 과제 3: 결제 검증 및 처리
**해결**: 아임포트 API를 활용하여 결제 정보 검증 및 처리 구현

```java
public class VerifyPaymentCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonData = (JSONObject) new JSONParser().parse(sb.toString());
            String impUid = (String) jsonData.get("imp_uid");

            // Access token 발급
            String accessToken = getAccessToken("API_KEY", "API_SECRET");

            // 결제 검증
            URL url = new URL("https://api.iamport.kr/payments/" + impUid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", accessToken);

            if (conn.getResponseCode() == 200) {
                response.getWriter().write("{\"success\": true, \"message\": \"결제가 성공적으로 검증되었습니다.\"}");
            } else {
                response.getWriter().write("{\"success\": false, \"message\": \"결제 검증에 실패했습니다.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getAccessToken(String apiKey, String apiSecret) throws Exception {
        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        JSONObject requestData = new JSONObject();
        requestData.put("imp_key", apiKey);
        requestData.put("imp_secret", apiSecret);

        OutputStream os = conn.getOutputStream();
        os.write(requestData.toJSONString().getBytes("UTF-8"));
        os.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            response.append(line);
        }
        br.close();

        JSONObject responseData = (JSONObject) new JSONParser().parse(response.toString());
        return (String) ((JSONObject) responseData.get("response")).get("access_token");
    }
}
```

## 6. 검색 기능 성능 개선 테스트 결과

3가지 검색 방식을 비교하여 성능을 측정하고, 각 방식의 장단점 분석

| **검색 방식**                     | **평균 실행 시간(ms)** | **메모리 사용량(KB)** | **검색 결과 수** | **분석 요약**                                                |
| --------------------------------- | ---------------------- | --------------------- | ---------------- | ------------------------------------------------------------ |
| **순차 검색**                     | 0.692                  | 529                   | 1                | - 구현이 간단하나 데이터 증가 시 실행 시간이 선형적으로 증가. - 현재 데이터셋에서 안정적으로 동작. |
| **인덱스 검색**                   | 0.714                  | 529                   | 1                | - 인덱스를 활용하여 빠른 검색 성능을 보임. - 순차 검색 대비 소폭 빠르며, 대규모 데이터에서 효율성 향상 기대. |
| **애플리케이션 검색** (이진 검색) | 3,412.324              | 21,643                | 1                | - 데이터 정렬 및 메모리 로딩으로 높은 메모리 사용량. - 실행 시간이 크게 증가하며, 대규모 데이터에서는 비효율적. |


###  **핵심 결과**

1. **순차 검색**과 **인덱스 검색**은 현재 데이터 규모(1,000,000개 레코드)에서 유사한 성능을 보였으나,
   - **인덱스 검색**이 상대적으로 더 효율적.
2. **애플리케이션 검색**은 메모리 사용량이 높아지고 실행 시간이 크게 늘어나 **비효율적**으로 판명.
3. **테스트 한계**:
   - 캐시 초기화 부족 및 단순한 검색 조건으로 인해 일부 실행 시간 결과의 신뢰도가 낮음.

###  **향후 개선 방향**

- **대규모 데이터** 및 **동시성 테스트**를 통해 더욱 현실적인 성능 차이 분석.
- **추가 성능 지표(CPU, I/O 부하)** 측정으로 세부적인 성능 요소 파악.
- **애플리케이션 알고리즘 최적화**:
  - 메모리 사용량 감소를 위한 데이터 구조 개선 및 효율화 전략 마련.

------
**자세한 작업 내역**: [보고서 링크]([https://github.com/kumugu/PJ_GameBox/blob/main/25_1_10_1.작업내역(검색 알고리즘 성능 비교 및 개선 작업 1차 보고서).md](https://github.com/kumugu/PJ_GameBox/blob/main/25_1_10_1.%EC%9E%91%EC%97%85%EB%82%B4%EC%97%AD(%EA%B2%80%EC%83%89%20%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98%20%EC%84%B1%EB%8A%A5%20%EB%B9%84%EA%B5%90%20%EB%B0%8F%20%EA%B0%9C%EC%84%A0%20%EC%9E%91%EC%97%85%201%EC%B0%A8%20%EB%B3%B4%EA%B3%A0%EC%84%9C).md))


## 7. 설치 및 실행 방법

### 데이터베이스 설정
1. Oracle DB XE를 설치
2. DB 설정 문서의 SQL 문을 사용하여 테이블 생성

### 프로젝트 설정
1. Tomcat 9 서버를 설정하고 프로젝트를 배포
2. `/resources/mapping.properties`에 데이터베이스 연결 정보 설정

### 실행
1. 브라우저에서 http://localhost:8080/GameBox로 접속

### 테스트
- **로그인/로그아웃**: 유효성 검사 및 세션 관리 테스트
- **SQL Injection 방지**: PreparedStatement 사용으로 모든 입력 필드 검증
- **결제 처리**: 아임포트 API 연동 및 검증 시나리오 테스트


## 8. 성과

- **기능 구현**: 게임 판매, 커뮤니티, 고객지원 등의 주요 기능 성공적으로 구현
- **시스템 안정성**: 안정적인 서버 운영과 사용자 데이터 관리
- **사용자 만족도**: 사용자 피드백을 반영하여 UI/UX 개선을 통한 높은 만족도 달성


## 9. 향후 개발 계획

- **Spring 전환**: 유지보수 및 확장성을 위한 Spring Boot로의 리팩터링
- **React 통합**: 최신 기술 활용한 프론트엔드 개선
- **추가 기능**: 다국어 지원, 고급 게임 관리 기능 추가
- **모바일 친화적 UI**: 모바일 사용자를 위한 향상된 반응형 디자인
