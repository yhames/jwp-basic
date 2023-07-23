package core.di.factory;

import core.annotation.Bean;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @PARAM : @Configuration이 포함된 설정 클래스
 * @DOING : @Bean으로 설정되어있는 메소드의 반환값을 BeanFactory에 BeanDefinition으로 등록
 */
public class AnnotatedBeanDefinitionReader {

    private BeanDefinitionRegistry beanDefinitionRegistry;

    public AnnotatedBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void register(Class<?>... annotatedClasses) {
        Set<Method> methods = BeanFactoryUtils.getBeanMethods(Bean.class, annotatedClasses);    // 설정 클래스들의 모든 @Bean 메소드 가져옴
        for (Method method : methods) {
            beanDefinitionRegistry.registerBeanDefinition(method.getReturnType(),
                    new AnnotatedBeanDefinition(method.getReturnType(), method));
        }
    }
}
