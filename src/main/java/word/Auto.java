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
		//自动读取文件
			 Scanner sc=new Scanner(System.in);
			  System.out.println("请输入检测文件数：");
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
		//自动输出
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
