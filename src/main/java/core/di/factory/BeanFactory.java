package core.di.factory;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.di.factory.injector.ConstructorInjector;
import core.di.factory.injector.FieldInjector;
import core.di.factory.injector.Injector;
import core.di.factory.injector.SetterInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    private Set<Class<?>> preInstanticateBeans;

    private Map<Class<?>, Object> beans = Maps.newHashMap();

    private List<Injector> injectors = new ArrayList<>();

    public BeanFactory(Set<Class<?>> preInstanticateBeans) {
        this.preInstanticateBeans = preInstanticateBeans;

        injectors.add(new ConstructorInjector(this));
        injectors.add(new FieldInjector(this));
        injectors.add(new SetterInjector(this));
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> requiredType) {
        return (T) beans.get(requiredType);
    }

    public void setBean(Class<?> clazz, Object bean) {
        beans.put(clazz, bean);
    }

    public Set<Class<?>> getpreInstanticateBeans() {
        return preInstanticateBeans;
    }

    public void initialize() {
        Injector injector = new FieldInjector(this);
        for (Class<?> clazz : preInstanticateBeans) {
            logger.debug("instantiated Class : {}", clazz);
            inject(clazz);
        }

        logger.debug(" Beans List ");
        beans.keySet().forEach(bean -> logger.debug("{}", beans.get(bean)));
    }

    public void inject(Class<?> clazz) {
        for (Injector injector : injectors) {
            injector.inject(clazz);
        }
    }

    public Map<Class<?>, Object> getControllers() {
        Map<Class<?>, Object> controllers = Maps.newHashMap();
        for (Class<?> clazz : preInstanticateBeans) {
            if (clazz.isAnnotationPresent(Controller.class)) {
                controllers.put(clazz, beans.get(clazz));
            }
        }
        return controllers;
    }
}
