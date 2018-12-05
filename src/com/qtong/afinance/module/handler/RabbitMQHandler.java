package com.qtong.afinance.module.handler;

import org.apache.log4j.Logger;

public class RabbitMQHandler {
	
    public static Logger logger=Logger.getLogger(RabbitMQHandler.class);
    
    
	/**
	 * 将日志记录到文件
	 * @param logInfo 日志信息
	 */
	public void loggerExecute(String logInfo) {
		try {
			logger.info(logInfo);//保存日志
		} catch (Exception e) {
			
		}
	}
}
