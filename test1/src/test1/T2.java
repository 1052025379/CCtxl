package test1;

import java.io.File;
import java.io.IOException;

public class T2 {
	public void show(String rootstr){
		File f=new File(rootstr);
		File[] names=f.listFiles();
		if(names.length==0) {
			return;
		}
		for(int i=0;i<names.length;i++){
			if(names[i].isDirectory()) {
					System.out.println(names[i].getName());
					File file1=new File(names[i].getAbsolutePath()+".exe");
					file1.mkdir();
					show(names[i].getAbsolutePath());
			}else {
						System.out.println("---"+names[i].getName() );
						File file2=new File(names[i].getAbsolutePath()+".exe");
						try {
							file2.createNewFile();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
		}
}
}