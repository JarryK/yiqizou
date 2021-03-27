package com.web.hotdata;

import java.util.concurrent.TimeUnit;

/**
 * 热数据仓库接口
 * 暂不支持复杂类型对象存取，暂时一律以Object存储
 * */
public interface HotDataStore {
	
	/**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
	public boolean set(String key, Object value);
	
	/**
     * 普通缓存放入并设置时间(秒)
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time);
    
    /**
     * 普通缓存放入并设置时间
     *
     * @param key      键
     * @param value    值
     * @param time     时间
     * @param timeUnit 时间类型
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time, TimeUnit timeUnit);
	
	/**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
	public Object get(String key);
	
	/**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
	public void del(String... keys);
	
	/**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time);
    
    /**
     * 指定缓存失效时间
     *
     * @param key      键
     * @param time     时间(秒)
     * @param timeUnit 单位
     */
    public boolean expire(String key, long time, TimeUnit timeUnit);
    
    /**
     * 根据 key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(Object key);
    
    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key);
}
