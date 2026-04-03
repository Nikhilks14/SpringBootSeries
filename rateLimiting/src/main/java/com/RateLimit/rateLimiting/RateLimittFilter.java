package com.RateLimit.rateLimiting;

import io.github.bucket4j.Bucket;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimittFilter implements Filter {


   private final BucketService bucketService;
   private final Logger logger = LoggerFactory.getLogger(RateLimittFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String clientip = req.getRemoteAddr();

        Bucket bucket = bucketService.resolveBucket(clientip);
        if(bucket.tryConsume(1)){
            logger.info("token left " + bucket.getAvailableTokens());
            filterChain.doFilter(req,resp);
        } else {
            resp.setStatus(429);
            resp.getWriter().println("Rate Limit Exceeded");
        }
    }
}
