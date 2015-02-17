package com.java.autoreload.monitor;
import java.io.File;  
import java.io.FileFilter;  
  
import org.apache.commons.io.IOCase;  
import org.apache.commons.io.monitor.FileAlterationListener;  
import org.apache.commons.io.monitor.FileAlterationObserver;  
  
public class FileObserver extends FileAlterationObserver{  
  
    private static final long serialVersionUID = 3637219592248717850L;  
  
    /** 
     * 设置观查的文件对象/路径对象 
     * @param directory 
     */  
    public FileObserver(String directory) {  
        this(new File(directory),(FileFilter)null);  
    }  
  
    /** 
     * 设置观查的文件路径,带文件过滤器，比如所有xml文件. 
     *     这里的IOCase是设置文件比较器(排序).[根据系统，可以不用理会] 
     * @param directoryName 
     * @param fileFilter 
     */  
    public FileObserver(File fileName, FileFilter fileFilter){  
        super(fileName,fileFilter,(IOCase)null);  
    }  
  
    /** 
     * 监控启动时初始化方法 
     *   1. 刷新本文件对象 
     *   2. 得到本文件列表 
     *   3. 循环初始化File实体 
     *   4. 设置本文件以及其下的上下级关系 
     */  
     public void initialize() throws Exception {  
        super.initialize();  
     }  
  
     /** 
      * 停止监控 
      *     默认啥都没做 
      */  
     public void destroy() throws Exception {  
         super.destroy();  
     }  
  
  
     /** 
      * 监测文件有没有创建，修改，删除 
      *        并触发相应监听 
      *        如果文件名称比原先的大，那么创建一个实体,并调用onDirectoryCreate/onFileCreate 
      *        如果文件名称比原先的小，那么删除一个实体,并调用onDirectoryDelete/onFileDelete 
      *        如果文件名称和原告的一样,新的文件与原先的文件进行对比，如果文件属性有改变，并调用onDirectoryChange/onFileChange,循环调用自身，可能是文件夹 
      *        如果名称长度排在之后，添加文件，并调用onDirectoryCreate/onFileCreate 
      * 这样就注册了所有文件的监听器 
      */  
     public void checkAndNotify() {  
         super.checkAndNotify();  
     }  
  
     /** 
      * 添加监听器 
      */  
     public void addListener(final FileAlterationListener listener) {  
         super.addListener(listener);  
     }  
  
     /** 
      * 移除监听器 
      */  
     public void removeListener(final FileAlterationListener listener) {  
         super.removeListener(listener);  
     }  
  
  
     /** 
      * 获取观察者对象的所有监听器 
      */  
     public Iterable<FileAlterationListener> getListeners() {  
         return super.getListeners();  
     }  
  
}  