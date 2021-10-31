package com.yjz.app.shriodemo.config;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShrioConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager) {
//        log.info("开始配置shiroFilter...");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //拦截器.
        Map<String, String> map = new HashMap<>();

//        // 配置不会被拦截的链接 顺序判断  相关静态资源
//        map.put("/static/**", "anon");
//        // 登录验证的地址不能跟打开页面的地址一样，否则shiro是先验证用户名密码，失败了才会验证验证码
        map.put("/admin/login", "anon");
//        map.put("/admin/logout", "anon");
//        map.put("/admin/no_auth", "anon");
//
//        //<!-- 过滤链定义，从上向下顺序执行，一般将/**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
//
//        //<!-- authc:所有url都必须认证通过才可以访问; user: 表示rememberMe后就可以访问 anon:所有url都都可以匿名访问 {@link org.apache.shiro.web.filter.mgt.DefaultFilter}-->
//        map.put("/admin/permission/**", "perms");
//        map.put("/admin/role/**", "perms");
//        map.put("/admin/system/**", "perms");
//        map.put("/admin/admin_user/**", "perms");

        map.put("/admin/**", "user");


        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/admin/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/admin/index");
//
//        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/admin/no_auth");

        return shiroFilterFactoryBean;
    }

    // 安全管理器配置
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        myShiroRealm.setCredentialsMatcher(myCredentialsMatcher());
        SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();
        simpleAccountRealm.addAccount("admin","admin","admin");

        securityManager.setRealm(simpleAccountRealm);
//        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(defaultWebSecurityManager);
        return authorizationAttributeSourceAdvisor;
    }


    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        // 记住我cookie生效时间 单位秒
        int adminRememberMeMaxAge = 7;
        simpleCookie.setMaxAge(adminRememberMeMaxAge * 24 * 60 * 60);
        return simpleCookie;
    }

    @Bean
    public CookieRememberMeManager rememberMeManager() {
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey(Base64.encode("jason is the best!".getBytes()));
        return cookieRememberMeManager;
    }

}
