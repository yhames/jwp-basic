package core.di.factory;

import java.util.Set;

public interface ApplicationContext {
    Object[] findBasePackages(Class<?>... annotatedClasses);

    <T> T getBean(Class<T> clazz);

    Set<Class<?>> getBeanClasses();
}
