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
			System.out.println("������ ��ٸ��� �ֽ��ϴ�....");
			socket = listener.accept();
			System.out.println("����Ǿ����ϴ�.");

			in = new DataInputStream(socket.getInputStream());

			while(true){
				int fileSize = in.readInt();
				String filename = in.readUTF(); 
							
				System.out.println("���� ���� ������ ũ�� "+ fileSize + ", ���� " + filename );
				
				File file = new File(filename);
				out = new FileOutputStream(file);

				byte[] buffer = new byte[1024];
				

				for(int received = 0;received < fileSize;)
				{
					int len = in.read(buffer);
					out.write(buffer,0,len); 
					received += len;
				}

				System.out.println("���� �Ϸ�� ������ ũ�� "+ fileSize);
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
				System.out.println("Ŭ���̾�Ʈ�� ��� �� ������ �߻��߽��ϴ�.");
			}

		}
	}

}
