package ua.javarush.textquest.dispatcher;

import java.lang.reflect.Method;

public class MethodMap {
    private final Object controllerInstance;
    private final MethodType methodType;
    private final Method method;

    public MethodMap(Object controllerInstance, MethodType methodType, Method method) {
        this.controllerInstance = controllerInstance;
        this.methodType = methodType;
        this.method = method;
    }

    public Object getControllerInstance() {
        return controllerInstance;
    }

    public Method getMethod() {
        return method;
    }

    public MethodType getMethodType() {
        return methodType;
    }
}
