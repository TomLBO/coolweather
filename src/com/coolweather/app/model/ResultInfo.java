package com.coolweather.app.model;

public class ResultInfo {

	private String weatherinfo;

	public String getWeatherinfo() {
		return weatherinfo;
	}

	public void setWeatherinfo(String weatherinfo) {
		this.weatherinfo = weatherinfo;
	}

	@Override
	public String toString() {
		return "ResultInfo [weatherinfo=" + weatherinfo + "]";
	}
	

}
