package word;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

public class Auto {
			int n=0;
			int i=0;
	
	public String []  jianche() {
		//�Զ���ȡ�ļ�
			 Scanner sc=new Scanner(System.in);
			  System.out.println("���������ļ�����");
			   this.n=sc.nextInt();
			  String []s=new String[n];
			  for(int i=0;i<n;i++) {
				  String str=new String("E:\\\\"+i+"in.txt");
				  System.out.println(str);
				  s[i]=str;
			
			  	}
			  return s;
	}
	
	public void shuchu(List<Score> scores) {
		//�Զ����
		 FileOutputStream fs=null;
	        PrintStream p =null;
			try {
				
					  String str=new String("E:\\\\"+i+"out.txt");
					  fs = new FileOutputStream(new File(str));
						p= new PrintStream(fs);
						 for (Score score1 : scores) {
					           
					            p.println(score1);
					        }
						 i++;
						 p.close();
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				   p.close();
			}
		
	}

}
