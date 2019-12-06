package ncu.im3069.demo.controller;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;
import ncu.im3069.demo.app.Admin;
import ncu.im3069.demo.app.AdminHelper;
import ncu.im3069.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class StudentController<br>
 * StudentController類別（class）主要用於處理Student相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
@WebServlet("/api/admin.do")
public class AdminController extends HttpServlet {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** ah，AdminHelper之物件與Admin相關之資料庫方法（Sigleton） */
    private AdminHelper ah = AdminHelper.getHelper();
    
   
    /**
     * 處理Http Method請求GET方法（取得資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        /** 若直接透過前端AJAX之data以key=value之字串方式進行傳遞參數，可以直接由此方法取回資料 */
        String id = jsr.getParameter("id");
        
        /** 判斷該字串是否存在，若存在代表要取回個別管理員之資料，否則代表要取回全部資料庫內管理員之資料 */
        if (id.isEmpty()) {
            /** 透過MemberHelper物件之getAll()方法取回所有管理員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = ah.getAll();
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "所有管理員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 透過StudentHelper物件的getByID()方法自資料庫取回該名管理員之資料，回傳之資料為JSONObject物件 */
            JSONObject query = ah.getByID(id);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "管理員資料取得成功");
            resp.put("response", query);
    
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
    }

    /**
     * 處理Http Method請求DELETE方法（刪除）停權是用update，不會刪除管理員
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("id");
        
        /** 透過MemberHelper物件的deleteByID()方法至資料庫刪除該名管理員，回傳之資料為JSONObject物件 */
        JSONObject query = ah.deleteByID(id);
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "管理員移除成功！");
        resp.put("response", query);

        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }

    /**
     * 處理Http Method請求PUT方法（更新）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPut(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int id = jso.getInt("id");
        String email = jso.getString("email");
        String password = jso.getString("password");
        String name = jso.getString("name");
        int status = jso.getInt("status");

        /** 透過傳入之參數，新建一個以這些參數之管理員物件 */
        Admin a = new Admin(id, email, password, name, status);
        
        /** 透過Admin物件的update()方法至資料庫更新該名管理員資料，回傳之資料為JSONObject物件 */
        JSONObject data = a.update();
        
        /** 新建一個JSONObject用於將回傳之資料進行封裝 */
        JSONObject resp = new JSONObject();
        resp.put("status", "200");
        resp.put("message", "成功! 更新管理員資料...");
        resp.put("response", data);
        
        /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
        jsr.response(resp, response);
    }
}