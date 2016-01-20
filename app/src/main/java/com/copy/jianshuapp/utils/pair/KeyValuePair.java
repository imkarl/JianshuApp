package com.copy.jianshuapp.utils.pair;

/**
 * KEY/VALUE键值对
 * @author alafighting 2016-01-19
 */
public class KeyValuePair<K, V> {

    private K key;
    private V value;

    public KeyValuePair(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }
    public KeyValuePair() {
        super();
    }


    @Override
    public String toString() {
        return "KeyValuePair{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }


    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
