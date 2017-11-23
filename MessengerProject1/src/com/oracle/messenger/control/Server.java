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
 * 写服务器的业务功能
 * @author TengSir
 */
public class Server {
	private ServerSocket  server;//声明一个服务器对象
	public static  final Map<String, ObjectOutputStream> clients;
	static{
		clients=new HashMap<String, ObjectOutputStream>();
	}
	public Server(){
		try {
			server=new ServerSocket(SocketConfig.port);//创建服务器
			System.out.println("Server started successfully ,now is running!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "服务器启动失败，检查端口或者IP是否冲突!", "状态消息", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	public void startService(){
		/**
		 * 循环等待客户连接
		 */
		while(true)
		{
			try {
				Socket client=server.accept();//服务器开始提供服务
				//开启一个线程，来专门读取该socket传入进来的数据
				MessageReciver  r=new MessageReciver(client);
				r.start();//启动该线程，让该线程读取socket的数据
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		new Server().startService();
	}
}