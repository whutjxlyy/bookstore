package cn.whutjxl.bookstore.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

	public static void copyfile(String sPath,String dPsth,String name){
		try {
			FileInputStream fin=new FileInputStream(new File(sPath+name));
			FileOutputStream fout=new FileOutputStream(new File(dPsth+name));
			
			byte[] bytes=new byte[1024];
			int n;
			while((n=fin.read(bytes))!=-1){
				fout.write(bytes, 0, n);
			}
			fin.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
