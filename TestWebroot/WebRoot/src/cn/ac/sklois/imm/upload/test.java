package cn.ac.sklois.imm.upload;
import java.io.*;
public class test {
//����:��ȡf:/aillo.txt�ļ�������(һ��һ�ж�),����������д��f:/jackie.txt��
//֪ʶ��:java���ļ���д�ļ�---<���ַ�����ʽ>
    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader("f:/Measurements_of_RPM");//����FileReader����������ȡ�ַ���
            BufferedReader br = new BufferedReader(fr);    //����ָ���ļ�������

            String myreadline;    //����һ��String���͵ı���,����ÿ�ζ�ȡһ��
            while (br.ready()) {
                myreadline = br.readLine();//��ȡһ��
                int pos = myreadline.indexOf("=");
                String name = myreadline.substring(0, pos);
                String hash = myreadline.substring(pos+1);
                
                System.out.println(name+"****"+hash);//����Ļ�����
            }
            br.close();
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
