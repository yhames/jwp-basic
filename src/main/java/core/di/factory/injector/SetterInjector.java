package core.di.factory.injector;

import com.google.common.collect.Lists;
import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class SetterInjector implements Injector {

    private static final Logger logger = LoggerFactory.getLogger(SetterInjector.class);

    private BeanFactory beanFactory;

    public SetterInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void inject(Class<?> clazz) {
        instantiateClass(clazz);
        Set<Method> methods = BeanFactoryUtils.getInjectedSetters(clazz);
        logger.debug("methods for Clazz : {}", clazz.getName());
        methods.forEach(System.out::println);
        logger.debug("==========================");

        try{
            for (Method method : methods) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length != 1) {
                    throw new IllegalStateException("DI할 메서드의 인자는 하나여야 합니다.");
                }

                Class<?> concreteClazz = BeanFactoryUtils.findConcreteClass(parameterTypes[0], beanFactory.getPreInstanticateBeans());
                Object bean = beanFactory.getBean(concreteClazz);
                if (bean == null) {
                    bean = instantiateClass(concreteClazz);
                }
                logger.debug("Class : {}, set : {}", concreteClazz.getName(), bean.getClass());
                method.invoke(beanFactory.getBean(method.getDeclaringClass()), bean);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object instantiateClass(Class<?> clazz) {
        Object bean = beanFactory.getBean(clazz);
        if (bean != null) {
            return bean;
        }

        Constructor<?> injectedConstructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (injectedConstructor == null) {
            bean = BeanUtils.instantiate(clazz);
            logger.debug("Register Beans : {}", bean.getClass());
            beanFactory.registerBean(clazz, bean);
            return bean;
        }

        logger.debug("Register Beans : {}", injectedConstructor);
        bean = instantiateConstructor(injectedConstructor);
        beanFactory.registerBean(clazz, bean);
        return bean;
    }

    private Object instantiateConstructor(Constructor<?> constructor) {
        Set<Class<?>> preInstanticateBeans = beanFactory.getPreInstanticateBeans();
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
        logger.debug("instantiateClass : {} with {}", constructor, args.toArray());
        return BeanUtils.instantiateClass(constructor, args.toArray());
    }
}
