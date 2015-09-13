package com.coolweather.app.activity;

import java.net.HttpURLConnection;

import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtill;
import com.coolweather.app.util.Utility;
import com.example.coolweather.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity {
	
	private LinearLayout weatherInfoLayout;
	/**
	 * 用于显示城市名
	 */
	private TextView cityNameText;
	/**
	 * 用于显示发布时间
	 */
	private TextView publishText;
	/**
	 * 用于显示天气描述信息
	 */
	private TextView weatherDespText;
	/**
	 * 用于显示气温1
	 */
	private TextView temp1Text; 
	/**
	 * 用于显示气温2
	 */
	private TextView temp2Text; 
	/**
	 * 用于显示当前时间
	 */
	private TextView currentDateText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout);
		initView();
		Intent intent = getIntent();
		String countyCode = intent.getStringExtra("county_code");
		if(!TextUtils.isEmpty(countyCode)){
			publishText.setText("同步中...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countyCode);
			
		}else{
			showWeather();
		}
	}

	/**
	 * 从SharedPreferences文件中读取存储的天气信息,并显示到界面上.
	 */
	private void showWeather() {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		cityNameText.setText(sp.getString("city_name", ""));
		temp1Text.setText(sp.getString("temp1", ""));
		temp2Text.setText(sp.getString("temp2", ""));
		weatherDespText.setText(sp.getString("weather_desp", ""));
		publishText.setText(sp.getString("publish_time", "") + "发布");
		currentDateText.setText(sp.getString("current_date", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		cityNameText.setVisibility(View.VISIBLE);
	}

	/**
	 * 查询县级代号对应的天气代号
	 * @param countyCode
	 */
	private void queryWeatherCode(String countyCode) {
		String address = "http://www.weather.com.cn/data/list3/city" + countyCode + ".xml";
		queryFromServer(address, "countyCode");
	}

	/**
	 * 查询天气代号对应的天气
	 * @param weatehrCode
	 */
	private void queryWeatherInfo(String weatehrCode){
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatehrCode + ".html";
		queryFromServer(address, "weatehrCode");
	}
	
	private void queryFromServer(String address, final String type) {
		HttpUtill.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onStart(HttpURLConnection connection) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinish(String response) {
				if("countyCode".equals(type)){
					if(!TextUtils.isEmpty(response)){
						String[] array = response.split("\\|");
						if(array != null && array.length == 2){
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
							
						}
					}
				}else if("weatherCode".equals(type)){
					Utility.handleWeatherResponse(WeatherActivity.this, response);
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							showWeather();
						}
					});
				}
				
			}
			
			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						publishText.setText("同步失败");
					}
				});
				
			}
		});
		
	}

	private void initView() {
		weatherInfoLayout = (LinearLayout) findViewById(R.id.weather_info_layout);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		weatherDespText = (TextView) findViewById(R.id.weather_desp); 
		temp1Text = (TextView) findViewById(R.id.temp1);       
		temp2Text = (TextView) findViewById(R.id.temp2);       
		currentDateText = (TextView) findViewById(R.id.current_date); 
		
	}
	
}
