
package com.cherrypicks.myproject.api.controller;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.myproject.api.vo.ResultVO;

public class BaseController {

	protected final static Logger logger = LoggerFactory.getLogger(BaseController.class);

	@PostConstruct
	public void init() {
		logger.info("init");
	}

	@PreDestroy
	public void destroy() {
		logger.info("destroy");
	}

	protected ResultVO getResultJson(final Object object) throws Exception {
		final ResultVO result = new ResultVO();
		result.setResult(object==null?"":object);
		return result;
	}

}
