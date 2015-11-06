package com.donishchenko.hibernatetest.dao;

import com.donishchenko.hibernatetest.dbutil.HibernateDbUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public abstract class AbstractDao {
    private SessionFactory factory;

    public AbstractDao() {
        this.factory = HibernateDbUtil.getSessionFactory();
    }

    protected List getAllWithParamaters(Class clazz) {
        Session session = factory.openSession();
        session.beginTransaction();

        List list = session.createQuery("FROM " + clazz.getSimpleName()).list();

        session.getTransaction().commit();
        return list;
    }
}
