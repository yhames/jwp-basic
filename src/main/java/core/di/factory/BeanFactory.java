package core.di.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import core.annotation.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void initialize() {
        try {
            for (Class<?> clazz : preInstanticateBeans) {
                if (!beans.containsKey(clazz)) {
                    beans.put(clazz, instantiateClass(clazz));
                }
            }
        } catch (InvocationTargetException | NoSuchMethodException
                 | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = new HashMap<>();
        for (Class<?> clazz : beans.keySet()) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }

    private Object instantiateClass(Class<?> clazz) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = BeanFactoryUtils.getInjectedConstructor(clazz);
        if (constructor == null) {
            return clazz.getConstructor().newInstance();
        } else {
            return instantiateConstructor(constructor);
        }
    }

    private Object instantiateConstructor(Constructor<?> constructor) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        List<Object> parameters = new ArrayList<>();
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        for (Class<?> parameterType : parameterTypes) {
            Class<?> clazz = BeanFactoryUtils.findConcreteClass(parameterType, preInstanticateBeans);
            if (!beans.containsKey(clazz)) {
                beans.put(clazz, instantiateClass(clazz));
            }
            parameters.add(beans.get(clazz));
        }
        return constructor.newInstance(parameters.toArray());
    }
}
