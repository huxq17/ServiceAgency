package com.buyi.huxq17.serviceagency;

import android.content.Context;

import com.buyi.huxq17.serviceagency.exception.AgencyException;
import com.buyi.huxq17.serviceagency.utils.ReflectUtil;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ServiceAgency {
    private final HashMap<Class, Object> cacheMap = new LinkedHashMap<>();
    private boolean isServiceConfigExists;

    private static class InstanceHolder {
        private static final ServiceAgency instance = new ServiceAgency();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getService(Class<T> tClass) {
        return InstanceHolder.instance.getServiceFromMap(tClass, true);
    }

    public static <T extends IService> T getService(Context context, Class<T> tClass) {
        T instance = InstanceHolder.instance.getServiceFromMap(tClass, true);
        instance.init(context);
        return instance;
    }

    /**
     * will not cache service's instance.
     * @param context Context
     * @param tClass Service class
     * @param <T> Service type
     * @return Service instance
     */
    public static <T extends IService> T getServiceWithoutCache(Context context, Class<T> tClass) {
        T instance = InstanceHolder.instance.getServiceFromMap(tClass, false);
        instance.init(context);
        return instance;
    }

    public static void clear() {
        InstanceHolder.instance.clearMap();
    }

    @SuppressWarnings("unchecked")
    public <T> T getServiceFromMap(Class<T> tClass, boolean isFromMap) {
        if (!isServiceConfigExists) {
            try {
                Class.forName("com.buyi.huxq17.serviceagency.ServiceConfig");
                isServiceConfigExists = true;
            } catch (ClassNotFoundException e) {
                throw new com.buyi.huxq17.serviceagency.exception.AgencyException("No class annotate with ServiceAgent.");
            }
        }
        T service = null;
        if (isFromMap) {
            service = (T) cacheMap.get(tClass);
        }
        if (service == null) {

            for (ServiceConfig serviceEnum : ServiceConfig.values()) {
                try {
                    Class serviceClass = Class.forName(serviceEnum.className);
                    if (tClass.isAssignableFrom(serviceClass)) {
                        service = ReflectUtil.newInstance((Class<T>) serviceClass);
                        if (isFromMap) {
                            cacheMap.put(tClass, service);
                        }
                        return service;
                    }
                } catch (ClassNotFoundException e) {
                    throw new com.buyi.huxq17.serviceagency.exception.AgencyException(e);
                }
            }
        } else {
        }
        if (service == null) {
            throw new AgencyException("No class implements " + tClass.getName() + " and annotated with ServiceAgent.");
        }
        return service;
    }

    public void clearMap() {
        cacheMap.clear();
    }

}
