package com.java.jdbc.dbutils;

import java.io.Serializable;
import java.util.Date;

public class BoxUser implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private String user_id;

    public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
     * 用户编号
     */
    private String userNo;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 密码
     */
    private String userPass;

    /**
     * 电话
     */
    private String tel;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ
     */
    private String qq;

    /**
     * 性别
     */
    private String sex;

    /**
     * 证件类别
     */
    private Integer idType;

    /**
     * 证件号码
     */
    private String idCard;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 注册时间
     */
    private Date regTime;

    /**
     * 个人主页
     */
    private String homepage;

    /**
     * 注册地点
     */
    private String regIp;

    /**
     * 问1
     */
    private String question;

    /**
     * 答1
     */
    private String answer;

    /**
     * 问2
     */
    private String bakQuestion;

    /**
     * 答2
     */
    private String bakAnswer;

    /**
     * 个人头像
     */
    private String headPic;

    /**
     * 状态(0无效,1有效,2待激活)
     */
    private String state;

    /**
     * 注册设备
     */
    private String devId;

    private String nickName;
   
    /**
     * @return 用户编号
     */
    public String getUserNo() {
        return userNo;
    }

    /**
     * @param userNo 
	 *            用户编号
     */
    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    /**
     * @return 用户名称
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName 
	 *            用户名称
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return 密码
     */
    public String getUserPass() {
        return userPass;
    }

    /**
     * @param userPass 
	 *            密码
     */
    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    /**
     * @return 电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel 
	 *            电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * @return 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile 
	 *            手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 
	 *            邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return QQ
     */
    public String getQq() {
        return qq;
    }

    /**
     * @param qq 
	 *            QQ
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * @return 性别
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex 
	 *            性别
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return 证件类别
     */
    public Integer getIdType() {
        return idType;
    }

    /**
     * @param idType 
	 *            证件类别
     */
    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    /**
     * @return 证件号码
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * @param idCard 
	 *            证件号码
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * @return 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday 
	 *            生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return 注册时间
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * @param regTime 
	 *            注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * @return 个人主页
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * @param homepage 
	 *            个人主页
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * @return 注册地点
     */
    public String getRegIp() {
        return regIp;
    }

    /**
     * @param regIp 
	 *            注册地点
     */
    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    /**
     * @return 问1
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question 
	 *            问1
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * @return 答1
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer 
	 *            答1
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * @return 问2
     */
    public String getBakQuestion() {
        return bakQuestion;
    }

    /**
     * @param bakQuestion 
	 *            问2
     */
    public void setBakQuestion(String bakQuestion) {
        this.bakQuestion = bakQuestion;
    }

    /**
     * @return 答2
     */
    public String getBakAnswer() {
        return bakAnswer;
    }

    /**
     * @param bakAnswer 
	 *            答2
     */
    public void setBakAnswer(String bakAnswer) {
        this.bakAnswer = bakAnswer;
    }

    /**
     * @return 个人头像
     */
    public String getHeadPic() {
        return headPic;
    }

    /**
     * @param headPic 
	 *            个人头像
     */
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    /**
     * @return 状态(0无效,1有效,2待激活)
     */
    public String getState() {
        return state;
    }

    /**
     * @param state 
	 *            状态(0无效,1有效,2待激活)
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return 注册设备
     */
    public String getDevId() {
        return devId;
    }

    /**
     * @param devId 
	 *            注册设备
     */
    public void setDevId(String devId) {
        this.devId = devId;
    }

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}
}