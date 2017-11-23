package com.oracle.messenger.control;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import com.oracle.messenger.config.SocketConfig;
/**
 * д��������ҵ����
 * @author TengSir
 */
public class Server {
	private ServerSocket  server;//����һ������������
	public static  final Map<String, ObjectOutputStream> clients;
	static{
		clients=new HashMap<String, ObjectOutputStream>();
	}
	public Server(){
		try {
			server=new ServerSocket(SocketConfig.port);//����������
			System.out.println("Server started successfully ,now is running!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "����������ʧ�ܣ����˿ڻ���IP�Ƿ��ͻ!", "״̬��Ϣ", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	public void startService(){
		/**
		 * ѭ���ȴ��ͻ�����
		 */
		while(true)
		{
			try {
				Socket client=server.accept();//��������ʼ�ṩ����
				//����һ���̣߳���ר�Ŷ�ȡ��socket�������������
				MessageReciver  r=new MessageReciver(client);
				r.start();//�������̣߳��ø��̶߳�ȡsocket������
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new Server().startService();
	}
}