package org.example.repository;

import org.example.model.Interceptor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterceptorRepository extends CrudRepository<Interceptor, String> {

    List<Interceptor> findByServiceEndPoint(String controllerName);

    @Query(value = "select inter from Interceptor inter where inter.startTime like  %:date%")
    List<Interceptor> findByDate(@Param(value = "date") String date);
}
