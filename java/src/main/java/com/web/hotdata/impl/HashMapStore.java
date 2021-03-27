package com.web.hotdata.impl;

import com.web.hotdata.HotDataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Component
@Primary    // 通过@Primary注解决定使用哪种HotDataStore缓存方案
public class HashMapStore implements HotDataStore{
	
	private static final Map<String, Object> Store = new ConcurrentHashMap<>();
	private static final Map<String, Long> ExpireStore = new ConcurrentHashMap<>();
	private static final Logger log = LoggerFactory.getLogger(HashMapStore.class);

	@Override
	public boolean set(String key, Object value) {
		try {
			Store.put(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
	}

	@Override
	public boolean set(String key, Object value, long time) {
		return set(key,value,time,TimeUnit.SECONDS);
	}

	@Override
	public boolean set(String key, Object value, long time, TimeUnit timeUnit) {
		try {
            if (time > 0) {
            	set(key, value);
            	expire(key,System.currentTimeMillis() + time,timeUnit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
	}

	@Override
	public Object get(String key) {
		if(key == null) {
			return null;
		}
		if(ExpireStore.get(key) != null && ExpireStore.get(key) <= System.currentTimeMillis()) {
			Store.remove(key);
			ExpireStore.remove(key);
			return null;
		}
		return Store.get(key);
	}

	@Override
	public void del(String... keys) {
		if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
            	Store.remove(keys[0]);
            	ExpireStore.remove(keys[0]);
                log.debug("--------------------------------------------");
                log.debug(new StringBuilder("删除缓存：").append(keys[0]).toString());
                log.debug("--------------------------------------------");
            } else {
                for (String key : keys) {
                    Store.remove(key);
                	ExpireStore.remove(key);
                }
                log.debug("--------------------------------------------");
                log.debug("成功删除缓存：" + keys.toString());
                log.debug("缓存删除数量：" + keys.length + "个");
                log.debug("--------------------------------------------");
            }
        }
	}

	@Override
	public boolean expire(String key, long time) {
		return expire(key,time,TimeUnit.SECONDS);
	}

	@Override
	public boolean expire(String key, long time, TimeUnit timeUnit) {
		time = System.currentTimeMillis() + timeUnit.toMillis(time);
		try {
			ExpireStore.put(key, time);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
	}

	@Override
	public long getExpire(Object key) {
		Long expire = ExpireStore.get(key);
		return expire == null? -2 : expire;
	}

	@Override
	public boolean hasKey(String key) {
		try {
            return ExpireStore.containsKey(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
	}

}
