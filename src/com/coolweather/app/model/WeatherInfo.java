package com.coolweather.app.model;

public class WeatherInfo {

	private String city;
	private int cityid;
	private String img1;
	private String img2;
	private String ptime;
	private String temp1;
	private String temp2;
	private String weather;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getCityid() {
		return cityid;
	}

	public void setCityid(int cityid) {
		this.cityid = cityid;
	}

	public String getImg1() {
		return img1;
	}

	public void setImg1(String img1) {
		this.img1 = img1;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public String getPtime() {
		return ptime;
	}

	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	public String getTemp1() {
		return temp1;
	}

	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	public String getTemp2() {
		return temp2;
	}

	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "WeatherInfo [city=" + city + ", cityid=" + cityid + ", img1="
				+ img1 + ", img2=" + img2 + ", ptime=" + ptime + ", temp1="
				+ temp1 + ", temp2=" + temp2 + ", weather=" + weather + "]";
	}

}
