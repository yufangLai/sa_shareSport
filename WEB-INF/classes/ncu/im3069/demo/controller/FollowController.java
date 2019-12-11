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
    
    
    
}