package com.gamebox.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.OutputStream;
import java.io.BufferedReader; // 추가된 부분
import java.io.InputStreamReader; // 추가된 부분
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class VerifyPaymentCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            // JSON 데이터 읽기
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = request.getReader()) { // BufferedReader 사용
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }

            JSONObject jsonData = (JSONObject) new JSONParser().parse(sb.toString());
            String impUid = (String) jsonData.get("imp_uid");
            String merchantUid = (String) jsonData.get("merchant_uid");

            System.out.println("VerifyPaymentCommand - Received imp_uid: " + impUid);
            System.out.println("VerifyPaymentCommand - Received merchant_uid: " + merchantUid);

            if (impUid == null || impUid.isEmpty()) {
                throw new IllegalArgumentException("결제 고유번호(imp_uid)가 제공되지 않았습니다.");
            }

            // 1. 아임포트 REST API Key와 Secret 설정
            String apiKey = "3582856416816475"; // REST API Key
            String apiSecret = "CShRCnwqgN3REYWOlkPDIwS7EfD4Hds3raeVq9uEWUTIelmZmZXqxQsfW3WSnKs3VQReMHlUnTEZemke"; // REST API Secret

            // 2. 아임포트 API 토큰 발급 요청
            String accessToken = getAccessToken(apiKey, apiSecret);
            if (accessToken == null) {
                throw new RuntimeException("Access Token 발급 실패");
            }

            // 3. 아임포트 결제 검증 API 호출
            URL url = new URL("https://api.iamport.kr/payments/" + impUid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", accessToken);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            // 응답 데이터 수신
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                Scanner sc = new Scanner(conn.getInputStream());
                StringBuilder responseSb = new StringBuilder();
                while (sc.hasNext()) {
                    responseSb.append(sc.nextLine());
                }
                sc.close();

                JSONObject responseJson = (JSONObject) new JSONParser().parse(responseSb.toString());
                System.out.println("검증 API 응답 데이터: " + responseJson);

                JSONObject responseData = (JSONObject) responseJson.get("response");
                String status = (String) responseData.get("status");

                if ("paid".equals(status)) {
                    response.getWriter().write("{\"success\": true, \"message\": \"결제가 성공적으로 검증되었습니다.\"}");
                } else {
                    response.getWriter().write("{\"success\": false, \"message\": \"결제 상태가 유효하지 않습니다.\"}");
                }
            } else {
                throw new RuntimeException("결제 검증 API 호출 실패: HTTP " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"success\": false, \"message\": \"결제 검증 중 오류 발생: " + e.getMessage() + "\"}");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    // 아임포트 API 토큰 발급 함수
    private String getAccessToken(String apiKey, String apiSecret) throws Exception {
        URL url = new URL("https://api.iamport.kr/users/getToken");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        conn.setDoOutput(true);

        JSONObject requestData = new JSONObject();
        requestData.put("imp_key", apiKey);
        requestData.put("imp_secret", apiSecret);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(requestData.toJSONString().getBytes("UTF-8"));
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == 200) {
            Scanner sc = new Scanner(conn.getInputStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            sc.close();

            JSONObject responseJson = (JSONObject) new JSONParser().parse(sb.toString());
            System.out.println("토큰 응답 데이터: " + responseJson);

            JSONObject responseData = (JSONObject) responseJson.get("response");
            return (String) responseData.get("access_token");
        } else {
            Scanner sc = new Scanner(conn.getErrorStream());
            StringBuilder sb = new StringBuilder();
            while (sc.hasNext()) {
                sb.append(sc.nextLine());
            }
            sc.close();

            throw new RuntimeException("토큰 요청 실패: HTTP " + responseCode + ", " + sb.toString());
        }
    }
}
