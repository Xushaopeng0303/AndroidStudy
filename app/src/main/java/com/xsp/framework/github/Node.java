package com.xsp.framework.github;

import java.util.ArrayList;
import java.util.List;

/**
 * 树节点 Node
 */
public class Node {

    /** 节点标识 Id */
    private int id;
    /** 根节点pId为0 */
    private int pId = 0;

    /** 展示内容 */
    private String text;
    /** 展示 Icon */
    private int icon;

    /** 当前的级别 */
    private int level;
    /** 是否展开 */
    private boolean isExpand = false;

    /** 父Node */
    private Node parent;
    /** 下一级的子 Node */
    private List<Node> children = new ArrayList<>();

    public Node() {

    }

    public Node(int id, int pId, String name) {
        super();
        this.id = id;
        this.pId = pId;
        this.text = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return pId;
    }

    public void setParentId(int pId) {
        this.pId = pId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    /**
     * 设置展开
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * 是否为跟节点
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断父节点是否展开
     */
    public boolean isParentExpand() {
        return parent != null && parent.isExpand();
    }

    /**
     * 是否是叶子界点
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

}
