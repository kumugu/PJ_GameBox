package com.gamebox.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gamebox.dto.GameDTO;
import com.gamebox.util.DBConnection;

public class GameDAO {

    // 게임 추가 메서드
    public boolean addGame(GameDTO game) throws Exception {
        String sql = "INSERT INTO games (title, description, genre, release_date, developer, price) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, game.getTitle());
            pstmt.setString(2, game.getDescription());
            pstmt.setString(3, game.getGenre());
            pstmt.setDate(4, new java.sql.Date(game.getReleaseDate().getTime()));
            pstmt.setString(5, game.getDeveloper());
            pstmt.setDouble(6, game.getPrice());

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게임 리스트 조회 메서드
    public List<GameDTO> getAllGames() throws Exception {
        String sql = "SELECT game_id, title, description, genre, release_date, developer, price FROM games";
        List<GameDTO> gameList = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                GameDTO game = new GameDTO();
                game.setGameId(rs.getInt("game_id"));
                game.setTitle(rs.getString("title"));
                game.setDescription(rs.getString("description"));
                game.setGenre(rs.getString("genre"));
                game.setReleaseDate(rs.getDate("release_date"));
                game.setDeveloper(rs.getString("developer"));
                game.setPrice(rs.getDouble("price"));
                gameList.add(game);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return gameList;
    }

    // 게임 삭제 메서드
    public boolean deleteGame(int gameId) throws Exception {
        String sql = "DELETE FROM games WHERE game_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            int rowsDeleted = pstmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 게임 수정 메서드
    public boolean updateGame(GameDTO game) throws Exception {
        String sql = "UPDATE games SET title = ?, description = ?, genre = ?, release_date = ?, developer = ?, price = ? WHERE game_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, game.getTitle());
            pstmt.setString(2, game.getDescription());
            pstmt.setString(3, game.getGenre());
            pstmt.setDate(4, new java.sql.Date(game.getReleaseDate().getTime()));
            pstmt.setString(5, game.getDeveloper());
            pstmt.setDouble(6, game.getPrice());
            pstmt.setInt(7, game.getGameId());

            int rowsUpdated = pstmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 특정 게임 조회 메서드
    public GameDTO getGameById(int gameId) throws Exception {
        String sql = "SELECT game_id, title, description, genre, release_date, developer, price FROM games WHERE game_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, gameId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    GameDTO game = new GameDTO();
                    game.setGameId(rs.getInt("game_id"));
                    game.setTitle(rs.getString("title"));
                    game.setDescription(rs.getString("description"));
                    game.setGenre(rs.getString("genre"));
                    game.setReleaseDate(rs.getDate("release_date"));
                    game.setDeveloper(rs.getString("developer"));
                    game.setPrice(rs.getDouble("price"));
                    return game;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
