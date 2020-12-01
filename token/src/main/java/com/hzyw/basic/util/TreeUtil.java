package com.hzyw.basic.util;

import com.hzyw.basic.vo.TreeVO;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    protected TreeUtil() {

    }

    private final static String TOP_NODE_ID = "0";

    /**
     * 用于构建树
     *
     * @param nodes nodes
     * @param <T>   <T>
     * @return <T> Tree<T>
     */
    public static <T> List<TreeVO<T>> build(List<TreeVO<T>> nodes) {
        if (nodes == null) {
            return null;
        }
        List<TreeVO<T>> topNodes = new ArrayList<>();
        nodes.forEach(node -> {
            String pid = node.getParentId()+"";
            if (pid == null || TOP_NODE_ID.equals(pid)) {
                topNodes.add(node);
                return;
            }
            for (TreeVO<T> n : nodes) {
                String id = n.getId()+"";
                if (id != null && id.equals(pid)) {
                    //封装菜单下按钮数据
                    if ("1".equals(node.getType())){
                        if (n.getButtonList()==null){
                            n.initButtonList();
                        }
                        n.getButtonList().add(node);
                        node.setHasParent(true);
                        n.setHasChildren(true);
                        n.setHasParent(true);
                        return;
                    }
                    if (n.getChildren() == null) {
                        n.initChildren();
                    }
                    n.getChildren().add(node);
                    node.setHasParent(true);
                    n.setHasChildren(true);
                    n.setHasParent(true);
                    return;
                }
//                if (id!= null && id.equals(pid) && "1".equals(n.getType())){
//                    if (n.getButtonList()==null){
//                        n.initButtonList();
//                    }
//                    n.getButtonList().add(node);
//                    node.setHasParent(true);
//                    n.setHasChildren(true);
//                    n.setHasParent(true);
//                    return;
//                }
            }
           // if (topNodes.isEmpty())
                topNodes.add(node);
        });
        return topNodes;
    }

}