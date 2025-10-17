package fileupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

public class FileUtil {
	public static String uploadFile(HttpServletRequest req, String sDirectory) throws ServletException, IOException {
		Part part = req.getPart("ofile"); //Part 객체를 통해 서버로 전송된 파일명 읽어 오기
		String partHeader = part.getHeader("content-disposition"); //Part 객체의 헤더값 중 content-disposition읽어 오기
		System.out.println("partHeader="+partHeader);
		
		String[] phArr = partHeader.split("filename=");	//헤더 문자열에서 filename=을 기준으로 문자열 분리 
		String originalFileName = phArr[1].trim().replace("\"", ""); //분리된 문자열 배열에서 파일명 추출
		
		if(!originalFileName.isEmpty()) {
			part.write(sDirectory + File.separator + originalFileName); //파일명이 있으면 디렉토리에 파일을 저장
		}
		return originalFileName; //파일명 반환
	}
	
	//파일명 변경
	public static String renameFile(String sDirectory, String fileName) {
		String ext = fileName.substring(fileName.lastIndexOf("."));
				String now =  new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
				String newFileName = now + ext;
				
				File oldFile = new File(sDirectory + File.separator + fileName);
				File newFile = new File(sDirectory + File.separator + newFileName);
				oldFile.renameTo(newFile);
				
		return newFileName;
	}
	
	//multiple 송성 추가로 2개 이상의 파일 업로드
	public static ArrayList<String> multipleFile(HttpServletRequest req, String sDirectory)  throws ServletException, IOException  {
		
		ArrayList<String> listFileName = new ArrayList<>();
		Collection<Part> parts = req.getParts();
		for(Part part : parts) {
			if(!part.getName().equals("ofile"))
				continue;
			
			String partHeader = part.getHeader("content-disposition");
			String[] phArr = partHeader.split("filename=");
			String originalFileName = phArr[1].trim().replace("\"", "");
			if(!originalFileName.isEmpty()) {
				part.write(sDirectory+ File.separator + originalFileName);
			}
			listFileName.add(originalFileName);
		}
				
		return listFileName;
	}
	
	//명시한 파일을 찾아 다운로드
	public static void download(HttpServletRequest req, HttpServletResponse resp, String directory, String sfileName, String ofileName) {
	    
		String sDirectory = req.getServletContext().getRealPath(directory);
		try {
			//파일을 찾아 입력 스트림 생성
			File file = new File(sDirectory, sfileName);
			InputStream iStream = new FileInputStream(file);
			
			//한글 파일명 깨짐 방지
			String client = req.getHeader("User-Agent");
			if (client.indexOf("WOW64") == -1) {
				ofileName = new String(ofileName.getBytes("UTF-8"), "ISO-8859-1");
			}else {
				ofileName = new String(ofileName.getBytes("KSC5601"), "ISO-8859-1");
			}
			
			//파일 다운로드용 응답 헤더 설정
			resp.reset();
			resp.setContentType("application/octet-stream");
			resp.setHeader("Content-Disposition", "attachment; filename=\""+ ofileName + "\"");
			resp.setHeader("Content-Length", "" + file.length());
			
			//out.clear(); //출력 스트림 초기화
			
			//response 내장 객체로부터 새로운 출력 스트림 생성
			OutputStream oStream = resp.getOutputStream();
			
			
			//출력 스트림에 파일 내용 출력
			byte b[] = new byte[(int)file.length()];
			int readBuffer = 0;
			while ((readBuffer  = iStream.read(b)) > 0) {
				oStream.write(b, 0, readBuffer);
			}
			
			//입출력 스트림 닫음
			iStream.close();
			oStream.close();
		}catch (FileNotFoundException e) {
			System.out.println("파일을 찾을 수 없습니다.");
			e.printStackTrace();
			
		}catch (Exception e) {
			System.out.println("예외가 발생하였습니다.");
			e.printStackTrace();
			
		}
		
		
	}

	//(삭제) 지정한 위치의 파일을 삭제
	//(삭제) 지정한 위치의 파일을 삭제
	public static void deleteFile(HttpServletRequest req, String directory, String filename) {
	    String sDirectory = req.getServletContext().getRealPath(directory);  // 매개변수 'directory' 사용
	    File file = new File(sDirectory + File.separator + filename);
	    if (file.exists()) {
	        file.delete();
	    }
	}

	
}
