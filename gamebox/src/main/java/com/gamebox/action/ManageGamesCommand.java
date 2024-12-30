package com.gamebox.action;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.gamebox.dao.GameDAO;
import com.gamebox.dto.GameDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManageGamesCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    // 요청 데이터 인코딩 설정
	    request.setCharacterEncoding("UTF-8");

	    String action = request.getParameter("action");
	    GameDAO gameDAO = new GameDAO();

	    // action 값에 따라 작업 처리
	    switch (action == null ? "view" : action) {
	        case "view":
	            return handleViewGames(request, gameDAO);
	        case "add_form":
	            return handleAddForm(request);
	        case "add":
	            return handleAddGame(request, gameDAO);
	        case "edit_form":
	            return handleEditForm(request, gameDAO);
	        case "edit":
	            return handleEditGame(request, gameDAO);
	        case "delete":
	            return handleDeleteGame(request, gameDAO);
	        default:
	            request.setAttribute("adminAlert", "알 수 없는 액션입니다!");
	            return "/manage_games.do?action=view";
	    }
	}


    /**
     * 게임 목록 보기 처리
     */
    private String handleViewGames(HttpServletRequest request, GameDAO gameDAO) throws Exception {
        List<GameDTO> gameList = gameDAO.getAllGames();
        request.setAttribute("gameList", gameList);
        return "/WEB-INF/views/admin/manage_games.jsp";
    }

    /**
     * 게임 추가 폼으로 이동 처리
     */
    private String handleAddForm(HttpServletRequest request) {
        String alertMessage = (String) request.getSession().getAttribute("adminAlert");
        if (alertMessage != null) {
            request.setAttribute("adminAlert", alertMessage);
            request.getSession().removeAttribute("adminAlert");
        }
        return "/WEB-INF/views/admin/add_games.jsp"; // 게임 추가 폼 페이지로 이동
    }
    
    /**
     * 게임 수정 폼으로 이동 처리
     */
    private String handleEditForm(HttpServletRequest request, GameDAO gameDAO) throws Exception {
        String gameId = request.getParameter("gameId");
        GameDTO game = gameDAO.getGameById(gameId);
        request.setAttribute("game", game);
        return "/WEB-INF/views/admin/edit_games.jsp";
    }

    /**
     * 게임 삭제 처리
     */
    private String handleDeleteGame(HttpServletRequest request, GameDAO gameDAO) throws Exception {
        String gameId = request.getParameter("gameId");
        boolean isDeleted = gameDAO.deleteGame(gameId);
        if (isDeleted) {
            request.getSession().setAttribute("adminAlert", "게임 삭제 성공!");
        } else {
            request.setAttribute("errorMessage", "게임 삭제 중 오류가 발생했습니다.");
            return "/WEB-INF/views/admin/error.jsp";
        }
        return "/manage_games.do?action=view";
    }

    

    /**
     * 게임 추가 처리
     */
    private String handleAddGame(HttpServletRequest request, GameDAO gameDAO) throws Exception {
        if (ServletFileUpload.isMultipartContent(request)) {
            // 파일 업로드 설정
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            
            // 프로젝트 내부의 올바른 경로로 설정
            String uploadPath = request.getServletContext().getRealPath("uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 하위 폴더까지 생성
                // 폴더 생성 후 권한 설정 (필요시)
                uploadDir.setWritable(true, false);
                uploadDir.setReadable(true, false);
                uploadDir.setExecutable(true, false);
            }

            Map<String, String> formData = new HashMap<>();
            String imagePath = null;

            try {
                List<FileItem> formItems = upload.parseRequest(request);
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        formData.put(item.getFieldName(), item.getString("UTF-8"));
                    } else if (!item.getName().isEmpty()) {
                        String fileName = new File(item.getName()).getName();
                        imagePath = "/uploads/" + fileName;
                        File storeFile = new File(uploadPath + File.separator + fileName);
                        item.write(storeFile);
                    }
                }

                // GameDTO 생성 및 데이터 설정
                GameDTO game = new GameDTO();
                game.setTitle(validateParameter(formData.get("title"), "제목 입력이 누락되었습니다."));
                game.setDescription(formData.getOrDefault("description", null));
                game.setGenre(validateParameter(formData.get("genre"), "장르 입력이 누락되었습니다."));
                game.setReleaseDate(java.sql.Date.valueOf(validateParameter(formData.get("releaseDate"), "출시일 입력이 누락되었습니다.")));
                game.setDeveloper(validateParameter(formData.get("developer"), "개발사 입력이 누락되었습니다."));
                game.setPrice(Double.parseDouble(validateParameter(formData.get("price"), "가격 입력이 누락되었습니다.")));
                game.setRating(Double.parseDouble(formData.getOrDefault("rating", "0.0"))); // 기본값 0.0 설정
                game.setImagePath(imagePath); // 업로드된 파일 경로 설정
                game.setVideoUrl(formData.getOrDefault("videoUrl", null));
                game.setReviewSummary(formData.getOrDefault("reviewSummary", null));
                game.setMinRequirements(formData.getOrDefault("minRequirements", null));
                game.setRecRequirements(formData.getOrDefault("recRequirements", null));

                // DB에 게임 추가
                boolean isAdded = gameDAO.addGame(game);
                if (isAdded) {
                    request.getSession().setAttribute("adminAlert", "게임 추가 성공!");
                } else {
                    request.setAttribute("errorMessage", "게임 추가에 실패했습니다.");
                    return "/WEB-INF/views/admin/error.jsp";
                }

            } catch (Exception e) {
                request.setAttribute("errorMessage", "게임 추가 중 오류 발생: " + e.getMessage());
                return "/WEB-INF/views/admin/error.jsp";
            }
        }
        return "/manage_games.do?action=view";
    }

    // 유효성 검사 메서드
    private String validateParameter(String parameter, String errorMessage) {
        if (parameter == null || parameter.isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
        return parameter;
    }


    /**
     * 게임 수정 처리
     */
    private String handleEditGame(HttpServletRequest request, GameDAO gameDAO) throws Exception {
        if (ServletFileUpload.isMultipartContent(request)) {
            // 파일 업로드 설정
            DiskFileItemFactory factory = new DiskFileItemFactory();
            factory.setSizeThreshold(1024 * 1024 * 2); // 2MB 임시 메모리
            factory.setRepository(new File(System.getProperty("java.io.tmpdir"))); // 임시 디렉토리 설정

            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setFileSizeMax(10 * 1024 * 1024); // 파일 최대 크기: 10MB

            String uploadPath = request.getServletContext().getRealPath("uploads");
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs(); // 하위 폴더까지 생성
                // 폴더 생성 후 권한 설정 (필요시)
                uploadDir.setWritable(true, false);
                uploadDir.setReadable(true, false);
                uploadDir.setExecutable(true, false);
            }

            Map<String, String> formData = new HashMap<>();
            String imagePath = null;

            try {
                List<FileItem> formItems = upload.parseRequest(request);
                for (FileItem item : formItems) {
                    if (item.isFormField()) {
                        formData.put(item.getFieldName(), item.getString("UTF-8"));
                    } else if (!item.getName().isEmpty()) {
                        String fileName = new File(item.getName()).getName();
                        imagePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(imagePath);
                        item.write(storeFile);
                        formData.put("imagePath", "/uploads/" + fileName);
                    }
                }

                // 기존 게임 데이터 가져오기
                String gameId = formData.get("gameId");
                GameDTO game = gameDAO.getGameById(gameId);

                // 게임 데이터 업데이트
                game.setTitle(formData.get("title"));
                game.setDescription(formData.get("description"));
                game.setGenre(formData.get("genre"));
                game.setDeveloper(formData.get("developer"));
                game.setPrice(Double.parseDouble(formData.get("price")));
                game.setReleaseDate(java.sql.Date.valueOf(formData.get("releaseDate")));
                game.setRating(Double.parseDouble(formData.getOrDefault("rating", "0.0"))); // 평점 업데이트
                game.setVideoUrl(formData.getOrDefault("videoUrl", null)); // 동영상 URL 업데이트
                game.setReviewSummary(formData.getOrDefault("reviewSummary", null)); // 리뷰 요약 업데이트
                game.setMinRequirements(formData.getOrDefault("minRequirements", null)); // 최소 요구 사항 업데이트
                game.setRecRequirements(formData.getOrDefault("recRequirements", null)); // 권장 요구 사항 업데이트

                // 이미지 경로 업데이트 (이미지 파일이 업로드되지 않은 경우 기존 경로 유지)
                game.setImagePath(imagePath != null ? formData.get("imagePath") : game.getImagePath());

                // 게임 수정
                gameDAO.updateGame(game);
                request.getSession().setAttribute("adminAlert", "게임 수정 성공!");
            } catch (Exception e) {
                request.setAttribute("errorMessage", "게임 수정 중 오류가 발생했습니다: " + e.getMessage());
                return "/WEB-INF/views/admin/error.jsp";
            }
        }
        return "/manage_games.do?action=view";
    }


}