package core.ref;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Field[] declaredFields = clazz.getDeclaredFields();
        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
        Method[] declaredMethods = clazz.getDeclaredMethods();

        logger.debug("declared Fields:");
        Arrays.stream(declaredFields).forEach((field) -> {
                    String modifier = Modifier.toString(field.getModifiers());
                    String fieldName = field.getName();
                    String typeName = field.getType().getTypeName();
                    String type = typeName.substring(typeName.lastIndexOf(".") + 1);
                    logger.debug("    {} {} {}",
                            modifier,
                            type,
                            fieldName);
                }
        );

        logger.debug("declared Constructors:");
        Arrays.stream(declaredConstructors).forEach((constructor) -> {
            String modifier = Modifier.toString(constructor.getModifiers());
            String constructorName = constructor.getName().substring(constructor.getName().lastIndexOf(".") + 1);
            String parameters = Arrays.stream(constructor.getParameterTypes())
                    .map(type -> type.getTypeName().substring(type.getTypeName().lastIndexOf(".") + 1))
                    .collect(Collectors.joining(", "));

            logger.debug("    {} {}({})", modifier, constructorName, parameters);
        });

        logger.debug("declared Methods:");
        Arrays.stream(declaredMethods).forEach((method) -> {
            String modifier = Modifier.toString(method.getModifiers());
            String methodName = method.getName();
            String paramters = Arrays.stream(method.getParameterTypes())
                    .map(type -> type.getTypeName().substring(type.getTypeName().lastIndexOf(".") + 1))
                    .collect(Collectors.joining(", "));
            logger.debug("    {} {}({})", modifier, methodName, paramters);
        });
    }

    @Test
    public void newInstanceWithConstructorArgs() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Constructor<User> constructor = clazz.getConstructor(String.class, String.class, String.class, String.class);
        User user = constructor.newInstance("userId", "password", "name", "email");
        logger.debug(user.toString());

        Assert.assertEquals("userId", user.getUserId());
        Assert.assertEquals("password", user.getPassword());
        Assert.assertEquals("name", user.getName());
        Assert.assertEquals("email", user.getEmail());

    }

    @Test
    public void privateFieldAccess() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());

        Student student = clazz.getConstructor().newInstance();

        Field[] fields = clazz.getDeclaredFields();
        fields[0].setAccessible(true);
        fields[0].set(student, "name");
        fields[1].setAccessible(true);
        fields[1].setInt(student, 25);

        logger.debug("student.getName = {}, student.getAge = {}", student.getName(), student.getAge());

        Assert.assertEquals("name", student.getName());
        Assert.assertEquals(25, student.getAge());


    }
}
