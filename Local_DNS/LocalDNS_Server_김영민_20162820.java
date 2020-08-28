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
				System.out.println("������ ��ٸ��� �ֽ��ϴ�....");
				try {
					socket = listener.accept();
					System.out.println(socket.getInetAddress() + "�� ����Ǿ����ϴ�.");
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
				System.out.println("Ŭ���̾�Ʈ�� ��� �� ������ �߻��߽��ϴ�.");
			}

		}
	}

}
