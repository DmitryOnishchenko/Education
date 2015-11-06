package com.donishchenko;

import com.donishchenko.hibernatetest.dao.TestEntityDao;
import com.donishchenko.hibernatetest.dbutil.HibernateDbUtil;
import com.donishchenko.hibernatetest.model.TestEntity;

import java.util.List;

public class App {
    public static void main(String[] args) {
        TestEntityDao dao = new TestEntityDao();
//
//        TestEntity obj = new TestEntity("test message");
//
//        dao.save(obj);
//
//        TestEntity obj2 = new TestEntity("second object");
//        obj2.setId(obj.getId());
//
//        dao.save(obj2);
//
//        // Delete
//        dao.delete(obj);

        // Pagination
//        int start = 0;
//        int max = 5;
//        int count = dao.getCount();
//        int totalPages = (count / max) + 1;
//
//        for (int i = 0; i < totalPages; i++) {
//            List<TestEntity> list = dao.getListPagination(start, max);
//            start += max;
//
//            System.out.println("Page" + (i + 1));
//            for (TestEntity testEntity : list) {
//                System.out.println(testEntity.getId() + " | " + testEntity.getMessage());
//            }
//        }

        // AbstractDao
        List<TestEntity> list = dao.getAll();
        for (TestEntity testEntity : list) {
            System.out.println(testEntity.getId() + " | " + testEntity.getMessage());
        }


        HibernateDbUtil.shoutdown();
    }
}
