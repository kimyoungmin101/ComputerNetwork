import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LocalDNS_Server {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerSocket listener = null;
		Socket socket = null;

		try {
			listener = new ServerSocket(9999);
			
			while(true) {
				System.out.println("연결을 기다리고 있습니다....");
				try {
					socket = listener.accept();
					System.out.println(socket.getInetAddress() + "와 연결되었습니다.");
				}
				catch(Exception e){
					break;	
				}
			}

		}catch(IOException e) {
			System.out.println(e.getMessage());
		}finally {
			try{
				socket.close();
				listener.close();
			}catch(IOException e) {
				System.out.println("클라이언트와 통신 중 오류가 발생했습니다.");
			}

		}
	}

}
