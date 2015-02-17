package com.java.recursion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test {

	private List<Tree> getTree() {
		List<Tree> list = new ArrayList<Tree>();
		Tree t1 = new Tree();
		t1.setId(1);
		t1.setName("name1");

		List<Tree> child1 = new ArrayList<Tree>();
		Tree c1 = new Tree();
		c1.setId(2);
		c1.setName("child1");
		child1.add(c1);
		Tree c2 = new Tree();
		c2.setId(3);
		c2.setName("child2");
		List<Tree> childC2 = new ArrayList<Tree>();
		Tree ct1 = new Tree();
		ct1.setId(32);
		ct1.setName("sdfsdsd");
		childC2.add(ct1);
		c2.setChildren(childC2);

		child1.add(c2);
		t1.setChildren(child1);

		list.add(t1);

		Tree t2 = new Tree();
		t2.setId(5);
		t2.setName("name5");

		List<Tree> child3 = new ArrayList<Tree>();
		Tree c3 = new Tree();
		c3.setId(6);
		c3.setName("child6");
		child3.add(c3);
		Tree c4 = new Tree();
		c4.setId(7);
		c4.setName("child7");
		child3.add(c4);
		t2.setChildren(child3);

		list.add(t2);
		return list;
	}

	/**
	 * 正确,递归只能有唯一一个出口
	 * 
	 * @param list
	 * @param nodeId
	 * @return
	 */
	private Tree getTreeNodeCorrect(List<Tree> list, Integer nodeId) {
		Tree tree = null;
		for (Iterator<Tree> it = list.iterator(); it.hasNext();) {
			Tree t = it.next();
			if (t.getId().intValue() == nodeId.intValue()) {
				tree = t;
				break;
			} else {
				if (t.getChildren() != null) {
					tree = getTreeNodeCorrect(t.getChildren(), nodeId);
					if (tree != null) {
						break;
					}
				}
			}
		}
		return tree;
	}
    /**
     * 錯誤例子
     * @param list
     * @param nodeId
     * @return
     */
	private Tree getTreeNodeError(List<Tree> list, Integer nodeId) {

		for (Iterator<Tree> it = list.iterator(); it.hasNext();) {
			Tree t = it.next();
			if (t.getId().intValue() == nodeId.intValue()) {
				return t;
			} else {
				if (t.getChildren() != null) {
					return getTreeNodeError(t.getChildren(), nodeId);
				}
			}
		}
		return null;
	}
	public static void main(String[] args) {
		Test t = new Test();
		List<Tree> list = t.getTree();
		//Tree t1 = t.getTreeNodeError(list, 7);
		//System.out.println(t1.getName());
		
		Tree t2 = t.getTreeNodeCorrect(list, 7);
		System.out.println(t2.getName());
	}
}
