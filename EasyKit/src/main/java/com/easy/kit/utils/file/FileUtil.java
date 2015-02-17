package com.easy.kit.utils.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import com.easy.kit.utils.log.ExceptionLogger;

public class FileUtil {

	public final static String charset = "utf-8";
	public static final int _MB = 1048576, _KB = 1024;

	public static String getCurrentPath() {
		String path = "";
		return path;
	}

	/**
	 * 
	 * @param content
	 *            内容
	 * @param FileName
	 *            文件名
	 * @param isOverWrite
	 *            是否覆盖
	 * @param isCreatePath
	 *            是否创建目录
	 * @param charset
	 *            编码
	 * @return 0:不成功<br/>
	 *         1:成功<br/>
	 *         -1:文件已存在<br/>
	 *         -2:创建文件夹失败<br/>
	 */
	public static int saveToFile(String content, String FileName,
			boolean isOverWrite, boolean isCreatePath, String charset) {
		return saveToFile(content, new File(FileName), isOverWrite,
				isCreatePath, charset);
	}

	/**
	 * 
	 * @param content
	 * @param file
	 * @param isOverWrite
	 * @param isCreatePath
	 * @param charset
	 * @return 0:不成功<br/>
	 *         1:成功<br/>
	 *         -1:文件已存在<br/>
	 *         -2:创建文件夹失败<br/>
	 */
	public static int saveToFile(String content, File file,
			boolean isOverWrite, boolean isCreatePath, String charset) {
		boolean result = true;
		if (isCreatePath) {
			String path = file.getParent();
			result = createFolder(path, false);
		}
		if (result) {
			return saveToFile(content, file, isOverWrite, charset);
		} else {
			return -2;
		}
	}

	/**
	 * 
	 * @param content
	 * @param file
	 * @param isOverWrite
	 * @param charset
	 * @return 0:不成功<br/>
	 *         1:成功<br/>
	 *         -1:文件已存在<br/>
	 */
	public static int saveToFile(String content, File file,
			boolean isOverWrite, String charset) {
		OutputStreamWriter osw = null;
		FileOutputStream fos = null;
		if (!file.exists() || isOverWrite) {
			try {
				fos = new FileOutputStream(file, false);
				osw = new OutputStreamWriter(fos, charset);
				osw.write(content);
			} catch (FileNotFoundException e) {
				throw new RuntimeException("文件不存在");
			} catch (UnsupportedEncodingException e) {
				ExceptionLogger.exception(e);
				return 0;
			} catch (IOException e) {
				ExceptionLogger.exception(e);
				return 0;
			} finally {
				try {
					if (osw != null) {
						osw.close();
						osw = null;
					}
					if (fos != null) {
						fos.close();
						fos = null;
					}
				} catch (IOException e) {
					ExceptionLogger.exception(e);
					return 0;
				}

			}
		} else {
			return -1;
		}
		return 1;
	}

	public static String getFileParentName(String filename) {
		File file = new File(filename);
		return file.getParent();
	}

	public static boolean createFolder(String strPath, boolean isAncestorExist) {
		try {
			strPath = getDirPath(strPath);
			File file = new File(strPath);
			boolean success = false;
			if (file.exists()) {
				success = true;
				return true;
			}
			if (isAncestorExist) {
				// create a directory;all ancestor directories must exist;
				success = file.mkdir();
				if (!success) {
					success = true;
				}
			} else {
				// create a directory;all non-ancestor directories are
				success = file.mkdirs();
			}
			return success;
		} catch (Exception ex) {
			ExceptionLogger.exception(ex);
			return false;
		}
	}

	public static String getDirPath(String value) {
		if (value.lastIndexOf("\\") == value.length() - 1
				|| value.lastIndexOf("/") == value.length() - 1) {
			return value;
		}
		if (0 == value.indexOf("/")) {
			return value + "/";
		}
		return (value + "\\");
	}

	/**
	 * getInputStream
	 * 
	 * @param fileName
	 * @return
	 */
	public static InputStream getInputStream(String fileName) {
		InputStream inputStream = null;
		File file = new File(fileName);
		try {
			if (file.exists())
				inputStream = new FileInputStream(file);
			else {
				inputStream = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream(fileName);
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		return inputStream;
	}

	public static long getFileSize(String filename) {
		File file = new File(filename);
		return file.length();
	}

	public static String loadfromfile(String filename, String charset,
			int bufferSize) {
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(getInputStream(filename), charset);
		} catch (IOException e) {
			throw new RuntimeException("找不到文件" + filename);
		}
		return loadfromfile(reader, bufferSize);
	}

	public static String loadfromfile(InputStreamReader isr, int bufferSize) {
		StringBuffer result = new StringBuffer();
		char[] buf = new char[bufferSize];
		int readlen = 0;
		try {
			while ((readlen = isr.read(buf)) > 0) {
				result = result.append(buf, 0, readlen);
			}
		} catch (IOException e) {
			ExceptionLogger.exception(e);
		} finally {
			try {
				isr.close();
			} catch (IOException e) {
				ExceptionLogger.exception(e);
			}
		}
		return result.toString();
	}

	/**
	 * 删除文件，可以是单个文件或文件夹
	 * 
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile()) {

				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			return true;
		} else {
			return false;
		}
	}

}
