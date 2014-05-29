package controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
public class BadRequestControllerAdvice {

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ErrorBean handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		ErrorBean errorBean = new ErrorBean();
		errorBean.setMessage(e.getMessage());
		return errorBean;
	}

	class ErrorBean {
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
