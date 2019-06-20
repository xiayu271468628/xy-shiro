package com.shiro.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * JdbcRealm验证
 */
public class JdbcRealmTest {
    /**
     * 数据源
     */
    DruidDataSource dataSource = new DruidDataSource();

    /**
     * 连接数据库
     */
    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro");
        dataSource.setUsername("root");
        dataSource.setPassword("annet");
    }

    @Test
    public void textAuthentication(){
        /**
         * JdbcRealm连接数据源
         */
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        //权限开关，默认为false,为false时不会去查询权限数据
        jdbcRealm.setPermissionsLookupEnabled(true);

        //自定义sql认证
        String sql = "select password from test_user where username = ?";
        jdbcRealm.setAuthenticationQuery(sql);
        //自定义角色认证
        String roleSql = "select role_name from test_user_role where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        //1.构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

        //2.主题提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("xy","123456");
        //认证
        subject.login(token);

        System.out.println("isAuthenticated："+subject.isAuthenticated());

        subject.checkRole("admin");
/**
        subject.checkPermission("user:select");*/
    }
}
