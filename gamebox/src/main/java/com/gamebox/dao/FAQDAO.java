package com.gamebox.dao;

import com.gamebox.dto.FAQDTO;
import com.gamebox.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FAQDAO {
    public List<FAQDTO> getAllFAQs() throws Exception {
        String sql = "SELECT faq_id, category, question, answer FROM FAQ";
        List<FAQDTO> faqs = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("FAQ 쿼리 실행 전");

            while (rs.next()) {
                FAQDTO faq = new FAQDTO();
                faq.setFaqId(rs.getInt("faq_id"));
                faq.setCategory(rs.getString("category"));
                faq.setQuestion(rs.getString("question"));
                faq.setAnswer(rs.getString("answer"));
                faqs.add(faq);

                // Detailed debugging for each FAQ
                System.out.println(String.format(
                    "FAQ 로드 - ID: %d, 카테고리: %s, 질문: %s, 답변 길이: %d",
                    faq.getFaqId(),
                    faq.getCategory(),
                    faq.getQuestion(),
                    faq.getAnswer() != null ? faq.getAnswer().length() : 0
                ));
            }

            System.out.println("FAQ 총 " + faqs.size() + "개 로드 완료");

        } catch (SQLException e) {
            System.err.println("FAQ SQL 오류: " + e.getMessage());
            e.printStackTrace();
        }
        return faqs;
    }
}
