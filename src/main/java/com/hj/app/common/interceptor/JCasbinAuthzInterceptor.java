package com.hj.app.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.casbin.jcasbin.main.Enforcer;

import java.nio.charset.Charset;
import java.util.Base64;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;

public class JCasbinAuthzInterceptor implements Interceptor {
    static Enforcer enforcer;

    // Initialize jCasbin's enforcer with model and policy rules.
    // Here we load policy from file, you can choose to load policy from database.
    static {
        enforcer = new Enforcer("jcasbin/basic_model.conf", "jcasbin/basic_policy.csv");
    }

    // In this demo, we use HTTP basic authentication as the authentication method.
    // This method retrieves the user name from the HTTP header and passes it to jCasbin.
    // You can change to your own authentication method like OAuth, JWT, Apache Shiro, etc.
    // You need to implement this getUser() method to make sure jCasbin can get the
    // authenticated user name.
    private String getUser(HttpServletRequest request) {
    	
        String res = "";

        final String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            // credentials = "username:password"
            String credentials = new String(Base64.getDecoder().decode(base64Credentials),
                    Charset.forName("UTF-8"));
            final String[] values = credentials.split(":", 2);
            res = values[0];
        }

        return res;
    }

    // Filters all requests through jCasbin's authorization.
    // If jCasbin allows the request, pass the request to next handler.
    // If jCasbin denies the request, return HTTP 403 Forbidden.
    public void intercept(Invocation inv) {
    	
        HttpServletRequest request = inv.getController().getRequest();
        HttpServletResponse response = inv.getController().getResponse();

        String user = getUser(request);
        String path = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("(" + user + ", " + path + ", " + method + ")");

        if (enforcer.enforce(user, path, method)) {
            inv.invoke();
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
