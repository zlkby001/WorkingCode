package cn.ac.sklois.imm.upload;
import java.io.*;
public class test {
//功能:读取f:/aillo.txt文件的内容(一行一行读),并将其内容写入f:/jackie.txt中
//知识点:java读文件、写文件---<以字符流方式>
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("f:/Measurements_of_RPM");//创建FileReader对象，用来读取字符流
            BufferedReader br = new BufferedReader(fr);    //缓冲指定文件的输入

            String myreadline;    //定义一个String类型的变量,用来每次读取一行
            while (br.ready()) {
                myreadline = br.readLine();//读取一行
                int pos = myreadline.indexOf("=");
                String name = myreadline.substring(0, pos);
                String hash = myreadline.substring(pos+1);
                
                System.out.println(name+"****"+hash);//在屏幕上输出
            }
            br.close();
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
