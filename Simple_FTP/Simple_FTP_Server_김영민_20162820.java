import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.FileOutputStream;

public class Simple_FTP_Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DataInputStream in = null;
		FileOutputStream out = null;
		ServerSocket listener = null; 
		Socket socket = null;

		try {
			listener = new ServerSocket(9999);
			System.out.println("연결을 기다리고 있습니다....");
			socket = listener.accept();
			System.out.println("연결되었습니다.");

			in = new DataInputStream(socket.getInputStream());

			while(true){
				int fileSize = in.readInt();
				String filename = in.readUTF(); 
							
				System.out.println("전송 받을 파일의 크기 "+ fileSize + ", 파일 " + filename );
				
				File file = new File(filename);
				out = new FileOutputStream(file);

				byte[] buffer = new byte[1024];
				

				for(int received = 0;received < fileSize;)
				{
					int len = in.read(buffer);
					out.write(buffer,0,len); 
					received += len;
				}

				System.out.println("전송 완료된 파일의 크기 "+ fileSize);
				out.flush();
				out.close();
			}

		}catch(IOException e) {
			System.out.println(e.getMessage());
		}finally {
			try{
				in.close();
				socket.close();
				listener.close();
			}catch(IOException e) {
				System.out.println("클라이언트와 통신 중 오류가 발생했습니다.");
			}

		}
	}

}
