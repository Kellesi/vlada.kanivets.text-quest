package ua.javarush.textquest.dispatcher;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class RegisterForControllers {
    public Map<String, MethodMap> register(Object... controllerInstances) {
        Map<String, MethodMap> urlToMethodMap = new HashMap<>();
        for (Object controllerInstance : controllerInstances) {
            Class<?> instanceClass = controllerInstance.getClass();
            Method[] methods = instanceClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                    String url = annotation.url();
                    MethodType httpMethodType = annotation.method();
                    urlToMethodMap.put(url, new MethodMap(controllerInstance, httpMethodType, method));
                }
            }
        }
        return urlToMethodMap;
    }
}
