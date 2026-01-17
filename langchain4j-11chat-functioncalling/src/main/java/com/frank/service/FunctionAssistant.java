package com.frank.service;

public interface FunctionAssistant
{
	//客戶指示：出差住宿發票開票，
	 // 開立發票資訊: 公司名稱xxx
	 // 稅號序列: xx
	 // 開票金額: xxx.00元
    String chat(String message);
}
