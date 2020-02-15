package com.api.ratelimiter.throttler.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Component
public class throttlerInterceptor implements HandlerInterceptor {

    @Value("${capacity}")
    private long capacity;
    @Value("${tokens}")
    private long tokens;

    @Value("${timeinseconds}")
    private long timeinseconds;



    private Bucket CreateNewBucket()
    {
        Refill refill = Refill.greedy(tokens, Duration.ofSeconds(timeinseconds));
        Bandwidth limit = Bandwidth.classic(capacity,refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Bucket bucket = (Bucket) request.getSession().getAttribute(request.getRemoteAddr());

        if(bucket==null)
        {
            bucket = CreateNewBucket();
            request.getSession().setAttribute(request.getRemoteAddr(),bucket);
        }

        if(bucket.tryConsume(1))
        {
            //Token available
            return true;
        }
        response.sendError(429,"To Many Requests");
        return false;
    }
}
