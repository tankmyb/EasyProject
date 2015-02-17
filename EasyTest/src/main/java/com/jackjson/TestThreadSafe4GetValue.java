package com.jackjson;



public class TestThreadSafe4GetValue {
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
						String source = TestThreadSafe4GetValue.test1();
						String name = TestThreadSafe4GetValue.getValue(source);
						if (!"admin".equals(name))
							System.err.print("Error");
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
						String source = TestThreadSafe4GetValue.test3();
						String name = TestThreadSafe4GetValue.getValue(source);
						if (!"admin1".equals(name))
							System.err.print("Error");
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
					System.out.println("runTimes:"+runTimes);
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
		String jsonString = ResolveUnit.getJsonStr(group);
		// System.out.println(jsonString);
		return jsonString;
	}

	public static String getValue(String source) {
		String name = ResolveUnit.getNodeValue(source,"name");
		return name;
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
		String jsonString = ResolveUnit.getJsonStr(group);
		//System.out.println(jsonString);
		return jsonString;
	}
}
