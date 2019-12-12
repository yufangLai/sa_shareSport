package ncu.im3069.demo.controller;

import java.io.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.json.*;

import ncu.im3069.demo.app.Coach;
import ncu.im3069.demo.app.CoachHelper;
import ncu.im3069.demo.app.Follow;
import ncu.im3069.demo.app.FollowHelper;
import ncu.im3069.tools.JsonReader;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * The Class FollowController<br>
 * FollowController類別（class）主要用於處理Follow相關之Http請求（Request），繼承HttpServlet
 * </p>
 * 
 * @author IPLab
 * @version 1.0.0
 * @since 1.0.0
 */
@WebServlet("/api/follow.do")
public class FollowController extends HttpServlet {
	/** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** fh，FollowHelper之物件與Follow相關之資料庫方法（Sigleton） */
    private FollowHelper fh =  FollowHelper.getHelper();
    
    /**
     * 處理Http Method請求POST方法（新增資料）
     *
     * @param request Servlet請求之HttpServletRequest之Request物件（前端到後端）
     * @param response Servlet回傳之HttpServletResponse之Response物件（後端到前端）
     * @throws ServletException the servlet exception
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        /** 透過JsonReader類別將Request之JSON格式資料解析並取回 */
        JsonReader jsr = new JsonReader(request);
        JSONObject jso = jsr.getObject();
        
        /** 取出經解析到JSONObject之Request參數 */
        int stuId = jso.getInt("follows_student_id");
        int coaId = jso.getInt("follows_coach_id ");
        
        /** 建立一個新的教練物件 */
        Follow f = new Follow(stuId, coaId);
        
//        /** 後端檢查是否有欄位為空值，若有則回傳錯誤訊息 */
//        if(email.isEmpty() || password.isEmpty() || name.isEmpty()|| sex.isEmpty()) {
//            /** 以字串組出JSON格式之資料 */
//            String resp = "{\"status\": \'400\', \"message\": \'欄位不能有空值\', \'response\': \'\'}";
//            /** 透過JsonReader物件回傳到前端（以字串方式） */
//            jsr.response(resp, response);
//        }
        /** 透過FollowHelper物件的checkDuplicate()檢查該追蹤紀錄是否有重複 */
        if (!fh.checkDuplicate(f)) {
            /** 透過FollowHelper物件的create()方法新建一筆追蹤紀錄至資料庫 */
            JSONObject data = fh.create(f);
            
            /** 新建一個JSONObject用於將回傳之資料進行封裝 */
            JSONObject resp = new JSONObject();
            resp.put("status", "200");
            resp.put("message", "成功! 註冊教練資料...");
            resp.put("response", data);
            
            /** 透過JsonReader物件回傳到前端（以JSONObject方式） */
            jsr.response(resp, response);
        }
        else {
            /** 以字串組出JSON格式之資料 */
            String resp = "{\"status\": \'400\', \"message\": \'追蹤失敗，此追蹤資料重複！\', \'response\': \'\'}";
            /** 透過JsonReader物件回傳到前端（以字串方式） */
            jsr.response(resp, response);
        }
    }
    
    
}