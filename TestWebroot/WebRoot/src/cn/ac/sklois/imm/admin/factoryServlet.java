package cn.ac.sklois.imm.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.net.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import cn.ac.sklois.imm.mappings.MappingService;

import com.java1234.util.*;
public class factoryServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public factoryServlet() {
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

		String type = request.getParameter("type");
		if (type.equals("add")) {
			
//			String name = Methods.unescape(request.getParameter("name"));
			String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
			String fid = request.getParameter("fid");
			MappingService ms = new MappingService();
			int r = ms.addFactory(name, fid);

			JSONObject result = new JSONObject();
			if (r > 0) {
				result.put("newid", r);
			} else {
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(response, result);
		} else if (type.equals("delete")) {
			
			String id = request.getParameter("id");
			MappingService ms = new MappingService();
			int delNums = ms.deleteFactory(id);

			JSONObject result = new JSONObject();
			if (delNums > 0) {
				result.put("success", "true");
			} else {
				result.put("errorMsg", "ɾ��ʧ��");
			}
			ResponseUtil.write(response, result);
		}
		else if (type.equals("edit")) {			
			String id = request.getParameter("id");
			String name = URLDecoder.decode(request.getParameter("name"),"UTF-8");
			MappingService ms = new MappingService();
			int delNums = ms.updateFactory(id,name);

			JSONObject result = new JSONObject();
			if (delNums > 0) {
				result.put("success", "true");
			} else {
				result.put("errorMsg", "�޸�ʧ��");
			}
			ResponseUtil.write(response, result);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
