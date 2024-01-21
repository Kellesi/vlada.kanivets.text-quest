package ua.javarush.textquest.dispatcher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

@Getter
@Setter
@AllArgsConstructor
public class MethodMap {
    private final Object controllerInstance;
    private final MethodType methodType;
    private final Method method;
}
