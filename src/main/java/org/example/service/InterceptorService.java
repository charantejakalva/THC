package org.example.service;

import org.example.model.Interceptor;
import org.example.repository.InterceptorRepository;
import org.springframework.stereotype.Service;

@Service
public class InterceptorService {

    private InterceptorRepository interceptorRepository;
    public  InterceptorService(InterceptorRepository interceptorRepository){
        this.interceptorRepository = interceptorRepository;
    }


    public Interceptor addExecutionTime(Interceptor interceptor){
        return interceptorRepository.save(interceptor);
    }


}
