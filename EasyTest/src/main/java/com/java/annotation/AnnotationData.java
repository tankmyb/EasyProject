package com.java.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**关键技术

 * 1.Java常用的内置注释

 *     @Override放在方法的修饰符前，表示该方法覆盖了父类的同名同参数方法，如果该方法没有覆盖父 类的方法而使用了该注释，则编译器会报错

 *     @Deprecated放在变量和方法的访问修饰符前，表示该变量或者方法已经不推荐使用，通常是因为它很危险或者存在更好的选择。

 * 2.可以自定义注释类型，与enum定义枚举类型一样，使用@interface定义注释类型

 * 3. 能在自定义的注释中定义其它类、属性和方法。定义属性时，以属性名为方法名，以属性类型为方法的返回值类型，方法后接default关键字表示属性的默认值。如"String name();"表示该注释类型具有name属性，类型为String;而"int age() default 20;"表示该注释具有age属性，类型为int，默认值为20.

 * 4. 可以在自定义的注释类型的声明中添加注释，即注释的元注释。Java有2个常用的内置元注释：

 *    @Target指定Annotation类型可以应用的程序元素，程序元素的类型由java.lang.annotation.ElementType枚举类定义。如ElementType.METHOD表示该注释类型只能用于方法声明中。

 *    @Retention和Java编译器处理Annotation类型的方式有关，这些方式由java.lang.annotation.RetentionPolicy枚举值定义。如RetentionPolicy.RUNTIME表示该注释类型 将被编译成class文件

 *    @Document指明需要在Javadoc中包含注释(默认是不包含的)

 */


public class AnnotationData {
	@Deprecated
	private String name;

	public AnnotationData(String name) {

		this.name = name;

	}

	// 方法声明中使用了内置的@Override元数据，表示该方法覆盖了父类的同名同参数方法

	// 如果父类不存在该方法，则编译不会通过

	@Override
	public String toString() {

		return super.toString() + this.name;

	}

	@Override
	public int hashCode() {

		return toString().hashCode();

	}

	/** 方法中使用了内置的@Deprecated元数据，表示该方法已经不被推荐使用了 */

	@Deprecated
	public String getName() {

		return name;

	}

	public String getAnnotationDataName() {

		return this.name;

	}

	// 下面定义元数据类型

	// 使用@interface声明Annotation类型

	public @interface MyAnnotation {

		// 在元数据中可以定义其它类

		public enum Severity {

			CRITICAL, IMPORTANT, TRIVIAL, DOCUMENT

		};

		// 定义数据类型不需要定义getter和setter方法

		// 只需要定义一个以成员名称命名的方法，并指定返回类型为需要的数据类型

		// default关键字为Annotation类型的成员设置默认值

		Severity severity() default Severity.IMPORTANT;

		String item();

		String assignedTo();

		String dateAssigned();

	}

	// 使用自定义的Annotation类型，在使用时，

	// 如果Annotation类型在其他的包下，需要与使用类一样，import它

	@MyAnnotation(severity = MyAnnotation.Severity.CRITICAL,

	item = "Must finish this method carefully",

	assignedTo = "Programmer A",

	dateAssigned = "2006/09/10")
	public void doFunction() {

		// do something

	}

	// 下面再定义一个Annotation类型，使用了元数据的元数据

	// @Retention(RetentionPolicy.RUNNTIME)这个meta-annotation

	// 表示了此类型的annotation将编译成class文件，而且还能被虚拟机读取

	// 而@Target(ElementType.METHOD)表示此类型的annotation只能用于修饰方法声明

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	public @interface MyNewAnnotation {

	}

}
