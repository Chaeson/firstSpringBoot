package com.cjw.toy;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class ToyApplicationTests {

    @Value("${property.test.name}")
    private String propertyTestName;

    @Value("${propertyTest}")
    private String propertyTest;

    @Value("${noKey:default value}")
    private String defaultValue;

    @Value("${propertyTestList}")
    private String[] propertyTestArray;

    @Value("#{'${propertyTestList}'.split(',')}")
    private List<String> propertyTestList;

    @Test
    public void test() {
        assertThat(propertyTestName, is("Value versus ConfigurationProperties"));
        assertThat(propertyTest, is("Test"));
        assertThat(defaultValue, is("default value"));
        assertThat(propertyTestArray[0], is("Test1"));
        assertThat(propertyTestArray[1], is("Test2"));
        assertThat(propertyTestArray[2], is("Test3"));

        assertThat(propertyTestList.get(0), is("Test1"));
        assertThat(propertyTestList.get(1), is("Test2"));
        assertThat(propertyTestList.get(2), is("Test3"));
    }

}
