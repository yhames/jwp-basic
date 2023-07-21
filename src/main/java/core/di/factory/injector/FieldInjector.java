package core.di.factory.injector;

import core.di.factory.BeanFactory;
import core.di.factory.BeanFactoryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Set;

public class FieldInjector extends AbstractInjector {

    private static Logger logger = LoggerFactory.getLogger(FieldInjector.class);

    public FieldInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    public void inject(Class<?> clazz) {
        logger.debug("FieldInjector inject clazz : {}", clazz.getName());
        // 먼저 인스턴스를 생성하고
        instantiateClass(clazz);

        // 주입할 필드를 찾아서
        Set<Field> fields = BeanFactoryUtils.getInjectedFields(clazz);

        // 의존관계를 주입한다
        FieldInjection(clazz, fields);
    }

    private void FieldInjection(Class<?> clazz, Set<Field> fields) {
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                field.set(beanFactory.getBean(clazz), instantiateClass(field.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

