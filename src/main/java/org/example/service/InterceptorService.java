package org.example.service;

import org.example.model.Interceptor;
import org.example.repository.InterceptorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterceptorService {

    private final InterceptorRepository interceptorRepository;
    public  InterceptorService(InterceptorRepository interceptorRepository){
        this.interceptorRepository = interceptorRepository;
    }


    public Interceptor addExecutionTime(Interceptor interceptor){
        return interceptorRepository.save(interceptor);
    }

    public List<Interceptor> getByName(String controllerName){
        return  (List<Interceptor>) interceptorRepository.findByServiceEndPoint(controllerName);
    }


    public List<Interceptor> getByDate(String date){
        return  (List<Interceptor>) interceptorRepository.findByDate(date);
    }

}
