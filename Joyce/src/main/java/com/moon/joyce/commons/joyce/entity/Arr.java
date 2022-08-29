package com.moon.joyce.commons.joyce.entity;

import com.moon.joyce.commons.joyce.entity.data.DataConstructor;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import com.moon.joyce.example.functionality.entity.JoyceException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/24-- 17:06
 * @describe: 通用类型
 */
public class Arr<T> implements DataConstructor<T>, Serializable {
  private T[] arr ;
  private int len = 0;
  private int of = 0;

    /**
     * 获取数组
     * @return
     */
    public T[] arr() {
        T[] objects = (T[])new Object[this.of];
        if (this.of!=0){
            for (int i = 0; i < objects.length; i++) {
                objects[i] = (T) this.arr[i];
            }
        }
        return objects;
    }

    /**
     * 添加
     * @param t
     * @return
     */
    @Override
    public boolean add(T t){
        try {
            if (this.of >= this.len){
             this.arr  = capacity(this.arr);
            }
            this.arr[this.of++] = t;
            this.len = this.of;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 新增元素
     * @param dataConstructor
     * @return
     */
    public boolean add(DataConstructor<T> dataConstructor){
        if (dataConstructor instanceof Arr){
            return add((Arr<T>)dataConstructor);
        }
        if (dataConstructor instanceof Node){
            Arr<T> tArr = nodeToArr((Node<T>) dataConstructor);
            return add(tArr);
        }
        return false;
    }

    private boolean add(Arr<T> arr){
        try {
            int size  = len();
            while ((len+this.len)>size){
                this.arr = capacity(this.arr);
                size = this.arr.length;
            }

            for (int i = 0; i < arr.len(); i++) {
                this.arr[len()+i] = arr.arr()[i];
            }
            this.len = this.of = this.len + arr.len();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.len == 0;
    }

    /**
     * 获取长度
     * @return
     */
    @Override
    public int len(){
        return this.len;
    }

    /**
     * 扩容
     * @param ts
     * @return
     */
    private T[] capacity(T[] ts){
        if (Objects.isNull(ts)){
             ts = (T[]) new Object[8];
        }else {
            T[] ts1  = (T[]) new Object[ts.length*2];
            for (int i = 0; i < (ts1.length)/2-1; i++) {
                ts1[i] = ts[i];
            }
            ts = ts1;
        }
        return ts;
    }

    /**
     * 移除下标为i的元素
     * @param i
     */
    public boolean remove(int i){
        if (i>this.len){
            throw new JoyceException("下标越界");
        }
        int flag = 0;
        try {
            T[] ts = (T[])new Object[this.len-1];
            for (int j = 0; j < this.len; j++) {
                if (j<i){
                    ts[j] = this.arr[j];
                    flag = 1;
                }
                if (j>i){
                    ts[j] = this.arr[j+1];
                    flag = 1;
                }
                this.of = this.len = this.of - 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag == 1;
    }

    /**
     * 移除目标元素
     * @param t
     */
    @Override
    public boolean remove(T t){
        int flag = 0;
        for (int j = 0; j < this.len; j++) {
           if (arr[j].equals(t)){
               boolean rs = remove(j);
               if (!rs){
                   flag ++;
               }
           }
        }
        return flag==0;
    }

    @Override
    public boolean removeObj(T t) {
        if (!(t instanceof BaseEntity)){
            throw new JoyceException("该变量不是所需对象");
        }
        int flag = 0;
        for (int j = 0; j < this.len; j++) {
            if (BaseEntity.equals(arr[j],t)){
                boolean rs = remove(j);
                if (!rs){
                    flag ++;
                }
            }
        }
        return flag==0;
    }

    public T get(int i){
        return arr[i];
    }
    @Override
    public String toString() {
        this.arr = arr();
        final StringBuffer sb = new StringBuffer("Arr{");
        sb.append("arr=").append(arr == null ? "null" : Arrays.asList(arr).toString());
        sb.append('}');
        return sb.toString();
    }

    /**
     * 节点转数组
     * @param node
     * @return
     */
    public Arr<T> nodeToArr(Node<T> node){
        Arr<T> tArr = new Arr<>();
        Node<T> head = node.getFirst().next();
        while (Objects.nonNull(head)){
            tArr.add(head.getValue());
            head = head.next();
        }
        return tArr;
    }
}
