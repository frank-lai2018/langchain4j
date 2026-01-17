package com.frank.service;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

	private static final String BASE_URL = "https://openapi.twse.com.tw/v1/opendata/exchangeReport/MI_MARGN";

	public JsonNode getWeatherV2() throws Exception {
		// 1 傳入呼叫位址url和apikey
		String url = String.format(BASE_URL);

		// 2 使用預設配置建立HttpClient實例
		var httpClient = HttpClients.createDefault();

		// 3 建立請求工廠並將其設定給RestTemplate
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
		String response = new RestTemplate(factory).getForObject(url, String.class);

		JsonNode jsonNode = new ObjectMapper().readTree(response);

		return jsonNode;
	}
}
