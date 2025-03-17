package com.dmdev.config;

import com.dmdev.repository.UserRepository;
import com.dmdev.utils.HibernateTestUtil;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan("com.dmdev")
public class ApplicationConfiguration {
    @Bean
    public SessionFactory sessionFactory() {
        return HibernateTestUtil.buildSessionFactory();
    }

    @Bean
    public EntityManager session(SessionFactory sessionFactory) {
        return (EntityManager) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepository(session(sessionFactory()));
    }
}
