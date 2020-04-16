package com.cherrypicks.myproject.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseServiceImpl {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    public void init() {
        logger.info("init");
    }

    @PreDestroy
    public void destroy() {
        logger.info("destroy");
    }
}
