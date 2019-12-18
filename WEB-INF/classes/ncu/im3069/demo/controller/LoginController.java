package ncu.im3069.demo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import org.json.JSONObject;
import org.json.*;

import ncu.im3069.demo.app.CoachHelper;
import ncu.im3069.demo.app.StudentHelper;
import ncu.im3069.demo.app.AdminHelper;
import ncu.im3069.tools.JsonReader;

@WebServlet("/api/login.do")
public class LoginController extends HttpServlet {
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** sh，StudentHelper之物件與Student相關之資料庫方法（Sigleton） */
    private StudentHelper sh =  StudentHelper.getHelper();
    private CoachHelper ch =  CoachHelper.getHelper();
    private AdminHelper ah =  AdminHelper.getHelper();
//    private String role = null;
    private String srole;
    private String crole;
    private String arole;
    
	@SuppressWarnings("unused")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		srole = (String) request.getParameter("student");
		crole = (String) request.getParameter("coach");
		arole = (String) request.getParameter("admin");

		if(arole != null) {
			System.out.println(arole);
			boolean flag = ah.checkLogin(email, password);
			JSONObject result = ah.getLogin(email, password);
			
			System.out.println(flag);
			System.out.println(result.getJSONArray("data").getJSONObject(0).get("name"));
			System.out.println(result.getJSONArray("data").getJSONObject(0).get("id"));
			
			//String name = (String) result.getJSONArray("data").getJSONObject(0).get("name");
			// 编码，解决中文乱码  
			String name = URLEncoder.encode((String) result.getJSONArray("data").getJSONObject(0).get("name"),"utf-8");
			String id = URLEncoder.encode(Integer.toString((int) result.getJSONArray("data").getJSONObject(0).get("id")),"utf-8");
			
			if(flag){
				Cookie idCookie = new Cookie("id", id);
				Cookie nameCookie = new Cookie("name", name);
				//setting cookie to expiry in 30 mins
				idCookie.setMaxAge(30*60);
				nameCookie.setMaxAge(30*60);
				response.addCookie(idCookie);
				response.addCookie(nameCookie);
				response.sendRedirect("/sa_shareSport/index.html");
			}else{
//				RequestDispatcher rd = getServletContext().getRequestDispatcher("/NCU_MIS_SA/index.html");
//				PrintWriter out= response.getWriter();
//				out.println("<font color=red>Either user name or password is wrong.</font>");
//				rd.include(request, response);
				response.sendRedirect("/sa_shareSport/index.html");
				System.out.println("not correct");
			}
		}
		if(srole != null) {
			System.out.println(srole);
			JSONObject result = sh.checkLogin(email, password);
			
			System.out.println(result.getJSONArray("data").getJSONObject(0).get("name"));
			System.out.println(result.getJSONArray("data").getJSONObject(0).get("id"));
			
			//String name = (String) result.getJSONArray("data").getJSONObject(0).get("name");
			// 编码，解决中文乱码  
			String name = URLEncoder.encode((String) result.getJSONArray("data").getJSONObject(0).get("name"),"utf-8");
			String id = URLEncoder.encode(Integer.toString((int) result.getJSONArray("data").getJSONObject(0).get("id")),"utf-8");
			
			if(result != null){
				Cookie idCookie = new Cookie("id", id);
				Cookie nameCookie = new Cookie("name", name);
				//setting cookie to expiry in 30 mins
				idCookie.setMaxAge(30*60);
				nameCookie.setMaxAge(30*60);
				response.addCookie(idCookie);
				response.addCookie(nameCookie);
				response.sendRedirect("/sa_shareSport/index.html");
			}else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/sa_shareSport/index.html");
				PrintWriter out= response.getWriter();
				out.println("<font color=red>Either user name or password is wrong.</font>");
				rd.include(request, response);
			}
		}
		if(crole != null){
			System.out.println(crole);
			JSONObject result = ch.checkLogin(email, password);
			
			System.out.println(result.getJSONArray("data").getJSONObject(0).get("name"));
			System.out.println(result.getJSONArray("data").getJSONObject(0).get("id"));
			
			// 编码，解决中文乱码  
			String name = URLEncoder.encode((String) result.getJSONArray("data").getJSONObject(0).get("name"),"utf-8");
			String id = URLEncoder.encode(Integer.toString((int) result.getJSONArray("data").getJSONObject(0).get("id")),"utf-8");
			
			if(result != null){
				Cookie idCookie = new Cookie("id", id);
				Cookie nameCookie = new Cookie("name", name);
				//setting cookie to expiry in 30 mins
				idCookie.setMaxAge(30*60);
				nameCookie.setMaxAge(30*60);
				response.addCookie(idCookie);
				response.addCookie(nameCookie);
				response.sendRedirect("/sa_shareSport/index.html");
			}else{
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/sa_shareSport/index.html");
				PrintWriter out= response.getWriter();
				out.println("<font color=red>Either user name or password is wrong.</font>");
				rd.include(request, response);
			}
		}
		
		
		
		
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
			
	    	String userId = null;
			Cookie[] cookies = request.getCookies();
			if(cookies !=null){
				for(Cookie cookie : cookies){
					if(cookie.getName().equals("id")) userId = cookie.getValue();
				}
			}
			if(userId == null) response.sendRedirect("/sa_shareSport/login.html");
			
			//System.out.println(userId);
	       
			/** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
	        JsonReader jsr = new JsonReader(request);
	        JSONObject query = null;

	        /** 透過StudentHelper物件的getByID()方法自資料庫取回該名會員之資料，回傳之資料為JSONObject物件 */
	        if(arole != null) {
	        	query = ah.getByID(userId);
	        }
	        if(srole != null) {
		        query = sh.getByID(userId);
	        }
	        if(crole != null){
	        	query = ch.getByID(userId); 
	        }
   
	        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
	        JSONObject resp = new JSONObject();
	        resp.put("status", "200");
	        resp.put("message", "cookie資料取得成功");
	        resp.put("response", query);
	    
	        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
	        jsr.response(resp, response);
	    }
}