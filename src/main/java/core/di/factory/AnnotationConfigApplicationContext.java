package core.di.factory;

import core.annotation.ComponentScan;

import java.util.*;

public class AnnotationConfigApplicationContext implements ApplicationContext {

    private BeanFactory beanFactory;

    public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
        Object[] basePackages = findBasePackages(annotatedClasses);

        beanFactory = new BeanFactory();
        AnnotatedBeanDefinitionReader abdr = new AnnotatedBeanDefinitionReader(beanFactory);
        abdr.register(annotatedClasses);

        ClasspathBeanDefinitionScanner scanner = new ClasspathBeanDefinitionScanner(beanFactory);
        scanner.doScan(basePackages);

        beanFactory.initialize();
    }

    public Object[] findBasePackages(Class<?>... annotatedClasses) {
        List<Object> basePackages = new ArrayList<>();
        for (Class<?> annotatedClass : annotatedClasses) {
            ComponentScan componentScan = annotatedClass.getAnnotation(ComponentScan.class);
            basePackages.addAll(Arrays.asList(componentScan.value()));
        }
        return basePackages.toArray();
    }

    public <T> T getBean(Class<T> clazz) {
        return beanFactory.getBean(clazz);
    }

    public Set<Class<?>> getBeanClasses() {
        return beanFactory.getBeanClasses();
    }
}
