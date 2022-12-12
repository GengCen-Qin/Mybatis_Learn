package cn.bugstack.Mybatis.test;

import cn.bugstack.Mybatis.test.dao.IUserDao;
import cn.bugstack.mybatis.binding.MapperProxyFactory;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class test {
    @Test
    public void test_jdk_proxy() {
        IUserDao userDao = (IUserDao) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{IUserDao.class},
                (proxy, method, args) -> {
                    return "您已经被代理了";
                }
        );
        System.out.println(userDao.queryUserName("100001"));
    }

    @Test
    public void test_MapperProxyFactory() {
        MapperProxyFactory<IUserDao> factory = new MapperProxyFactory<>(IUserDao.class);
        Map<String, String> sqlSession = new HashMap<>();

        sqlSession.put("cn.bugstack.Mybatis.test.dao.IUserDao.queryUserName", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户姓名");
        sqlSession.put("cn.bugstack.Mybatis.test.dao.IUserDao.queryUserAge", "模拟执行 Mapper.xml 中 SQL 语句的操作：查询用户年龄");
        IUserDao userDao = factory.newInstance(sqlSession);

        String res = userDao.queryUserName("10001");
        System.out.println("测试结果："+ res);
    }

}
