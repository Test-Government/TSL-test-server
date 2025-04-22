package ee.test_gov.tsl.server.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ReflectionUtils {

    public static <T> T newInstance(Class<T> type) {
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("No default constructor for type: " + type.getName());
        } catch (IllegalAccessException e) {
            throw new IllegalStateException("Unable to call default constructor for type: " + type.getName());
        } catch (InvocationTargetException | InstantiationException e) {
            throw new IllegalStateException("Failed to instantiate type: " + type.getName(), e);
        }
    }

}
