import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

public class Simple_FTP_Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileInputStream in = null; 
		DataOutputStream out = null; 
		Socket socket = null;

		Scanner scanner = new Scanner(System.in); 
		
		try {
			socket = new Socket("localhost", 9999);
			out = new DataOutputStream(socket.getOutputStream()); 
		

		while (true) {
				String filename = scanner.next(); 
				File file = new File(filename);
				in = new FileInputStream(file); 
								
				int fileSize = 0;				
				byte [] buffer = new byte[1024]; 
				
				while(true) {
					int len = in.read(buffer);				
					if( len <= 0 )
						break;
					
					fileSize += len;
				}
				in.close(); 
				
				String fileNameOnly = file.getName(); 
				System.out.println("전송 할 파일 크기 " + fileSize + ", 파일명 " + fileNameOnly );

				out.writeInt(fileSize);
				out.writeUTF(fileNameOnly);
				
				int sentSize = 0; 
				
				in = new FileInputStream(file);
				while(true) {
					int len = in.read(buffer); 
					if( len <= 0 ) 
						break;
					
					out.write(buffer, 0, len);
					sentSize += len;
					
					if( sentSize >= fileSize ) 
						break;
				}
				
				out.flush();
				in.close();
				System.out.println("전송 완료 파일 크기 "+ fileSize);
			}
			
		}catch(IOException e) {
			System.out.println(e.getMessage());
			
		}finally {
			try {
				out.close(); 
				if(socket != null) 
					socket.close();
				
				scanner.close();
			}catch(IOException e) {
				System.out.println("서버와 통신 중 오류가 발생했습니다.");
			}
		}
	}

}
