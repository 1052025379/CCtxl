package com.oracle.corejava.test;

import java.io.File;

public class FileDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File f=new File("C:");
		String[] tree=f.list();
		for(String s:tree)
		{
			System.out.println(s);
		}
	}

}
