package com.fastjson;

import com.alibaba.fastjson.JSON;


public class TestMain {
	public static long runTimes = 0L;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread() {
				public void run() {
					while (true) {
						runTimes++;
						String source = TestMain.test1();
						Group g = TestMain.test2(source);
						if (!"admin".equals(g.getName()))
							System.out.print("Error");
						try {
							Thread.sleep(1L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			t.start();
		}
		for (int i = 0; i < 100; i++) {
			Thread t = new Thread() {
				public void run() {
					while (true) {
						runTimes++;
						String source = TestMain.test3();
						Group g = TestMain.test2(source);
						if (!"admin1".equals(g.getName()))
							System.out.print("Error");
						try {
							Thread.sleep(1L);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			};
			t.start();
		}
		
		Thread t = new Thread() {
			public void run() {
				while (true) {
					System.out.println("���д���"+runTimes);
					try {
						Thread.sleep(1000L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t.start();
	}

	public static String test1() {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");
		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");
		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");
		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		String jsonString = JSON.toJSONString(group);
		// System.out.println(jsonString);
		return jsonString;
	}

	public static Group test2(String source) {
		Group group2 = JSON.parseObject(source, Group.class);
		return group2;
	}

	public static String test3() {
		Group group = new Group();
		group.setId(0L);
		group.setName("admin1");
		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");
		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");
		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		String jsonString = JSON.toJSONString(group);
		// System.out.println(jsonString);
		return jsonString;
	}
}
