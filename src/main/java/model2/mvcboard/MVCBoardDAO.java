package model2.mvcboard;

import java.util.List;
import java.util.Map;
import java.util.Vector;

import common.JDBConnect;


public class MVCBoardDAO extends JDBConnect {

	public MVCBoardDAO() {
		super();
	}
	
	//검색 조건에 맞는 게시물의 개수를 반환 List
	public int selectCount(Map<String, Object> map) {
		int totalCount = 0;
		
		String query = "SELECT COUNT(*) FROM mvcboard";
		if(map.get("searchWord")!=null) {
			query += " WHERE " + map.get("searchField") + " "
					+ " LIKE '%" + map.get("searchWord")+ "%'";
		}
		
		try {
			psmt = con.prepareStatement(query);
			rs = psmt.executeQuery();
			rs.next();
			totalCount = rs.getInt(1); //검색된 게시물 개수 저장

		}catch (Exception e) {
			System.out.println("게시물 수를 구하는 중 예외 발생");
			e.printStackTrace();
		}
		return totalCount; //게시물 개수를 서블릿으로 반환
	}
	
	//검색 조건에 맞는 게시물 목록을 반환(페이징 기능지원) List
	public List<MVCBoardDTO> selectListPage(Map<String, Object> map){
		List<MVCBoardDTO> board = new Vector<MVCBoardDTO>();
		//쿼리문 템플릿
		String query = "SELECT * FROM mvcboard";		
	
		//검색조건추가2
		if (map.get("searchWord") != null) {
			query += " WHERE " + map.get("searchField")
			+ " LIKE '%" + map.get("searchWord") + "%' ";
		}
		query += " ORDER BY idx DESC limit ?,? ";
	
		try {
					
			//쿼리문 완성 4
			psmt = con.prepareStatement(query);
			psmt.setInt(1, (int)map.get("start"));
			psmt.setInt(2, (int)map.get("pageSize"));

			
			
			//쿼리문 실행 5
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				MVCBoardDTO dto = new MVCBoardDTO();
				dto.setIdx(rs.getString("idx"));
				dto.setName(rs.getString("name"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setPostdate(rs.getDate("postdate"));
				dto.setOfile(rs.getString("ofile"));
				dto.setSfile(rs.getString("sfile"));
				dto.setDowncount(rs.getInt("downcount"));
				dto.setPass(rs.getString("pass"));
				dto.setVisitcount(rs.getInt("visitcount"));
				
				//반환할 결과 목록에 게시물 추가
				board.add(dto);
			}
			
		}catch (Exception e) {
			System.out.println("게시물 조회 중 예외 발생");
			e.printStackTrace();
		}
		return board;
	}
	
	//게시글 데이터를 받아 DB에 추가(파일 업로드 지원) Write 
	public int insertWrite(MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "INSERT INTO mvcboard( "
					+ " name, title, content, ofile, sfile, pass) "
					+ " VALUES ( "
					+ " ?, ?, ?, ?, ?, ?)";
			
			psmt = con.prepareStatement (query);
			psmt.setString(1, dto.getName());
			psmt.setString(2, dto.getTitle());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getPass());

			
			result = psmt.executeUpdate();
		}
		catch (Exception e) {
			System.out.println("게시물 입력 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//(상세보기)주어진 일련번호에 해당하는 게시물을 DTO에 담아 반환 view
	public MVCBoardDTO selectView(String idx) {
		MVCBoardDTO dto = new MVCBoardDTO();
		String query = "SELECT * FROM mvcboard WHERE idx=?";
		try {
			psmt = con.prepareStatement (query);
			psmt.setString(1, idx);
			rs = psmt.executeQuery();
			
			if(rs.next()) {
				dto.setIdx(rs.getString(1));
				dto.setName(rs.getString(2));
				dto.setTitle(rs.getString(3));
				dto.setContent(rs.getString(4));
				dto.setPostdate(rs.getDate(5));
				dto.setOfile(rs.getString(6));
				dto.setSfile(rs.getString(7));
				dto.setDowncount(rs.getInt(8));
				dto.setPass(rs.getString(9));
				dto.setVisitcount(rs.getInt(10));
			}

		}catch (Exception e) {
			System.out.println("게시물 상세보기 중 예외 발생");
			e.printStackTrace();
		}
		return dto; //결과 반환
	}
	
	//(상세보기)주어진 일련번호에 해당하는 게시물의 조회수를 1 증가
	public void updateVisitCount(String idx) {
		String query = "UPDATE mvcboard SET "
				+ " visitcount=visitcount+1 "
				+ " WHERE idx=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeUpdate(); //executeQuery();
		}catch(Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	//(다운로드)다운로드 횟수를 1증가
	public void downCountPlus(String idx) {
		String query = "UPDATE mvcboard SET "
				+ " visitcount=visitcount+1 "
				+ " WHERE idx=?";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			psmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("게시물 조회수 증가 중 예외 발생");
			e.printStackTrace();
		}
	}
	
	//(삭제)입력한 비밀번호가 지정한 일련번호의 게시물의 비밀전호와 일치하는지 확인 
	public boolean confirmPassword(String pass, String idx) {
		boolean isCorr = true;
		try {
			String query = "SELECT COUNT(*) FROM mvcboard WHERE pass=? AND idx=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, pass);
			psmt.setString(2, idx);
			rs = psmt.executeQuery();
			rs.next();
			
			if(rs.getInt(1)==0) {
				isCorr = false;
			}
			
		}catch(Exception e) {
			isCorr = false;
			e.printStackTrace();
		}
		return isCorr;
	}
	
	//(삭제)지정한 일련번호의 게시물을 삭제
	public int deletePost(String idx) {
		int result = 0;
		try {
			String query = "DELETE FROM mvcboard WHERE idx=?";
			psmt = con.prepareStatement(query);
			psmt.setString(1, idx);
			result = psmt.executeUpdate();
			System.out.println("[삭제 처리] idx = " + idx + ", result = " + result);
			
		}catch(Exception e) {
			System.out.println("게시물 삭제 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	//(수정)게시글 데이터를 받아 DB에 저장되어 있던 내용을 갱신
	public int updatePost(MVCBoardDTO dto) {
		int result = 0;
		try {
			String query = "UPDATE mvcboard SET "
					+ " title=?, name=?, content=?, ofile=?, sfile=? "
					+ " WHERE idx=? and pass=?";
			
			psmt = con.prepareStatement(query);
			psmt.setString(1, dto.getTitle());
			psmt.setString(2, dto.getName());
			psmt.setString(3, dto.getContent());
			psmt.setString(4, dto.getOfile());
			psmt.setString(5, dto.getSfile());
			psmt.setString(6, dto.getIdx());
			psmt.setString(7, dto.getPass());
			
			//쿼리문 실행
			result = psmt.executeUpdate();
			
		}catch(Exception e) {
			System.out.println("게시물 수정 중 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	

	
}
