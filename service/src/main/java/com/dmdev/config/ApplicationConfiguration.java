package com.dmdev.config;

import com.dmdev.HibernateUtil;
import com.dmdev.repository.CandidateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.reflect.Proxy;

@Configuration
@PropertySource("classpath:application.properties")
@ComponentScan("com.dmdev")
public class ApplicationConfiguration {

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @Bean
    public Session session() {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory().getCurrentSession(), args1));
    }

    @Bean
    public CandidateRepository candidateRepository() {
        return new CandidateRepository(session());
    }
}
