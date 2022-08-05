package org.Lesson6;

import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.lesson6.db.dao.CategoriesMapper;
import org.lesson6.db.model.*;

import java.io.InputStream;


public class GetCategoryTest {
    SqlSession sqlSession;
    CategoriesMapper categoriesMapper;

    @SneakyThrows
    @BeforeEach
    void beforeEach() {

        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        sqlSession = sqlSessionFactory.openSession();
        categoriesMapper = sqlSession.getMapper(CategoriesMapper.class);

    }

    @ParameterizedTest
    @CsvSource(value = {
            "1, Food",
            "2, Electronic"
    })
    void getCategoryById(long id, String title) {

        CategoriesExample categoriesExample = new CategoriesExample();
        categoriesExample.createCriteria().andIdEqualTo(id);

        Categories selected = categoriesMapper.selectByPrimaryKey(id);
        Assertions.assertEquals(title, selected.getTitle());

    }

    @AfterEach
    void tearDown() {
        sqlSession.close();
    }
}
