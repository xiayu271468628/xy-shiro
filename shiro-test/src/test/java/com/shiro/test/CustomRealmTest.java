package com.shiro.test;

import com.xy.realm.CustomRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 自定义Realm测试
 */
public class CustomRealmTest {

    @Test
    public void textAuthentication(){

        CustomRealm customRealm = new CustomRealm();

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

        //加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        //加密方式（算法名称）
        matcher.setHashAlgorithmName("md5");
        //加密次数
        matcher.setHashIterations(1);
        // 设置matcher
        customRealm.setCredentialsMatcher(matcher);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xy","123456");
        subject.login(token);

        System.out.println("isAuthenticated："+subject.isAuthenticated());
    /*
        //单角色授权
        subject.checkRole("admin");

        //权限
        subject.checkPermission("user:add");*/
    }

}
