package org.example.interceptor;

import lombok.extern.log4j.Log4j2;
import org.example.model.Interceptor;
import org.example.service.InterceptorService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Log4j2
@Component
public class RestaurantServiceInterceptor implements HandlerInterceptor {
    private Timestamp startTime;
    private Timestamp endTime;
    private InterceptorService interceptorService;

    public RestaurantServiceInterceptor(InterceptorService interceptorService){
        this.interceptorService = interceptorService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("In preHandle method");
        System.out.println("In preHandle Method");
        startTime = new Timestamp(System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        System.out.println("In postHandle Method");



    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        System.out.println("In afterCompletion Method");

        endTime= new Timestamp(System.currentTimeMillis());

        Interceptor interceptor = new Interceptor();
        interceptor.setStartTime(startTime);
        interceptor.setEndTime(endTime);
        interceptor.setRequest(request.toString());
        interceptor.setExecutionTime(endTime.getTime() - startTime.getTime());

        Interceptor interceptor1 = interceptorService.addExecutionTime(interceptor);

        if(interceptor1 != null ){
            System.out.println("Execution Time Recorded");
        }
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
}
