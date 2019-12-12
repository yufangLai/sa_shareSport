package ncu.im3069.demo.app;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

import org.json.*;

public class Follow{
	private int id;
	private int follows_student_id;
	private int follows_coach_id;
	private JSONObject studentName;
	private JSONObject coachName;

	private StudentHelper stuh =  StudentHelper.getHelper();
	private CoachHelper coah =  CoachHelper.getHelper();

    public Follow(int id, int follows_student_id, int follows_coach_id) {
    	this.id = id; 
    	this.follows_student_id = follows_student_id;
    	this.follows_coach_id = follows_coach_id;
    }
    public Follow(int follows_student_id, int follows_coach_id) {
    	this.follows_student_id = follows_student_id;
    	this.follows_coach_id = follows_coach_id;
    }
    private int getId() {
		return this.id;
	}
    public int getFollowsStudentId() {
        return this.follows_student_id;
    }
    public int getFollowsCoachId() {
        return this.follows_coach_id;
    }

    public JSONObject getStudentName(int stuId) {
    	String id = String.valueOf(stuId);
        this.studentName = stuh.getNameByID(id);
        return studentName;
    }
    public JSONObject getCoachName(int coaId) {
    	String id = String.valueOf(coaId);
        this.coachName = coah.getNameByID(id);
        return coachName;
    }
    /**
     * 取得該名教練所有資料
     *
     * @return the data 取得該名教練之所有資料並封裝於JSONObject物件內
     */
    public JSONObject getData() {
        /** 透過JSONObject將該名教練所需之資料全部進行封裝*/ 
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("follows_student_id", getFollowsStudentId());
        jso.put("student_name", getStudentName(getFollowsStudentId()));
        jso.put("follows_coach_id", getFollowsCoachId());
        jso.put("coach_name", getCoachName(getFollowsCoachId()));
        return jso;
    }
    //get coach's follower's id and name
    public JSONObject getCoachFollowData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("follows_student_id", getFollowsStudentId());
        jso.put("student_name", getStudentName(getFollowsStudentId()));
        return jso;
    }

	//get student's follow list of coaches'id and name
    public JSONObject getStudentFollowData() {
        JSONObject jso = new JSONObject();
        jso.put("id", getId());
        jso.put("follows_coach_id", getFollowsCoachId());
        jso.put("coach_name", getCoachName(getFollowsCoachId()));
        return jso;
    }
}