package com.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * IniRealm测试
 */
public class IniRealmTest {

    @Test
    public void textAuthentication(){
        //创建IniRealm对象
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xy","123456");
        //认证
        subject.login(token);

        System.out.println("isAuthenticated："+subject.isAuthenticated());
        //授权
        subject.checkRole("admin");
        //权限
        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");
    }
}
