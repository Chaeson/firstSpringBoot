package com.cjw.toy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * 에러 발생 원인: SpringBootTest는 value와 properties를 같이 사용하면 에러가 발생한다.
 *             두개가 Aliasfor로 같이 잡혀 있다... 둘중에 하나만 사용할 것!
 */
@RunWith(SpringRunner.class)
@SpringBootTest(value = "value=test", properties = {"property.value=propertyTest"}, classes = {ToyApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ToyApplicationTests {

    @Value("${value}")
    private String value;

    @Value("${property.value}")
    private String propertyValue;

    @Test
    public void contextLoad(){
        assertThat(value, is("test"));
        assertThat(propertyValue, is("propertyTest"));
    }
}
