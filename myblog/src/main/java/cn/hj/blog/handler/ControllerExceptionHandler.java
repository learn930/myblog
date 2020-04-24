package cn.hj.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(HttpServletRequest request,Exception e) throws Exception {
//        logger.error("Request URL : {},Exception: {}",request.getRequestURL(),e.getMessage());
//        if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class)!=null){
//            throw e;
//        }
//        ModelAndView mv=new ModelAndView();
//        ModelAndView mv1=new ModelAndView();
//        mv.addObject("url",request.getRequestURL());
//        mv.addObject("exception",e);
//        mv.setViewName("error/error");
//        mv1.addObject("mv",mv);
//        return mv;
        return null;
    }
}
