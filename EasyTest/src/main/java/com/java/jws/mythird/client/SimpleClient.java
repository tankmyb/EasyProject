/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.java.jws.mythird.client;

import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.java.jws.mythird.server.User;

public final class SimpleClient {

	private static final QName SERVICE_NAME = new QName("http://server.hw.demo/", "ServiceDemo");

	private SimpleClient() {
	}

	public static void main(String args[]) throws Exception {
		Service service = Service.create(new URL("http://localhost:9898/ServiceDemo?wsdl"), SERVICE_NAME);
		ServiceDemo demo = service.getPort(ServiceDemo.class);
		List<User> list = demo.listUser();
		for (User user : list) {
			System.out.println(user.getName());
		}
	}

}
