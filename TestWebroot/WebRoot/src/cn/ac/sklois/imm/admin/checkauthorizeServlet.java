package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java1234.util.ResponseUtil;

public class checkauthorizeServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public checkauthorizeServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//授权验证出错返回-1,验证成功返回0,试用版本返回试用天数
		
		AuthVerify verifier=new AuthVerify();
		int verifyres=verifier.Verify();
		if(verifyres==-1||verifyres==-3||verifyres==0||verifyres==1)
		{
			ResponseUtil.write(response, verifyres);
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String now=df.format(new Date());
		long times=0;
		try {
			times=(df.parse(Integer.toString(verifyres)).getTime()-df.parse(now).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		times=times/1000/60/60/24+1;
		ResponseUtil.write(response, times);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		AuthVerify verifier=new AuthVerify();
		int verifyres=verifier.Verify();
		if(verifyres==-1||verifyres==-3||verifyres==0||verifyres==1)
		{
			ResponseUtil.write(response, verifyres);
			return;
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String now=df.format(new Date());
		long times=0;
		try {
			times=(df.parse(Integer.toString(verifyres)).getTime()-df.parse(now).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		times=times/1000/60/60/24+1;
		ResponseUtil.write(response, times);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
