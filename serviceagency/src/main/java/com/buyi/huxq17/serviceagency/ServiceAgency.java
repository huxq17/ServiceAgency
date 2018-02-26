package com.buyi.huxq17.serviceagency;

import com.buyi.huxq17.serviceagency.utils.ReflectUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ServiceAgency {
    private static final HashMap<Class, Object> cacheMap = new LinkedHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> tClass) {
        try {
            Class.forName("com.buyi.huxq17.serviceagency.ServiceConfig");
        } catch (ClassNotFoundException e) {
            throw new AgencyException("No class annotate with ServiceAgent.");
        }

        T service = (T) cacheMap.get(tClass);
        if (service == null) {
            for (ServiceConfig serviceEnum : ServiceConfig.values()) {
                try {
                    Class serviceClass = Class.forName(serviceEnum.className);
                    if (tClass.isAssignableFrom(serviceClass)) {
                        service = ReflectUtil.newInstance((Class<T>) serviceClass);
                        cacheMap.put(tClass, service);
                        return service;
                    }
                } catch (ClassNotFoundException e) {
                    throw new AgencyException(e);
                }
            }
        } else {
        }
        if (service == null) {
            throw new AgencyException("No class implements " + tClass.getName() + " and annotated with ServiceAgent.");
        }
        return service;
    }

    public static void clear() {
        cacheMap.clear();
    }
}
