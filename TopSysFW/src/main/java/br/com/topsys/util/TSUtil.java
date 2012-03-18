/*
 * Created on 08/05/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

package br.com.topsys.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;

import br.com.topsys.exception.TSSystemException;

/**
 * @author andre
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public final class TSUtil {

	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

	private static final int TAM_CNPJ = 14;

	private static final int TAM_CPF = 11;

	private static final String STRING_DEFAULT = "0";

	/**
	 * <p>
	 * The <code>file.encoding</code> System Property.
	 * </p>
	 * <p>
	 * File encoding, such as <code>Cp1252</code>.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since 2.0
	 * @since Java 1.2.
	 */
	public static final String FILE_ENCODING = getSystemProperty("file.encoding");

	/**
	 * <p>
	 * The <code>file.separator</code> System Property. File separator (
	 * <code>&quot;/&quot;</code> on UNIX).
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1.
	 */
	public static final String FILE_SEPARATOR = getSystemProperty("file.separator");

	/**
	 * <p>
	 * The <code>java.class.path</code> System Property. Java class path.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1.
	 */
	public static final String JAVA_CLASS_PATH = getSystemProperty("java.class.path");

	/**
	 * <p>
	 * The <code>java.class.version</code> System Property. Java class format
	 * version number.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1.
	 */
	public static final String JAVA_CLASS_VERSION = getSystemProperty("java.class.version");

	/**
	 * <p>
	 * The <code>java.compiler</code> System Property. Name of JIT compiler to
	 * use. First in JDK version 1.2. Not used in Sun JDKs after 1.2.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2. Not used in Sun versions after 1.2.
	 */
	public static final String JAVA_COMPILER = getSystemProperty("java.compiler");

	/**
	 * <p>
	 * The <code>java.ext.dirs</code> System Property. Path of extension
	 * directory or directories.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.3
	 */
	public static final String JAVA_EXT_DIRS = getSystemProperty("java.ext.dirs");

	/**
	 * <p>
	 * The <code>java.home</code> System Property. Java installation directory.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String JAVA_HOME = getSystemProperty("java.home");

	/**
	 * <p>
	 * The <code>java.io.tmpdir</code> System Property. Default temp file path.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_IO_TMPDIR = getSystemProperty("java.io.tmpdir");

	/**
	 * <p>
	 * The <code>java.library.path</code> System Property. List of paths to
	 * search when loading libraries.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_LIBRARY_PATH = getSystemProperty("java.library.path");

	/**
	 * <p>
	 * The <code>java.runtime.name</code> System Property. Java Runtime
	 * Environment name.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since 2.0
	 * @since Java 1.3
	 */
	public static final String JAVA_RUNTIME_NAME = getSystemProperty("java.runtime.name");

	/**
	 * <p>
	 * The <code>java.runtime.version</code> System Property. Java Runtime
	 * Environment version.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since 2.0
	 * @since Java 1.3
	 */
	public static final String JAVA_RUNTIME_VERSION = getSystemProperty("java.runtime.version");

	/**
	 * <p>
	 * The <code>java.specification.name</code> System Property. Java Runtime
	 * Environment specification name.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_SPECIFICATION_NAME = getSystemProperty("java.specification.name");

	/**
	 * <p>
	 * The <code>java.specification.vendor</code> System Property. Java Runtime
	 * Environment specification vendor.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_SPECIFICATION_VENDOR = getSystemProperty("java.specification.vendor");

	/**
	 * <p>
	 * The <code>java.specification.version</code> System Property. Java Runtime
	 * Environment specification version.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.3
	 */
	public static final String JAVA_SPECIFICATION_VERSION = getSystemProperty("java.specification.version");

	/**
	 * <p>
	 * The <code>java.vendor</code> System Property. Java vendor-specific
	 * string.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String JAVA_VENDOR = getSystemProperty("java.vendor");

	/**
	 * <p>
	 * The <code>java.vendor.url</code> System Property. Java vendor URL.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String JAVA_VENDOR_URL = getSystemProperty("java.vendor.url");

	/**
	 * <p>
	 * The <code>java.version</code> System Property. Java version number.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String JAVA_VERSION = getSystemProperty("java.version");

	/**
	 * <p>
	 * The <code>java.vm.info</code> System Property. Java Virtual Machine
	 * implementation info.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since 2.0
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_INFO = getSystemProperty("java.vm.info");

	/**
	 * <p>
	 * The <code>java.vm.name</code> System Property. Java Virtual Machine
	 * implementation name.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_NAME = getSystemProperty("java.vm.name");

	/**
	 * <p>
	 * The <code>java.vm.specification.name</code> System Property. Java Virtual
	 * Machine specification name.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_SPECIFICATION_NAME = getSystemProperty("java.vm.specification.name");

	/**
	 * <p>
	 * The <code>java.vm.specification.vendor</code> System Property. Java
	 * Virtual Machine specification vendor.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_SPECIFICATION_VENDOR = getSystemProperty("java.vm.specification.vendor");

	/**
	 * <p>
	 * The <code>java.vm.specification.version</code> System Property. Java
	 * Virtual Machine specification version.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_SPECIFICATION_VERSION = getSystemProperty("java.vm.specification.version");

	/**
	 * <p>
	 * The <code>java.vm.vendor</code> System Property. Java Virtual Machine
	 * implementation vendor.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_VENDOR = getSystemProperty("java.vm.vendor");

	/**
	 * <p>
	 * The <code>java.vm.version</code> System Property. Java Virtual Machine
	 * implementation version.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.2
	 */
	public static final String JAVA_VM_VERSION = getSystemProperty("java.vm.version");

	/**
	 * <p>
	 * The <code>line.separator</code> System Property. Line separator (
	 * <code>&quot;\n<&quot;</code> on UNIX).
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String LINE_SEPARATOR = getSystemProperty("line.separator");

	/**
	 * <p>
	 * The <code>os.arch</code> System Property. Operating system architecture.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String OS_ARCH = getSystemProperty("os.arch");

	/**
	 * <p>
	 * The <code>os.name</code> System Property. Operating system name.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String OS_NAME = getSystemProperty("os.name");

	/**
	 * <p>
	 * The <code>os.version</code> System Property. Operating system version.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String OS_VERSION = getSystemProperty("os.version");

	/**
	 * <p>
	 * The <code>path.separator</code> System Property. Path separator (
	 * <code>&quot;:&quot;</code> on UNIX).
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String PATH_SEPARATOR = getSystemProperty("path.separator");

	/**
	 * <p>
	 * The <code>user.country</code> or <code>user.region</code> System
	 * Property. User's country code, such as <code>GB</code>. First in JDK
	 * version 1.2 as <code>user.region</code>. Renamed to
	 * <code>user.country</code> in 1.4
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since 2.0
	 * @since Java 1.2
	 */
	public static final String USER_COUNTRY = (getSystemProperty("user.country") == null ? getSystemProperty("user.region") : getSystemProperty("user.country"));

	/**
	 * <p>
	 * The <code>user.dir</code> System Property. User's current working
	 * directory.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String USER_DIR = getSystemProperty("user.dir");

	/**
	 * <p>
	 * The <code>user.home</code> System Property. User's home directory.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String USER_HOME = getSystemProperty("user.home");

	/**
	 * <p>
	 * The <code>user.language</code> System Property. User's language code,
	 * such as 'en'.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since 2.0
	 * @since Java 1.2
	 */
	public static final String USER_LANGUAGE = getSystemProperty("user.language");

	/**
	 * <p>
	 * The <code>user.name</code> System Property. User's account name.
	 * </p>
	 * 
	 * <p>
	 * Defaults to <code>null</code> if the runtime does not have security
	 * access to read this property or the property does not exist.
	 * </p>
	 * 
	 * @since Java 1.1
	 */
	public static final String USER_NAME = getSystemProperty("user.name");

	// Java version
	// -----------------------------------------------------------------------
	// These MUST be declared after those above as they depend on the
	// values being set up

	/**
	 * <p>
	 * Gets the Java version as a <code>float</code>.
	 * </p>
	 * 
	 * <p>
	 * Example return values:
	 * </p>
	 * <ul>
	 * <li><code>1.2f</code> for JDK 1.2
	 * <li><code>1.31f</code> for JDK 1.3.1
	 * </ul>
	 * 
	 * <p>
	 * The field will return zero if {@link #JAVA_VERSION}is <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final float JAVA_VERSION_FLOAT = getJavaVersionAsFloat();

	/**
	 * <p>
	 * Gets the Java version as an <code>int</code>.
	 * </p>
	 * 
	 * <p>
	 * Example return values:
	 * </p>
	 * <ul>
	 * <li><code>120</code> for JDK 1.2
	 * <li><code>131</code> for JDK 1.3.1
	 * </ul>
	 * 
	 * <p>
	 * The field will return zero if {@link #JAVA_VERSION}is <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final int JAVA_VERSION_INT = getJavaVersionAsInt();

	// Java version checks
	// -----------------------------------------------------------------------
	// These MUST be declared after those above as they depend on the
	// values being set up

	/**
	 * <p>
	 * Is <code>true</code> if this is Java version 1.1 (also 1.1.x versions).
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if {@link #JAVA_VERSION}is
	 * <code>null</code>.
	 * </p>
	 */
	public static final boolean IS_JAVA_1_1 = getJavaVersionMatches("1.1");

	/**
	 * <p>
	 * Is <code>true</code> if this is Java version 1.2 (also 1.2.x versions).
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if {@link #JAVA_VERSION}is
	 * <code>null</code>.
	 * </p>
	 */
	public static final boolean IS_JAVA_1_2 = getJavaVersionMatches("1.2");

	/**
	 * <p>
	 * Is <code>true</code> if this is Java version 1.3 (also 1.3.x versions).
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if {@link #JAVA_VERSION}is
	 * <code>null</code>.
	 * </p>
	 */
	public static final boolean IS_JAVA_1_3 = getJavaVersionMatches("1.3");

	/**
	 * <p>
	 * Is <code>true</code> if this is Java version 1.4 (also 1.4.x versions).
	 * </p>
	 * 
	 * <p>
	 * The field will <code>false</code> false if {@link #JAVA_VERSION}is
	 * <code>null</code>.
	 * </p>
	 */
	public static final boolean IS_JAVA_1_4 = getJavaVersionMatches("1.4");

	/**
	 * <p>
	 * Is <code>true</code> if this is Java version 1.5 (also 1.5.x versions).
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if {@link #JAVA_VERSION}is
	 * <code>null</code>.
	 * </p>
	 */
	public static final boolean IS_JAVA_1_5 = getJavaVersionMatches("1.5");

	// Operating system checks
	// -----------------------------------------------------------------------
	// These MUST be declared after those above as they depend on the
	// values being set up
	// OS names from http://www.vamphq.com/os.html
	// Selected ones included - please advise commons-dev@jakarta.apache.org
	// if you want another added or a mistake corrected

	/**
	 * <p>
	 * Is <code>true</code> if this is AIX.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_AIX = getOSMatches("AIX");

	/**
	 * <p>
	 * Is <code>true</code> if this is HP-UX.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_HP_UX = getOSMatches("HP-UX");

	/**
	 * <p>
	 * Is <code>true</code> if this is Irix.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_IRIX = getOSMatches("Irix");

	/**
	 * <p>
	 * Is <code>true</code> if this is Linux.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_LINUX = getOSMatches("Linux") || getOSMatches("LINUX");

	/**
	 * <p>
	 * Is <code>true</code> if this is Mac.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_MAC = getOSMatches("Mac");

	/**
	 * <p>
	 * Is <code>true</code> if this is Mac.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_MAC_OSX = getOSMatches("Mac OS X");

	/**
	 * <p>
	 * Is <code>true</code> if this is OS/2.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_OS2 = getOSMatches("OS/2");

	/**
	 * <p>
	 * Is <code>true</code> if this is Solaris.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_SOLARIS = getOSMatches("Solaris");

	/**
	 * <p>
	 * Is <code>true</code> if this is SunOS.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_SUN_OS = getOSMatches("SunOS");

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS = getOSMatches("Windows");

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows 2000.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS_2000 = getOSMatches("Windows", "5.0");

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows 95.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS_95 = getOSMatches("Windows 9", "4.0");

	// JDK 1.2 running on Windows98 returns 'Windows 95', hence the above

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows 98.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS_98 = getOSMatches("Windows 9", "4.1");

	// JDK 1.2 running on Windows98 returns 'Windows 95', hence the above

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows ME.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS_ME = getOSMatches("Windows", "4.9");

	// JDK 1.2 running on WindowsME may return 'Windows 95', hence the above

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows NT.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS_NT = getOSMatches("Windows NT");

	// Windows 2000 returns 'Windows 2000' but may suffer from same JDK1.2
	// problem

	/**
	 * <p>
	 * Is <code>true</code> if this is Windows XP.
	 * </p>
	 * 
	 * <p>
	 * The field will return <code>false</code> if <code>OS_NAME</code> is
	 * <code>null</code>.
	 * </p>
	 * 
	 * @since 2.0
	 */
	public static final boolean IS_OS_WINDOWS_XP = getOSMatches("Windows", "5.1");

	// Windows XP returns 'Windows 2000' just for fun...

	// -----------------------------------------------------------------------

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the Java version number as a <code>float</code>.
	 * </p>
	 * 
	 * <p>
	 * Example return values:
	 * </p>
	 * <ul>
	 * <li><code>1.2f</code> for JDK 1.2
	 * <li><code>1.31f</code> for JDK 1.3.1
	 * </ul>
	 * 
	 * @return the version, for example 1.31f for JDK 1.3.1
	 * @deprecated Use {@link #JAVA_VERSION_FLOAT}instead. Method will be
	 *             removed in Commons Lang 3.0.
	 */
	public static float getJavaVersion() {
		return JAVA_VERSION_FLOAT;
	}

	/**
	 * <p>
	 * Gets the Java version number as a <code>float</code>.
	 * </p>
	 * 
	 * <p>
	 * Example return values:
	 * </p>
	 * <ul>
	 * <li><code>1.2f</code> for JDK 1.2
	 * <li><code>1.31f</code> for JDK 1.3.1
	 * </ul>
	 * 
	 * <p>
	 * Patch releases are not reported. Zero is returned if
	 * {@link #JAVA_VERSION}is <code>null</code>.
	 * </p>
	 * 
	 * @return the version, for example 1.31f for JDK 1.3.1
	 */
	private static float getJavaVersionAsFloat() {
		if (JAVA_VERSION == null) {
			return 0f;
		}
		String str = JAVA_VERSION.substring(0, 3);
		if (JAVA_VERSION.length() >= 5) {
			str = str + JAVA_VERSION.substring(4, 5);
		}
		return Float.parseFloat(str);
	}

	/**
	 * <p>
	 * Gets the Java version number as an <code>int</code>.
	 * </p>
	 * 
	 * <p>
	 * Example return values:
	 * </p>
	 * <ul>
	 * <li><code>120</code> for JDK 1.2
	 * <li><code>131</code> for JDK 1.3.1
	 * </ul>
	 * 
	 * <p>
	 * Patch releases are not reported. Zero is returned if
	 * {@link #JAVA_VERSION}is <code>null</code>.
	 * </p>
	 * 
	 * @return the version, for example 131 for JDK 1.3.1
	 */
	private static int getJavaVersionAsInt() {
		if (JAVA_VERSION == null) {
			return 0;
		}
		String str = JAVA_VERSION.substring(0, 1);
		str = str + JAVA_VERSION.substring(2, 3);
		if (JAVA_VERSION.length() >= 5) {
			str = str + JAVA_VERSION.substring(4, 5);
		} else {
			str = str + "0";
		}
		return Integer.parseInt(str);
	}

	/**
	 * <p>
	 * Decides if the java version matches.
	 * </p>
	 * 
	 * @param versionPrefix
	 *            the prefix for the java version
	 * @return true if matches, or false if not or can't determine
	 */
	private static boolean getJavaVersionMatches(String versionPrefix) {
		if (JAVA_VERSION == null) {
			return false;
		}
		return JAVA_VERSION.startsWith(versionPrefix);
	}

	/**
	 * <p>
	 * Decides if the operating system matches.
	 * </p>
	 * 
	 * @param osNamePrefix
	 *            the prefix for the os name
	 * @return true if matches, or false if not or can't determine
	 */
	private static boolean getOSMatches(String osNamePrefix) {
		if (OS_NAME == null) {
			return false;
		}
		return OS_NAME.startsWith(osNamePrefix);
	}

	/**
	 * <p>
	 * Decides if the operating system matches.
	 * </p>
	 * 
	 * @param osNamePrefix
	 *            the prefix for the os name
	 * @param osVersionPrefix
	 *            the prefix for the version
	 * @return true if matches, or false if not or can't determine
	 */
	private static boolean getOSMatches(String osNamePrefix, String osVersionPrefix) {
		if (OS_NAME == null || OS_VERSION == null) {
			return false;
		}
		return OS_NAME.startsWith(osNamePrefix) && OS_VERSION.startsWith(osVersionPrefix);
	}

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets a System property, defaulting to <code>null</code> if the property
	 * cannot be read.
	 * </p>
	 * 
	 * <p>
	 * If a <code>SecurityException</code> is caught, the return value is
	 * <code>null</code> and a message is written to <code>System.err</code>.
	 * </p>
	 * 
	 * @param property
	 *            the system property name
	 * @return the system property value or <code>null</code> if a security
	 *         problem occurs
	 */
	private static String getSystemProperty(String property) {
		try {
			return System.getProperty(property);
		} catch (SecurityException ex) {
			// we are not allowed to look at this property
			System.err.println("Caught a SecurityException reading the system property '" + property + "'; the SystemUtils property value will default to null.");
			return null;
		}
	}

	/**
	 * <p>
	 * Is the Java version at least the requested version.
	 * </p>
	 * 
	 * <p>
	 * Example input:
	 * </p>
	 * <ul>
	 * <li><code>1.2f</code> to test for JDK 1.2</li>
	 * <li><code>1.31f</code> to test for JDK 1.3.1</li>
	 * </ul>
	 * 
	 * @param requiredVersion
	 *            the required version, for example 1.31f
	 * @return <code>true</code> if the actual version is equal or greater than
	 *         the required version
	 */
	public static boolean isJavaVersionAtLeast(float requiredVersion) {
		return (JAVA_VERSION_FLOAT >= requiredVersion);
	}

	/**
	 * <p>
	 * Is the Java version at least the requested version.
	 * </p>
	 * 
	 * <p>
	 * Example input:
	 * </p>
	 * <ul>
	 * <li><code>120</code> to test for JDK 1.2 or greater</li>
	 * <li><code>131</code> to test for JDK 1.3.1 or greater</li>
	 * </ul>
	 * 
	 * @param requiredVersion
	 *            the required version, for example 131
	 * @return <code>true</code> if the actual version is equal or greater than
	 *         the required version
	 * @since 2.0
	 */
	public static boolean isJavaVersionAtLeast(int requiredVersion) {
		return (JAVA_VERSION_INT >= requiredVersion);
	}

	/**
	 * Esse metodo sincroniza dois JavaBeans que tenha as mesmas propriedades. O
	 * primeiro parametro será o bean que irá receber todos os valores do bean
	 * do segundo parametro.
	 * 
	 * @param bean
	 * @param form
	 * @throws br.com.topazio.exception.SystemException
	 */
	public static void sincronizarBeans(Object bean, Object form) {
		try {
			BeanUtils.copyProperties(bean, form);

		} catch (Exception e) {
			throw new TSSystemException(e);
		}
	}

	/**
	 * @author Kiev
	 * @param cpf
	 * @return
	 */
	public static String cpfComMascara(String cpf) {
		if (cpf == null || cpf.equals("")) {
			return null;
		}

		cpf = removerCaracter(cpf);

		cpf = preencheString(cpf, TAM_CPF);

		return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
	}

	/**
	 * Recebe cnpj e retorna-o mascarado (Ex: entrada 11111111111111 saída
	 * 11.111.111/1111-11)
	 * 
	 * @author Kiev
	 * @param cnpj
	 * @return
	 */
	public static String cnpjComMascara(String cnpj) {
		if (cnpj == null || cnpj.equals("")) {
			cnpj = new String();
		}
		cnpj = removerCaracter(cnpj);

		cnpj = preencheString(cnpj, TAM_CNPJ);

		return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
	}

	/**
	 * Use o metodo removerNaoDigitos.
	 * 
	 * @deprecated
	 * @Author Kiev
	 * @param param
	 * @return
	 */
	public static String removerCaracter(String param) {

		if (param == null) {
			return null;
		}

		return param.replaceAll("\\D", "");
	}

	/**
	 * @Author Kiev
	 * @param param
	 * @return
	 */
	public static String removerNaoDigitos(String param) {

		if (param == null) {
			return null;
		}

		return param.replaceAll("\\D", "");
	}

	/**
	 * @Author Kiev
	 * @param param
	 * @param tamanho
	 * @return
	 */
	private static String preencheString(String param, int tamanho) {

		if (param == null) {
			param = new String();
		}
		int dif = tamanho - param.length();

		for (; dif > 0; dif--) {
			param = STRING_DEFAULT + param;
		}
		return param;
	}

	public static boolean isEmpty(String str) {
		return (str == null || str.trim().length() == 0);
	}

	public static boolean isNotEmpty(String str) {
		return (str != null && str.length() > 0);
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(Object value) {

		if (value == null) {
			return true;
		} else if (value instanceof Collection) {
			return ((Collection) value).isEmpty();
		} else if (value instanceof String) {
			return isEmpty((String) value);
		}

		return false;
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmailValid(String email) {
		Pattern patterns = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher matcher = patterns.matcher(email);
		if (matcher.matches()) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @param strCpf
	 * @return
	 */
	/*
	 * public static boolean isCPFValid(String strCpf) { strCpf =
	 * TSStringUtil.replace(strCpf, ".", ""); strCpf =
	 * TSStringUtil.replace(strCpf, "-", "");
	 * 
	 * int d1, d2; int digito1, digito2, resto; int digitoCPF; String
	 * nDigResult;
	 * 
	 * d1 = d2 = 0; digito1 = digito2 = resto = 0;
	 * 
	 * for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) { digitoCPF
	 * = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();
	 * 
	 * // multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 // e
	 * assim por diante. d1 = d1 + (11 - nCount) * digitoCPF;
	 * 
	 * // para o segundo digito repita o procedimento incluindo o primeiro //
	 * digito calculado no passo anterior. d2 = d2 + (12 - nCount) * digitoCPF;
	 * } ;
	 * 
	 * // Primeiro resto da divisão por 11. resto = (d1 % 11);
	 * 
	 * // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 //
	 * menos o resultado anterior. if (resto < 2) { digito1 = 0; } else {
	 * digito1 = 11 - resto; }
	 * 
	 * d2 += 2 * digito1;
	 * 
	 * // Segundo resto da divisão por 11. resto = (d2 % 11);
	 * 
	 * // Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 //
	 * menos o resultado anterior. if (resto < 2) { digito2 = 0; } else {
	 * digito2 = 11 - resto; } // Digito verificador do CPF que está sendo
	 * validado. String nDigVerific = strCpf.substring(strCpf.length() - 2,
	 * strCpf.length());
	 * 
	 * // Concatenando o primeiro resto com o segundo. nDigResult =
	 * String.valueOf(digito1) + String.valueOf(digito2);
	 * 
	 * // comparar o digito verificador do cpf com o primeiro resto + o segundo
	 * // resto. return nDigVerific.equals(nDigResult); }
	 */

	/**
	 * 
	 * @param numero
	 * @return
	 */
	public static boolean isNumeric(String numero) {
		return TSStringUtil.isNumeric(numero);

	}

	public static String gerarId() {

		Calendar calendario = Calendar.getInstance();

		int diaAtual = calendario.get(Calendar.DAY_OF_MONTH);
		int mesAtual = calendario.get(Calendar.MONTH) + 1;
		int anoAtual = calendario.get(Calendar.YEAR);
		int horaAtual = calendario.get(Calendar.HOUR_OF_DAY);
		int minutoAtual = calendario.get(Calendar.MINUTE);
		int segAtual = calendario.get(Calendar.SECOND);
		int miliAtual = calendario.get(Calendar.MILLISECOND);

		return String.valueOf(anoAtual) + String.valueOf(mesAtual) + String.valueOf(diaAtual) + String.valueOf(horaAtual) + String.valueOf(minutoAtual) + String.valueOf(segAtual) + String.valueOf(miliAtual);

	}

	public static String calcularPorcetagem(Double total, Double valorParcial) {

		if (!TSUtil.isEmpty(total) && total.equals(0L)) {

			return "0";

		} else {

			DecimalFormat df = new DecimalFormat("0.00");

			Double parcial = valorParcial * 100;

			Double porcetagem = parcial.doubleValue() / total;

			return df.format(porcetagem).replaceAll(",00", "");

		}

	}

	private static int calcularDigito(String str, int[] peso) {

		int soma = 0;

		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {

			digito = Integer.parseInt(str.substring(indice, indice + 1));

			soma += digito * peso[peso.length - str.length() + indice];

		}

		soma = 11 - soma % 11;

		return soma > 9 ? 0 : soma;

	}

	public static boolean isValidCPF(String cpf) {

		if ((cpf == null) || (cpf.length() != 11))
			return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);

		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);

		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());

	}

	public static boolean isValidCNPJ(String cnpj) {

		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);

		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);

		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());

	}

	public static String formatarMoeda(Double valor) {

		String valorFormatado = "";

		if (!TSUtil.isEmpty(valor)) {

			NumberFormat nf = new DecimalFormat("###,###,##0.00");

			valorFormatado = nf.format(valor);

		}

		return valorFormatado;

	}

	public static Double desformatarMoeda(String valor) {

		Number n = 0;

		if (!TSUtil.isEmpty(valor)) {

			NumberFormat nf = new DecimalFormat("###,###,###,##0.00");

			try {

				n = nf.parse(valor);

			} catch (ParseException e) {

				e.printStackTrace();

			}

		}

		return new Double(n.doubleValue());

	}

	public static String getAnoMes(Date data) {

		if (!TSUtil.isEmpty(data)) {

			return TSParseUtil.dateToString(data, TSDateUtil.YYYY) + "/" + TSParseUtil.dateToString(data, TSDateUtil.MM) + "/";

		} else {

			return null;

		}

	}

	public static String msgResultado(List<?> lista) {

		String resultado = "";

		if (TSUtil.isEmpty(lista)) {

			resultado = "A pesquisa não retornou nenhuma ocorrência";

		} else {

			Integer tamanho = lista.size();

			if (tamanho.equals(1)) {

				resultado = "A pesquisa retornou 1 ocorrência";

			} else {

				resultado = "A pesquisa retornou " + tamanho + " ocorrências";

			}

		}

		return resultado;

	}

	public static String getSituacao(Boolean flagAtivo) {

		if (!TSUtil.isEmpty(flagAtivo) && flagAtivo) {

			return "Ativo";

		} else {

			return "Inativo";

		}

	}

	public static String gerarSenha() {

		try {

			return TSCryptoUtil.criptografar(gerarId());

		} catch (Exception e) {

			e.printStackTrace();

			return null;

		}

	}

	public static String tratarString(String valor) {

		if (!TSUtil.isEmpty(valor)) {

			valor = valor.trim().replaceAll("  ", " ");

		}

		return valor;

	}

	public static Long tratarLong(Long valor) {

		if (!TSUtil.isEmpty(valor) && valor.equals(0L)) {

			valor = null;

		}

		return valor;

	}

	public static Integer tratarInteger(Integer valor) {

		if (!TSUtil.isEmpty(valor) && valor.equals(0)) {

			valor = null;

		}

		return valor;

	}



}