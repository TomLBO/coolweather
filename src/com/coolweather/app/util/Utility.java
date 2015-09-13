package com.coolweather.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.ResultInfo;
import com.coolweather.app.model.WeatherInfo;

public class Utility {
	@SuppressWarnings("unused")
	private static final String TAG = "Utility";

//	private Province province;
	

	/**
	 * 解析和处理服务器返回的省级数据
	 * @param coolWeatherDB 数据库操作类	
	 * @param response		服务器返回数据
	 * @return				写入数据库是否成功
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
//		Log.i(TAG, "response: " + response);
		if(!TextUtils.isEmpty(response)){
			String[] allProvinces = response.split(",");
			if(allProvinces != null && allProvinces.length > 0){
				for (String p : allProvinces) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 解析和处理服务器返回的市级数据
	 * @param coolWeatherDB	数据库操作类
	 * @param response		服务器返回数据
	 * @param provinceId	省份ID
	 * @return				写入数据库是否成功
	 */
	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, String response, int provinceId){
		if(!TextUtils.isEmpty(response)){
			String[] allCities = response.split(",");
			for (String c : allCities) {
				String[] array = c.split("\\|");
				City city = new City();
				city.setCityCode(array[0]);
				city.setCityName(array[1]);
				city.setProvinceId(provinceId);
				coolWeatherDB.saveCity(city);
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 解析和处理服务器返回的县级数据
	 * @param coolWeatherDB	数据库操作类
	 * @param response		返回数据	
	 * @param cityId		城市ID
	 * @return				写入数据库是否成功
	 */
	
	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, String response, int cityId){
		if(!TextUtils.isEmpty(response)){
			String[] allCounties = response.split(",");
			for (String c : allCounties) {
				String[] array = c.split("\\|");
				County county = new County();
				county.setCountyCode(array[0]);
				county.setCountyName(array[1]);
				county.setCityId(cityId);
				coolWeatherDB.saveCounty(county);
			}
			return true;
		}
		return false;
	}

	/**
	 * 解析服务器返回的数据,并将解析出来的数据存入本地
	 * @param context		上下文
	 * @param response		返回的数据
	 */
	public static void handleWeatherResponse(Context context, String response){
//		WeatherInfo weatherInfo = JSON.parseObject(response, WeatherInfo.class);
		ResultInfo resultInfo = JSON.parseObject(response, ResultInfo.class);
		WeatherInfo weatherInfo = JSON.parseObject(resultInfo.getWeatherinfo(), WeatherInfo.class);
		saveWeatherInfo(context, weatherInfo);
	}

	private static void saveWeatherInfo(Context context, WeatherInfo weatherInfo) {
		SimpleDateFormat date = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = sharedPreferences.edit();
		edit.putBoolean("city_selected", true);
		edit.putString("city_name", weatherInfo.getCity());
		edit.putString("weather_code", weatherInfo.getCityid() + "");
		edit.putString("temp1", weatherInfo.getTemp1());
		edit.putString("temp2", weatherInfo.getTemp2());
		edit.putString("weather_desp", weatherInfo.getWeather());
		edit.putString("publish_time", weatherInfo.getPtime());
		edit.putString("current_date", date.format(new Date()));
		edit.commit();
	}
}
