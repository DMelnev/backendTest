package org.Lesson6;

import com.github.javafaker.Faker;
import lombok.SneakyThrows;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.lesson5.dto.ProductDto;
import org.lesson6.db.dao.CategoriesMapper;
import org.lesson6.db.dao.ProductsMapper;
import org.lesson6.db.model.Categories;
import org.lesson6.db.model.Products;
import org.lesson6.db.model.ProductsExample;

import java.io.InputStream;
import java.util.List;

public class ProductTest {
    ProductDto productDto, newProductDto;
    SqlSession sqlSession;
    CategoriesMapper categoriesMapper;
    ProductsMapper productsMapper;

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
        productsMapper = sqlSession.getMapper((ProductsMapper.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void createProductTest(long categoryId) {

        createProduct(categoryId);
        clean();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2})
    void modifyProductTest(long categoryId) {
        String newTitle = productDto.getTitle() + "AAA";
        createProduct(categoryId);

        Products product = new Products();
        product.setCategory_id(categoryId);
        product.setTitle(newTitle);
        product.setPrice(productDto.getPrice());
        product.setId((long) productDto.getId());
        productsMapper.updateByPrimaryKey(product);
        Assertions.assertEquals(newTitle, productsMapper.selectByPrimaryKey(product.getId()).getTitle());
        clean();
    }


    @Test
    void getProductByIdTest() {
        long Id = (long) (Math.random() * 5 + 0.5);
        Assertions.assertEquals(Id, productsMapper.selectByPrimaryKey(Id).getId());

    }

    @AfterEach
    void tearDown() {
        sqlSession.close();
    }

    private void clean() {
        productsMapper.deleteByPrimaryKey((long) productDto.getId());
        sqlSession.commit();
    }

    private void createProduct(long categoryId) {
        Products product = new Products();
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        product.setCategory_id(categoryId);
        productsMapper.insert(product);
        sqlSession.commit();

        ProductsExample example = new ProductsExample();
        example.createCriteria()
                .andTitleLike(productDto.getTitle())
                .andPriceEqualTo(productDto.getPrice());
        List<Products> list = productsMapper.selectByExample(example);
        Assertions.assertEquals(1, list.size());
        productDto.setId(Math.toIntExact(list.get(0).getId()));
//        return product;

    }

}
