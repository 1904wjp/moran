package com.moon.joyce.commons.joyce.entity;



import com.moon.joyce.commons.joyce.entity.data.DataConstructor;
import com.moon.joyce.example.entity.base.entity.BaseEntity;
import com.moon.joyce.example.functionality.entity.JoyceException;

import java.util.Objects;

/**
 * @author: Joyce
 * @autograph: Logic is justice
 * @date: 2022/08/24-- 17:07
 * @describe:
 */

public class Node<T> implements DataConstructor<T> {
    private T value;
    private Node<T> previous;
    private Node<T> next;
    private Node<T> last;
    private Node<T> first;
    private int len;
    private Node<T> node;

    public Node(Node<T> node) {
        this.node = node;
    }

    public Node() { }

    /**
     * 查询下一个是否存在
     * @return
     */
    public boolean hasNext() {
        return Objects.nonNull(this.next);
    }

    /**
     * 下一个元素
     * @return
     */
    public Node<T> next() {
        return this.next;
    }

    /**
     * 上一个元素
     * @return
     */
    public Node<T> previous() {
        return this.previous;
    }

    private void setPrevious(Node<T> p) {
        isNullCreate();
        this.node.previous = p;
        p.next = this.node;
    }

    private void setNext(Node<T> n) {
        isNullCreate();
        Node<T> current = this.last;
        n.previous = current;
        this.last = current.next = n;
    }

    /**
     * 对象为空则创建
     */
    private void isNullCreate(){
        if (Objects.isNull(this.node)) {
        this.node = new Node<>();
        this.first = this.last = this.node;
        }
    }

    /**
     * 获取最后一个元素
     * @return
     */
    public Node<T> getLast() {
        return this.last;
    }

    /**
     * 获取第一个元素
     * @return
     */
    public Node<T> getFirst() {
        return this.first;
    }

    /**
     * 添加元素
     * @param t
     * @return
     */
    @Override
    public boolean add(T t) {
        try {
            Node<T> newNode = new Node<>();
            newNode.value = t;
            setNext(newNode);
            this.len++;
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
     * 添加元素
     * @param n
     * @return
     */
    public boolean add(Node<T> n) {
        try {
            this.last.next = n.getFirst().next();
            this.last = n.getLast();
            this.len = this.len + n.len();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 添加元素
     * @param dataConstructor
     * @return
     */
    public boolean add(DataConstructor<T> dataConstructor) {
        if (dataConstructor instanceof Node){
            return add((Node<T>) dataConstructor);
        }
        if (dataConstructor instanceof Arr){
            Node<T> node = arrToNode((Arr<T>) dataConstructor);
            return add(node);
        }
        return false;
    }


    /**
     * 移除
     * @param t
     * @return
     */
    @Override
    public boolean remove(T t) {
        Node<T> head = getFirst();
        int count = -1;
        int flag = 0;
        try {
            while (Objects.nonNull(head)) {
                count++;
                if (t.equals(head.value)) {
                    if (count==this.len){
                        head.previous.next = null;
                        flag++;
                    }else {
                        head.previous.next = head.next;
                        head.next.previous = head.previous;
                        flag++;
                    }
                    this.len--;
                }
                head = head.next;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag == 0;
    }


    /**
     * 移除
     * @param t
     * @return
     */
    public boolean removeObj(T t) {
        if (t instanceof BaseEntity){
            throw new JoyceException("该类型不正确");
        }
        Node<T> head = getFirst();
        int count = -1;
        int flag = 0;
        try {
            while (Objects.nonNull(head)) {
                count++;
                if (BaseEntity.equals(t,head.value)) {
                    if (count==this.len){
                        head.previous.next = null;
                        flag++;
                    }else {
                        head.previous.next = head.next;
                        head.next.previous = head.previous;
                        flag++;
                    }
                    this.len--;
                }
                head = head.next;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag == 0;
    }


    public T getValue() {
        return this.value;
    }

    @Override
    public int len() {
        return this.len;
    }

    /**
     * 数组转节点
     * @param arr
     * @return
     */
    public Node<T> arrToNode(Arr<T> arr ){
        Node<T> node = new Node<>();
        for (T t : arr.getArr()) {
            node.add(t);
        }
        return node;
    }
}
