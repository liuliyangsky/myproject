package com.cherrypicks.myproject.api.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@WebListener
public class InitListener implements ServletContextListener {

	private final Log logger = LogFactory.getLog(InitListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		logger.info("ServletContextEvent 初始化");
		logger.info(servletContextEvent.getServletContext().getServerInfo());
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		logger.info("ServletContextEvent 销毁");
	}
}
