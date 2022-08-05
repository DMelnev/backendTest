package org.Lesson6;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.lesson5.dto.ProductDto;
import org.lesson6.db.dao.CategoriesMapper;
import org.lesson6.db.model.Categories;
import org.lesson6.db.model.CategoriesExample;

import java.io.InputStream;


public class GetCategoryTest {
    ProductDto productDto, newProductDto;
    SqlSession sqlSession;
    CategoriesMapper categoriesMapper;

    @SneakyThrows
    @BeforeEach
    void beforeEach() {
        productDto = new ProductDto()
                .withTitle(new Faker().food().ingredient())
                .withPrice((int) (Math.random() * 10000));
        newProductDto = new ProductDto()
                .withTitle(new Faker().food().ingredient())
                .withPrice((int) (Math.random() * 10000));
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
        Assertions.assertEquals(title,selected.getTitle());

    }

    @AfterEach
    void tearDown() {
        sqlSession.close();
    }
}
