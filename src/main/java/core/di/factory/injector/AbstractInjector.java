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

public abstract class AbstractInjector implements Injector {

    private static final Logger logger = LoggerFactory.getLogger(AbstractInjector.class);

    BeanFactory beanFactory;

    public AbstractInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    Object instantiateClass(Class<?> clazz) {
        Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, beanFactory.getpreInstanticateBeans());

        Object bean = beanFactory.getBean(concreteClazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(concreteClazz);
        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(concreteClazz);
            beanFactory.setBean(concreteClazz, bean);
            return bean;
        }

        logger.debug("Constructor : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beanFactory.setBean(concreteClazz, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Set<Class<?>> preInstanticateBeans = beanFactory.getpreInstanticateBeans();
        Class<?>[] pTypes = constructor.getParameterTypes();
        List<Object> args = Lists.newArrayList();
        for (Class<?> clazz : pTypes) {
            Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(clazz, preInstanticateBeans);
            if (!preInstanticateBeans.contains(concreteClazz)) {
                throw new IllegalStateException(clazz + "는 Bean이 아니다.");
            }

            Object bean = beanFactory.getBean(concreteClazz);
            if (bean == null) {
                bean = instantiateClass(concreteClazz);
            }
            args.add(bean);
        }
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
