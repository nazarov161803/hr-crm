package com.dmdev;

import com.dmdev.entity.Candidate;
import com.dmdev.entity.Department;
import com.dmdev.entity.Interview;
import com.dmdev.entity.User;
import com.dmdev.entity.Vacancy;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        final Configuration configuration = buildConfiguration();
        configuration.configure();

        return configuration.buildSessionFactory();
    }

    public static Configuration buildConfiguration() {
        final Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Candidate.class);
        configuration.addAnnotatedClass(Vacancy.class);
        configuration.addAnnotatedClass(Interview.class);
        configuration.addAnnotatedClass(Department.class);
        return configuration;

    }
}
