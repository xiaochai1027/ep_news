package com.jdk8.pc;

/**
 * author fangchen
 * date  2018/4/28 下午2:54
 */
public interface Parent {

    void messge(String mess);

    default void welcome(){
        messge("parent");
    }

    public String getLastMessage();

}
