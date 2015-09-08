package com.coolweather.app.util;

import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.model.ResultInfo;
import com.coolweather.app.model.S_City;

public class Utility {
	private static final String TAG = "Utility";
	
	static{
		City city = new City();
		Log.i("", "这里是测试代码,测试git提交");
	}
	/**
	 * 解析和处理服务器返回的省级数据
	 * @param coolWeatherDB 数据库操作类	
	 * @param response		服务器返回数据
	 * @return				写入数据库是否成功
	 */
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, String response){
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
				return true;
			}
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
	 * 蛋疼,没啥用..
	 * @param list
	 * @return
	 */
	private static List<City> CityService2Local(List<S_City> list) {
		List<City> cities = new ArrayList<City>();
		for (S_City sCity : list) {
			City city = new City();
			city.setId(sCity.getArea_id());
			city.setCityName(sCity.getName_cn());
			city.setCityCode(sCity.getArea_id()+"");
//			city.setProvinceId(sCity.getProvince_cn());
			cities.add(city);
		}
		return cities;
	}

}
