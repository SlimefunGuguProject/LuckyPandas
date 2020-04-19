package dev.j3fftw.luckypanda.utils;

import org.bukkit.Bukkit;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class ReflectionUtils {

    public static final String CB = Bukkit.getServer().getClass().getPackage().getName();
    public static final String VER = CB.substring(CB.lastIndexOf('.') + 1);
    public static final String NMS = "net.minecraft.server." + VER;

    private ReflectionUtils() {}

    @Nullable
    public static Class<?> getNMSClass(String className) {
        try {
            return Class.forName(NMS + '.' + className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Class<?> getInnerNMSClass(String className, String innerClass) {
        try {
            return Class.forName(NMS + '.' + className + '$' + innerClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Class<?> getCBClass(String className) {
        try {
            return Class.forName(CB + '.' + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Nullable
    public static Object newInstance(Class<?> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Object newInstance(Class<?> clazz, Class<?>[] parameterTypes, Object... parameters) {
        try {
            Constructor constructor = clazz.getConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor.newInstance(parameters);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static Field getField(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object getFieldValue(Class<?> clazz, Object instance, String fieldName) {
        Field field = getField(clazz, fieldName);
        if (field == null) return null;

        field.setAccessible(true);
        try {
            return field.get(instance);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setField(Class<?> clazz, Object instance, String fieldName, Object newValue) {
        Field field = getField(clazz, fieldName);
        if (field == null) return;
        field.setAccessible(true);

        try {
            field.set(instance, newValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... argumentTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, argumentTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethod(Method method, Object instance, Object... arguments) {
        try {
            method.setAccessible(true);
            return method.invoke(instance, arguments);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object invokeMethod(Class<?> clazz, Object instance, String methodName) {
        Method method = getMethod(clazz, methodName);
        if (method != null) {
            return invokeMethod(method, instance);
        } else
            return null;
    }

    public static Object invokeMethod(Class<?> clazz, Object instance, String methodName,
                                      Class<?>[] argumentTypes, Object... arguments) {
        Method method = getMethod(clazz, methodName, argumentTypes);
        if (method != null)
            return invokeMethod(method, instance, arguments);
        else
            return null;
    }
}
