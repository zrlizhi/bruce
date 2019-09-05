package com.hj.app.wx.po;

public class SendUniformMessagePo {

	private String touser;
	private String access_token;
	
	private WeappTemplateMsg weapp_template_msg;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public WeappTemplateMsg getWeapp_template_msg() {
		return weapp_template_msg;
	}

	public void setWeapp_template_msg(WeappTemplateMsg weapp_template_msg) {
		this.weapp_template_msg = weapp_template_msg;
	}
	
	
}
