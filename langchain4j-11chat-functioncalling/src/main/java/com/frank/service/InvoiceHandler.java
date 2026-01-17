package com.frank.service;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;

/**
 * 知識出處，https://docs.langchain4j.dev/tutorials/tools/#tool
 */
@Slf4j
public class InvoiceHandler {

	@Tool("根據用戶提交的開票資訊進行開票")
	public String handle(@P("公司名稱") String companyName, @P("稅號") String dutyNumber, @P("金額保留兩位有效數字") String amount)
			throws Exception {
		log.info("companyName =>>>> {} dutyNumber =>>>> {} amount =>>>> {}", companyName, dutyNumber, amount);
		// ----------------------------------
		// 這塊寫自己的業務邏輯，呼叫redis/rabbitmq/kafka/mybatis/順豐單據/醫療化驗報表/付款介面等第3方
		// ----------------------------------
		System.out.println(new WeatherService().getWeatherV2());

		return "開票成功";
	}
}
