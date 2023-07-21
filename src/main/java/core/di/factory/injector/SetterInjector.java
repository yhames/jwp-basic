package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class SetterInjector extends AbstractInjector {

    private static Logger logger = LoggerFactory.getLogger(SetterInjector.class);

    public SetterInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(Class<?> clazz) {
        logger.debug("SetterInjector inject clazz : {}", clazz.getName());
        // 먼저 인스턴스를 생성하고
        instantiateClass(clazz);

        // 주입할 프로퍼티를 찾아서
        Set<Method> methods = BeanFactoryUtils.getInjectedSetter(clazz);

        // 의존관계를 주입한다
        methodInjection(clazz, methods);
    }

    private void methodInjection(Class<?> clazz, Set<Method> methods) {
        for (Method method : methods) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new IllegalArgumentException("Setter의 파라미터는 1개여야 합니다.");
            }

            try {
                method.invoke(beanFactory.getBean(clazz), instantiateClass(parameterTypes[0]));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
