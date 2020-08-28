import java.io.*;

import java.net.Socket;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.InetSocketAddress;

public class LocalDNS_Client {

	public static String FindDomain(String ip) {
		try{
			File file = new File(".\\LocalDNS.txt");

			FileReader reader = new FileReader(file);

			BufferedReader bufReader = new BufferedReader(reader);

			String line = "";

			while((line = bufReader.readLine()) != null){
				// ���� ���ڿ��� �����̽� ' '�� �������� �ɰ���.
				StringTokenizer tokenizer = new StringTokenizer(line, " ");
				if( tokenizer == null || tokenizer.countTokens() <= 0 )
					continue;

				if( tokenizer.nextToken().equals(ip) == true )
					return tokenizer.nextToken();
			}

			bufReader.close();
			reader.close();
		}catch (Exception e) {
		}

		return ip; // ã�� �� ���� ��� �޾Ҵ� ip�� �״�� ��ȯ �Ѵ�.
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);

			InetAddress addr[] = null;
			System.out.print("�������� �������̳� ������ �Է� : ");
			String remote = scanner.nextLine(); 

			boolean connected = false;
			try {
				String domain = "";

				addr = InetAddress.getAllByName(remote);
				if( addr[0].getHostAddress().equals(remote) == true ) { // ���� ip���, getHostAddress ���� ip �� ���̹Ƿ� ip�� ��쿡�� �Ʒ� �ڵ尡 ���� �ȴ�.
					domain = FindDomain(remote); // ���Ϸ� ���� ip�� �ش��ϴ� �������� ã�� ��ȯ �޴� ��.
				}else {
					domain = addr[0].getHostAddress();
				}

				System.out.println( "������ " + domain + "(��)�� ���� �õ�" );

				try {
					// Ÿ�Ӿƿ��� �ɱ� ���� Socket ��ü ������ ���ÿ� ���ӽ�Ű���� ����� ��� ���� �ʴ� ��.
					// �⺻ ���� Ÿ�Ӿƿ��� ��������� �� ����� �������� Ȯ������ ���Ѵ�.
					socket = new Socket();
					socket.setSoTimeout(1000); // ������ ���� �� Ÿ�Ӿƿ� 1�ʷ� ���� 

					SocketAddress address = new InetSocketAddress(addr[0].getHostAddress(), 9999);
					socket.connect(address, 1000); // ���ӿ� ���� Ÿ�Ӿƿ� 1��

					connected = true;
				}catch(Exception e) {
					// �������� ���� ������ ������ ���� ���� ���
					connected = false;
				}
			} catch (Exception e) {
				// �����ο��� ��巹���� ������ ���� ���� ���
				connected = false;
			}

			if( connected == true ) {
				System.out.println("�������� ���� ����. " + addr[0]);
			}
			else {
				System.out.println("ã�� �� ����");
			}
		}finally {
			try {
				if(socket != null) 
					socket.close();

				scanner.close();
			}catch(IOException e) {
				System.out.println("������ ��� �� ������ �߻��߽��ϴ�.");
			}
		}
	}
}