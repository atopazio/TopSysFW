/*
 * Created on 24/08/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.topsys.util;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.WordUtils;

/**
 * @author andre
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public final class TSStringUtil {
	/**
	 * The empty String <code>""</code>.
	 * 
	 * @since 2.0
	 */
	public static final String EMPTY = "";

	/**
	 * <p>
	 * The maximum size to which the padding constant(s) can expand.
	 * </p>
	 */
	private static final int PAD_LIMIT = 8192;

	/**
	 * <p>
	 * An array of <code>String</code> s used for padding.
	 * </p>
	 * 
	 * <p>
	 * Used for efficient space padding. The length of each String expands as
	 * needed.
	 * </p>
	 */
	private static final String[] PADDING = new String[Character.MAX_VALUE];

	static {
		// space padding is most common, start with 64 chars
		PADDING[32] = "                                                                ";
	}

	/**
	 * <p>
	 * Checks if a String is whitespace, empty ("") or null.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isBlank(null)      = true
	 *  StringUtils.isBlank(&quot;&quot;)        = true
	 *  StringUtils.isBlank(&quot; &quot;)       = true
	 *  StringUtils.isBlank(&quot;bob&quot;)     = false
	 *  StringUtils.isBlank(&quot;  bob  &quot;) = false
	 * 
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is null, empty or whitespace
	 * @since 2.0
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if a String is not empty (""), not null and not whitespace only.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isNotBlank(null)      = false
	 *  StringUtils.isNotBlank(&quot;&quot;)        = false
	 *  StringUtils.isNotBlank(&quot; &quot;)       = false
	 *  StringUtils.isNotBlank(&quot;bob&quot;)     = true
	 *  StringUtils.isNotBlank(&quot;  bob  &quot;) = true
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if the String is not empty and not null and not
	 *         whitespace
	 * @since 2.0
	 */
	public static boolean isNotBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return false;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return true;
			}
		}
		return false;
	}

	// Trim
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String,
	 * handling <code>null</code> by returning an empty String ("").
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.clean(null)          = &quot;&quot;
	 *  StringUtils.clean(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.clean(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.clean(&quot;    abc    &quot;) = &quot;abc&quot;
	 *  StringUtils.clean(&quot;     &quot;)       = &quot;&quot;
	 * 
	 * </pre>
	 * 
	 * @see java.lang.String#trim()
	 * @param str
	 *            the String to clean, may be null
	 * @return the trimmed text, never <code>null</code>
	 * @deprecated Use the clearer named {@link #trimToEmpty(String)}. Method
	 *             will be removed in Commons Lang 3.0.
	 */
	public static String clean(String str) {
		return (str == null ? EMPTY : str.trim());
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String,
	 * handling <code>null</code> by returning <code>null</code>.
	 * </p>
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use {@link #strip(String)}.
	 * </p>
	 * 
	 * <p>
	 * To trim your choice of characters, use the {@link #strip(String, String)}
	 * methods.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.trim(null)          = null
	 *  StringUtils.trim(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.trim(&quot;     &quot;)       = &quot;&quot;
	 *  StringUtils.trim(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trim(&quot;    abc    &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed string, <code>null</code> if null String input
	 */
	public static String trim(String str) {
		return (str == null ? null : str.trim());
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning <code>null</code> if the String is empty ("") after the trim or
	 * if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToNull(String)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.trimToNull(null)          = null
	 *  StringUtils.trimToNull(&quot;&quot;)            = null
	 *  StringUtils.trimToNull(&quot;     &quot;)       = null
	 *  StringUtils.trimToNull(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trimToNull(&quot;    abc    &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, <code>null</code> if only chars &lt;= 32,
	 *         empty or null String input
	 * @since 2.0
	 */
	public static String trimToNull(String str) {
		String ts = trim(str);
		return (ts == null || ts.length() == 0 ? null : ts);
	}

	/**
	 * <p>
	 * Removes control characters (char &lt;= 32) from both ends of this String
	 * returning an empty String ("") if the String is empty ("") after the trim
	 * or if it is <code>null</code>.
	 * 
	 * <p>
	 * The String is trimmed using {@link String#trim()}. Trim removes start and
	 * end characters &lt;= 32. To strip whitespace use
	 * {@link #stripToEmpty(String)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.trimToEmpty(null)          = &quot;&quot;
	 *  StringUtils.trimToEmpty(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.trimToEmpty(&quot;     &quot;)       = &quot;&quot;
	 *  StringUtils.trimToEmpty(&quot;abc&quot;)         = &quot;abc&quot;
	 *  StringUtils.trimToEmpty(&quot;    abc    &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to be trimmed, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String trimToEmpty(String str) {
		return (str == null ? EMPTY : str.trim());
	}

	// Stripping
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Strips whitespace from the start and end of a String.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trim(String)}but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.strip(null)     = null
	 *  StringUtils.strip(&quot;&quot;)       = &quot;&quot;
	 *  StringUtils.strip(&quot;   &quot;)    = &quot;&quot;
	 *  StringUtils.strip(&quot;abc&quot;)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; ab c &quot;) = &quot;ab c&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove whitespace from, may be null
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String strip(String str) {
		return strip(str, null);
	}

	/**
	 * <p>
	 * Strips whitespace from the start and end of a String returning
	 * <code>null</code> if the String is empty ("") after the strip.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trimToNull(String)}but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.strip(null)     = null
	 *  StringUtils.strip(&quot;&quot;)       = null
	 *  StringUtils.strip(&quot;   &quot;)    = null
	 *  StringUtils.strip(&quot;abc&quot;)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; ab c &quot;) = &quot;ab c&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to be stripped, may be null
	 * @return the stripped String, <code>null</code> if whitespace, empty or
	 *         null String input
	 * @since 2.0
	 */
	public static String stripToNull(String str) {
		if (str == null) {
			return null;
		}
		str = strip(str, null);
		return (str.length() == 0 ? null : str);
	}

	/**
	 * <p>
	 * Strips whitespace from the start and end of a String returning an empty
	 * String if <code>null</code> input.
	 * </p>
	 * 
	 * <p>
	 * This is similar to {@link #trimToEmpty(String)}but removes whitespace.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.strip(null)     = &quot;&quot;
	 *  StringUtils.strip(&quot;&quot;)       = &quot;&quot;
	 *  StringUtils.strip(&quot;   &quot;)    = &quot;&quot;
	 *  StringUtils.strip(&quot;abc&quot;)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;)  = &quot;abc&quot;
	 *  StringUtils.strip(&quot; ab c &quot;) = &quot;ab c&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to be stripped, may be null
	 * @return the trimmed String, or an empty String if <code>null</code> input
	 * @since 2.0
	 */
	public static String stripToEmpty(String str) {
		return (str == null ? EMPTY : strip(str, null));
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start and end of a String.
	 * This is similar to {@link String#trim()}but allows the characters to be
	 * stripped to be controlled.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}. Alternatively use
	 * {@link #strip(String)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.strip(null, *)          = null
	 *  StringUtils.strip(&quot;&quot;, *)            = &quot;&quot;
	 *  StringUtils.strip(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot; abc &quot;, null)    = &quot;abc&quot;
	 *  StringUtils.strip(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String strip(String str, String stripChars) {
		if (str == null || str.length() == 0) {
			return str;
		}
		str = stripStart(str, stripChars);
		return stripEnd(str, stripChars);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start of a String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.stripStart(null, *)          = null
	 * 
	 *  StringUtils.stripStart(&quot;&quot;, *)            = &quot;&quot;
	 *  StringUtils.stripStart(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 *  StringUtils.stripStart(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *  StringUtils.stripStart(&quot;  abc&quot;, null)    = &quot;abc&quot;
	 *  StringUtils.stripStart(&quot;abc  &quot;, null)    = &quot;abc  &quot;
	 *  StringUtils.stripStart(&quot; abc &quot;, null)    = &quot;abc &quot;
	 *  StringUtils.stripStart(&quot;yxabc  &quot;, &quot;xyz&quot;) = &quot;abc  &quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}
		return str.substring(start);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the end of a String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. An empty
	 * string ("") input returns the empty string.
	 * </p>
	 * 
	 * <p>
	 * If the stripChars String is <code>null</code>, whitespace is stripped as
	 * defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.stripEnd(null, *)          = null
	 *  StringUtils.stripEnd(&quot;&quot;, *)            = &quot;&quot;
	 *  StringUtils.stripEnd(&quot;abc&quot;, &quot;&quot;)        = &quot;abc&quot;
	 *  StringUtils.stripEnd(&quot;abc&quot;, null)      = &quot;abc&quot;
	 *  StringUtils.stripEnd(&quot;  abc&quot;, null)    = &quot;  abc&quot;
	 *  StringUtils.stripEnd(&quot;abc  &quot;, null)    = &quot;abc&quot;
	 *  StringUtils.stripEnd(&quot; abc &quot;, null)    = &quot; abc&quot;
	 *  StringUtils.stripEnd(&quot;  abcyx&quot;, &quot;xyz&quot;) = &quot;  abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped String, <code>null</code> if null String input
	 */
	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}
		return str.substring(0, end);
	}

	// StripAll
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Strips whitespace from the start and end of every String in an array.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * A new array is returned each time, except for length zero. A
	 * <code>null</code> array will return <code>null</code>. An empty array
	 * will return itself. A <code>null</code> array entry will be ignored.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.stripAll(null)             = null
	 *  StringUtils.stripAll([])               = []
	 *  StringUtils.stripAll([&quot;abc&quot;, &quot;  abc&quot;]) = [&quot;abc&quot;, &quot;abc&quot;]
	 *  StringUtils.stripAll([&quot;abc  &quot;, null])  = [&quot;abc&quot;, null]
	 * 
	 * </pre>
	 * 
	 * @param strs
	 *            the array to remove whitespace from, may be null
	 * @return the stripped Strings, <code>null</code> if null array input
	 */
	public static String[] stripAll(String[] strs) {
		return stripAll(strs, null);
	}

	/**
	 * <p>
	 * Strips any of a set of characters from the start and end of every String
	 * in an array.
	 * </p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. </p>
	 * 
	 * <p>
	 * A new array is returned each time, except for length zero. A
	 * <code>null</code> array will return <code>null</code>. An empty array
	 * will return itself. A <code>null</code> array entry will be ignored. A
	 * <code>null</code> stripChars will strip whitespace as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.stripAll(null, *)                = null
	 *  StringUtils.stripAll([], *)                  = []
	 *  StringUtils.stripAll([&quot;abc&quot;, &quot;  abc&quot;], null) = [&quot;abc&quot;, &quot;abc&quot;]
	 *  StringUtils.stripAll([&quot;abc  &quot;, null], null)  = [&quot;abc&quot;, null]
	 *  StringUtils.stripAll([&quot;abc  &quot;, null], &quot;yz&quot;)  = [&quot;abc  &quot;, null]
	 *  StringUtils.stripAll([&quot;yabcz&quot;, null], &quot;yz&quot;)  = [&quot;abc&quot;, null]
	 * 
	 * </pre>
	 * 
	 * @param strs
	 *            the array to remove characters from, may be null
	 * @param stripChars
	 *            the characters to remove, null treated as whitespace
	 * @return the stripped Strings, <code>null</code> if null array input
	 */
	public static String[] stripAll(String[] strs, String stripChars) {
		int strsLen;
		if (strs == null || (strsLen = strs.length) == 0) {
			return strs;
		}
		String[] newArr = new String[strsLen];
		for (int i = 0; i < strsLen; i++) {
			newArr[i] = strip(strs[i], stripChars);
		}
		return newArr;
	}

	// Equals
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> s are handled without exceptions. Two <code>null</code>
	 * references are considered to be equal. The comparison is case sensitive.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.equals(null, null)   = true
	 *  StringUtils.equals(null, &quot;abc&quot;)  = false
	 *  StringUtils.equals(&quot;abc&quot;, null)  = false
	 *  StringUtils.equals(&quot;abc&quot;, &quot;abc&quot;) = true
	 *  StringUtils.equals(&quot;abc&quot;, &quot;ABC&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @see java.lang.String#equals(Object)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case sensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equals(String str1, String str2) {
		return (str1 == null ? str2 == null : str1.equals(str2));
	}

	/**
	 * <p>
	 * Compares two Strings, returning <code>true</code> if they are equal
	 * ignoring the case.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> s are handled without exceptions. Two <code>null</code>
	 * references are considered equal. Comparison is case insensitive.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.equalsIgnoreCase(null, null)   = true
	 *  StringUtils.equalsIgnoreCase(null, &quot;abc&quot;)  = false
	 *  StringUtils.equalsIgnoreCase(&quot;abc&quot;, null)  = false
	 *  StringUtils.equalsIgnoreCase(&quot;abc&quot;, &quot;abc&quot;) = true
	 *  StringUtils.equalsIgnoreCase(&quot;abc&quot;, &quot;ABC&quot;) = true
	 * 
	 * </pre>
	 * 
	 * @see java.lang.String#equalsIgnoreCase(String)
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return <code>true</code> if the Strings are equal, case insensitive, or
	 *         both <code>null</code>
	 */
	public static boolean equalsIgnoreCase(String str1, String str2) {
		return (str1 == null ? str2 == null : str1.equalsIgnoreCase(str2));
	}

	// IndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOf(null, *)         = -1
	 *  StringUtils.indexOf(&quot;&quot;, *)           = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'a') = 0
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b') = 2
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		return str.indexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the first index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#indexOf(int, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position is treated as zero. A start position greater than
	 * the string length returns <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOf(null, *, *)          = -1
	 *  StringUtils.indexOf(&quot;&quot;, *, *)            = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', 0)  = 2
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', 3)  = 5
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', 9)  = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, 'b', -1) = 2
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, char searchChar, int startPos) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		return str.indexOf(searchChar, startPos);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(String)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOf(null, *)          = -1
	 *  StringUtils.indexOf(*, null)          = -1
	 *  StringUtils.indexOf(&quot;&quot;, &quot;&quot;)           = 0
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;)  = 0
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;)  = 2
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;) = 1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;&quot;)   = 0
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.indexOf(searchStr);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(String, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position is treated as zero. An empty ("") search String always matches.
	 * A start position greater than the string length only matches an empty
	 * search String.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOf(null, *, *)          = -1
	 *  StringUtils.indexOf(*, null, *)          = -1
	 *  StringUtils.indexOf(&quot;&quot;, &quot;&quot;, 0)           = 0
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 0)  = 0
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 0)  = 2
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 0) = 1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 3)  = 5
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 9)  = -1
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, -1) = 2
	 *  StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;&quot;, 2)   = 2
	 *  StringUtils.indexOf(&quot;abc&quot;, &quot;&quot;, 9)        = 3
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int indexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		// JDK1.2/JDK1.3 have a bug, when startPos > str.length for "", hence
		if (searchStr.length() == 0 && startPos >= str.length()) {
			return str.length();
		}
		return str.indexOf(searchStr, startPos);
	}

	// LastIndexOf
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.lastIndexOf(null, *)         = -1
	 *  StringUtils.lastIndexOf(&quot;&quot;, *)           = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'a') = 7
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b') = 5
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		return str.lastIndexOf(searchChar);
	}

	/**
	 * <p>
	 * Finds the last index within a String from a start position, handling
	 * <code>null</code>. This method uses {@link String#lastIndexOf(int, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>-1</code>. A
	 * negative start position returns <code>-1</code>. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.lastIndexOf(null, *, *)          = -1
	 *  StringUtils.lastIndexOf(&quot;&quot;, *,  *)           = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 8)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 4)  = 2
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 0)  = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', 9)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'b', -1) = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, 'a', 0)  = 0
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @param startPos
	 *            the start position
	 * @return the last index of the search character, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, char searchChar, int startPos) {
		if (str == null || str.length() == 0) {
			return -1;
		}
		return str.lastIndexOf(searchChar, startPos);
	}

	/**
	 * <p>
	 * Finds the last index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.lastIndexOf(null, *)          = -1
	 *  StringUtils.lastIndexOf(*, null)          = -1
	 *  StringUtils.lastIndexOf(&quot;&quot;, &quot;&quot;)           = 0
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;)  = 0
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;)  = 2
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;ab&quot;) = 1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;&quot;)   = 8
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return the last index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr);
	}

	/**
	 * <p>
	 * Finds the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#lastIndexOf(String, int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A negative start
	 * position returns <code>-1</code>. An empty ("") search String always
	 * matches unless the start position is negative. A start position greater
	 * than the string length searches the whole string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.lastIndexOf(null, *, *)          = -1
	 *  StringUtils.lastIndexOf(*, null, *)          = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 8)  = 7
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 8)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 8) = 4
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 9)  = 5
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, -1) = -1
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 0)  = 0
	 *  StringUtils.lastIndexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 0)  = -1
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @param startPos
	 *            the start position, negative treated as zero
	 * @return the first index of the search String, -1 if no match or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static int lastIndexOf(String str, String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.lastIndexOf(searchStr, startPos);
	}

	// Contains
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if String contains a search character, handling <code>null</code>.
	 * This method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.contains(null, *)    = false
	 *  StringUtils.contains(&quot;&quot;, *)      = false
	 *  StringUtils.contains(&quot;abc&quot;, 'a') = true
	 *  StringUtils.contains(&quot;abc&quot;, 'z') = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChar
	 *            the character to find
	 * @return true if the String contains the search character, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, char searchChar) {
		if (str == null || str.length() == 0) {
			return false;
		}
		return (str.indexOf(searchChar) >= 0);
	}

	/**
	 * <p>
	 * Find the first index within a String, handling <code>null</code>. This
	 * method uses {@link String#indexOf(int)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.contains(null, *)     = false
	 *  StringUtils.contains(*, null)     = false
	 *  StringUtils.contains(&quot;&quot;, &quot;&quot;)      = true
	 *  StringUtils.contains(&quot;abc&quot;, &quot;&quot;)   = true
	 *  StringUtils.contains(&quot;abc&quot;, &quot;a&quot;)  = true
	 *  StringUtils.contains(&quot;abc&quot;, &quot;z&quot;)  = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStr
	 *            the String to find, may be null
	 * @return true if the String contains the search character, false if not or
	 *         <code>null</code> string input
	 * @since 2.0
	 */
	public static boolean contains(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		return (str.indexOf(searchStr) >= 0);
	}

	// IndexOfAny chars
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Search a String to find the first index of any character in the given set
	 * of characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> or zero length search array will return <code>-1</code>
	 * .
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOfAny(null, *)                = -1
	 *  StringUtils.indexOfAny(&quot;&quot;, *)                  = -1
	 *  StringUtils.indexOfAny(*, null)                = -1
	 *  StringUtils.indexOfAny(*, [])                  = -1
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;,['z','a']) = 0
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;,['b','y']) = 3
	 *  StringUtils.indexOfAny(&quot;aba&quot;, ['z'])           = -1
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChars
	 *            the chars to search for, may be null
	 * @return the index of any of the chars, -1 if no match or null input
	 * @since 2.0
	 */
	public static int indexOfAny(String str, char[] searchChars) {
		if (str == null || str.length() == 0 || searchChars == null || searchChars.length == 0) {
			return -1;
		}
		for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * <p>
	 * Search a String to find the first index of any character in the given set
	 * of characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> search string will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOfAny(null, *)            = -1
	 *  StringUtils.indexOfAny(&quot;&quot;, *)              = -1
	 *  StringUtils.indexOfAny(*, null)            = -1
	 *  StringUtils.indexOfAny(*, &quot;&quot;)              = -1
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, &quot;za&quot;) = 0
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, &quot;by&quot;) = 3
	 *  StringUtils.indexOfAny(&quot;aba&quot;,&quot;z&quot;)          = -1
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChars
	 *            the chars to search for, may be null
	 * @return the index of any of the chars, -1 if no match or null input
	 * @since 2.0
	 */
	public static int indexOfAny(String str, String searchChars) {
		if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0) {
			return -1;
		}
		return indexOfAny(str, searchChars.toCharArray());
	}

	// IndexOfAnyBut chars
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Search a String to find the first index of any character not in the given
	 * set of characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> or zero length search array will return <code>-1</code>
	 * .
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOfAnyBut(null, *)           = -1
	 *  StringUtils.indexOfAnyBut(&quot;&quot;, *)             = -1
	 *  StringUtils.indexOfAnyBut(*, null)           = -1
	 *  StringUtils.indexOfAnyBut(*, [])             = -1
	 *  StringUtils.indexOfAnyBut(&quot;zzabyycdxx&quot;,'za') = 3
	 *  StringUtils.indexOfAnyBut(&quot;zzabyycdxx&quot;, '')  = 0
	 *  StringUtils.indexOfAnyBut(&quot;aba&quot;, 'ab')       = -1
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChars
	 *            the chars to search for, may be null
	 * @return the index of any of the chars, -1 if no match or null input
	 * @since 2.0
	 */
	public static int indexOfAnyBut(String str, char[] searchChars) {
		if (str == null || str.length() == 0 || searchChars == null || searchChars.length == 0) {
			return -1;
		}
		outer: for (int i = 0; i < str.length(); i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < searchChars.length; j++) {
				if (searchChars[j] == ch) {
					continue outer;
				}
			}
			return i;
		}
		return -1;
	}

	/**
	 * <p>
	 * Search a String to find the first index of any character not in the given
	 * set of characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> search string will return <code>-1</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOfAnyBut(null, *)            = -1
	 *  StringUtils.indexOfAnyBut(&quot;&quot;, *)              = -1
	 *  StringUtils.indexOfAnyBut(*, null)            = -1
	 *  StringUtils.indexOfAnyBut(*, &quot;&quot;)              = -1
	 *  StringUtils.indexOfAnyBut(&quot;zzabyycdxx&quot;, &quot;za&quot;) = 3
	 *  StringUtils.indexOfAnyBut(&quot;zzabyycdxx&quot;, &quot;&quot;)   = 0
	 *  StringUtils.indexOfAnyBut(&quot;aba&quot;,&quot;ab&quot;)         = -1
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchChars
	 *            the chars to search for, may be null
	 * @return the index of any of the chars, -1 if no match or null input
	 * @since 2.0
	 */
	public static int indexOfAnyBut(String str, String searchChars) {
		if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0) {
			return -1;
		}
		for (int i = 0; i < str.length(); i++) {
			if (searchChars.indexOf(str.charAt(i)) < 0) {
				return i;
			}
		}
		return -1;
	}

	// ContainsOnly
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if the String contains only certain characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>. A
	 * <code>null</code> valid character array will return <code>false</code>.
	 * An empty String ("") always returns <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.containsOnly(null, *)       = false
	 *  StringUtils.containsOnly(*, null)       = false
	 *  StringUtils.containsOnly(&quot;&quot;, *)         = true
	 *  StringUtils.containsOnly(&quot;ab&quot;, '')      = false
	 *  StringUtils.containsOnly(&quot;abab&quot;, 'abc') = true
	 *  StringUtils.containsOnly(&quot;ab1&quot;, 'abc')  = false
	 *  StringUtils.containsOnly(&quot;abz&quot;, 'abc')  = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param valid
	 *            an array of valid chars, may be null
	 * @return true if it only contains valid chars and is non-null
	 */
	public static boolean containsOnly(String str, char[] valid) {
		// All these pre-checks are to maintain API with an older version
		if ((valid == null) || (str == null)) {
			return false;
		}
		if (str.length() == 0) {
			return true;
		}
		if (valid.length == 0) {
			return false;
		}
		return indexOfAnyBut(str, valid) == -1;
	}

	/**
	 * <p>
	 * Checks if the String contains only certain characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>false</code>. A
	 * <code>null</code> valid character String will return <code>false</code>.
	 * An empty String ("") always returns <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.containsOnly(null, *)       = false
	 *  StringUtils.containsOnly(*, null)       = false
	 *  StringUtils.containsOnly(&quot;&quot;, *)         = true
	 *  StringUtils.containsOnly(&quot;ab&quot;, &quot;&quot;)      = false
	 *  StringUtils.containsOnly(&quot;abab&quot;, &quot;abc&quot;) = true
	 *  StringUtils.containsOnly(&quot;ab1&quot;, &quot;abc&quot;)  = false
	 *  StringUtils.containsOnly(&quot;abz&quot;, &quot;abc&quot;)  = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param validChars
	 *            a String of valid chars, may be null
	 * @return true if it only contains valid chars and is non-null
	 * @since 2.0
	 */
	public static boolean containsOnly(String str, String validChars) {
		if (str == null || validChars == null) {
			return false;
		}
		return containsOnly(str, validChars.toCharArray());
	}

	// ContainsNone
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks that the String does not contain certain characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>true</code>. A
	 * <code>null</code> invalid character array will return <code>true</code>.
	 * An empty String ("") always returns true.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.containsNone(null, *)       = true
	 *  StringUtils.containsNone(*, null)       = true
	 *  StringUtils.containsNone(&quot;&quot;, *)         = true
	 *  StringUtils.containsNone(&quot;ab&quot;, '')      = true
	 *  StringUtils.containsNone(&quot;abab&quot;, 'xyz') = true
	 *  StringUtils.containsNone(&quot;ab1&quot;, 'xyz')  = true
	 *  StringUtils.containsNone(&quot;abz&quot;, 'xyz')  = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param invalidChars
	 *            an array of invalid chars, may be null
	 * @return true if it contains none of the invalid chars, or is null
	 * @since 2.0
	 */
	public static boolean containsNone(String str, char[] invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		int strSize = str.length();
		int validSize = invalidChars.length;
		for (int i = 0; i < strSize; i++) {
			char ch = str.charAt(i);
			for (int j = 0; j < validSize; j++) {
				if (invalidChars[j] == ch) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks that the String does not contain certain characters.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>true</code>. A
	 * <code>null</code> invalid character array will return <code>true</code>.
	 * An empty String ("") always returns true.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.containsNone(null, *)       = true
	 *  StringUtils.containsNone(*, null)       = true
	 *  StringUtils.containsNone(&quot;&quot;, *)         = true
	 *  StringUtils.containsNone(&quot;ab&quot;, &quot;&quot;)      = true
	 *  StringUtils.containsNone(&quot;abab&quot;, &quot;xyz&quot;) = true
	 *  StringUtils.containsNone(&quot;ab1&quot;, &quot;xyz&quot;)  = true
	 *  StringUtils.containsNone(&quot;abz&quot;, &quot;xyz&quot;)  = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param invalidChars
	 *            a String of invalid chars, may be null
	 * @return true if it contains none of the invalid chars, or is null
	 * @since 2.0
	 */
	public static boolean containsNone(String str, String invalidChars) {
		if (str == null || invalidChars == null) {
			return true;
		}
		return containsNone(str, invalidChars.toCharArray());
	}

	// IndexOfAny strings
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Find the first index of any of a set of potential substrings.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> or zero length search array will return <code>-1</code>
	 * . A <code>null</code> search array entry will be ignored, but a search
	 * array containing "" will return <code>0</code> if <code>str</code> is not
	 * null. This method uses {@link String#indexOf(String)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOfAny(null, *)                     = -1
	 *  StringUtils.indexOfAny(*, null)                     = -1
	 *  StringUtils.indexOfAny(*, [])                       = -1
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;ab&quot;,&quot;cd&quot;])   = 2
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;cd&quot;,&quot;ab&quot;])   = 2
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;op&quot;])   = -1
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;zab&quot;,&quot;aby&quot;]) = 1
	 *  StringUtils.indexOfAny(&quot;zzabyycdxx&quot;, [&quot;&quot;])          = 0
	 *  StringUtils.indexOfAny(&quot;&quot;, [&quot;&quot;])                    = 0
	 *  StringUtils.indexOfAny(&quot;&quot;, [&quot;a&quot;])                   = -1
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStrs
	 *            the Strings to search for, may be null
	 * @return the first index of any of the searchStrs in str, -1 if no match
	 */
	public static int indexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;

		// String's can't have a MAX_VALUEth index.
		int ret = Integer.MAX_VALUE;

		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.indexOf(search);
			if (tmp == -1) {
				continue;
			}

			if (tmp < ret) {
				ret = tmp;
			}
		}

		return (ret == Integer.MAX_VALUE) ? -1 : ret;
	}

	/**
	 * <p>
	 * Find the latest index of any of a set of potential substrings.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>-1</code>. A
	 * <code>null</code> search array will return <code>-1</code>. A
	 * <code>null</code> or zero length search array entry will be ignored, but
	 * a search array containing "" will return the length of <code>str</code>
	 * if <code>str</code> is not null. This method uses
	 * {@link String#indexOf(String)}
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.lastIndexOfAny(null, *)                   = -1
	 *  StringUtils.lastIndexOfAny(*, null)                   = -1
	 *  StringUtils.lastIndexOfAny(*, [])                     = -1
	 *  StringUtils.lastIndexOfAny(*, [null])                 = -1
	 *  StringUtils.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;ab&quot;,&quot;cd&quot;]) = 6
	 *  StringUtils.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;cd&quot;,&quot;ab&quot;]) = 6
	 *  StringUtils.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;op&quot;]) = -1
	 *  StringUtils.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;op&quot;]) = -1
	 *  StringUtils.lastIndexOfAny(&quot;zzabyycdxx&quot;, [&quot;mn&quot;,&quot;&quot;])   = 10
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param searchStrs
	 *            the Strings to search for, may be null
	 * @return the last index of any of the Strings, -1 if no match
	 */
	public static int lastIndexOfAny(String str, String[] searchStrs) {
		if ((str == null) || (searchStrs == null)) {
			return -1;
		}
		int sz = searchStrs.length;
		int ret = -1;
		int tmp = 0;
		for (int i = 0; i < sz; i++) {
			String search = searchStrs[i];
			if (search == null) {
				continue;
			}
			tmp = str.lastIndexOf(search);
			if (tmp > ret) {
				ret = tmp;
			}
		}
		return ret;
	}

	// Substring
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 * 
	 * <p>
	 * A negative start position can be used to start <code>n</code> characters
	 * from the end of the String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> String will return <code>null</code>. An empty ("")
	 * String will return "".
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substring(null, *)   = null
	 *  StringUtils.substring(&quot;&quot;, *)     = &quot;&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 0)  = &quot;abc&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 2)  = &quot;c&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 4)  = &quot;&quot;
	 *  StringUtils.substring(&quot;abc&quot;, -2) = &quot;bc&quot;
	 *  StringUtils.substring(&quot;abc&quot;, -4) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the substring from, may be null
	 * @param start
	 *            the position to start from, negative means count back from the
	 *            end of the String by this many characters
	 * @return substring from start position, <code>null</code> if null String
	 *         input
	 */
	public static String substring(String str, int start) {
		if (str == null) {
			return null;
		}

		// handle negatives, which means last n characters
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		if (start < 0) {
			start = 0;
		}
		if (start > str.length()) {
			return EMPTY;
		}

		return str.substring(start);
	}

	/**
	 * <p>
	 * Gets a substring from the specified String avoiding exceptions.
	 * </p>
	 * 
	 * <p>
	 * A negative start position can be used to start/end <code>n</code>
	 * characters from the end of the String.
	 * </p>
	 * 
	 * <p>
	 * The returned substring starts with the character in the
	 * <code>start</code> position and ends before the <code>end</code>
	 * position. All postion counting is zero-based -- i.e., to start at the
	 * beginning of the string use <code>start = 0</code>. Negative start and
	 * end positions can be used to specify offsets relative to the end of the
	 * String.
	 * </p>
	 * 
	 * <p>
	 * If <code>start</code> is not strictly to the left of <code>end</code>, ""
	 * is returned.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substring(null, *, *)    = null
	 *  StringUtils.substring(&quot;&quot;, * ,  *)    = &quot;&quot;;
	 *  StringUtils.substring(&quot;abc&quot;, 0, 2)   = &quot;ab&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 2, 0)   = &quot;&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 2, 4)   = &quot;c&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 4, 6)   = &quot;&quot;
	 *  StringUtils.substring(&quot;abc&quot;, 2, 2)   = &quot;&quot;
	 *  StringUtils.substring(&quot;abc&quot;, -2, -1) = &quot;b&quot;
	 *  StringUtils.substring(&quot;abc&quot;, -4, 2)  = &quot;ab&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the substring from, may be null
	 * @param start
	 *            the position to start from, negative means count back from the
	 *            end of the String by this many characters
	 * @param end
	 *            the position to end at (exclusive), negative means count back
	 *            from the end of the String by this many characters
	 * @return substring from start position to end positon, <code>null</code>
	 *         if null String input
	 */
	public static String substring(String str, int start, int end) {
		if (str == null) {
			return null;
		}

		// handle negatives
		if (end < 0) {
			end = str.length() + end; // remember end is negative
		}
		if (start < 0) {
			start = str.length() + start; // remember start is negative
		}

		// check length next
		if (end > str.length()) {
			end = str.length();
		}

		// if start is greater than end, return ""
		if (start > end) {
			return EMPTY;
		}

		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}

		return str.substring(start, end);
	}

	// Left/Right/Mid
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the leftmost <code>len</code> characters of a String.
	 * </p>
	 * 
	 * <p>
	 * If <code>len</code> characters are not available, or the String is
	 * <code>null</code>, the String will be returned without an exception. An
	 * exception is thrown if len is negative.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.left(null, *)    = null
	 *  StringUtils.left(*, -ve)     = &quot;&quot;
	 *  StringUtils.left(&quot;&quot;, *)      = &quot;&quot;
	 *  StringUtils.left(&quot;abc&quot;, 0)   = &quot;&quot;
	 *  StringUtils.left(&quot;abc&quot;, 2)   = &quot;ab&quot;
	 *  StringUtils.left(&quot;abc&quot;, 4)   = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the leftmost characters from, may be null
	 * @param len
	 *            the length of the required String, must be zero or positive
	 * @return the leftmost characters, <code>null</code> if null String input
	 */
	public static String left(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(0, len);
		}
	}

	/**
	 * <p>
	 * Gets the rightmost <code>len</code> characters of a String.
	 * </p>
	 * 
	 * <p>
	 * If <code>len</code> characters are not available, or the String is
	 * <code>null</code>, the String will be returned without an an exception.
	 * An exception is thrown if len is negative.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.right(null, *)    = null
	 *  StringUtils.right(*, -ve)     = &quot;&quot;
	 *  StringUtils.right(&quot;&quot;, *)      = &quot;&quot;
	 *  StringUtils.right(&quot;abc&quot;, 0)   = &quot;&quot;
	 *  StringUtils.right(&quot;abc&quot;, 2)   = &quot;bc&quot;
	 *  StringUtils.right(&quot;abc&quot;, 4)   = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the rightmost characters from, may be null
	 * @param len
	 *            the length of the required String, must be zero or positive
	 * @return the rightmost characters, <code>null</code> if null String input
	 */
	public static String right(String str, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0) {
			return EMPTY;
		}
		if (str.length() <= len) {
			return str;
		} else {
			return str.substring(str.length() - len);
		}
	}

	/**
	 * <p>
	 * Gets <code>len</code> characters from the middle of a String.
	 * </p>
	 * 
	 * <p>
	 * If <code>len</code> characters are not available, the remainder of the
	 * String will be returned without an exception. If the String is
	 * <code>null</code>,<code>null</code> will be returned. An exception is
	 * thrown if len is negative.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.mid(null, *, *)    = null
	 *  StringUtils.mid(*, *, -ve)     = &quot;&quot;
	 *  StringUtils.mid(&quot;&quot;, 0, *)      = &quot;&quot;
	 *  StringUtils.mid(&quot;abc&quot;, 0, 2)   = &quot;ab&quot;
	 *  StringUtils.mid(&quot;abc&quot;, 0, 4)   = &quot;abc&quot;
	 *  StringUtils.mid(&quot;abc&quot;, 2, 4)   = &quot;c&quot;
	 *  StringUtils.mid(&quot;abc&quot;, 4, 2)   = &quot;&quot;
	 *  StringUtils.mid(&quot;abc&quot;, -2, 2)  = &quot;ab&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get the characters from, may be null
	 * @param pos
	 *            the position to start from, negative treated as zero
	 * @param len
	 *            the length of the required String, must be zero or positive
	 * @return the middle characters, <code>null</code> if null String input
	 */
	public static String mid(String str, int pos, int len) {
		if (str == null) {
			return null;
		}
		if (len < 0 || pos > str.length()) {
			return EMPTY;
		}
		if (pos < 0) {
			pos = 0;
		}
		if (str.length() <= (pos + len)) {
			return str.substring(pos);
		} else {
			return str.substring(pos, pos + len);
		}
	}

	// SubStringAfter/SubStringBefore
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the substring before the first occurance of a separator. The
	 * separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. A <code>null</code>
	 * separator will return the input string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substringBefore(null, *)      = null
	 *  StringUtils.substringBefore(&quot;&quot;, *)        = &quot;&quot;
	 *  StringUtils.substringBefore(&quot;abc&quot;, &quot;a&quot;)   = &quot;&quot;
	 *  StringUtils.substringBefore(&quot;abcba&quot;, &quot;b&quot;) = &quot;a&quot;
	 *  StringUtils.substringBefore(&quot;abc&quot;, &quot;c&quot;)   = &quot;ab&quot;
	 *  StringUtils.substringBefore(&quot;abc&quot;, &quot;d&quot;)   = &quot;abc&quot;
	 *  StringUtils.substringBefore(&quot;abc&quot;, &quot;&quot;)    = &quot;&quot;
	 *  StringUtils.substringBefore(&quot;abc&quot;, null)  = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring before the first occurance of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringBefore(String str, String separator) {
		if (str == null || separator == null || str.length() == 0) {
			return str;
		}
		if (separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring after the first occurance of a separator. The
	 * separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. A <code>null</code>
	 * separator will return the empty string if the input string is not
	 * <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substringAfter(null, *)      = null
	 *  StringUtils.substringAfter(&quot;&quot;, *)        = &quot;&quot;
	 *  StringUtils.substringAfter(*, null)      = &quot;&quot;
	 *  StringUtils.substringAfter(&quot;abc&quot;, &quot;a&quot;)   = &quot;bc&quot;
	 *  StringUtils.substringAfter(&quot;abcba&quot;, &quot;b&quot;) = &quot;cba&quot;
	 *  StringUtils.substringAfter(&quot;abc&quot;, &quot;c&quot;)   = &quot;&quot;
	 *  StringUtils.substringAfter(&quot;abc&quot;, &quot;d&quot;)   = &quot;&quot;
	 *  StringUtils.substringAfter(&quot;abc&quot;, &quot;&quot;)    = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring after the first occurance of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfter(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null) {
			return EMPTY;
		}
		int pos = str.indexOf(separator);
		if (pos == -1) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	/**
	 * <p>
	 * Gets the substring before the last occurance of a separator. The
	 * separator is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. An empty or
	 * <code>null</code> separator will return the input string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substringBeforeLast(null, *)      = null
	 *  StringUtils.substringBeforeLast(&quot;&quot;, *)        = &quot;&quot;
	 *  StringUtils.substringBeforeLast(&quot;abcba&quot;, &quot;b&quot;) = &quot;abc&quot;
	 *  StringUtils.substringBeforeLast(&quot;abc&quot;, &quot;c&quot;)   = &quot;ab&quot;
	 *  StringUtils.substringBeforeLast(&quot;a&quot;, &quot;a&quot;)     = &quot;&quot;
	 *  StringUtils.substringBeforeLast(&quot;a&quot;, &quot;z&quot;)     = &quot;a&quot;
	 *  StringUtils.substringBeforeLast(&quot;a&quot;, null)    = &quot;a&quot;
	 *  StringUtils.substringBeforeLast(&quot;a&quot;, &quot;&quot;)      = &quot;a&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring before the last occurance of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringBeforeLast(String str, String separator) {
		if (str == null || separator == null || str.length() == 0 || separator.length() == 0) {
			return str;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1) {
			return str;
		}
		return str.substring(0, pos);
	}

	/**
	 * <p>
	 * Gets the substring after the last occurance of a separator. The separator
	 * is not returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input will return <code>null</code>. An empty
	 * ("") string input will return the empty string. An empty or
	 * <code>null</code> separator will return the empty string if the input
	 * string is not <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substringAfterLast(null, *)      = null
	 *  StringUtils.substringAfterLast(&quot;&quot;, *)        = &quot;&quot;
	 *  StringUtils.substringAfterLast(*, &quot;&quot;)        = &quot;&quot;
	 *  StringUtils.substringAfterLast(*, null)      = &quot;&quot;
	 *  StringUtils.substringAfterLast(&quot;abc&quot;, &quot;a&quot;)   = &quot;bc&quot;
	 *  StringUtils.substringAfterLast(&quot;abcba&quot;, &quot;b&quot;) = &quot;a&quot;
	 *  StringUtils.substringAfterLast(&quot;abc&quot;, &quot;c&quot;)   = &quot;&quot;
	 *  StringUtils.substringAfterLast(&quot;a&quot;, &quot;a&quot;)     = &quot;&quot;
	 *  StringUtils.substringAfterLast(&quot;a&quot;, &quot;z&quot;)     = &quot;&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to get a substring from, may be null
	 * @param separator
	 *            the String to search for, may be null
	 * @return the substring after the last occurance of the separator,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String substringAfterLast(String str, String separator) {
		if (str == null || str.length() == 0) {
			return str;
		}
		if (separator == null || separator.length() == 0) {
			return EMPTY;
		}
		int pos = str.lastIndexOf(separator);
		if (pos == -1 || pos == (str.length() - separator.length())) {
			return EMPTY;
		}
		return str.substring(pos + separator.length());
	}

	// Substring between
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the String that is nested in between two instances of the same
	 * String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> tag returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substringBetween(null, *)            = null
	 *  StringUtils.substringBetween(&quot;&quot;, &quot;&quot;)             = &quot;&quot;
	 *  StringUtils.substringBetween(&quot;&quot;, &quot;tag&quot;)          = null
	 *  StringUtils.substringBetween(&quot;tagabctag&quot;, null)  = null
	 *  StringUtils.substringBetween(&quot;tagabctag&quot;, &quot;&quot;)    = &quot;&quot;
	 *  StringUtils.substringBetween(&quot;tagabctag&quot;, &quot;tag&quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String containing the substring, may be null
	 * @param tag
	 *            the String before and after the substring, may be null
	 * @return the substring, <code>null</code> if no match
	 * @since 2.0
	 */
	public static String substringBetween(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two Strings. Only the first
	 * match is returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> open/close returns <code>null</code> (no match). An
	 * empty ("") open/close returns an empty string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.substringBetween(null, *, *)          = null
	 *  StringUtils.substringBetween(&quot;&quot;, &quot;&quot;, &quot;&quot;)          = &quot;&quot;
	 *  StringUtils.substringBetween(&quot;&quot;, &quot;&quot;, &quot;tag&quot;)       = null
	 *  StringUtils.substringBetween(&quot;&quot;, &quot;tag&quot;, &quot;tag&quot;)    = null
	 *  StringUtils.substringBetween(&quot;yabcz&quot;, null, null) = null
	 *  StringUtils.substringBetween(&quot;yabcz&quot;, &quot;&quot;, &quot;&quot;)     = &quot;&quot;
	 *  StringUtils.substringBetween(&quot;yabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 *  StringUtils.substringBetween(&quot;yabczyabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String containing the substring, may be null
	 * @param open
	 *            the String before the substring, may be null
	 * @param close
	 *            the String after the substring, may be null
	 * @return the substring, <code>null</code> if no match
	 * @since 2.0
	 */
	public static String substringBetween(String str, String open, String close) {
		if (str == null || open == null || close == null) {
			return null;
		}
		int start = str.indexOf(open);
		if (start != -1) {
			int end = str.indexOf(close, start + open.length());
			if (end != -1) {
				return str.substring(start + open.length(), end);
			}
		}
		return null;
	}

	// Nested extraction
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the String that is nested in between two instances of the same
	 * String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> tag returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.getNestedString(null, *)            = null
	 *  StringUtils.getNestedString(&quot;&quot;, &quot;&quot;)             = &quot;&quot;
	 *  StringUtils.getNestedString(&quot;&quot;, &quot;tag&quot;)          = null
	 *  StringUtils.getNestedString(&quot;tagabctag&quot;, null)  = null
	 *  StringUtils.getNestedString(&quot;tagabctag&quot;, &quot;&quot;)    = &quot;&quot;
	 *  StringUtils.getNestedString(&quot;tagabctag&quot;, &quot;tag&quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String containing nested-string, may be null
	 * 
	 * @param tag
	 *            the String before and after nested-string, may be null
	 * @return the nested String, <code>null</code> if no match
	 * @deprecated Use the better named
	 *             {@link #substringBetween(String, String)}. Method will be
	 *             removed in Commons Lang 3.0.
	 */
	public static String getNestedString(String str, String tag) {
		return substringBetween(str, tag, tag);
	}

	/**
	 * <p>
	 * Gets the String that is nested in between two Strings. Only the first
	 * match is returned.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> open/close returns <code>null</code> (no match). An
	 * empty ("") open/close returns an empty string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.getNestedString(null, *, *)          = null
	 *  StringUtils.getNestedString(&quot;&quot;, &quot;&quot;, &quot;&quot;)          = &quot;&quot;
	 *  StringUtils.getNestedString(&quot;&quot;, &quot;&quot;, &quot;tag&quot;)       = null
	 *  StringUtils.getNestedString(&quot;&quot;, &quot;tag&quot;, &quot;tag&quot;)    = null
	 *  StringUtils.getNestedString(&quot;yabcz&quot;, null, null) = null
	 *  StringUtils.getNestedString(&quot;yabcz&quot;, &quot;&quot;, &quot;&quot;)     = &quot;&quot;
	 *  StringUtils.getNestedString(&quot;yabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 *  StringUtils.getNestedString(&quot;yabczyabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String containing nested-string, may be null
	 * @param open
	 *            the String before nested-string, may be null
	 * @param close
	 *            the String after nested-string, may be null
	 * @return the nested String, <code>null</code> if no match
	 * @deprecated Use the better named
	 *             {@link #substringBetween(String, String, String)}. Method
	 *             will be removed in Commons Lang 3.0.
	 */
	public static String getNestedString(String str, String open, String close) {
		return substringBetween(str, open, close);
	}

	// Splitting
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Splits the provided text into an array, using whitespace as the
	 * separator. Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.split(null)       = null
	 *  StringUtils.split(&quot;&quot;)         = []
	 *  StringUtils.split(&quot;abc def&quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 *  StringUtils.split(&quot;abc  def&quot;) = [&quot;abc&quot;, &quot;def&quot;]
	 *  StringUtils.split(&quot; abc &quot;)    = [&quot;abc&quot;]
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str) {
		return split(str, null, -1);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separator specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.split(null, *)         = null
	 *  StringUtils.split(&quot;&quot;, *)           = []
	 *  StringUtils.split(&quot;a.b.c&quot;, '.')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 *  StringUtils.split(&quot;a..b.c&quot;, '.')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 *  StringUtils.split(&quot;a:b:c&quot;, '.')    = [&quot;a:b:c&quot;]
	 *  StringUtils.split(&quot;a\tb\nc&quot;, null) = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 *  StringUtils.split(&quot;a b c&quot;, ' ')    = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChar
	 *            the character used as the delimiter, <code>null</code> splits
	 *            on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 * @since 2.0
	 */
	@SuppressWarnings("unchecked")
	public static String[] split(String str, char separatorChar) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return TSArrayUtil.EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int i = 0, start = 0;
		boolean match = false;
		while (i < len) {
			if (str.charAt(i) == separatorChar) {
				if (match) {
					list.add(str.substring(start, i));
					match = false;
				}
				start = ++i;
				continue;
			}
			match = true;
			i++;
		}
		if (match) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separators specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.split(null, *)         = null
	 *  StringUtils.split(&quot;&quot;, *)           = []
	 *  StringUtils.split(&quot;abc def&quot;, null) = [&quot;abc&quot;, &quot;def&quot;]
	 *  StringUtils.split(&quot;abc def&quot;, &quot; &quot;)  = [&quot;abc&quot;, &quot;def&quot;]
	 *  StringUtils.split(&quot;abc  def&quot;, &quot; &quot;) = [&quot;abc&quot;, &quot;def&quot;]
	 *  StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	public static String[] split(String str, String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * <p>
	 * Splits the provided text into an array, separators specified. This is an
	 * alternative to using StringTokenizer.
	 * </p>
	 * 
	 * <p>
	 * The separator is not included in the returned String array. Adjacent
	 * separators are treated as one separator.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>. A
	 * <code>null</code> separatorChars splits on whitespace.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.split(null, *, *)            = null
	 *  StringUtils.split(&quot;&quot;, *, *)              = []
	 *  StringUtils.split(&quot;ab de fg&quot;, null, 0)   = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *  StringUtils.split(&quot;ab   de fg&quot;, null, 0) = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *  StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 0)    = [&quot;ab&quot;, &quot;cd&quot;, &quot;ef&quot;]
	 *  StringUtils.split(&quot;ab:cd:ef&quot;, &quot;:&quot;, 2)    = [&quot;ab&quot;, &quot;cdef&quot;]
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to parse, may be null
	 * @param separatorChars
	 *            the characters used as the delimiters, <code>null</code>
	 *            splits on whitespace
	 * @param max
	 *            the maximum number of elements to include in the array. A zero
	 *            or negative value implies no limit
	 * @return an array of parsed Strings, <code>null</code> if null String
	 *         input
	 */
	@SuppressWarnings("unchecked")
	public static String[] split(String str, String separatorChars, int max) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()

		if (str == null) {
			return null;
		}
		int len = str.length();
		if (len == 0) {
			return TSArrayUtil.EMPTY_STRING_ARRAY;
		}
		List list = new ArrayList();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		if (separatorChars == null) {
			// Null separator means use whitespace
			while (i < len) {
				if (Character.isWhitespace(str.charAt(i))) {
					if (match) {
						if (sizePlus1++ == max) {
							i = len;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				match = true;
				i++;
			}
		} else if (separatorChars.length() == 1) {
			// Optimise 1 character case
			char sep = separatorChars.charAt(0);
			while (i < len) {
				if (str.charAt(i) == sep) {
					if (match) {
						if (sizePlus1++ == max) {
							i = len;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				match = true;
				i++;
			}
		} else {
			// standard case
			while (i < len) {
				if (separatorChars.indexOf(str.charAt(i)) >= 0) {
					if (match) {
						if (sizePlus1++ == max) {
							i = len;
						}
						list.add(str.substring(start, i));
						match = false;
					}
					start = ++i;
					continue;
				}
				match = true;
				i++;
			}
		}
		if (match) {
			list.add(str.substring(start, i));
		}
		return (String[]) list.toArray(new String[list.size()]);
	}

	// Joining
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Concatenates elements of an array into a single String. Null objects or
	 * empty strings within the array are represented by empty strings.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.concatenate(null)            = null
	 *  StringUtils.concatenate([])              = &quot;&quot;
	 *  StringUtils.concatenate([null])          = &quot;&quot;
	 *  StringUtils.concatenate([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]) = &quot;abc&quot;
	 *  StringUtils.concatenate([null, &quot;&quot;, &quot;a&quot;]) = &quot;a&quot;
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            the array of values to concatenate, may be null
	 * @return the concatenated String, <code>null</code> if null array input
	 * @deprecated Use the better named {@link #join(Object[])}instead. Method
	 *             will be removed in Commons Lang 3.0.
	 */
	public static String concatenate(Object[] array) {
		return join(array, null);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 * 
	 * <p>
	 * No separator is added to the joined String. Null objects or empty strings
	 * within the array are represented by empty strings.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.join(null)            = null
	 *  StringUtils.join([])              = &quot;&quot;
	 *  StringUtils.join([null])          = &quot;&quot;
	 *  StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]) = &quot;abc&quot;
	 *  StringUtils.join([null, &quot;&quot;, &quot;a&quot;]) = &quot;a&quot;
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            the array of values to join together, may be null
	 * @return the joined String, <code>null</code> if null array input
	 * @since 2.0
	 */
	public static String join(Object[] array) {
		return join(array, null);
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.join(null, *)               = null
	 *  StringUtils.join([], *)                 = &quot;&quot;
	 *  StringUtils.join([null], *)             = &quot;&quot;
	 *  StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], ';')  = &quot;a;b;c&quot;
	 *  StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null) = &quot;abc&quot;
	 *  StringUtils.join([null, &quot;&quot;, &quot;a&quot;], ';')  = &quot;;;a&quot;
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, <code>null</code> if null array input
	 * @since 2.0
	 */
	public static String join(Object[] array, char separator) {
		if (array == null) {
			return null;
		}
		int arraySize = array.length;
		int bufSize = (arraySize == 0 ? 0 : ((array[0] == null ? 16 : array[0].toString().length()) + 1) * arraySize);
		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if (i > 0) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided array into a single String containing
	 * the provided list of elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String (""). Null objects or empty
	 * strings within the array are represented by empty strings.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.join(null, *)                = null
	 *  StringUtils.join([], *)                  = &quot;&quot;
	 *  StringUtils.join([null], *)              = &quot;&quot;
	 *  StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;--&quot;)  = &quot;a--b--c&quot;
	 *  StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null)  = &quot;abc&quot;
	 *  StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], &quot;&quot;)    = &quot;abc&quot;
	 *  StringUtils.join([null, &quot;&quot;, &quot;a&quot;], ',')   = &quot;,,a&quot;
	 * 
	 * </pre>
	 * 
	 * @param array
	 *            the array of values to join together, may be null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null array input
	 */
	public static String join(Object[] array, String separator) {
		if (array == null) {
			return null;
		}
		if (separator == null) {
			separator = EMPTY;
		}
		int arraySize = array.length;

		// ArraySize == 0: Len = 0
		// ArraySize > 0: Len = NofStrings *(len(firstString) + len(separator))
		// (Assuming that all Strings are roughly equally long)
		int bufSize = ((arraySize == 0) ? 0 : arraySize * ((array[0] == null ? 16 : array[0].toString().length()) + ((separator != null) ? separator.length() : 0)));

		StringBuffer buf = new StringBuffer(bufSize);

		for (int i = 0; i < arraySize; i++) {
			if ((separator != null) && (i > 0)) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(array[i]);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided <code>Iterator</code> into a single
	 * String containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. Null objects or empty
	 * strings within the iteration are represented by empty strings.
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],char)}.
	 * </p>
	 * 
	 * @param iterator
	 *            the <code>Iterator</code> of values to join together, may be
	 *            null
	 * @param separator
	 *            the separator character to use
	 * @return the joined String, <code>null</code> if null iterator input
	 * @since 2.0
	 */
	public static String join(Iterator iterator, char separator) {
		if (iterator == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(256); // Java default is 16,
													// probably too small
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
			if (iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	/**
	 * <p>
	 * Joins the elements of the provided <code>Iterator</code> into a single
	 * String containing the provided elements.
	 * </p>
	 * 
	 * <p>
	 * No delimiter is added before or after the list. A <code>null</code>
	 * separator is the same as an empty String ("").
	 * </p>
	 * 
	 * <p>
	 * See the examples here: {@link #join(Object[],String)}.
	 * </p>
	 * 
	 * @param iterator
	 *            the <code>Iterator</code> of values to join together, may be
	 *            null
	 * @param separator
	 *            the separator character to use, null treated as ""
	 * @return the joined String, <code>null</code> if null iterator input
	 */
	public static String join(Iterator iterator, String separator) {
		if (iterator == null) {
			return null;
		}
		StringBuffer buf = new StringBuffer(256); // Java default is 16,
													// probably too small
		while (iterator.hasNext()) {
			Object obj = iterator.next();
			if (obj != null) {
				buf.append(obj);
			}
			if ((separator != null) && iterator.hasNext()) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	// Delete
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Deletes all 'space' characters from a String as defined by
	 * {@link Character#isSpace(char)}.
	 * </p>
	 * 
	 * <p>
	 * This is the only StringUtils method that uses the <code>isSpace</code>
	 * definition. You are advised to use {@link #deleteWhitespace(String)}
	 * instead as whitespace is much better localized.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.deleteSpaces(null)           = null
	 *  StringUtils.deleteSpaces(&quot;&quot;)             = &quot;&quot;
	 *  StringUtils.deleteSpaces(&quot;abc&quot;)          = &quot;abc&quot;
	 *  StringUtils.deleteSpaces(&quot; \t  abc \n &quot;) = &quot;abc&quot;
	 *  StringUtils.deleteSpaces(&quot;ab  c&quot;)        = &quot;abc&quot;
	 *  StringUtils.deleteSpaces(&quot;a\nb\tc     &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * <p>
	 * Spaces are defined as <code>{' ', '\t', '\r', '\n', '\b'}</code> in line
	 * with the deprecated <code>isSpace</code> method.
	 * </p>
	 * 
	 * @param str
	 *            the String to delete spaces from, may be null
	 * @return the String without 'spaces', <code>null</code> if null String
	 *         input
	 * @deprecated Use the better localized {@link #deleteWhitespace(String)}.
	 *             Method will be removed in Commons Lang 3.0.
	 */
	public static String deleteSpaces(String str) {
		if (str == null) {
			return null;
		}
		return TSCharSetUtil.delete(str, " \t\r\n\b");
	}

	/**
	 * <p>
	 * Deletes all whitespaces from a String as defined by
	 * {@link Character#isWhitespace(char)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.deleteWhitespace(null)         = null
	 *  StringUtils.deleteWhitespace(&quot;&quot;)           = &quot;&quot;
	 *  StringUtils.deleteWhitespace(&quot;abc&quot;)        = &quot;abc&quot;
	 *  StringUtils.deleteWhitespace(&quot;   ab  c  &quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to delete whitespace from, may be null
	 * @return the String without whitespaces, <code>null</code> if null String
	 *         input
	 */
	public static String deleteWhitespace(String str) {
		if (str == null) {
			return null;
		}
		int sz = str.length();
		StringBuffer buffer = new StringBuffer(sz);
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				buffer.append(str.charAt(i));
			}
		}
		return buffer.toString();
	}

	// Replacing
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, once.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.replaceOnce(null, *, *)        = null
	 *  StringUtils.replaceOnce(&quot;&quot;, *, *)          = &quot;&quot;
	 *  StringUtils.replaceOnce(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 *  StringUtils.replaceOnce(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 *  StringUtils.replaceOnce(&quot;aba&quot;, &quot;a&quot;, null)  = &quot;aba&quot;
	 *  StringUtils.replaceOnce(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;)    = &quot;aba&quot;
	 *  StringUtils.replaceOnce(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;)   = &quot;zba&quot;
	 * 
	 * </pre>
	 * 
	 * @see #replace(String text, String repl, String with, int max)
	 * @param text
	 *            text to search and replace in, may be null
	 * @param repl
	 *            the String to search for, may be null
	 * @param with
	 *            the String to replace with, may be null
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replaceOnce(String text, String repl, String with) {
		return replace(text, repl, with, 1);
	}

	/**
	 * <p>
	 * Replaces all occurances of a String within another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.replace(null, *, *)        = null
	 *  StringUtils.replace(&quot;&quot;, *, *)          = &quot;&quot;
	 *  StringUtils.replace(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 *  StringUtils.replace(&quot;aba&quot;, null, null) = &quot;aba&quot;
	 *  StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, null)  = &quot;aba&quot;
	 *  StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, &quot;&quot;)    = &quot;aba&quot;
	 *  StringUtils.replace(&quot;aba&quot;, &quot;a&quot;, &quot;z&quot;)   = &quot;zbz&quot;
	 * 
	 * </pre>
	 * 
	 * @see #replace(String text, String repl, String with, int max)
	 * @param text
	 *            text to search and replace in, may be null
	 * @param repl
	 *            the String to search for, may be null
	 * @param with
	 *            the String to replace with, may be null
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String repl, String with) {
		return replace(text, repl, with, -1);
	}

	/**
	 * <p>
	 * Replaces a String with another String inside a larger String, for the
	 * first <code>max</code> values of the search String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> reference passed to this method is a no-op.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.replace(null, *, *, *)         = null
	 *  StringUtils.replace(&quot;&quot;, *, *, *)           = &quot;&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, null, null, 1) = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, null, 1)  = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;&quot;, 1)    = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 0)   = &quot;abaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 1)   = &quot;zbaa&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, 2)   = &quot;zbza&quot;
	 *  StringUtils.replace(&quot;abaa&quot;, &quot;a&quot;, &quot;z&quot;, -1)  = &quot;zbzz&quot;
	 * 
	 * </pre>
	 * 
	 * @param text
	 *            text to search and replace in, may be null
	 * @param repl
	 *            the String to search for, may be null
	 * @param with
	 *            the String to replace with, may be null
	 * @param max
	 *            maximum number of values to replace, or <code>-1</code> if no
	 *            maximum
	 * @return the text with any replacements processed, <code>null</code> if
	 *         null String input
	 */
	public static String replace(String text, String repl, String with, int max) {
		if (text == null || repl == null || with == null || repl.length() == 0 || max == 0) {
			return text;
		}

		StringBuffer buf = new StringBuffer(text.length());
		int start = 0, end = 0;
		while ((end = text.indexOf(repl, start)) != -1) {
			buf.append(text.substring(start, end)).append(with);
			start = end + repl.length();

			if (--max == 0) {
				break;
			}
		}
		buf.append(text.substring(start));
		return buf.toString();
	}

	// Replace, character based
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Replaces all occurrances of a character in a String with another. This is
	 * a null-safe version of {@link String#replace(char, char)}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input returns <code>null</code>. An empty ("")
	 * string input returns an empty string.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.replaceChars(null, *, *)        = null
	 *  StringUtils.replaceChars(&quot;&quot;, *, *)          = &quot;&quot;
	 *  StringUtils.replaceChars(&quot;abcba&quot;, 'b', 'y') = &quot;aycya&quot;
	 *  StringUtils.replaceChars(&quot;abcba&quot;, 'z', 'y') = &quot;abcba&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            String to replace characters in, may be null
	 * @param searchChar
	 *            the character to search for, may be null
	 * @param replaceChar
	 *            the character to replace, may be null
	 * @return modified String, <code>null</code> if null string input
	 * @since 2.0
	 */
	public static String replaceChars(String str, char searchChar, char replaceChar) {
		if (str == null) {
			return null;
		}
		return str.replace(searchChar, replaceChar);
	}

	/**
	 * <p>
	 * Replaces multiple characters in a String in one go. This method can also
	 * be used to delete characters.
	 * </p>
	 * 
	 * <p>
	 * For example: <br />
	 * <code>replaceChars(&quot;hello&quot;, &quot;ho&quot;, &quot;jy&quot;) = jelly</code>
	 * .
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input returns <code>null</code>. An empty ("")
	 * string input returns an empty string. A null or empty set of search
	 * characters returns the input string.
	 * </p>
	 * 
	 * <p>
	 * The length of the search characters should normally equal the length of
	 * the replace characters. If the search characters is longer, then the
	 * extra search characters are deleted. If the search characters is shorter,
	 * then the extra replace characters are ignored.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.replaceChars(null, *, *)           = null
	 *  StringUtils.replaceChars(&quot;&quot;, *, *)             = &quot;&quot;
	 *  StringUtils.replaceChars(&quot;abc&quot;, null, *)       = &quot;abc&quot;
	 *  StringUtils.replaceChars(&quot;abc&quot;, &quot;&quot;, *)         = &quot;abc&quot;
	 *  StringUtils.replaceChars(&quot;abc&quot;, &quot;b&quot;, null)     = &quot;ac&quot;
	 *  StringUtils.replaceChars(&quot;abc&quot;, &quot;b&quot;, &quot;&quot;)       = &quot;ac&quot;
	 *  StringUtils.replaceChars(&quot;abcba&quot;, &quot;bc&quot;, &quot;yz&quot;)  = &quot;ayzya&quot;
	 *  StringUtils.replaceChars(&quot;abcba&quot;, &quot;bc&quot;, &quot;y&quot;)   = &quot;ayya&quot;
	 *  StringUtils.replaceChars(&quot;abcba&quot;, &quot;bc&quot;, &quot;yzx&quot;) = &quot;ayzya&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            String to replace characters in, may be null
	 * @param searchChars
	 *            a set of characters to search for, may be null
	 * @param replaceChars
	 *            a set of characters to replace, may be null
	 * @return modified String, <code>null</code> if null string input
	 * @since 2.0
	 */
	public static String replaceChars(String str, String searchChars, String replaceChars) {
		if (str == null || str.length() == 0 || searchChars == null || searchChars.length() == 0) {
			return str;
		}
		char[] chars = str.toCharArray();
		int len = chars.length;
		boolean modified = false;
		for (int i = 0, isize = searchChars.length(); i < isize; i++) {
			char searchChar = searchChars.charAt(i);
			if (replaceChars == null || i >= replaceChars.length()) {
				// delete
				int pos = 0;
				for (int j = 0; j < len; j++) {
					if (chars[j] != searchChar) {
						chars[pos++] = chars[j];
					} else {
						modified = true;
					}
				}
				len = pos;
			} else {
				// replace
				for (int j = 0; j < len; j++) {
					if (chars[j] == searchChar) {
						chars[j] = replaceChars.charAt(i);
						modified = true;
					}
				}
			}
		}
		if (modified == false) {
			return str;
		}
		return new String(chars, 0, len);
	}

	/**
	 * M�todo que ir� modificar um texto os caracteres especificados no
	 * array pelo parametro de string.
	 * 
	 * @param texto
	 * @param array
	 * @param string
	 * @return
	 */
	public static String replaceChars(String texto, char[] array, char a) {

		if (texto == null || array == null) {
			return null;
		}

		for (int i = 0; i < array.length; i++) {
			texto = texto.replace(array[i], a);

		}

		return deleteSpaces(texto);
	}

	// Overlay
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Overlays part of a String with another String.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.overlayString(null, *, *, *)           = NullPointerException
	 *  StringUtils.overlayString(*, null, *, *)           = NullPointerException
	 *  StringUtils.overlayString(&quot;&quot;, &quot;abc&quot;, 0, 0)         = &quot;abc&quot;
	 *  StringUtils.overlayString(&quot;abcdef&quot;, null, 2, 4)    = &quot;abef&quot;
	 *  StringUtils.overlayString(&quot;abcdef&quot;, &quot;&quot;, 2, 4)      = &quot;abef&quot;
	 *  StringUtils.overlayString(&quot;abcdef&quot;, &quot;zzzz&quot;, 2, 4)  = &quot;abzzzzef&quot;
	 *  StringUtils.overlayString(&quot;abcdef&quot;, &quot;zzzz&quot;, 4, 2)  = &quot;abcdzzzzcdef&quot;
	 *  StringUtils.overlayString(&quot;abcdef&quot;, &quot;zzzz&quot;, -1, 4) = IndexOutOfBoundsException
	 *  StringUtils.overlayString(&quot;abcdef&quot;, &quot;zzzz&quot;, 2, 8)  = IndexOutOfBoundsException
	 * 
	 * </pre>
	 * 
	 * @param text
	 *            the String to do overlaying in, may be null
	 * @param overlay
	 *            the String to overlay, may be null
	 * @param start
	 *            the position to start overlaying at, must be valid
	 * @param end
	 *            the position to stop overlaying before, must be valid
	 * @return overlayed String, <code>null</code> if null String input
	 * @throws NullPointerException
	 *             if text or overlay is null
	 * @throws IndexOutOfBoundsException
	 *             if either position is invalid
	 * @deprecated Use better named {@link #overlay(String, String, int, int)}
	 *             instead. Method will be removed in Commons Lang 3.0.
	 */
	public static String overlayString(String text, String overlay, int start, int end) {
		return new StringBuffer(start + overlay.length() + text.length() - end + 1).append(text.substring(0, start)).append(overlay).append(text.substring(end)).toString();
	}

	/**
	 * <p>
	 * Overlays part of a String with another String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> string input returns <code>null</code>. A negative
	 * index is treated as zero. An index greater than the string length is
	 * treated as the string length. The start index is always the smaller of
	 * the two indices.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.overlay(null, *, *, *)            = null
	 *  StringUtils.overlay(&quot;&quot;, &quot;abc&quot;, 0, 0)          = &quot;abc&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, null, 2, 4)     = &quot;abef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;&quot;, 2, 4)       = &quot;abef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;&quot;, 4, 2)       = &quot;abef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 2, 4)   = &quot;abzzzzef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 4, 2)   = &quot;abzzzzef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, -1, 4)  = &quot;zzzzef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 2, 8)   = &quot;abzzzz&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, -2, -3) = &quot;zzzzabcdef&quot;
	 *  StringUtils.overlay(&quot;abcdef&quot;, &quot;zzzz&quot;, 8, 10)  = &quot;abcdefzzzz&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to do overlaying in, may be null
	 * @param overlay
	 *            the String to overlay, may be null
	 * @param start
	 *            the position to start overlaying at
	 * @param end
	 *            the position to stop overlaying before
	 * @return overlayed String, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String overlay(String str, String overlay, int start, int end) {
		if (str == null) {
			return null;
		}
		if (overlay == null) {
			overlay = EMPTY;
		}
		int len = str.length();
		if (start < 0) {
			start = 0;
		}
		if (start > len) {
			start = len;
		}
		if (end < 0) {
			end = 0;
		}
		if (end > len) {
			end = len;
		}
		if (start > end) {
			int temp = start;
			start = end;
			end = temp;
		}
		return new StringBuffer(len + start - end + overlay.length() + 1).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
	}

	// Chomping
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Removes one newline from end of a String if it's there, otherwise leave
	 * it alone. A newline is &quot; <code>\n</code> &quot;, &quot;
	 * <code>\r</code> &quot;, or &quot; <code>\r\n</code> &quot;.
	 * </p>
	 * 
	 * <p>
	 * NOTE: This method changed in 2.0. It now more closely matches Perl chomp.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.chomp(null)          = null
	 *  StringUtils.chomp(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.chomp(&quot;abc \r&quot;)      = &quot;abc &quot;
	 *  StringUtils.chomp(&quot;abc\n&quot;)       = &quot;abc&quot;
	 *  StringUtils.chomp(&quot;abc\r\n&quot;)     = &quot;abc&quot;
	 *  StringUtils.chomp(&quot;abc\r\n\r\n&quot;) = &quot;abc\r\n&quot;
	 *  StringUtils.chomp(&quot;abc\n\r&quot;)     = &quot;abc\n&quot;
	 *  StringUtils.chomp(&quot;abc\n\rabc&quot;)  = &quot;abc\n\rabc&quot;
	 *  StringUtils.chomp(&quot;\r&quot;)          = &quot;&quot;
	 *  StringUtils.chomp(&quot;\n&quot;)          = &quot;&quot;
	 *  StringUtils.chomp(&quot;\r\n&quot;)        = &quot;&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to chomp a newline from, may be null
	 * @return String without newline, <code>null</code> if null String input
	 */
	public static String chomp(String str) {
		if (str == null || str.length() == 0) {
			return str;
		}

		if (str.length() == 1) {
			char ch = str.charAt(0);
			if (ch == '\r' || ch == '\n') {
				return EMPTY;
			} else {
				return str;
			}
		}

		int lastIdx = str.length() - 1;
		char last = str.charAt(lastIdx);

		if (last == '\n') {
			if (str.charAt(lastIdx - 1) == '\r') {
				lastIdx--;
			}
		} else if (last == '\r') {

		} else {
			lastIdx++;
		}
		return str.substring(0, lastIdx);
	}

	/**
	 * <p>
	 * Removes <code>separator</code> from the end of <code>str</code> if it's
	 * there, otherwise leave it alone.
	 * </p>
	 * 
	 * <p>
	 * NOTE: This method changed in version 2.0. It now more closely matches
	 * Perl chomp. For the previous behavior, use
	 * {@link #substringBeforeLast(String, String)}. This method uses
	 * {@link String#endsWith(String)}.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.chomp(null, *)         = null
	 *  StringUtils.chomp(&quot;&quot;, *)           = &quot;&quot;
	 *  StringUtils.chomp(&quot;foobar&quot;, &quot;bar&quot;) = &quot;foo&quot;
	 *  StringUtils.chomp(&quot;foobar&quot;, &quot;baz&quot;) = &quot;foobar&quot;
	 *  StringUtils.chomp(&quot;foo&quot;, &quot;foo&quot;)    = &quot;&quot;
	 *  StringUtils.chomp(&quot;foo &quot;, &quot;foo&quot;)   = &quot;foo&quot;
	 *  StringUtils.chomp(&quot; foo&quot;, &quot;foo&quot;)   = &quot; &quot;
	 *  StringUtils.chomp(&quot;foo&quot;, &quot;foooo&quot;)  = &quot;foo&quot;
	 *  StringUtils.chomp(&quot;foo&quot;, &quot;&quot;)       = &quot;foo&quot;
	 *  StringUtils.chomp(&quot;foo&quot;, null)     = &quot;foo&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to chomp from, may be null
	 * @param separator
	 *            separator String, may be null
	 * @return String without trailing separator, <code>null</code> if null
	 *         String input
	 */
	public static String chomp(String str, String separator) {
		if (str == null || str.length() == 0 || separator == null) {
			return str;
		}
		if (str.endsWith(separator)) {
			return str.substring(0, str.length() - separator.length());
		}
		return str;
	}

	/**
	 * <p>
	 * Remove any &quot;\n&quot; if and only if it is at the end of the supplied
	 * String.
	 * </p>
	 * 
	 * @param str
	 *            the String to chomp from, must not be null
	 * @return String without chomped ending
	 * @throws NullPointerException
	 *             if str is <code>null</code>
	 * @deprecated Use {@link #chomp(String)}instead. Method will be removed in
	 *             Commons Lang 3.0.
	 */
	public static String chompLast(String str) {
		return chompLast(str, "\n");
	}

	/**
	 * <p>
	 * Remove a value if and only if the String ends with that value.
	 * </p>
	 * 
	 * @param str
	 *            the String to chomp from, must not be null
	 * @param sep
	 *            the String to chomp, must not be null
	 * @return String without chomped ending
	 * @throws NullPointerException
	 *             if str or sep is <code>null</code>
	 * @deprecated Use {@link #chomp(String,String)}instead. Method will be
	 *             removed in Commons Lang 3.0.
	 */
	public static String chompLast(String str, String sep) {
		if (str.length() == 0) {
			return str;
		}
		String sub = str.substring(str.length() - sep.length());
		if (sep.equals(sub)) {
			return str.substring(0, str.length() - sep.length());
		} else {
			return str;
		}
	}

	/**
	 * <p>
	 * Remove everything and return the last value of a supplied String, and
	 * everything after it from a String.
	 * </p>
	 * 
	 * @param str
	 *            the String to chomp from, must not be null
	 * @param sep
	 *            the String to chomp, must not be null
	 * @return String chomped
	 * @throws NullPointerException
	 *             if str or sep is <code>null</code>
	 * @deprecated Use {@link #substringAfterLast(String, String)}instead
	 *             (although this doesn't include the separator) Method will be
	 *             removed in Commons Lang 3.0.
	 */
	public static String getChomp(String str, String sep) {
		int idx = str.lastIndexOf(sep);
		if (idx == str.length() - sep.length()) {
			return sep;
		} else if (idx != -1) {
			return str.substring(idx);
		} else {
			return EMPTY;
		}
	}

	/**
	 * <p>
	 * Remove the first value of a supplied String, and everything before it
	 * from a String.
	 * </p>
	 * 
	 * @param str
	 *            the String to chomp from, must not be null
	 * @param sep
	 *            the String to chomp, must not be null
	 * @return String without chomped beginning
	 * @throws NullPointerException
	 *             if str or sep is <code>null</code>
	 * @deprecated Use {@link #substringAfter(String,String)}instead. Method
	 *             will be removed in Commons Lang 3.0.
	 */
	public static String prechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx != -1) {
			return str.substring(idx + sep.length());
		} else {
			return str;
		}
	}

	/**
	 * <p>
	 * Remove and return everything before the first value of a supplied String
	 * from another String.
	 * </p>
	 * 
	 * @param str
	 *            the String to chomp from, must not be null
	 * @param sep
	 *            the String to chomp, must not be null
	 * @return String prechomped
	 * @throws NullPointerException
	 *             if str or sep is <code>null</code>
	 * @deprecated Use {@link #substringBefore(String,String)}instead (although
	 *             this doesn't include the separator). Method will be removed
	 *             in Commons Lang 3.0.
	 */
	public static String getPrechomp(String str, String sep) {
		int idx = str.indexOf(sep);
		if (idx != -1) {
			return str.substring(0, idx + sep.length());
		} else {
			return EMPTY;
		}
	}

	// Chopping
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Remove the last character from a String.
	 * </p>
	 * 
	 * <p>
	 * If the String ends in <code>\r\n</code>, then remove both of them.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.chop(null)          = null
	 *  StringUtils.chop(&quot;&quot;)            = &quot;&quot;
	 *  StringUtils.chop(&quot;abc \r&quot;)      = &quot;abc &quot;
	 *  StringUtils.chop(&quot;abc\n&quot;)       = &quot;abc&quot;
	 *  StringUtils.chop(&quot;abc\r\n&quot;)     = &quot;abc&quot;
	 *  StringUtils.chop(&quot;abc&quot;)         = &quot;ab&quot;
	 *  StringUtils.chop(&quot;abc\nabc&quot;)    = &quot;abc\nab&quot;
	 *  StringUtils.chop(&quot;a&quot;)           = &quot;&quot;
	 *  StringUtils.chop(&quot;\r&quot;)          = &quot;&quot;
	 *  StringUtils.chop(&quot;\n&quot;)          = &quot;&quot;
	 *  StringUtils.chop(&quot;\r\n&quot;)        = &quot;&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to chop last character from, may be null
	 * @return String without last character, <code>null</code> if null String
	 *         input
	 */
	public static String chop(String str) {
		if (str == null) {
			return null;
		}
		int strLen = str.length();
		if (strLen < 2) {
			return EMPTY;
		}
		int lastIdx = strLen - 1;
		String ret = str.substring(0, lastIdx);
		char last = str.charAt(lastIdx);
		if (last == '\n') {
			if (ret.charAt(lastIdx - 1) == '\r') {
				return ret.substring(0, lastIdx - 1);
			}
		}
		return ret;
	}

	/**
	 * <p>
	 * Removes <code>\n</code> from end of a String if it's there. If a
	 * <code>\r</code> precedes it, then remove that too.
	 * </p>
	 * 
	 * @param str
	 *            the String to chop a newline from, must not be null
	 * @return String without newline
	 * @throws NullPointerException
	 *             if str is <code>null</code>
	 * @deprecated Use {@link #chomp(String)}instead. Method will be removed in
	 *             Commons Lang 3.0.
	 */
	public static String chopNewline(String str) {
		int lastIdx = str.length() - 1;
		if (lastIdx <= 0) {
			return EMPTY;
		}
		char last = str.charAt(lastIdx);
		if (last == '\n') {
			if (str.charAt(lastIdx - 1) == '\r') {
				lastIdx--;
			}
		} else {
			lastIdx++;
		}
		return str.substring(0, lastIdx);
	}

	// Padding
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Repeat a String <code>repeat</code> times to form a new String.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.repeat(null, 2) = null
	 *  StringUtils.repeat(&quot;&quot;, 0)   = &quot;&quot;
	 *  StringUtils.repeat(&quot;&quot;, 2)   = &quot;&quot;
	 *  StringUtils.repeat(&quot;a&quot;, 3)  = &quot;aaa&quot;
	 *  StringUtils.repeat(&quot;ab&quot;, 2) = &quot;abab&quot;
	 *  StringUtils.repeat(&quot;a&quot;, -2) = &quot;&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to repeat, may be null
	 * @param repeat
	 *            number of times to repeat str, negative treated as zero
	 * @return a new String consisting of the original String repeated,
	 *         <code>null</code> if null String input
	 */
	public static String repeat(String str, int repeat) {
		// Performance tuned for 2.0 (JDK1.4)

		if (str == null) {
			return null;
		}
		if (repeat <= 0) {
			return EMPTY;
		}
		int inputLength = str.length();
		if (repeat == 1 || inputLength == 0) {
			return str;
		}
		if (inputLength == 1 && repeat <= PAD_LIMIT) {
			return padding(repeat, str.charAt(0));
		}

		int outputLength = inputLength * repeat;
		switch (inputLength) {
		case 1:
			char ch = str.charAt(0);
			char[] output1 = new char[outputLength];
			for (int i = repeat - 1; i >= 0; i--) {
				output1[i] = ch;
			}
			return new String(output1);
		case 2:
			char ch0 = str.charAt(0);
			char ch1 = str.charAt(1);
			char[] output2 = new char[outputLength];
			for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
				output2[i] = ch0;
				output2[i + 1] = ch1;
			}
			return new String(output2);
		default:
			StringBuffer buf = new StringBuffer(outputLength);
			for (int i = 0; i < repeat; i++) {
				buf.append(str);
			}
			return buf.toString();
		}
	}

	/**
	 * <p>
	 * Returns padding using the specified delimiter repeated to a given length.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.padding(0, 'e')  = &quot;&quot;
	 *  StringUtils.padding(3, 'e')  = &quot;eee&quot;
	 *  StringUtils.padding(-2, 'e') = IndexOutOfBoundsException
	 * 
	 * </pre>
	 * 
	 * @param repeat
	 *            number of times to repeat delim
	 * @param padChar
	 *            character to repeat
	 * @return String with repeated character
	 * @throws IndexOutOfBoundsException
	 *             if <code>repeat &lt; 0</code>
	 */
	private static String padding(int repeat, char padChar) {
		// be careful of synchronization in this method
		// we are assuming that get and set from an array index is atomic
		String pad = PADDING[padChar];
		if (pad == null) {
			pad = String.valueOf(padChar);
		}
		while (pad.length() < repeat) {
			pad = pad.concat(pad);
		}
		PADDING[padChar] = pad;
		return pad.substring(0, repeat);
	}

	/**
	 * <p>
	 * Right pad a String with spaces (' ').
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.rightPad(null, *)   = null
	 *  StringUtils.rightPad(&quot;&quot;, 3)     = &quot;   &quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 3)  = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 5)  = &quot;bat  &quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 1)  = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, -1) = &quot;bat&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size) {
		return rightPad(str, size, ' ');
	}

	/**
	 * <p>
	 * Right pad a String with a specified character.
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.rightPad(null, *, *)     = null
	 *  StringUtils.rightPad(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 5, 'z')  = &quot;batzz&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padChar
	 *            the character to pad with
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String rightPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return rightPad(str, size, String.valueOf(padChar));
		}
		return str.concat(padding(pads, padChar));
	}

	/**
	 * <p>
	 * Right pad a String with a specified String.
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.rightPad(null, *, *)      = null
	 *  StringUtils.rightPad(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;batyz&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;batyzyzy&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 5, null)  = &quot;bat  &quot;
	 *  StringUtils.rightPad(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;bat  &quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padStr
	 * 
	 *            the String to pad with, null or empty treated as single space
	 * @return right padded String or original String if no padding is
	 *         necessary, <code>null</code> if null String input
	 */
	public static String rightPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (padStr == null || padStr.length() == 0) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return rightPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return str.concat(padStr);
		} else if (pads < padLen) {
			return str.concat(padStr.substring(0, pads));
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return str.concat(new String(padding));
		}
	}

	/**
	 * <p>
	 * Left pad a String with spaces (' ').
	 * </p>
	 * 
	 * <p>
	 * The String is padded to the size of <code>size<code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.leftPad(null, *)   = null
	 *  StringUtils.leftPad(&quot;&quot;, 3)     = &quot;   &quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 3)  = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 5)  = &quot;  bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 1)  = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, -1) = &quot;bat&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @return left padded String or original String if no padding is necessary,
	 *         <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size) {
		return leftPad(str, size, ' ');
	}

	/**
	 * <p>
	 * Left pad a String with a specified character.
	 * </p>
	 * 
	 * <p>
	 * Pad to a size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.leftPad(null, *, *)     = null
	 *  StringUtils.leftPad(&quot;&quot;, 3, 'z')     = &quot;zzz&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 3, 'z')  = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 5, 'z')  = &quot;zzbat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 1, 'z')  = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, -1, 'z') = &quot;bat&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padChar
	 *            the character to pad with
	 * @return left padded String or original String if no padding is necessary,
	 *         <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String leftPad(String str, int size, char padChar) {
		if (str == null) {
			return null;
		}
		int pads = size - str.length();
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (pads > PAD_LIMIT) {
			return leftPad(str, size, String.valueOf(padChar));
		}
		return padding(pads, padChar).concat(str);
	}

	/**
	 * <p>
	 * Left pad a String with a specified String.
	 * </p>
	 * 
	 * <p>
	 * Pad to a size of <code>size</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.leftPad(null, *, *)      = null
	 *  StringUtils.leftPad(&quot;&quot;, 3, &quot;z&quot;)      = &quot;zzz&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 3, &quot;yz&quot;)  = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 5, &quot;yz&quot;)  = &quot;yzbat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 8, &quot;yz&quot;)  = &quot;yzyzybat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 1, &quot;yz&quot;)  = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, -1, &quot;yz&quot;) = &quot;bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 5, null)  = &quot;  bat&quot;
	 *  StringUtils.leftPad(&quot;bat&quot;, 5, &quot;&quot;)    = &quot;  bat&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to pad out, may be null
	 * @param size
	 *            the size to pad to
	 * @param padStr
	 *            the String to pad with, null or empty treated as single space
	 * @return left padded String or original String if no padding is necessary,
	 *         <code>null</code> if null String input
	 */
	public static String leftPad(String str, int size, String padStr) {
		if (str == null) {
			return null;
		}
		if (padStr == null || padStr.length() == 0) {
			padStr = " ";
		}
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str; // returns original String when possible
		}
		if (padLen == 1 && pads <= PAD_LIMIT) {
			return leftPad(str, size, padStr.charAt(0));
		}

		if (pads == padLen) {
			return padStr.concat(str);
		} else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		} else {
			char[] padding = new char[pads];
			char[] padChars = padStr.toCharArray();
			for (int i = 0; i < pads; i++) {
				padding[i] = padChars[i % padLen];
			}
			return new String(padding).concat(str);
		}
	}

	// Centering
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Centers a String in a larger String of size <code>size</code> using the
	 * space character (' ').
	 * <p>
	 * 
	 * <p>
	 * If the size is less than the String length, the String is returned. A
	 * <code>null</code> String returns <code>null</code>. A negative size is
	 * treated as zero.
	 * </p>
	 * 
	 * <p>
	 * Equivalent to <code>center(str, size, " ")</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.center(null, *)   = null
	 *  StringUtils.center(&quot;&quot;, 4)     = &quot;    &quot;
	 *  StringUtils.center(&quot;ab&quot;, -1)  = &quot;ab&quot;
	 *  StringUtils.center(&quot;ab&quot;, 4)   = &quot; ab &quot;
	 *  StringUtils.center(&quot;abcd&quot;, 2) = &quot;abcd&quot;
	 *  StringUtils.center(&quot;a&quot;, 4)    = &quot; a  &quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to center, may be null
	 * @param size
	 *            the int size of new String, negative treated as zero
	 * @return centered String, <code>null</code> if null String input
	 */
	public static String center(String str, int size) {
		return center(str, size, ' ');
	}

	/**
	 * <p>
	 * Centers a String in a larger String of size <code>size</code>. Uses a
	 * supplied character as the value to pad the String with.
	 * </p>
	 * 
	 * <p>
	 * If the size is less than the String length, the String is returned. A
	 * <code>null</code> String returns <code>null</code>. A negative size is
	 * treated as zero.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.center(null, *, *)     = null
	 *  StringUtils.center(&quot;&quot;, 4, ' ')     = &quot;    &quot;
	 *  StringUtils.center(&quot;ab&quot;, -1, ' ')  = &quot;ab&quot;
	 *  StringUtils.center(&quot;ab&quot;, 4, ' ')   = &quot; ab&quot;
	 *  StringUtils.center(&quot;abcd&quot;, 2, ' ') = &quot;abcd&quot;
	 *  StringUtils.center(&quot;a&quot;, 4, ' ')    = &quot; a  &quot;
	 *  StringUtils.center(&quot;a&quot;, 4, 'y')    = &quot;yayy&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to center, may be null
	 * @param size
	 *            the int size of new String, negative treated as zero
	 * @param padChar
	 *            the character to pad the new String with
	 * @return centered String, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String center(String str, int size, char padChar) {
		if (str == null || size <= 0) {
			return str;
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padChar);
		str = rightPad(str, size, padChar);
		return str;
	}

	/**
	 * <p>
	 * Centers a String in a larger String of size <code>size</code>. Uses a
	 * supplied String as the value to pad the String with.
	 * </p>
	 * 
	 * <p>
	 * If the size is less than the String length, the String is returned. A
	 * <code>null</code> String returns <code>null</code>. A negative size is
	 * treated as zero.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.center(null, *, *)     = null
	 *  StringUtils.center(&quot;&quot;, 4, &quot; &quot;)     = &quot;    &quot;
	 *  StringUtils.center(&quot;ab&quot;, -1, &quot; &quot;)  = &quot;ab&quot;
	 *  StringUtils.center(&quot;ab&quot;, 4, &quot; &quot;)   = &quot; ab&quot;
	 *  StringUtils.center(&quot;abcd&quot;, 2, &quot; &quot;) = &quot;abcd&quot;
	 *  StringUtils.center(&quot;a&quot;, 4, &quot; &quot;)    = &quot; a  &quot;
	 *  StringUtils.center(&quot;a&quot;, 4, &quot;yz&quot;)   = &quot;yayz&quot;
	 *  StringUtils.center(&quot;abc&quot;, 7, null) = &quot;  abc  &quot;
	 *  StringUtils.center(&quot;abc&quot;, 7, &quot;&quot;)   = &quot;  abc  &quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to center, may be null
	 * @param size
	 *            the int size of new String, negative treated as zero
	 * @param padStr
	 *            the String to pad the new String with, must not be null or
	 *            empty
	 * @return centered String, <code>null</code> if null String input
	 * @throws IllegalArgumentException
	 *             if padStr is <code>null</code> or empty
	 */
	public static String center(String str, int size, String padStr) {
		if (str == null || size <= 0) {
			return str;
		}
		if (padStr == null || padStr.length() == 0) {
			padStr = " ";
		}
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		str = leftPad(str, strLen + pads / 2, padStr);
		str = rightPad(str, size, padStr);
		return str;
	}

	// Case conversion
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Converts a String to upper case as per {@link String#toUpperCase()}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.upperCase(null)  = null
	 *  StringUtils.upperCase(&quot;&quot;)    = &quot;&quot;
	 *  StringUtils.upperCase(&quot;aBc&quot;) = &quot;ABC&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to upper case, may be null
	 * @return the upper cased String, <code>null</code> if null String input
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toUpperCase();
	}

	/**
	 * <p>
	 * Converts a String to lower case as per {@link String#toLowerCase()}.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.lowerCase(null)  = null
	 *  StringUtils.lowerCase(&quot;&quot;)    = &quot;&quot;
	 *  StringUtils.lowerCase(&quot;aBc&quot;) = &quot;abc&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 * 
	 *            the String to lower case, may be null
	 * @return the lower cased String, <code>null</code> if null String input
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}
		return str.toLowerCase();
	}

	/**
	 * <p>
	 * Capitalizes a String changing the first letter to title case as per
	 * {@link Character#toTitleCase(char)}. No other letters are changed.
	 * </p>
	 * 
	 * <p>
	 * For a word based alorithm, see {@link WordUtils#capitalize(String)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.capitalize(null)  = null
	 *  StringUtils.capitalize(&quot;&quot;)    = &quot;&quot;
	 *  StringUtils.capitalize(&quot;cat&quot;) = &quot;Cat&quot;
	 *  StringUtils.capitalize(&quot;cAt&quot;) = &quot;CAt&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return the capitalized String, <code>null</code> if null String input
	 * @see WordUtils#capitalize(String)
	 * @see #uncapitalize(String)
	 * @since 2.0
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(Character.toTitleCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * <p>
	 * Capitalizes a String changing the first letter to title case as per
	 * {@link Character#toTitleCase(char)}. No other letters are changed.
	 * </p>
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return the capitalized String, <code>null</code> if null String input
	 * @deprecated Use the standardly named {@link #capitalize(String)}. Method
	 *             will be removed in Commons Lang 3.0.
	 */
	public static String capitalise(String str) {
		return capitalize(str);
	}

	/**
	 * <p>
	 * Uncapitalizes a String changing the first letter to title case as per
	 * {@link Character#toLowerCase(char)}. No other letters are changed.
	 * </p>
	 * 
	 * <p>
	 * For a word based alorithm, see {@link WordUtils#uncapitalize(String)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.uncapitalize(null)  = null
	 *  StringUtils.uncapitalize(&quot;&quot;)    = &quot;&quot;
	 *  StringUtils.uncapitalize(&quot;Cat&quot;) = &quot;cat&quot;
	 *  StringUtils.uncapitalize(&quot;CAT&quot;) = &quot;cAT&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to uncapitalize, may be null
	 * @return the uncapitalized String, <code>null</code> if null String input
	 * @see WordUtils#uncapitalize(String)
	 * @see #capitalize(String)
	 * @since 2.0
	 */
	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		return new StringBuffer(strLen).append(Character.toLowerCase(str.charAt(0))).append(str.substring(1)).toString();
	}

	/**
	 * <p>
	 * Uncapitalizes a String changing the first letter to title case as per
	 * {@link Character#toLowerCase(char)}. No other letters are changed.
	 * </p>
	 * 
	 * @param str
	 *            the String to uncapitalize, may be null
	 * @return the uncapitalized String, <code>null</code> if null String input
	 * @deprecated Use the standardly named {@link #uncapitalize(String)}.
	 *             Method will be removed in Commons Lang 3.0.
	 */
	public static String uncapitalise(String str) {
		return uncapitalize(str);
	}

	/**
	 * <p>
	 * Swaps the case of a String changing upper and title case to lower case,
	 * and lower case to upper case.
	 * </p>
	 * 
	 * <ul>
	 * <li>Upper case character converts to Lower case</li>
	 * <li>Title case character converts to Lower case</li>
	 * <li>Lower case character converts to Upper case</li>
	 * </ul>
	 * 
	 * <p>
	 * For a word based alorithm, see {@link WordUtils#swapCase(String)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.swapCase(null)                 = null
	 *  StringUtils.swapCase(&quot;&quot;)                   = &quot;&quot;
	 *  StringUtils.swapCase(&quot;The dog has a BONE&quot;) = &quot;tHE DOG HAS A bone&quot;
	 * 
	 * </pre>
	 * 
	 * <p>
	 * NOTE: This method changed in Lang version 2.0. It no longer performs a
	 * word based alorithm. If you only use ASCII, you will notice no change.
	 * That functionality is available in WordUtils.
	 * </p>
	 * 
	 * @param str
	 *            the String to swap case, may be null
	 * @return the changed String, <code>null</code> if null String input
	 */
	public static String swapCase(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);

		char ch = 0;
		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				ch = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				ch = Character.toUpperCase(ch);
			}
			buffer.append(ch);
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * Capitalizes all the whitespace separated words in a String. Only the
	 * first letter of each word is changed.
	 * </p>
	 * 
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return capitalized String, <code>null</code> if null String input
	 * @deprecated Use the relocated {@link WordUtils#capitalize(String)}.
	 *             Method will be removed in Commons Lang 3.0.
	 */
	public static String capitaliseAllWords(String str) {
		return TSWordUtil.capitalize(str);
	}

	// Count matches
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Counts how many times the substring appears in the larger String.
	 * </p>
	 * 
	 * <p>
	 * A <code>null</code> or empty ("") String input returns <code>0</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.countMatches(null, *)       = 0
	 *  StringUtils.countMatches(&quot;&quot;, *)         = 0
	 *  StringUtils.countMatches(&quot;abba&quot;, null)  = 0
	 *  StringUtils.countMatches(&quot;abba&quot;, &quot;&quot;)    = 0
	 *  StringUtils.countMatches(&quot;abba&quot;, &quot;a&quot;)   = 2
	 *  StringUtils.countMatches(&quot;abba&quot;, &quot;ab&quot;)  = 1
	 *  StringUtils.countMatches(&quot;abba&quot;, &quot;xxx&quot;) = 0
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param sub
	 *            the substring to count, may be null
	 * @return the number of occurances, 0 if either String is <code>null</code>
	 */
	public static int countMatches(String str, String sub) {
		if (str == null || str.length() == 0 || sub == null || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int idx = 0;
		while ((idx = str.indexOf(sub, idx)) != -1) {
			count++;
			idx += sub.length();
		}
		return count;
	}

	// Character Tests
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Checks if the String contains only unicode letters.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isAlpha(null)   = false
	 *  StringUtils.isAlpha(&quot;&quot;)     = true
	 *  StringUtils.isAlpha(&quot;  &quot;)   = false
	 *  StringUtils.isAlpha(&quot;abc&quot;)  = true
	 *  StringUtils.isAlpha(&quot;ab2c&quot;) = false
	 *  StringUtils.isAlpha(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters, and is non-null
	 */
	public static boolean isAlpha(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetter(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters and space (' ').
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code> An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isAlphaSpace(null)   = false
	 *  StringUtils.isAlphaSpace(&quot;&quot;)     = true
	 *  StringUtils.isAlphaSpace(&quot;  &quot;)   = true
	 *  StringUtils.isAlphaSpace(&quot;abc&quot;)  = true
	 *  StringUtils.isAlphaSpace(&quot;ab c&quot;) = true
	 *  StringUtils.isAlphaSpace(&quot;ab2c&quot;) = false
	 *  StringUtils.isAlphaSpace(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters and space, and is
	 *         non-null
	 */
	public static boolean isAlphaSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetter(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters or digits.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isAlphanumeric(null)   = false
	 *  StringUtils.isAlphanumeric(&quot;&quot;)     = true
	 *  StringUtils.isAlphanumeric(&quot;  &quot;)   = false
	 *  StringUtils.isAlphanumeric(&quot;abc&quot;)  = true
	 *  StringUtils.isAlphanumeric(&quot;ab c&quot;) = false
	 *  StringUtils.isAlphanumeric(&quot;ab2c&quot;) = true
	 *  StringUtils.isAlphanumeric(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters or digits, and is
	 *         non-null
	 */
	public static boolean isAlphanumeric(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isLetterOrDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode letters, digits or space (
	 * <code>' '</code>).
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isAlphanumeric(null)   = false
	 *  StringUtils.isAlphanumeric(&quot;&quot;)     = true
	 *  StringUtils.isAlphanumeric(&quot;  &quot;)   = true
	 *  StringUtils.isAlphanumeric(&quot;abc&quot;)  = true
	 *  StringUtils.isAlphanumeric(&quot;ab c&quot;) = true
	 *  StringUtils.isAlphanumeric(&quot;ab2c&quot;) = true
	 *  StringUtils.isAlphanumeric(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains letters, digits or space, and
	 *         is non-null
	 */
	public static boolean isAlphanumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isLetterOrDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits. A decimal point is not
	 * a unicode digit and returns false.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isNumeric(null)   = false
	 *  StringUtils.isNumeric(&quot;&quot;)     = true
	 *  StringUtils.isNumeric(&quot;  &quot;)   = false
	 *  StringUtils.isNumeric(&quot;123&quot;)  = true
	 *  StringUtils.isNumeric(&quot;12 3&quot;) = false
	 *  StringUtils.isNumeric(&quot;ab2c&quot;) = false
	 *  StringUtils.isNumeric(&quot;12-3&quot;) = false
	 *  StringUtils.isNumeric(&quot;12.3&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains digits, and is non-null
	 */
	public static boolean isNumeric(String str) {
		if (str == null || "".equals(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only unicode digits or space (
	 * <code>' '</code>). A decimal point is not a unicode digit and returns
	 * false.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isNumeric(null)   = false
	 *  StringUtils.isNumeric(&quot;&quot;)     = true
	 *  StringUtils.isNumeric(&quot;  &quot;)   = true
	 *  StringUtils.isNumeric(&quot;123&quot;)  = true
	 *  StringUtils.isNumeric(&quot;12 3&quot;) = true
	 *  StringUtils.isNumeric(&quot;ab2c&quot;) = false
	 *  StringUtils.isNumeric(&quot;12-3&quot;) = false
	 *  StringUtils.isNumeric(&quot;12.3&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains digits or space, and is
	 *         non-null
	 */
	public static boolean isNumericSpace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isDigit(str.charAt(i)) == false) && (str.charAt(i) != ' ')) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>
	 * Checks if the String contains only whitespace.
	 * </p>
	 * 
	 * <p>
	 * <code>null</code> will return <code>false</code>. An empty String ("")
	 * will return <code>true</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.isWhitespace(null)   = false
	 *  StringUtils.isWhitespace(&quot;&quot;)     = true
	 *  StringUtils.isWhitespace(&quot;  &quot;)   = true
	 *  StringUtils.isWhitespace(&quot;abc&quot;)  = false
	 *  StringUtils.isWhitespace(&quot;ab2c&quot;) = false
	 *  StringUtils.isWhitespace(&quot;ab-c&quot;) = false
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @return <code>true</code> if only contains whitespace, and is non-null
	 * @since 2.0
	 */
	public static boolean isWhitespace(String str) {
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}

	// Defaults
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Returns either the passed in String, or if the String is
	 * <code>null</code>, an empty String ("").
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.defaultString(null)  = &quot;&quot;
	 *  StringUtils.defaultString(&quot;&quot;)    = &quot;&quot;
	 *  StringUtils.defaultString(&quot;bat&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 * 
	 * @see ObjectUtils#toString(Object)
	 * @see String#valueOf(Object)
	 * @param str
	 *            the String to check, may be null
	 * @return the passed in String, or the empty String if it was
	 *         <code>null</code>
	 */
	public static String defaultString(String str) {
		return (str == null ? EMPTY : str);
	}

	/**
	 * <p>
	 * Returns either the passed in String, or if the String is
	 * <code>null</code>, an empty String ("").
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.defaultString(null, &quot;null&quot;)  = &quot;null&quot;
	 *  StringUtils.defaultString(&quot;&quot;, &quot;null&quot;)    = &quot;&quot;
	 *  StringUtils.defaultString(&quot;bat&quot;, &quot;null&quot;) = &quot;bat&quot;
	 * 
	 * </pre>
	 * 
	 * @see ObjectUtils#toString(Object,String)
	 * @see String#valueOf(Object)
	 * @param str
	 *            the String to check, may be null
	 * @param defaultStr
	 *            the default String to return if the input is <code>null</code>
	 *            , may be null
	 * @return the passed in String, or the default if it was <code>null</code>
	 */
	public static String defaultString(String str, String defaultStr) {
		return (str == null ? defaultStr : str);
	}

	// Reversing
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Reverses a String as per {@link StringBuffer#reverse()}.
	 * </p>
	 * 
	 * <p>
	 * <A code>null</code> String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.reverse(null)  = null
	 *  StringUtils.reverse(&quot;&quot;)    = &quot;&quot;
	 *  StringUtils.reverse(&quot;bat&quot;) = &quot;tab&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to reverse, may be null
	 * @return the reversed String, <code>null</code> if null String input
	 */
	public static String reverse(String str) {
		if (str == null) {
			return null;
		}
		return new StringBuffer(str).reverse().toString();
	}

	/**
	 * <p>
	 * Reverses a String that is delimited by a specific character.
	 * </p>
	 * 
	 * <p>
	 * The Strings between the delimiters are not reversed. Thus
	 * java.lang.String becomes String.lang.java (if the delimiter is
	 * <code>'.'</code>).
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.reverseDelimited(null, *)      = null
	 *  StringUtils.reverseDelimited(&quot;&quot;, *)        = &quot;&quot;
	 *  StringUtils.reverseDelimited(&quot;a.b.c&quot;, 'x') = &quot;a.b.c&quot;
	 *  StringUtils.reverseDelimited(&quot;a.b.c&quot;, &quot;.&quot;) = &quot;c.b.a&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to reverse, may be null
	 * @param separatorChar
	 *            the separator character to use
	 * @return the reversed String, <code>null</code> if null String input
	 * @since 2.0
	 */
	public static String reverseDelimited(String str, char separatorChar) {
		if (str == null) {
			return null;
		}
		// could implement manually, but simple way is to reuse other,
		// probably slower, methods.
		String[] strs = split(str, separatorChar);
		TSArrayUtil.reverse(strs);
		return join(strs, separatorChar);
	}

	/**
	 * <p>
	 * Reverses a String that is delimited by a specific character.
	 * </p>
	 * 
	 * <p>
	 * The Strings between the delimiters are not reversed. Thus
	 * java.lang.String becomes String.lang.java (if the delimiter is
	 * <code>"."</code>).
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.reverseDelimitedString(null, *)       = null
	 *  StringUtils.reverseDelimitedString(&quot;&quot;,*)          = &quot;&quot;
	 *  StringUtils.reverseDelimitedString(&quot;a.b.c&quot;, null) = &quot;a.b.c&quot;
	 *  StringUtils.reverseDelimitedString(&quot;a.b.c&quot;, &quot;.&quot;)  = &quot;c.b.a&quot;
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to reverse, may be null
	 * @param separatorChars
	 *            the separator characters to use, null treated as whitespace
	 * @return the reversed String, <code>null</code> if null String input
	 * @deprecated Use {@link #reverseDelimited(String, char)}instead. This
	 *             method is broken as the join doesn't know which char to use.
	 *             Method will be removed in Commons Lang 3.0.
	 * 
	 */
	public static String reverseDelimitedString(String str, String separatorChars) {
		if (str == null) {
			return null;
		}
		// could implement manually, but simple way is to reuse other,
		// probably slower, methods.
		String[] strs = split(str, separatorChars);
		TSArrayUtil.reverse(strs);
		if (separatorChars == null) {
			return join(strs, ' ');
		}
		return join(strs, separatorChars);
	}

	// Abbreviating
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Abbreviates a String using ellipses. This will turn "Now is the time for
	 * all good men" into "Now is the time for..."
	 * </p>
	 * 
	 * <p>
	 * Specifically:
	 * <ul>
	 * <li>If <code>str</code> is less than <code>maxWidth</code> characters
	 * long, return it.</li>
	 * <li>Else abbreviate it to <code>(substring(str, 0, max-3) + "...")</code>
	 * .</li>
	 * <li>If <code>maxWidth</code> is less than <code>4</code>, throw an
	 * <code>IllegalArgumentException</code>.</li>
	 * <li>In no case will it return a String of length greater than
	 * <code>maxWidth</code>.</li>
	 * </ul>
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.abbreviate(null, *)      = null
	 *  StringUtils.abbreviate(&quot;&quot;, 4)        = &quot;&quot;
	 *  StringUtils.abbreviate(&quot;abcdefg&quot;, 6) = &quot;abc...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefg&quot;, 7) = &quot;abcdefg&quot;
	 *  StringUtils.abbreviate(&quot;abcdefg&quot;, 8) = &quot;abcdefg&quot;
	 *  StringUtils.abbreviate(&quot;abcdefg&quot;, 4) = &quot;a...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefg&quot;, 3) = IllegalArgumentException
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param maxWidth
	 *            maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws IllegalArgumentException
	 *             if the width is too small
	 * @since 2.0
	 */
	public static String abbreviate(String str, int maxWidth) {
		return abbreviate(str, 0, maxWidth);
	}

	/**
	 * <p>
	 * Abbreviates a String using ellipses. This will turn "Now is the time for
	 * all good men" into "...is the time for..."
	 * </p>
	 * 
	 * <p>
	 * Works like <code>abbreviate(String, int)</code>, but allows you to
	 * specify a "left edge" offset. Note that this left edge is not necessarily
	 * going to be the leftmost character in the result, or the first character
	 * following the ellipses, but it will appear somewhere in the result.
	 * 
	 * <p>
	 * In no case will it return a String of length greater than
	 * <code>maxWidth</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.abbreviate(null, *, *)                = null
	 *  StringUtils.abbreviate(&quot;&quot;, 0, 4)                  = &quot;&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, -1, 10) = &quot;abcdefg...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 0, 10)  = &quot;abcdefg...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 1, 10)  = &quot;abcdefg...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 4, 10)  = &quot;abcdefg...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 5, 10)  = &quot;...fghi...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 6, 10)  = &quot;...ghij...&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 8, 10)  = &quot;...ijklmno&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 10, 10) = &quot;...ijklmno&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghijklmno&quot;, 12, 10) = &quot;...ijklmno&quot;
	 *  StringUtils.abbreviate(&quot;abcdefghij&quot;, 0, 3)        = IllegalArgumentException
	 *  StringUtils.abbreviate(&quot;abcdefghij&quot;, 5, 6)        = IllegalArgumentException
	 * 
	 * </pre>
	 * 
	 * @param str
	 *            the String to check, may be null
	 * @param offset
	 *            left edge of source String
	 * @param maxWidth
	 *            maximum length of result String, must be at least 4
	 * @return abbreviated String, <code>null</code> if null String input
	 * @throws IllegalArgumentException
	 *             if the width is too small
	 * @since 2.0
	 */
	public static String abbreviate(String str, int offset, int maxWidth) {
		if (str == null) {
			return null;
		}
		if (maxWidth < 4) {
			throw new IllegalArgumentException("Minimum abbreviation width is 4");
		}
		if (str.length() <= maxWidth) {
			return str;
		}
		if (offset > str.length()) {
			offset = str.length();
		}
		if ((str.length() - offset) < (maxWidth - 3)) {
			offset = str.length() - (maxWidth - 3);
		}
		if (offset <= 4) {
			return str.substring(0, maxWidth - 3) + "...";
		}
		if (maxWidth < 7) {
			throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
		}
		if ((offset + (maxWidth - 3)) < str.length()) {
			return "..." + abbreviate(str.substring(offset), maxWidth - 3);
		}
		return "..." + str.substring(str.length() - (maxWidth - 3));
	}

	// Difference
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Compares two Strings, and returns the portion where they differ. (More
	 * precisely, return the remainder of the second String, starting from where
	 * it's different from the first.)
	 * </p>
	 * 
	 * <p>
	 * For example,
	 * <code>difference("i am a machine", "i am a robot") -> "robot"</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.difference(null, null) = null
	 *  StringUtils.difference(&quot;&quot;, &quot;&quot;) = &quot;&quot;
	 *  StringUtils.difference(&quot;&quot;, &quot;abc&quot;) = &quot;abc&quot;
	 *  StringUtils.difference(&quot;abc&quot;, &quot;&quot;) = &quot;&quot;
	 *  StringUtils.difference(&quot;abc&quot;, &quot;abc&quot;) = &quot;&quot;
	 *  StringUtils.difference(&quot;ab&quot;, &quot;abxyz&quot;) = &quot;xyz&quot;
	 *  StringUtils.difference(&quot;abcde&quot;, &quot;abxyz&quot;) = &quot;xyz&quot;
	 *  StringUtils.difference(&quot;abcde&quot;, &quot;xyz&quot;) = &quot;xyz&quot;
	 * 
	 * </pre>
	 * 
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return the portion of str2 where it differs from str1; returns the empty
	 *         String if they are equal
	 * @since 2.0
	 */
	public static String difference(String str1, String str2) {
		if (str1 == null) {
			return str2;
		}
		if (str2 == null) {
			return str1;
		}
		int at = indexOfDifference(str1, str2);
		if (at == -1) {
			return EMPTY;
		}
		return str2.substring(at);
	}

	/**
	 * <p>
	 * Compares two Strings, and returns the index at which the Strings begin to
	 * differ.
	 * </p>
	 * 
	 * <p>
	 * For example,
	 * <code>indexOfDifference("i am a machine", "i am a robot") -> 7</code>
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.indexOfDifference(null, null) = -1
	 *  StringUtils.indexOfDifference(&quot;&quot;, &quot;&quot;) = -1
	 *  StringUtils.indexOfDifference(&quot;&quot;, &quot;abc&quot;) = 0
	 *  StringUtils.indexOfDifference(&quot;abc&quot;, &quot;&quot;) = 0
	 *  StringUtils.indexOfDifference(&quot;abc&quot;, &quot;abc&quot;) = -1
	 *  StringUtils.indexOfDifference(&quot;ab&quot;, &quot;abxyz&quot;) = 2
	 *  StringUtils.indexOfDifference(&quot;abcde&quot;, &quot;abxyz&quot;) = 2
	 *  StringUtils.indexOfDifference(&quot;abcde&quot;, &quot;xyz&quot;) = 0
	 * 
	 * </pre>
	 * 
	 * @param str1
	 *            the first String, may be null
	 * @param str2
	 *            the second String, may be null
	 * @return the index where str2 and str1 begin to differ; -1 if they are
	 *         equal
	 * @since 2.0
	 */
	public static int indexOfDifference(String str1, String str2) {
		if (str1 == str2) {
			return -1;
		}
		if (str1 == null || str2 == null) {
			return 0;
		}
		int i;
		for (i = 0; i < str1.length() && i < str2.length(); ++i) {
			if (str1.charAt(i) != str2.charAt(i)) {
				break;
			}
		}
		if (i < str2.length() || i < str1.length()) {
			return i;
		}
		return -1;
	}

	// Misc
	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Find the Levenshtein distance between two Strings.
	 * </p>
	 * 
	 * <p>
	 * This is the number of changes needed to change one String into another,
	 * where each change is a single character modification (deletion, insertion
	 * or substitution).
	 * </p>
	 * 
	 * <p>
	 * This implementation of the Levenshtein distance algorithm is from <a
	 * href=
	 * "http://www.merriampark.com/ld.htm">http://www.merriampark.com/ld.htm
	 * </a>
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  StringUtils.getLevenshteinDistance(null, *)             = IllegalArgumentException
	 *  StringUtils.getLevenshteinDistance(*, null)             = IllegalArgumentException
	 *  StringUtils.getLevenshteinDistance(&quot;&quot;,&quot;&quot;)               = 0
	 *  StringUtils.getLevenshteinDistance(&quot;&quot;,&quot;a&quot;)              = 1
	 *  StringUtils.getLevenshteinDistance(&quot;aaapppp&quot;, &quot;&quot;)       = 7
	 *  StringUtils.getLevenshteinDistance(&quot;frog&quot;, &quot;fog&quot;)       = 1
	 *  StringUtils.getLevenshteinDistance(&quot;fly&quot;, &quot;ant&quot;)        = 3
	 *  StringUtils.getLevenshteinDistance(&quot;elephant&quot;, &quot;hippo&quot;) = 7
	 *  StringUtils.getLevenshteinDistance(&quot;hippo&quot;, &quot;elephant&quot;) = 7
	 *  StringUtils.getLevenshteinDistance(&quot;hippo&quot;, &quot;zzzzzzzz&quot;) = 8
	 *  StringUtils.getLevenshteinDistance(&quot;hello&quot;, &quot;hallo&quot;)    = 1
	 * 
	 * </pre>
	 * 
	 * @param s
	 *            the first String, must not be null
	 * @param t
	 *            the second String, must not be null
	 * @return result distance
	 * @throws IllegalArgumentException
	 *             if either String input <code>null</code>
	 */
	public static int getLevenshteinDistance(String s, String t) {
		if (s == null || t == null) {
			throw new IllegalArgumentException("Strings must not be null");
		}
		int d[][]; // matrix
		int n; // length of s
		int m; // length of t
		int i; // iterates through s
		int j; // iterates through t
		char s_i; // ith character of s
		char t_j; // jth character of t
		int cost; // cost

		// Step 1
		n = s.length();
		m = t.length();
		if (n == 0) {
			return m;
		}
		if (m == 0) {
			return n;
		}
		d = new int[n + 1][m + 1];

		// Step 2
		for (i = 0; i <= n; i++) {
			d[i][0] = i;
		}

		for (j = 0; j <= m; j++) {
			d[0][j] = j;
		}

		// Step 3
		for (i = 1; i <= n; i++) {
			s_i = s.charAt(i - 1);

			// Step 4
			for (j = 1; j <= m; j++) {
				t_j = t.charAt(j - 1);

				// Step 5
				if (s_i == t_j) {
					cost = 0;
				} else {
					cost = 1;
				}

				// Step 6
				d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
			}
		}

		// Step 7
		return d[n][m];
	}

	/**
	 * <p>
	 * Gets the minimum of three <code>int</code> values.
	 * </p>
	 * 
	 * @param a
	 *            value 1
	 * @param b
	 *            value 2
	 * @param c
	 *            value 3
	 * @return the smallest of the values
	 */
	private static int min(int a, int b, int c) {
		// Method copied from NumberUtils to avoid dependency on subpackage
		if (b < a) {
			a = b;
		}
		if (c < a) {
			a = c;
		}
		return a;
	}

	/**
	 * <p>
	 * Escapes the characters in a <code>String</code> to be suitable to pass to
	 * an SQL query.
	 * </p>
	 * 
	 * <p>
	 * For example,
	 * 
	 * <pre>
	 * statement.executeQuery(&quot;SELECT * FROM MOVIES WHERE TITLE='&quot; + StringEscapeUtils.escapeSql(&quot;McHale's Navy&quot;) + &quot;'&quot;);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * At present, this method only turns single-quotes into doubled
	 * single-quotes (<code>"McHale's Navy"</code>=>
	 * <code>"McHale''s Navy"</code>). It does not handle the cases of percent
	 * (%) or underscore (_) for use in LIKE clauses.
	 * </p>
	 * 
	 * see http://www.jguru.com/faq/view.jsp?EID=8881
	 * 
	 * @param str
	 *            the string to escape, may be null
	 * @return a new String, escaped for SQL, <code>null</code> if null string
	 *         input
	 */
	public static String escapeSql(String str) {
		if (str == null) {
			return null;
		}
		return replace(str, "'", "''");
	}

	public static String removerAcentos(String palavra) {

		if (TSUtil.isEmpty(palavra)) {

			return null;

		} else {

			return Normalizer.normalize(new StringBuilder(palavra), Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

		}

	}

	public static String removerNaoAlfaNumerico(String palavra) {

		if (TSUtil.isEmpty(palavra)) {

			return null;

		} else {

			return palavra.replaceAll("\\W", "");

		}

	}

	public static String formatarNomeProprio(String nome) {

		if (!TSUtil.isEmpty(nome)) {

			List<String> preposicoes = new ArrayList<String>();

			preposicoes.add("da");

			preposicoes.add("das");

			preposicoes.add("de");

			preposicoes.add("do");

			preposicoes.add("dos");

			preposicoes.add("e");

			nome = nome.replaceAll("  ", " ").trim().toLowerCase();

			String nomes[] = nome.split(" ");

			String nomeFormatado = "";

			for (String parte : nomes) {

				if (!preposicoes.contains(parte)) {

					nomeFormatado += " " + parte.substring(0, 1).toUpperCase() + parte.substring(1, parte.length());

				} else {

					nomeFormatado += " " + parte;

				}

			}

			return nomeFormatado.trim();

		} else {

			return null;

		}

	}

	public static String removerFormatacaoIdentificador(String documento) {

		if (!TSUtil.isEmpty(documento)) {

			documento = documento.replaceAll("-", "");

			documento = documento.replaceAll("/", "");

			String numero = "";

			for (int i = 0; i < documento.length(); i++) {

				if (documento.charAt(i) != '.') {

					numero += documento.charAt(i);

				}

			}

			documento = numero;

		}

		return documento;

	}
	
}