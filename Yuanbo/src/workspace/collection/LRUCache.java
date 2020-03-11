package workspace.collection;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private static int MAX_ENTRIES = 100;

    @Override
    protected boolean removeEldestEntry(Map.Entry eldest) {
        return size() > MAX_ENTRIES;
    }

    /* 将 LinkedHashMap 改造成缓存，需要重写 LinkedHashMap 中 removeEldestEntry(Map.Entry<K,V> eldest)，这个方法，
     改方法是 protected 方法，不能直接调用，只能继承重写。当插入数据时（调用 put 或者 putAll 时）会调用这个方法用于判断是否移除最老元素，
     返回 true 表示删除，否则不删除，Java 源代码中，该方法直接返回 false，如下图所示，看来是专门留给开发者扩展额。*/
    public LRUCache(int maxCacheSize) {

        // 第三个参数为 accessOrder，默认为false。表示按照按照访问顺序排列元素，最近访问的元素会排雷在队末尾
        super(maxCacheSize, 0.75f, true);

        this.MAX_ENTRIES = maxCacheSize;

    }


    public V putCache(K key, V value) {
        synchronized (this) {
            return this.put(key, value);
        }
    }

    /* 通过简单的方式就可以快速实现一个LRU缓存类，但 LinkedHashMap 不是线程安全额，在面对高并发的情况下还需要进一步封装，比如通过 synchronized 封装代理方法，如：*/


    /*
    *
Output：
test1:{k1=v1}
test2:{k1=v1, k2=v2}
test3:{k1=v1, k2=v2, k3=v3}
test4:{k2=v2, k3=v3, k4=v4}
test5:{k3=v3, k4=v4, k2=v2}
test5:{k2=v2, k5=k5, k6=k6}
*/
    public static void main(String[] args) {

/*
        Map<String, String> cache = Collections.synchronizedMap(new LruCache<String, String>(3));
*/


        LRUCache<String, String> cache = new LRUCache<String, String>(3);

        cache.put("k1", "v1");

        System.out.println("test1:" + cache);

        cache.put("k2", "v2");

        System.out.println("test2:" + cache);

        cache.put("k3", "v3");

        System.out.println("test3:" + cache);

        cache.put("k4", "v4");

        System.out.println("test4:" + cache);

//因为我们在后再对象时，accessOrder设置为true，访问一次 k2，k2对应的元素就会排在队尾部，被看做最新元素

        cache.get("k2");

        System.out.println("test5:" + cache);


        Map<String, String> multiKV = new HashMap<String, String>();

        multiKV.put("k5", "k5");

        multiKV.put("k6", "k6");

        cache.putAll(multiKV);

        System.out.println("test5:" + cache);
    }

}
