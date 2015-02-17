package com.jackjson;


public class TestPerformance {
	private static JsonUtil json = JsonUtil.buildNonNullBinder();
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//TestPerformance.test1();
		//TestPerformance.test2("{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}");
		TestPerformance.getValueByGetNodeValue("{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}");
		//TestPerformance.getValueByBean("{\"id1\":0,\"id\":0,\"name\":\"admin\",\"users\":[{\"id\":2,\"name\":\"guest\"},{\"id\":3,\"name\":\"root\"}]}");
	}
  public static void getValueByGetNodeValue(String message){
  	long start = System.currentTimeMillis();
  	String name = null;
  	//for(int i=0;i<1000;i++){
  		name =ResolveUnit.getNodeValue(message, "id");
  	//}
  	System.out.println(name);
  	System.out.println(System.currentTimeMillis() - start);
  }
  public static void getValueByBean(String message){
  	long start = System.currentTimeMillis();
  	String name = null;
  	for(int i=0;i<1000;i++){
  		Group group = ResolveUnit.resolve(message, Group.class);
  		name = group.getName();
  	}
  	System.out.println(name);
  	System.out.println(System.currentTimeMillis() - start);
  }{
  	
  }
	private static String test1() {
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
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			String jsonString = JsonUtil.buildNonNullBinder().toJson(group);
		}
		System.out.println(System.currentTimeMillis() - start);
		return null;
	}

	private static Group test2(String source) {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 10000; i++) {
			Group group2 = JsonUtil.buildNonNullBinder().fromJson(source, Group.class);
		}
		System.out.println(System.currentTimeMillis() - start);
		return null;
	}
}
