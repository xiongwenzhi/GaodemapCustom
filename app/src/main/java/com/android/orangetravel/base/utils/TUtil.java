package com.android.orangetravel.base.utils;//package com.yang.base.utils;
//
//import com.yang.base.mvp.BasePresenter;
//import com.yang.base.annotation.CreatePresenter;
//
///**
// * 根据类获取实例
// *
// * @author yangfei
// */
//public final class TUtil {
//
//    private TUtil() {
//        // 这个类不能实例化
//    }
//
//    public static <P extends BasePresenter> P getPresenter(Class<?> cls) {
//        if (cls.isAnnotationPresent(CreatePresenter.class)) {
//            CreatePresenter annotation = cls.getAnnotation(CreatePresenter.class);
//            if (null != annotation) {
//                try {
//                    Class<P> pClass = (Class<P>) annotation.value();
//                    if (null != pClass) {
//                        return pClass.newInstance();
//                    }
//                } catch (InstantiationException e) {
//                    // e.printStackTrace();
//                    return null;
//                } catch (IllegalAccessException e) {
//                    // e.printStackTrace();
//                    return null;
//                }
//            }
//        }
//        return null;
//    }
//
//    /*public static <T> T getT(Object o, int i) {
//        try {
//            return ((Class<T>) ((ParameterizedType) (o.getClass().getGenericSuperclass())).getActualTypeArguments()[i]).newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassCastException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }*/
//
//    /*public static Class<?> forName(String className) {
//        try {
//            return Class.forName(className);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }*/
//
//}