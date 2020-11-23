package org.michalbrzezinski.securitate.config.security;

import org.michalbrzezinski.securitate.feature.security.objects.ControllerDO;
import org.springframework.http.HttpMethod;

class StringifiyController {

    public static String stringifyController(String classLevelAnnotation, String methodLevelAnnotation, String httpMethod) {
        String stringified = classLevelAnnotation.concat((methodLevelAnnotation.length() > 0 ? methodLevelAnnotation : ""));
        stringified = stringified.concat("$" + httpMethod);
        return stringified;
    }

    public static String stringifyController(ControllerDO controllerDO) {
        String s = stringifyController(controllerDO.getController(), controllerDO.getMethod(), controllerDO.getHttp());
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
