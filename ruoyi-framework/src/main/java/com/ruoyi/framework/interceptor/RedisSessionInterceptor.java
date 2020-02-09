package com.ruoyi.framework.interceptor;

import com.ruoyi.framework.redis.RedisService;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: xuyang
 * @Date: 2020/2/9 17:06
 * @Description:
 */
@Component
public class RedisSessionInterceptor implements HandlerInterceptor {

    private static final String REDIS_USER = "REDIS_USER";

    private static final int SESSION_TIME_OUT = 6400;

    @Autowired
    private RedisService redisService;

    @Autowired
    private ISysUserService sysUserService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //redis托管
        String userInt = (String) redisService.get(request.getSession().getId());
        if (userInt != null) {

            int userID = Integer.valueOf(userInt);

            SysUser user = (SysUser) request.getSession().getAttribute(REDIS_USER);
            // 如果user不为空则放行
            if (null == user) {
                SysUser userInfo = sysUserService.selectUserById(Long.valueOf(userID));
                request.getSession().setAttribute(REDIS_USER, userInfo);
                //更新shiro中的用户信息
                ShiroUtils.setSysUser(userInfo);
            }

            //共享session：其他平台根据userID获取session活性
            redisService.set(String.valueOf(userID), request.getSession().getId(), SESSION_TIME_OUT);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
