package core.di.factory;

import core.di.factory.example.ExampleConfig;
import org.junit.Assert;
import org.junit.Test;

import javax.sql.DataSource;

public class AnnotatedBeanDefinitionReaderTest {

    @Test
    public void register_simple() {
        BeanFactory beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.register(ExampleConfig.class);
        beanFactory.initialize();

        Assert.assertNotNull(beanFactory.getBean(DataSource.class));
    }
}
