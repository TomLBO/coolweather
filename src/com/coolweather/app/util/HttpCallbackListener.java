package com.coolweather.app.util;

import java.net.HttpURLConnection;

public interface HttpCallbackListener {
	void onStart(HttpURLConnection connection);
	void onFinish(String response);
	void onError(Exception e);
}
