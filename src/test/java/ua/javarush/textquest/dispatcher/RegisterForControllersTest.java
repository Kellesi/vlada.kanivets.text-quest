package ua.javarush.textquest.dispatcher;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegisterForControllersTest {
    private final RegisterForControllers registerForControllers = new RegisterForControllers();

    @Test
    void registerShouldAddAllMethodsWithAnnotationToMap() {
        class TestController {
            @RequestMapping(url = "/test", method = MethodType.GET)
            public void testMethod() {
            }

            @RequestMapping(url = "/test2", method = MethodType.POST)
            public void testMethod2() {
            }
        }
        TestController testController = new TestController();
        int expectedSize = 2;

        Map<String, MethodMap> result = registerForControllers.register(testController);

        assertTrue(result.containsKey("/test"));
        MethodMap methodMap = result.get("/test");
        assertNotNull(methodMap);
        assertEquals(expectedSize, result.size());
    }

    @Test
    void registerShouldNotAddMethodsWithoutAnnotationToMap() {
        class TestController {
            public void testMethod() {
            }
        }
        TestController testController = new TestController();

        Map<String, MethodMap> result = registerForControllers.register(testController);

        assertTrue(result.isEmpty());
    }
}