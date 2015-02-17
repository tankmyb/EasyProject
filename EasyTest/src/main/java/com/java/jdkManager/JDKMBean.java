package com.java.jdkManager;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.CompilationMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;
import java.util.List;

/**
 * 本例介绍如何监控和管理Java虚拟机，包括获取Java虚拟机的内存使用情况、线程数、运行时间等。
 * 
 * 在J2SE5.0中使用Bean监控和管理Java虚拟机，java.lang.management.ManagementFactory是管理Bean的工厂类
 * ，
 * 
 * 通过它的get系列方法能够获得不同的管理Bean的实例。
 * 
 * 1. java.lang.management.MemoryMXBean: 该Bean用于管理Java虚拟机的内存系统。
 * 
 * 一个Java虚拟机具有一个实例。
 * 
 * 2.java.lang.management.ClassLoadingMXBean:该Bean用于管理Java虚拟机的类加载系统。
 * 
 * 一个Java虚拟机具有一个实例。
 * 
 * 3.java.lang.management.TreadMXBean:该Bean用于管理Java虚拟机的线程系统。
 * 
 * 一个Java虚拟机具有一个实例。
 * 
 * 4.java.lang.management.RuntimeMXBean:该Bean用于管理Java虚拟机的线程系统。
 * 
 * 一个Java虚拟机具有一个实例。
 * 
 * 5.java.lang.management.OperatingSystemMXBean:该Bean用于管理操作系统。
 * 
 * 一个Java虚拟机具有一个实例。
 * 
 * 6.java.lang.management.CompilationMXBean:该Bean用于管理Java虚拟机的编译系统。
 * 
 * 一个Java虚拟机具有一个实例。
 * 
 * 7.java.lang.management.GarbageCollectorMXBean:该Bean用于管理Java虚拟机的垃圾回收系统。
 * 
 * 一个Java虚拟机具有一个或者多个实例。
 */

public class JDKMBean {
	public static void printMemoryMXBean() {

		// 获得单一实例

		MemoryMXBean instance = ManagementFactory.getMemoryMXBean();

		System.out.printf("%n---%s---%n", instance.getClass().getName());

		// 返回用于对象分配的堆的当前内存使用量

		System.out.printf("%s: %s%n", "HeapMemoryUsage",
				instance.getHeapMemoryUsage());

		// 返回Java虚拟机使用的非堆内存的当前使用量

		System.out.printf("%s: %s%n", "getNonHeapMemoryUsage",
				instance.getNonHeapMemoryUsage());

		instance.gc();

	}

	public static void printClassLoadingMXBean() {

		// 获得单一实例

		ClassLoadingMXBean instance = ManagementFactory.getClassLoadingMXBean();

		System.out.printf("%n---%s---%n", instance.getClass().getName());

		// 返回当前加载到Java虚拟机中的类的数量

		System.out.printf("%s: %s%n", "LoadedClassCount",
				instance.getLoadedClassCount());

		// 返回自Java虚拟机开始执行到目前已经加载的类的总数

		System.out.printf("%s: %s%n", "TotalLoadedClassCount",
				instance.getTotalLoadedClassCount());

		// 返回自Java虚拟机开始执行到目前已经卸载的类的总数

		System.out.printf("%s: %s%n", "UnloadedLoadedClassCount",
				instance.getUnloadedClassCount());

	}

	public static void printThreadMXBean() {

		// 获得单一实例

		ThreadMXBean instance = ManagementFactory.getThreadMXBean();

		System.out.printf("%n---%s---%n", instance.getClass().getName());

		// 返回活动线程的当前数目，包括守护线程和非守护线程

		System.out.printf("%s: %s%n", "ThreadCount", instance.getThreadCount());

		// 返回活动线程ID

		System.out.printf("%s: %n", "Thread IDs");

		long[] ids = instance.getAllThreadIds();

		for (long id : ids) {

			System.out.printf("%s;    ", id);

		}

		System.out.println();

		// 返回活动守护线程的当前数目

		System.out.printf("%s: %s%n", "DaemonThreadCount",
				instance.getDaemonThreadCount());

		// 返回自从java虚拟机启动或峰值重置以来峰值活动线程计数

		System.out.printf("%s: %s%n", "PeakThreadCount",
				instance.getPeakThreadCount());

		// 返回当前线程的总CPU时间

		System.out.printf("%s: %s%n", "CurrentCpuTime",
				instance.getCurrentThreadCpuTime());

		// 返回当前线程在用户模式中执行的CPU时间

		System.out.printf("%s: %s%n", "CurrentThreadCpuTime",
				instance.getCurrentThreadUserTime());

	}

	public static void printRuntimeMXBean() {

		// 获得单一实例

		RuntimeMXBean instance = ManagementFactory.getRuntimeMXBean();

		System.out.printf("%n---%s---%n", instance.getClass().getName());

		// 返回由引导类加载器用于搜索类文件的引导类路径

		System.out.printf("%s: %s%n", "BootClassPath",
				instance.getBootClassPath());

		// 返回系统类加载器用于搜索类文件的Java类路径

		System.out.printf("%s: %s%n", "ClassPath", instance.getClassPath());

		// 引用传递给Java虚拟机的输入变量，其中不包括传递给main方法的变量

		System.out.printf("%s: %n", "InputArguments");

		List<String> args = instance.getInputArguments();

		for (String arg : args) {

			System.out.printf("%s;  ", arg);

		}

		// 返回Java库路径

		System.out.printf("%s: %s%n", "LibraryPath", instance.getLibraryPath());

		// 返回正在运行的Java虚拟机实现的管理接口的规范版本

		System.out.printf("%s: %s%n", "ManagementSpecVersion",
				instance.getManagementSpecVersion());

		// 返回正在运行的Java虚拟机的名称

		System.out.printf("%s: %s%n", "Name", instance.getName());

		// 返回Java虚拟机规范名称

		System.out.printf("%s: %s%n", "SpecName", instance.getSpecName());

		// 返回Java虚拟机规范提供商

		System.out.printf("%s: %s%n", "SpecVendor", instance.getSpecVendor());

		// 返回Java虚拟机规范版本

		System.out.printf("%s: %s%n", "SpecVersion", instance.getSpecVersion());

		// 返回Java虚拟机实现名称

		System.out.printf("%s: %s%n", "VmName", instance.getVmName());

		// 返回Java虚拟机实现提供商

		System.out.printf("%s: %s%n", "VmVendor", instance.getVmVendor());

		// 返回Java虚拟机实现版本

		System.out.printf("%s: %s%n", "VmVersion", instance.getVmVersion());

		// 返回Java虚拟机的启动时间

		System.out.printf("%s: %s%n", "startTime", instance.getStartTime());

		// 返回Java虚拟机的正常运行时间

		System.out.printf("%s: %s%n", "Uptime", instance.getUptime());

	}

	public static void printOperatingSystemMXBean() {

		// 获得单一实例

		OperatingSystemMXBean instance = ManagementFactory
				.getOperatingSystemMXBean();

		System.out.printf("%n---%s---%n", instance.getClass().getName());

		// 返回操作系统的架构

		System.out.printf("%s: %s%n", "Arch", instance.getArch());

		// 返回Java虚拟机可以使用的处理器数目

		System.out.printf("%s: %s%n", "AvailableProcessors",
				instance.getAvailableProcessors());

		// 返回操作系统名称

		System.out.printf("%s: %s%n", "Name", instance.getName());

		// 返回操作系统版本

		System.out.printf("%s: %s%n", "Version", instance.getVersion());

	}

	public static void printCompilationMXBean() {

		// 获得单一实例

		CompilationMXBean instance = ManagementFactory.getCompilationMXBean();

		System.out.printf("%n---%s---%n", instance.getClass().getName());

		// 返回即时(JIT)编译器的名称

		System.out.printf("%s: %s%n", "JIT", instance.getName());

		// 返回在编译上花费的累积耗费时间的近似值

		System.out.printf("%s: %s%n", "TotalCompilationTime",
				instance.getTotalCompilationTime());

	}

	public static void printGargageCollectorMXBean() {

		// 获得单一实例

		List<GarbageCollectorMXBean> instances = ManagementFactory
				.getGarbageCollectorMXBeans();

		System.out.printf("%n---%s---%n",
				GarbageCollectorMXBean.class.getName());

		// 遍历每个实例

		for (GarbageCollectorMXBean instance : instances) {

			// 返回垃圾收集器的名字

			System.out.printf("***%s: %s***%n", "Name", instance.getName());

			// 返回已发生的回收的总次数

			System.out.printf("%s: %s%n", "CollectionCount",
					instance.getCollectionCount());

			// 返回近似的累积回收时间

			System.out.printf("%s: %s%n", "CollectionTime",
					instance.getCollectionTime());

		}

	}

	public static void main(String[] args) {

		JDKMBean.printMemoryMXBean();

		JDKMBean.printClassLoadingMXBean();

		JDKMBean.printThreadMXBean();

		JDKMBean.printRuntimeMXBean();

		JDKMBean.printOperatingSystemMXBean();

		JDKMBean.printCompilationMXBean();

		JDKMBean.printGargageCollectorMXBean();

	}

}
