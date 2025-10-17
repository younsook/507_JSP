package model1.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;
import jakarta.servlet.ServletContext;

public class BoardDAO extends JDBConnect{
	
		public BoardDAO(ServletContext application) {
			super(application);
		}
		
		public BoardDAO(String string, String string2, String string3, String string4) {
			// TODO Auto-generated constructor stub
			super(string, string2, string3, string4);
		}

		//1-A.검색 조건에 맞는 게시물의 개수를 반환 LIKE
		public int selectCount(Map<String, Object> map) {
			int totalCount = 0;
			
			String query = "SELECT COUNT(*) FROM board";
			if(map.get("searchWord")!=null) {
				query += " WHERE " + map.get("searchField") + " "
						+ " LIKE '%" + map.get("searchWord")+ "%'";
			}
			
			try {
				psmt = con.prepareStatement(query);
				rs = psmt.executeQuery();
				rs.next();
				totalCount = rs.getInt(1);

			}catch (Exception e) {
				System.out.println("게시물 수를 구하는 중 예외 발생");
				e.printStackTrace();
			}
			return totalCount;
		}
		
		//1-B.검색 조건에 맞는 게시물 목록을 반환 SELECT
		public List<BoardDTO> selectList(Map<String, Object> map){
			List<BoardDTO> bbs = new Vector<BoardDTO>();
			
			String query = "SELECT * FROM board ";
			if(map.get("searchWord") != null) {
				query += " WHERE " + map.get("searchField") + " "
						+ " LIKE '%" + map.get("searchWord") + "%' ";
			}
			query += " ORDER BY num DESC ";
			
			try {
				psmt = con.prepareStatement(query);
				rs = psmt.executeQuery();
								
				while(rs.next()) {
					BoardDTO dto = new BoardDTO();
					
					dto.setNum(rs.getString("num"));
					dto.setTitle(rs.getString("title"));
					dto.setContent(rs.getString("content"));
					dto.setPostdate(rs.getDate("postdate"));
					dto.setId(rs.getString("id"));
					dto.setVisitcount(rs.getString("visitcount"));
					
					bbs.add(dto);
				}

			}catch (Exception e) {
				System.out.println("게시물 조회 중 예외 발생");
				e.printStackTrace();
			}
			return bbs;
		}
		
		//09-1.검색 조건에 맞는 게시물 목록을 반환(페이징기능지원)
		public List<BoardDTO> selectListPage(Map<String, Object> map){
			List<BoardDTO> bbs = new Vector<BoardDTO>();
			//쿼리문 템플릿
			String query = "SELECT * FROM board";		
//			String query = " SELECT * FROM ( " //오라클용쿼리
//					+ " 		SELECT Tb.*, ROWNUM rNum FROM ( "
//					+ " 			SELECT * FROM board ";
			
			//검색조건추가2
			if (map.get("searchWord") != null) {
				query += " WHERE " + map.get("searchField")
				+ " LIKE '%" + map.get("searchWord") + "%' ";
			}
			query += " ORDER BY num DESC LIMIT ?,? ";
//			query += " ORDER BY num DESC " //오라클용쿼리
//					+ "    ) Tb "
//					+ " ) "
//					+ " WHERE rNum BETWEEN ? AND ?";
			
			try {
				
			//				int pageNum = Integer.parseInt(map.get("pageNum").toString());
			//				int pageSize = Integer.parseInt(map.get("pageSize").toString());
			//				int offset = (pageNum - 1) * pageSize;
				
				//쿼리문 완성 4
				psmt = con.prepareStatement(query);
				//mysql start----------------------
				psmt.setInt(1, (int)map.get("start"));
				psmt.setInt(2, (int)map.get("pageSize"));
				//mysql end----------------------
				//oracle start----------------------
//				psmt.setString(1, map.get("start").toString());
//				psmt.setString(2, map.get("end").toString());
				//oracle end----------------------
			//				psmt.setInt(1, pageSize);  // LIMIT = 몇 개
			//				psmt.setInt(2, offset);    // OFFSET = 어디서부터
				
				//쿼리문 실행 5
				rs = psmt.executeQuery();
				
				while (rs.next()) {
					BoardDTO dto = new BoardDTO();
					dto.setNum(rs.getString("num"));
					dto.setTitle(rs.getString("title"));
					dto.setContent(rs.getString("content"));
					dto.setPostdate(rs.getDate("postdate"));
					dto.setId(rs.getString("id"));
					dto.setVisitcount(rs.getString("visitcount"));
					
					//반환할 결과 목록에 게시물 추가
					bbs.add(dto);
				}
				
			}catch (Exception e) {
				System.out.println("게시물 조회 중 예외 발생");
				e.printStackTrace();
			}
			return bbs;
		}
		
		//2.게시글 데이터를 받아 DB에 추가 INSERT
		public int insertWrite(BoardDTO dto) {
			int result = 0;
			
			try {
				String query = "INSERT INTO board( "
						+ " title, content, id, visitcount) "
						+ " VALUES ( "
						+ " ?, ?, ?, ?)";
				
				psmt = con.prepareStatement (query);
				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getContent());
				psmt.setString(3, dto.getId());
				psmt.setInt(4, 0);
				
				result = psmt.executeUpdate();
			}
			catch (Exception e) {
				System.out.println("게시물 입력 중 예외 발생");
				e.printStackTrace();
			}
			return result;
		}
		
		//3.지정한 게시물을 찾아 내용을 반환 view
		public BoardDTO selectView(String num) {
			BoardDTO dto = new BoardDTO();
			
			String query = "SELECT B.*, M.name FROM member M INNER JOIN board B "
					+ " ON M.id=B.id WHERE num=?";
			
			try {
				
				psmt = con.prepareStatement (query);
				psmt.setString(1, num);
				rs = psmt.executeQuery();
				
				if(rs.next()) {
					dto.setNum(rs.getString(1));
					dto.setTitle(rs.getString(2));
					dto.setContent(rs.getString("content"));
					dto.setPostdate(rs.getDate("postdate"));
					dto.setId(rs.getString("id"));
					dto.setVisitcount(rs.getString(6));
					dto.setName(rs.getString("name"));
				}
				
			}catch(Exception e) {
				System.out.println("게시물 상세보기 중 예외 발생");
				e.printStackTrace();
			}
			
			return dto;
			
		}
		
		//4.지정한 게시물의 조회수를 1 증가 visitcount UPDATE
		public void updateVisitCount(String num) {
			String query = "UPDATE board SET "
					+ " visitcount=visitcount+1 "
					+ " WHERE num=?";
			
			try {
				psmt = con.prepareStatement(query);
				psmt.setString(1, num);
				psmt.executeUpdate();
			}catch(Exception e) {
				System.out.println("게시물 조회수 증가 중 예외 발생");
				e.printStackTrace();
			}
		}
		
		//5.지정한 게시물 수정
		public int updateEdit(BoardDTO dto) {
			int result = 0;
			
			try {
				
				String query = "UPDATE board SET "
						+ " title=?, content=? "
						+ " WHERE num=?";
				
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getTitle());
				psmt.setString(2, dto.getContent());
				psmt.setString(3, dto.getNum());
				result = psmt.executeUpdate();
			}catch(Exception e) {
				System.out.println("게시물 수정 중 예외 발생");
				e.printStackTrace();
			}
			
			return result;
		}
		
		//6.지정한 게시물을 삭제
		public int deletePost(BoardDTO dto) {
			int result =0;
			try {
				String query = "DELETE FROM board WHERE num=?";
				
				psmt = con.prepareStatement(query);
				psmt.setString(1, dto.getNum());
				
				result = psmt.executeUpdate();
				
			}catch(Exception e) {
				System.out.println("게시물 삭제 중 예외 발생");
				e.printStackTrace();
			}
			return result;
		}
		
		public static void main(String[] args) {
			BoardDAO dao = new BoardDAO(
					"com.mysql.cj.jdbc.Driver",
			        "jdbc:mysql://localhost:3306/musthave",
			        "musthave",
			        "tiger"
			);
			
			//1-A-1.BoardDTO 객체 생성 및 값 세팅
		    BoardDTO dto = new BoardDTO();
		    dto.setTitle("테스트 제목");
		    dto.setContent("테스트 내용입니다.");
		    dto.setId("musthave"); // 실제 사용자 ID가 존재하는 값
		    
//		 // 1-A-2.insert 실행
//		    int result = dao.insertWrite(dto);
//		    
//		 // 1-A-3.결과 확인
//		    if (result == 1) {
//		        System.out.println("글쓰기 성공");
//		    } else {
//		        System.out.println("글쓰기 실패");
//		    }
			
		 // 1-B. 글 목록 출력 테스트
			Map<String, Object> map = new HashMap<>();
			map.put("searchField", "title");
			map.put("searchWord", "111");
			List<BoardDTO> list = dao.selectList(map);
			for (BoardDTO item : list)
				System.out.println(item);
			
			//3. view 출력
			String testNum = "1"; // 확인하고 싶은 게시물 번호 입력
		    BoardDTO dto1 = dao.selectView(testNum);

		    if (dto1.getTitle() == null) {
		        System.out.println("게시물이 존재하지 않거나 내용을 읽을 수 없습니다.");
		    } else {
		        System.out.println("번호: " + dto1.getNum());
		        System.out.println("제목: " + dto1.getTitle());
		        System.out.println("내용: " + dto1.getContent());
		        System.out.println("작성자: " + dto1.getId());
		        System.out.println("작성일: " + dto1.getPostdate());
		        System.out.println("조회수: " + dto1.getVisitcount());
		        System.out.println("이름: " + dto1.getName());
		    }
			
		    //4.조회수
		    String targetNum = "12"; // 조회수를 증가시킬 게시글 번호

		    System.out.println("조회수 증가 전 --------------------");
		    BoardDTO before = dao.selectView(targetNum);
		    System.out.println("조회수: " + before.getVisitcount());

		    dao.updateVisitCount(targetNum); // 조회수 증가

		    System.out.println("조회수 증가 후 --------------------");
		    BoardDTO after = dao.selectView(targetNum);
		    System.out.println("조회수: " + after.getVisitcount());
		    
		 // 5. 게시물 수정 테스트
		    String editNum = "12"; // 수정할 게시물 번호 (실제 존재하는 번호로 바꿔야 함)

		    BoardDTO editDto = new BoardDTO();
		    editDto.setNum(editNum);
		    editDto.setTitle("수정된 제목입니다.");
		    editDto.setContent("수정된 내용입니다.");

		    int editResult = dao.updateEdit(editDto);

		    if (editResult == 1) {
		        System.out.println("게시물 수정 성공");

		        // 수정 후 데이터 다시 조회해서 확인
		        BoardDTO updated = dao.selectView(editNum);
		        System.out.println("수정 후 제목: " + updated.getTitle());
		        System.out.println("수정 후 내용: " + updated.getContent());
		    } else {
		        System.out.println("게시물 수정 실패");
		    }
		    
		    map.clear(); // 기존 map 비우고 다시 사용
		    map.put("searchField", "title");
		    map.put("searchWord", "테스트");
		    map.put("pageNum", 1);
		    map.put("pageSize", 10);

		    List<BoardDTO> result1 = dao.selectListPage(map);
		    for (BoardDTO dto11 : result1) {
		    	System.out.println(dto11.getTitle() + " / " + dto11.getId());
		    }
			
			dao.close();
		}
}
