package com.umgsai.wx.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取不带jsessionId的URI
        String requestURI = request.getServletPath();
        if (StringUtils.contains(requestURI, "/static/")) {
            return true;
        }
        if (StringUtils.equals(requestURI, "/error.html")) {
            return true;
        }
        if (request.getUserPrincipal() == null) {
//            response.sendRedirect("/login");
            return true;
        }
        String username = request.getUserPrincipal().getName();

        log.error("User: {}, URI: {}", username, requestURI);

        /*
        boolean auth = authManager.auth(requestURI, username);

        if (!auth) {
            String errorMsg = String.format("Ops...没有权限. 请钉钉联系%s", authManager.getSystemUser().getSuperUserSet());
            log.error("User: {}, URI: {}, errorMsg: {}", username, requestURI, errorMsg);
            request.setAttribute("msg", errorMsg);
            request.getRequestDispatcher("/error.html").forward(request, response);
            return false;
        }
        */

        /*
        Set<String> authUriSet = authManager.getAuthUriSet(username);
        List<NavGroup> navGroupList = authManager.getNavGroupList(authUriSet);
        request.setAttribute("navGroupList", JSON.toJSONString(navGroupList));

        log.info("User: {}, URI: {}, 鉴权通过", username, requestURI);
        TraceContext.getContext().put("loginName", username);
        */
        return true;
    }


}
