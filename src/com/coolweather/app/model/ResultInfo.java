package com.coolweather.app.model;

public class ResultInfo {
	private int errNum;
	private String errMsg;
	private String retData;
	public int getErrNum() {
		return errNum;
	}
	public void setErrNum(int errNum) {
		this.errNum = errNum;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getRetData() {
		return retData;
	}
	public void setRetData(String retData) {
		this.retData = retData;
	}
	@Override
	public String toString() {
		return "ResultInfo [errNum=" + errNum + ", errMsg=" + errMsg
				+ ", retData=" + retData + "]";
	}
}
