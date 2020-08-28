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
				// 읽은 문자열을 스페이스 ' '를 기준으로 쪼갠다.
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

		return ip; // 찾을 수 없는 경우 받았던 ip를 그대로 반환 한다.
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Socket socket = null;
		Scanner scanner = null;

		try {
			scanner = new Scanner(System.in);

			InetAddress addr[] = null;
			System.out.print("원격지의 도메인이나 아이피 입력 : ");
			String remote = scanner.nextLine(); 

			boolean connected = false;
			try {
				String domain = "";

				addr = InetAddress.getAllByName(remote);
				if( addr[0].getHostAddress().equals(remote) == true ) { // 만약 ip라면, getHostAddress 값도 ip 일 것이므로 ip인 경우에만 아래 코드가 실행 된다.
					domain = FindDomain(remote); // 파일로 부터 ip에 해당하는 도메인을 찾아 반환 받는 다.
				}else {
					domain = addr[0].getHostAddress();
				}

				System.out.println( "원격지 " + domain + "(으)로 접속 시도" );

				try {
					// 타임아웃을 걸기 위해 Socket 객체 생성과 동시에 접속시키지는 기능은 사용 하지 않는 다.
					// 기본 접속 타임아웃은 상대적으로 길어서 결과를 빨리빨리 확인하지 못한다.
					socket = new Socket();
					socket.setSoTimeout(1000); // 데이터 읽을 때 타임아웃 1초로 설정 

					SocketAddress address = new InetSocketAddress(addr[0].getHostAddress(), 9999);
					socket.connect(address, 1000); // 접속에 대한 타임아웃 1초

					connected = true;
				}catch(Exception e) {
					// 도메인은 존재 하지만 접속을 하지 못한 경우
					connected = false;
				}
			} catch (Exception e) {
				// 도메인에서 어드레스를 가지고 오지 못한 경우
				connected = false;
			}

			if( connected == true ) {
				System.out.println("원격지에 접속 가능. " + addr[0]);
			}
			else {
				System.out.println("찾을 수 없음");
			}
		}finally {
			try {
				if(socket != null) 
					socket.close();

				scanner.close();
			}catch(IOException e) {
				System.out.println("서버와 통신 중 오류가 발생했습니다.");
			}
		}
	}
}