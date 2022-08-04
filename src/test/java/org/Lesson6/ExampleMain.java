package org.Lesson6;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.lesson6.db.dao.CategoriesMapper;
import org.lesson6.db.model.Categories;
import org.lesson6.db.model.CategoriesExample;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ExampleMain {

    public static void main(String[] args) throws IOException {
        SqlSession session = null;
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new
                    SqlSessionFactoryBuilder().build(inputStream);
            session = sqlSessionFactory.openSession();
//            System.out.println("session must be open here");
            CategoriesMapper categoriesMapper = session.getMapper(CategoriesMapper.class);
            CategoriesExample example = new CategoriesExample();

            example.createCriteria().andIdEqualTo(1L);
            List<Categories> list = categoriesMapper.selectByExample(example);
            System.out.println(categoriesMapper.countByExample(example));

            Categories categories = new Categories();
            categories.setTitle("test");
            categoriesMapper.insert(categories);
            session.commit();

            CategoriesExample example2 = new CategoriesExample();
            example2.createCriteria().andTitleLike("%test%");
            List<Categories> list2 = categoriesMapper.selectByExample(example2);
            Categories categories2 = list2.get(0);
            categories2.setTitle("test100");
            categoriesMapper.updateByPrimaryKey(categories2);
            session.commit();

            categoriesMapper.deleteByPrimaryKey(categories2.getId());
            session.commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        session.close();


    }
}
