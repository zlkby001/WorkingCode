package cn.ac.sklois.imm.mappings;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import cn.ac.sklois.imm.admin.AdminService;

import com.java1234.util.ResponseUtil;

public class deleteAlertDetailServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public deleteAlertDetailServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String delIds = request.getParameter("delIds");
		;
		AdminService as=new AdminService();
		boolean b = as.deleteAlertDetail(delIds);
		JSONObject result=new JSONObject();
		if(b){
			result.put("success", "true");
		}
		else{
			result.put("errorMsg", "删除失败");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request,response);
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
