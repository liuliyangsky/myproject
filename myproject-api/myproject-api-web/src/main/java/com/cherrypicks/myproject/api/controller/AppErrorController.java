package com.cherrypicks.myproject.api.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cherrypicks.myproject.api.vo.ResultVO;
import com.cherrypicks.myproject.exception.BaseException;
import com.cherrypicks.myproject.service.MessageService;
import  com.cherrypicks.myproject.util.ApiCodeStatus;
import com.cherrypicks.myproject.util.Constants;
import com.cherrypicks.myproject.util.Json;

@Controller
@RequestMapping(Constants.ERROR_PATH)
public class AppErrorController extends BaseController implements ErrorController {
	
	@Autowired
	private ErrorAttributes errorAttributes;
	
	@Autowired
	private MessageService messageService;
	
	@Value("${system.error.debug:false}")
	private Boolean systemErrorDebug;
	
	@Override
	public String getErrorPath() {
		return Constants.ERROR_PATH;
	}

	@RequestMapping(value="",produces=Constants.CONTENT_TYPE_HTML)
	public ModelAndView  errorHtml(final HttpServletRequest request, final HttpServletResponse response){
		ModelAndView modelAndView = new ModelAndView(Constants.ERROR_PATH);
		final String json = Json.toJson(getErrorAttributes(request,response, getTraceParameter(request)));
		modelAndView.addObject(Constants.MESSAGE, json);
		return modelAndView;
	}
	
	@RequestMapping(value="",produces=Constants.CONTENT_TYPE_JSON)
	@ResponseBody
	public ResultVO error(final HttpServletRequest request, final HttpServletResponse response){
		return getErrorAttributes(request,response, getTraceParameter(request));
	}
	
	private ResultVO getErrorAttributes(HttpServletRequest request, final HttpServletResponse response, boolean includeStackTrace) {
		ServletWebRequest servletWebRequest = new ServletWebRequest(request);
		final Exception exception = (Exception) errorAttributes.getError(servletWebRequest);
		ResultVO result = new ResultVO();
		result.setResult("");
		int errorCode = ApiCodeStatus.FAILED;
		String errorMessage = "";
		if(null != exception) {
			String lang = request.getAttribute(Constants.LANG_NAME)!=null?(String) request.getAttribute(Constants.LANG_NAME):null;
			if (exception instanceof BaseException) {
				final BaseException baseEx = (BaseException) exception;
				errorCode = baseEx.getErrorCode();
				errorMessage = messageService.getMessage(errorCode, baseEx.getArgs(), lang);
			} else {
				errorCode = ApiCodeStatus.FAILED;
				if (systemErrorDebug) {
					errorMessage = messageService.getMessage(ApiCodeStatus.FAILED, null, lang);
				} else {
					errorMessage = exception.getMessage();
				}
			}
		}
		result.setErrorCode(errorCode);
		result.setErrorMessage(errorMessage);
		response.setStatus(HttpServletResponse.SC_OK);
		return result;
	}
	
	private boolean getTraceParameter(final HttpServletRequest request) {
		final String parameter = request.getParameter("trace");
		if (parameter == null) {
			return false;
		}
		return !"false".equals(parameter.toLowerCase());
	}
}
