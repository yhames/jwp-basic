package core.di.factory.injector;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

public class ConstructorInjector extends AbstractInjector {

    private static final Logger logger = LoggerFactory.getLogger(ConstructorInjector.class);

    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(Class<?> clazz) {
        logger.debug("ConstructorInjector inject clazz : {}", clazz.getName());
        instantiateClass(clazz);
    }
}
