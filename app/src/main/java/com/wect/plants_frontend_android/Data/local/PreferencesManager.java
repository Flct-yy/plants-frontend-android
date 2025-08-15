package com.wect.plants_frontend_android.Data.local;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

// 键值对存储
public class PreferencesManager {
    // 初始化 RxDataStore 实例
    private static RxDataStore<Preferences> rxDataStore;

    // 定义键
    public static final String DARK_MODE_KEY = "dark_mode";
    public static final String USER_TOKEN_KEY = "user_token";
    public static final String USER_ID_KEY = "user_id";
    public static final String LAST_LOGIN_KEY = "last_login";

    // 使用 PreferencesKeys 定义键
    private static final Preferences.Key<Boolean> PREF_DARK_MODE = PreferencesKeys.booleanKey(DARK_MODE_KEY);
    private static final Preferences.Key<String> PREF_USER_TOKEN = PreferencesKeys.stringKey(USER_TOKEN_KEY);
    private static final Preferences.Key<Long> PREF_USER_ID = PreferencesKeys.longKey(USER_ID_KEY);
    private static final Preferences.Key<Long> PREF_LAST_LOGIN = PreferencesKeys.longKey(LAST_LOGIN_KEY);

    // 作用：初始化 DataStore，创建或打开偏好设置文件
    // context：用于获取应用上下文，确定存储位置
    // 注意：必须在其他方法调用前执行一次
    public static void initialize(Context context) {
        if (rxDataStore == null) {
            rxDataStore = new RxPreferenceDataStoreBuilder(
                    context.getApplicationContext(),
                    "app_preferences"
            ).build();
        }
    }

    // 保存布尔值
    // key：存储的键名（如 "dark_mode"）
    // value：要存储的布尔值（如 true/false）
    public static void saveBoolean(Context context, String key, boolean value) {
        initialize(context);

        rxDataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.set(getBooleanKey(key), value);
            return Single.just(mutablePreferences);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    // 读取布尔值
    // 参数：
    // key：要读取的键名
    // defaultValue：当键不存在时返回的默认值
    // 返回值：LiveData<Boolean> 可观察数据对象
    public static Flowable<Boolean> getBoolean(Context context, String key, boolean defaultValue) {
        initialize(context);

        return rxDataStore.data()
                .map(prefs -> {
                    Boolean value = prefs.get(getBooleanKey(key));
                    return value != null ? value : defaultValue;
                })
                .subscribeOn(Schedulers.io());
    }

    // 保存字符串
    // 参数：
    // key：存储的键名（如 "user_token"）
    // value：要存储的字符串
    public static void saveString(Context context, String key, String value) {
        initialize(context);

        rxDataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.set(getStringKey(key), value);
            return Single.just(mutablePreferences);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    // 读取字符串
    public static Flowable<String> getString(Context context, String key, String defaultValue) {
        initialize(context);

        return rxDataStore.data()
                .map(prefs -> prefs.get(getStringKey(key)))
                .map(value -> value != null ? value : defaultValue)
                .subscribeOn(Schedulers.io());
    }

    // 保存长整型
    public static void saveLong(Context context, String key, long value) {
        initialize(context);

        rxDataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.set(getLongKey(key), value);
            return Single.just(mutablePreferences);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    // 读取长整型
    public static Flowable<Long> getLong(Context context, String key, long defaultValue) {
        initialize(context);

        return rxDataStore.data()
                .map(prefs -> prefs.get(getLongKey(key)))
                .map(value -> value != null ? value : defaultValue)
                .subscribeOn(Schedulers.io());
    }

    // 清除指定键
    public static void removeKey(Context context, String key) {
        initialize(context);

        rxDataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.remove(getAnyKey(key));
            return Single.just(mutablePreferences);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    // 清除所有数据
    public static void clearAll(Context context) {
        initialize(context);

        rxDataStore.updateDataAsync(prefs -> {
            MutablePreferences mutablePreferences = prefs.toMutablePreferences();
            mutablePreferences.clear();
            return Single.just(mutablePreferences);
        }).subscribeOn(Schedulers.io()).subscribe();
    }

    // 辅助方法：根据键名获取键对象
    private static Preferences.Key<?> getAnyKey(String key) {
        switch (key) {
            case DARK_MODE_KEY: return PREF_DARK_MODE;
            case USER_TOKEN_KEY: return PREF_USER_TOKEN;
            case USER_ID_KEY: return PREF_USER_ID;
            case LAST_LOGIN_KEY: return PREF_LAST_LOGIN;
            default: return PreferencesKeys.stringKey(key);
        }
    }

    private static Preferences.Key<Boolean> getBooleanKey(String key) {
        return key.equals(DARK_MODE_KEY) ? PREF_DARK_MODE : PreferencesKeys.booleanKey(key);
    }

    private static Preferences.Key<String> getStringKey(String key) {
        return key.equals(USER_TOKEN_KEY) ? PREF_USER_TOKEN : PreferencesKeys.stringKey(key);
    }

    private static Preferences.Key<Long> getLongKey(String key) {
        if (key.equals(USER_ID_KEY)) return PREF_USER_ID;
        if (key.equals(LAST_LOGIN_KEY)) return PREF_LAST_LOGIN;
        return PreferencesKeys.longKey(key);
    }
}