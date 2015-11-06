package com.donishchenko.hibernatetest.dao;

import com.donishchenko.hibernatetest.dbutil.HibernateDbUtil;
import com.donishchenko.hibernatetest.model.TestEntity;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class TestEntityDao extends AbstractDao {
    private SessionFactory factory;

    public TestEntityDao() {
        this.factory = HibernateDbUtil.getSessionFactory();
    }

    public void save(TestEntity obj) {
        Session session = factory.openSession();
        session.beginTransaction();

        session.saveOrUpdate(obj);

        session.getTransaction().commit();
        session.close();
    }

    public boolean delete(TestEntity obj) {
        return delete(obj.getId());
    }

    public boolean delete(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        TestEntity obj = session.load(TestEntity.class, id);
        session.delete(obj);

        session.getTransaction().commit();

        return true;
    }

    public TestEntity get(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        TestEntity obj = session.get(TestEntity.class, id);

        session.getTransaction().commit();

        return obj;
    }

    public Long getNotFullEntity(Long id) {
        Session session = factory.openSession();
        session.beginTransaction();

        Long obj = (Long) session.createQuery("SELECT id FROM TestEntity WHERE id = :id").setLong("id", id).uniqueResult();

        session.getTransaction().commit();

        return obj;
    }

    public int getCount() {
        Session session = factory.openSession();
        session.beginTransaction();

        long count = (long) session.createQuery("SELECT count (id) FROM TestEntity").uniqueResult();

        session.getTransaction().commit();

        return (int) count;
    }

    public List<TestEntity> getListPagination(int start, int max) {
        Session session = factory.openSession();
        session.beginTransaction();

        Query query = session.createQuery("FROM TestEntity");
        query.setFirstResult(start);
        query.setMaxResults(max);

        @SuppressWarnings("unchecked")
        List<TestEntity> list = query.list();

        session.getTransaction().commit();

        return list;
    }

    public List<TestEntity> getAll() {
        return (List<TestEntity>) super.getAllWithParamaters(TestEntity.class);
    }
}
