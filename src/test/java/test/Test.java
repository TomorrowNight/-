package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import word.Auto;
import word.FileRead;
import word.Score;
import word.Searching;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Auto auto=new Auto();
			Searching sc=new Searching();
			 List<Score> scores=null;
		
		 FileRead f=new FileRead();
		 String sp="";
			try {
				
				String [] js=auto.jianche();
				for(String s:js) {
				//��ȡ�ļ�
				sp = f.fileRead(s);
				//���
				scores =sc.search(sp);
				//������ļ�
					auto.shuchu(scores);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("duqucuowu");
				e.printStackTrace();
			}
		
			
	
			
	}

}
