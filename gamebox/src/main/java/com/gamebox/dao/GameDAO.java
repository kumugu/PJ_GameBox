package com.gamebox.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gamebox.dto.GameDTO;
import com.gamebox.util.DBConnection;

public class GameDAO {

    // 게임 추가 메서드
	public boolean addGame(GameDTO game) throws Exception {
	    String sql = "INSERT INTO games (game_id, title, image_path, description, genre, rating, release_date, developer, price, video_url, review_summary, min_requirements, rec_requirements, created_at, updated_at) "
	               + "VALUES (games_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

	        pstmt.setString(1, game.getTitle());
	        pstmt.setString(2, game.getImagePath());
	        pstmt.setString(3, game.getDescription());
	        pstmt.setString(4, game.getGenre());
	        pstmt.setDouble(5, game.getRating());
	        pstmt.setDate(6, new java.sql.Date(game.getReleaseDate().getTime()));
	        pstmt.setString(7, game.getDeveloper());
	        pstmt.setDouble(8, game.getPrice());
	        pstmt.setString(9, game.getVideoUrl());
	        pstmt.setString(10, game.getReviewSummary());
	        pstmt.setString(11, game.getMinRequirements());
	        pstmt.setString(12, game.getRecRequirements());

	        // 모든 값이 잘 설정되었는지 확인
	        int rowsInserted = pstmt.executeUpdate();

	        // 자동 생성된 game_id 값을 문자열로 가져옴
	        try (ResultSet rs = pstmt.getGeneratedKeys()) {
	            if (rs.next()) {
	                String generatedGameId = rs.getString(1); // 문자열로 가져옴
	                game.setGameId(generatedGameId); // 문자열로 설정
	            }
	        }

	        return rowsInserted > 0;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


    // 게임 리스트 조회 메서드
    public List<GameDTO> getAllGames() throws Exception {
        String sql = "SELECT game_id, title, image_path, description, genre, release_date, developer, price, video_url, review_summary, min_requirements, rec_requirements, created_at, updated_at FROM games";
        List<GameDTO> gameList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                GameDTO game = new GameDTO();
                game.setGameId(rs.getString("game_id")); // 문자열로 가져옴
                game.setTitle(rs.getString("title"));
                game.setImagePath(rs.getString("image_path"));
                game.setDescription(rs.getString("description"));
                game.setGenre(rs.getString("genre"));
                game.setReleaseDate(rs.getDate("release_date"));
                game.setDeveloper(rs.getString("developer"));
                game.setPrice(rs.getDouble("price"));
                game.setVideoUrl(rs.getString("video_url"));
                game.setReviewSummary(rs.getString("review_summary"));
                game.setMinRequirements(rs.getString("min_requirements"));
                game.setRecRequirements(rs.getString("rec_requirements"));
                game.setCreatedAt(rs.getTimestamp("created_at"));
                game.setUpdatedAt(rs.getTimestamp("updated_at"));
                gameList.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    // 게임 삭제 메서드
    public boolean deleteGame(String gameId) throws Exception {
        String sql = "DELETE FROM games WHERE game_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gameId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게임 수정 메서드
    public boolean updateGame(GameDTO game) throws Exception {
        String sql = "UPDATE games SET title = ?, image_path = ?, description = ?, genre = ?, release_date = ?, developer = ?, price = ?, video_url = ?, review_summary = ?, min_requirements = ?, rec_requirements = ? WHERE game_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, game.getTitle());
            pstmt.setString(2, game.getImagePath());
            pstmt.setString(3, game.getDescription());
            pstmt.setString(4, game.getGenre());
            pstmt.setDate(5, new java.sql.Date(game.getReleaseDate().getTime()));
            pstmt.setString(6, game.getDeveloper());
            pstmt.setDouble(7, game.getPrice());
            pstmt.setString(8, game.getVideoUrl());
            pstmt.setString(9, game.getReviewSummary());
            pstmt.setString(10, game.getMinRequirements());
            pstmt.setString(11, game.getRecRequirements());
            pstmt.setString(12, game.getGameId()); // 문자열로 설정

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 특정 게임 조회 메서드
    public GameDTO getGameById(String gameId) throws Exception {
        String sql = "SELECT game_id, title, image_path, description, genre, release_date, developer, price, video_url, review_summary, min_requirements, rec_requirements, created_at, updated_at FROM games WHERE game_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    GameDTO game = new GameDTO();
                    game.setGameId(rs.getString("game_id")); // 문자열로 가져옴
                    game.setTitle(rs.getString("title"));
                    game.setImagePath(rs.getString("image_path"));
                    game.setDescription(rs.getString("description"));
                    game.setGenre(rs.getString("genre"));
                    game.setReleaseDate(rs.getDate("release_date"));
                    game.setDeveloper(rs.getString("developer"));
                    game.setPrice(rs.getDouble("price"));
                    game.setVideoUrl(rs.getString("video_url"));
                    game.setReviewSummary(rs.getString("review_summary"));
                    game.setMinRequirements(rs.getString("min_requirements"));
                    game.setRecRequirements(rs.getString("rec_requirements"));
                    game.setCreatedAt(rs.getTimestamp("created_at"));
                    game.setUpdatedAt(rs.getTimestamp("updated_at"));
                    return game;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
