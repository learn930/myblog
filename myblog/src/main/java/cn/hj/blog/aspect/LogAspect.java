package cn.hj.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Pointcut("execution(*  cn.hj.blog.web.*.*.*(..))")
    public void log(){
    }
    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request=attributes.getRequest();
        String url=request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod=joinPoint.getSignature()+"."+joinPoint.getSignature().getName();
        Object[] args=joinPoint.getArgs();
        Requestlogs requestlog=new Requestlogs(url,ip,classMethod,args);
        logger.info("request: {}",requestlog);
    }
    @After("log()")
    public void doAfter(){
    }
    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        logger.info("return result: "+result);
    }
    private class Requestlogs{
        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;

        public Requestlogs() {
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object[] getArgs() {
            return args;
        }

        public void setArgs(Object[] args) {
            this.args = args;
        }

        public Requestlogs(String url, String ip, String classMethod, Object[] args) {
            this.url = url;
            this.ip = ip;
            this.classMethod = classMethod;
            this.args = args;
        }

        @Override
        public String toString() {
            return "Requestlogs{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", args=" + Arrays.toString(args) +
                    '}';
        }
    }
}
