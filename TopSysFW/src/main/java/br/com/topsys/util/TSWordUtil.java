/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002-2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */
package br.com.topsys.util;

/**
 * <p>
 * Operations on Strings that contain words.
 * </p>
 * 
 * <p>
 * This class tries to handle <code>null</code> input gracefully. An exception
 * will not be thrown for a <code>null</code> input. Each method documents its
 * behaviour in more detail.
 * </p>
 * 
 * @author Apache Jakarta Velocity
 * @author Henri Yandell
 * @author Stephen Colebourne
 * @author <a href="mailto:hps@intermeta.de">Henning P. Schmiedehausen </a>
 * @author Gary Gregory
 * @since 2.0
 * @version $Id: TSWordUtil.java,v 1.1 2008/10/03 23:42:05 andre.topazio Exp $
 */
public class TSWordUtil {

	/**
	 * <p>
	 * <code>WordWrapUtils</code> instances should NOT be constructed in
	 * standard programming. Instead, the class should be used as
	 * <code>WordWrapUtils.wrap("foo bar", 20);</code>.
	 * </p>
	 * 
	 * <p>
	 * This constructor is public to permit tools that require a JavaBean
	 * instance to operate.
	 * </p>
	 */
	public TSWordUtil() {
	}

	// Wrapping
	//--------------------------------------------------------------------------
	//    /**
	//     * <p>Wraps a block of text to a specified line length using '\n' as
	//     * a newline.</p>
	//     *
	//     * <p>This method takes a block of text, which might have long lines in it
	//     * and wraps the long lines based on the supplied lineLength
	// parameter.</p>
	//     *
	//     * <p>If a single word is longer than the line length (eg. a URL), it will
	//     * not be broken, and will display beyond the expected width.</p>
	//     *
	//     * <p>If there are tabs in inString, you are going to get results that are
	//     * a bit strange. Tabs are a single character but are displayed as 4 or 8
	//     * spaces. Remove the tabs.</p>
	//     *
	//     * @param str text which is in need of word-wrapping, may be null
	//     * @param lineLength the column to wrap the words at
	//     * @return the text with all the long lines word-wrapped
	//     * <code>null</code> if null string input
	//     */
	//    public static String wrapText(String str, int lineLength) {
	//        return wrap(str, null, lineLength);
	//    }

	//    /**
	//     * <p>Wraps a block of text to a specified line length.</p>
	//     *
	//     * <p>This method takes a block of text, which might have long lines in it
	//     * and wraps the long lines based on the supplied lineLength
	// parameter.</p>
	//     *
	//     * <p>If a single word is longer than the wrapColumn (eg. a URL), it will
	//     * not be broken, and will display beyond the expected width.</p>
	//     *
	//     * <p>If there are tabs in inString, you are going to get results that are
	//     * a bit strange. Tabs are a single character but are displayed as 4 or 8
	//     * spaces. Remove the tabs.</p>

	//     *
	//     * @param str text which is in need of word-wrapping, may be null
	//     * @param newLineChars the characters that define a newline, null treated
	// as \n
	//     * @param lineLength the column to wrap the words at
	//     * @return the text with all the long lines word-wrapped
	//     * <code>null</code> if null string input
	//     */
	//    public static String wrapText(String str, String newLineChars, int
	// lineLength) {
	//        if (str == null) {
	//            return null;
	//        }
	//        if (newLineChars == null) {
	//            newLineChars = "\n";
	//        }
	//        StringTokenizer lineTokenizer = new StringTokenizer(str, newLineChars,
	// true);
	//        StringBuffer stringBuffer = new StringBuffer();
	//
	//        while (lineTokenizer.hasMoreTokens()) {
	//            try {
	//                String nextLine = lineTokenizer.nextToken();
	//
	//                if (nextLine.length() > lineLength) {
	//                    // This line is long enough to be wrapped.
	//                    nextLine = wrapLine(nextLine, null, lineLength, false);
	//                }
	//
	//                stringBuffer.append(nextLine);
	//
	//            } catch (NoSuchElementException nsee) {
	//                // thrown by nextToken(), but I don't know why it would
	//                break;
	//            }
	//        }
	//
	//        return (stringBuffer.toString());
	//    }

	// Wrapping
	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Wraps a single line of text, identifying words by <code>' '</code>.
	 * </p>
	 * 
	 * <p>
	 * New lines will be separated by the system property line separator. Very
	 * long words, such as URLs will <i>not </i> be wrapped.
	 * </p>
	 * 
	 * <p>
	 * Leading spaces on a new line are stripped. Trailing spaces are not
	 * stripped.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  WordUtils.wrap(null, *) = null
	 *  WordUtils.wrap(&quot;&quot;, *) = &quot;&quot;
	 *  
	 * </pre>
	 * 
	 * @param str
	 *            the String to be word wrapped, may be null
	 * @param wrapLength
	 *            the column to wrap the words at, less than 1 is treated as 1
	 * @return a line with newlines inserted, <code>null</code> if null input
	 */
	public static String wrap(String str, int wrapLength) {
		return wrap(str, wrapLength, null, false);
	}

	/**
	 * <p>
	 * Wraps a single line of text, identifying words by <code>' '</code>.
	 * </p>
	 * 
	 * <p>
	 * Leading spaces on a new line are stripped. Trailing spaces are not
	 * stripped.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  WordUtils.wrap(null, *, *, *) = null
	 *  WordUtils.wrap(&quot;&quot;, *, *, *) = &quot;&quot;
	 *  
	 * </pre>
	 * 
	 * @param str
	 *            the String to be word wrapped, may be null
	 * @param wrapLength
	 *            the column to wrap the words at, less than 1 is treated as 1
	 * @param newLineStr
	 *            the string to insert for a new line, <code>null</code> uses
	 *            the system property line separator
	 * @param wrapLongWords
	 *            true if long words (such as URLs) should be wrapped
	 * @return a line with newlines inserted, <code>null</code> if null input
	 */
	public static String wrap(String str, int wrapLength, String newLineStr,
			boolean wrapLongWords) {
		if (str == null) {
			return null;
		}
		if (newLineStr == null) {
			newLineStr = TSUtil.LINE_SEPARATOR;
		}
		if (wrapLength < 1) {
			wrapLength = 1;
		}
		int inputLineLength = str.length();
		int offset = 0;
		StringBuffer wrappedLine = new StringBuffer(inputLineLength + 32);

		while ((inputLineLength - offset) > wrapLength) {
			if (str.charAt(offset) == ' ') {
				offset++;
				continue;
			}
			int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);

			if (spaceToWrapAt >= offset) {
				// normal case
				wrappedLine.append(str.substring(offset, spaceToWrapAt));
				wrappedLine.append(newLineStr);
				offset = spaceToWrapAt + 1;

			} else {
				// really long word or URL
				if (wrapLongWords) {
					// wrap really long word one line at a time
					wrappedLine.append(str.substring(offset, wrapLength
							+ offset));
					wrappedLine.append(newLineStr);
					offset += wrapLength;
				} else {
					// do not wrap really long word, just extend beyond limit
					spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
					if (spaceToWrapAt >= 0) {
						wrappedLine
								.append(str.substring(offset, spaceToWrapAt));
						wrappedLine.append(newLineStr);
						offset = spaceToWrapAt + 1;
					} else {
						wrappedLine.append(str.substring(offset));
						offset = inputLineLength;
					}
				}
			}
		}

		// Whatever is left in line is short enough to just pass through
		wrappedLine.append(str.substring(offset));

		return wrappedLine.toString();
	}

	// Capitalizing
	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Capitalizes all the whitespace separated words in a String. Only the
	 * first letter of each word is changed. To change all letters to the
	 * capitalized case, use {@link #capitalizeFully(String)}.
	 * </p>
	 * 
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * Capitalization uses the unicode title case, normally equivalent to upper
	 * case.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  WordUtils.capitalize(null)        = null
	 *  WordUtils.capitalize(&quot;&quot;)          = &quot;&quot;
	 *  WordUtils.capitalize(&quot;i am FINE&quot;) = &quot;I Am FINE&quot;
	 *  
	 * </pre>
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return capitalized String, <code>null</code> if null String input
	 * @see #uncapitalize(String)
	 * @see #capitalizeFully(String)
	 */
	public static String capitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);
		boolean whitespace = true;
		for (int i = 0; i < strLen; i++) {
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				buffer.append(ch);
				whitespace = true;
			} else if (whitespace) {
				buffer.append(Character.toTitleCase(ch));
				whitespace = false;
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * Capitalizes all the whitespace separated words in a String. All letters
	 * are changed, so the resulting string will be fully changed.
	 * </p>
	 * 
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * Capitalization uses the unicode title case, normally equivalent to upper
	 * case.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  WordUtils.capitalize(null)        = null
	 *  WordUtils.capitalize(&quot;&quot;)          = &quot;&quot;
	 *  WordUtils.capitalize(&quot;i am FINE&quot;) = &quot;I Am Fine&quot;
	 *  
	 * </pre>
	 * 
	 * @param str
	 *            the String to capitalize, may be null
	 * @return capitalized String, <code>null</code> if null String input
	 */
	public static String capitalizeFully(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);
		boolean whitespace = true;
		for (int i = 0; i < strLen; i++) {
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				buffer.append(ch);
				whitespace = true;
			} else if (whitespace) {
				buffer.append(Character.toTitleCase(ch));
				whitespace = false;
			} else {
				buffer.append(Character.toLowerCase(ch));
			}
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * Uncapitalizes all the whitespace separated words in a String. Only the
	 * first letter of each word is changed.
	 * </p>
	 * 
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. A
	 * <code>null</code> input String returns <code>null</code>.
	 * </p>
	 * 
	 * <pre>
	 * 
	 *  WordUtils.uncapitalize(null)        = null
	 *  WordUtils.uncapitalize(&quot;&quot;)          = &quot;&quot;
	 *  WordUtils.uncapitalize(&quot;I Am FINE&quot;) = &quot;i am fINE&quot;
	 *  
	 * </pre>
	 * 
	 * @param str
	 *            the String to uncapitalize, may be null
	 * @return uncapitalized String, <code>null</code> if null String input
	 * @see #capitalize(String)
	 */
	public static String uncapitalize(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		StringBuffer buffer = new StringBuffer(strLen);
		boolean whitespace = true;
		for (int i = 0; i < strLen; i++) {
			char ch = str.charAt(i);
			if (Character.isWhitespace(ch)) {
				buffer.append(ch);
				whitespace = true;
			} else if (whitespace) {
				buffer.append(Character.toLowerCase(ch));
				whitespace = false;
			} else {
				buffer.append(ch);
			}
		}
		return buffer.toString();
	}

	/**
	 * <p>
	 * Swaps the case of a String using a word based algorithm.
	 * </p>
	 * 
	 * <ul>
	 * <li>Upper case character converts to Lower case</li>
	 * <li>Title case character converts to Lower case</li>
	 * <li>Lower case character after Whitespace or at start converts to Title
	 * case</li>
	 * <li>Other Lower case character converts to Upper case</li>
	 * </ul>
	 * 
	 * <p>
	 * Whitespace is defined by {@link Character#isWhitespace(char)}. A
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

		boolean whitespace = true;
		char ch = 0;
		char tmp = 0;

		for (int i = 0; i < strLen; i++) {
			ch = str.charAt(i);
			if (Character.isUpperCase(ch)) {
				tmp = Character.toLowerCase(ch);
			} else if (Character.isTitleCase(ch)) {
				tmp = Character.toLowerCase(ch);
			} else if (Character.isLowerCase(ch)) {
				if (whitespace) {
					tmp = Character.toTitleCase(ch);
				} else {
					tmp = Character.toUpperCase(ch);
				}
			} else {
				tmp = ch;
			}
			buffer.append(tmp);
			whitespace = Character.isWhitespace(ch);
		}
		return buffer.toString();
	}

}