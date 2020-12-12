package org.michalbrzezinski.securitate.config.security;

import org.michalbrzezinski.securitate.feature.security.objects.Controller;
import org.springframework.http.HttpMethod;

class StringifiyController {

    public static String stringifyController(String classLevelAnnotation, String methodLevelAnnotation, String httpMethod) {
        String stringified = classLevelAnnotation.concat((methodLevelAnnotation.length() > 0 ? methodLevelAnnotation : ""));
        stringified = stringified.concat("$" + httpMethod);
        return stringified;
    }

    public static String stringifyController(Controller controller) {
        String s = stringifyController(controller.getController(), controller.getMethod(), controller.getHttp());
        return s;
    }

    public static HttpMethod getHttpMethodFromAuthority(String auth) {
        int index = auth.lastIndexOf("$");
        HttpMethod r = null;
        if (index > 0) {
            String method = auth.substring(index + 1);
            r = HttpMethod.valueOf(method);
        }
        return r;
    }
}
