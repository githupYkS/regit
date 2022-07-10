package com.yks.regit.filter;

import com.alibaba.fastjson.JSON;
import com.yks.regit.common.BaseContext;
import com.yks.regit.common.R;
import com.yks.regit.emtity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheakFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheakFilter implements Filter {

    /**
     * 路径匹配器，支持通配符，专门用来作为路径比较的
     */
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request1 = (HttpServletRequest) request;
        HttpServletResponse response1 = (HttpServletResponse) response;
        //获取本次请求的URL
        String requestURI = request1.getRequestURI();
        //打印信息
        log.info("拦截到的请求：{}",requestURI);

        //电定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        //判断本次请求是否需要处理
        boolean check = check(urls, requestURI);
        //如果不需要处理，直接放行
        if (check==true){
            log.info("本次请求：{}不需要处理",requestURI);
            chain.doFilter(request1,response1);
            return;
        }
        //判断登录状态，如果已经登录，则直接放行
        if (request1.getSession().getAttribute("employee")!=null){
            log.info("用户已经登录，用户ID为：{}",request1.getSession().getAttribute("employee"));

            Long empId = (Long) request1.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            chain.doFilter(request1,response1);
            return;
        }

        //4-2判断登录状态，如果已经登录，则直接放行
        if (request1.getSession().getAttribute("user")!=null){
            log.info("用户已经登录，用户ID为：{}",request1.getSession().getAttribute("user"));

            Long userId = (Long) request1.getSession().getAttribute("employee");
            BaseContext.setCurrentId(userId);

            chain.doFilter(request1,response1);
            return;
        }

        log.info("用户未登录");
        //如果未登录则返回未登录结果,通过输出流的方式 向客户端响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url: urls) {
            boolean match = PATH_MATCHER.match(url,requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
}
