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

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * A set of characters.
 * </p>
 * 
 * <p>
 * Instances are immutable, but instances of subclasses may not be.
 * </p>
 * 
 * @author Henri Yandell
 * @author Stephen Colebourne
 * @author Phil Steitz
 * @author Pete Gieser
 * @author Gary Gregory
 * @since 1.0
 * @version $Id: TSCharSet.java,v 1.1 2008/10/03 23:42:06 andre.topazio Exp $
 */
public class TSCharSet implements Serializable {

	/** Serialization lock, Lang version 2.0. */
	private static final long serialVersionUID = 5947847346149275958L;

	/**
	 * A CharSet defining no characters.
	 * 
	 * @since 2.0
	 */
	public static final TSCharSet EMPTY = new TSCharSet((String) null);

	/**
	 * A CharSet defining ASCII alphabetic characters "a-zA-Z".
	 * 
	 * @since 2.0
	 */
	public static final TSCharSet ASCII_ALPHA = new TSCharSet("a-zA-Z");

	/**
	 * A CharSet defining ASCII alphabetic characters "a-z".
	 * 
	 * @since 2.0
	 */
	public static final TSCharSet ASCII_ALPHA_LOWER = new TSCharSet("a-z");

	/**
	 * A CharSet defining ASCII alphabetic characters "A-Z".
	 * 
	 * @since 2.0
	 */
	public static final TSCharSet ASCII_ALPHA_UPPER = new TSCharSet("A-Z");

	/**
	 * A CharSet defining ASCII alphabetic characters "0-9".
	 * 
	 * @since 2.0
	 */
	public static final TSCharSet ASCII_NUMERIC = new TSCharSet("0-9");

	/**
	 * A Map of the common cases used in the factory. Subclasses can add more
	 * common patterns if desired.
	 * 
	 * @since 2.0
	 */
	protected static final Map COMMON = new HashMap();

	static {
		COMMON.put(null, EMPTY);
		COMMON.put("", EMPTY);
		COMMON.put("a-zA-Z", ASCII_ALPHA);
		COMMON.put("A-Za-z", ASCII_ALPHA);
		COMMON.put("a-z", ASCII_ALPHA_LOWER);
		COMMON.put("A-Z", ASCII_ALPHA_UPPER);
		COMMON.put("0-9", ASCII_NUMERIC);
	}

	/** The set of CharRange objects. */
	private Set set = new HashSet();

	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Factory method to create a new CharSet using a special syntax.
	 * </p>
	 * 
	 * <ul>
	 * <li><code>null</code> or empty string ("") - set containing no
	 * characters</li>
	 * <li>Single character, such as "a" - set containing just that character
	 * </li>
	 * <li>Multi character, such as "a-e" - set containing characters from one
	 * character to the other</li>
	 * <li>Negated, such as "^a" or "^a-e" - set containing all characters
	 * except those defined</li>
	 * <li>Combinations, such as "abe-g" - set containing all the characters
	 * from the individual sets</li>
	 * </ul>
	 * 
	 * <p>
	 * The matching order is:
	 * </p>
	 * <ol>
	 * <li>Negated multi character range, such as "^a-e"
	 * <li>Ordinary multi character range, such as "a-e"
	 * <li>Negated single character, such as "^a"
	 * <li>Ordinary single character, such as "a"
	 * </ol>
	 * <p>
	 * Matching works left to right. Once a match is found the search starts
	 * again from the next character.
	 * </p>
	 * 
	 * <p>
	 * If the same range is defined twice using the same syntax, only one range
	 * will be kept. Thus, "a-ca-c" creates only one range of "a-c".
	 * </p>
	 * 
	 * <p>
	 * If the start and end of a range are in the wrong order, they are
	 * reversed. Thus "a-e" is the same as "e-a". As a result, "a-ee-a" would
	 * create only one range, as the "a-e" and "e-a" are the same.
	 * </p>
	 * 
	 * <p>
	 * The set of characters represented is the union of the specified ranges.
	 * </p>
	 * 
	 * <p>
	 * All CharSet objects returned by this method will be immutable.
	 * </p>
	 * 
	 * @param setStr
	 *            the String describing the set, may be null
	 * @return a CharSet instance
	 * @since 2.0
	 */
	public static TSCharSet getInstance(String setStr) {
		Object set = COMMON.get(setStr);
		if (set != null) {
			return (TSCharSet) set;
		}
		return new TSCharSet(setStr);
	}

	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Constructs a new CharSet using the set syntax.
	 * </p>
	 * 
	 * @param setStr
	 *            the String describing the set, may be null
	 * @since 2.0
	 */
	protected TSCharSet(String setStr) {
		super();
		add(setStr);
	}

	/**
	 * <p>
	 * Constructs a new CharSet using the set syntax. Each string is merged in
	 * with the set.
	 * </p>
	 * 
	 * @param set
	 *            Strings to merge into the initial set
	 * @throws NullPointerException
	 *             if set is <code>null</code>
	 */
	protected TSCharSet(String[] set) {
		super();
		int sz = set.length;
		for (int i = 0; i < sz; i++) {
			add(set[i]);
		}
	}

	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Add a set definition string to the <code>CharSet</code>.
	 * </p>
	 * 
	 * @param str
	 *            set definition string
	 */
	@SuppressWarnings("unchecked")
	protected void add(String str) {
		if (str == null) {
			return;
		}

		int len = str.length();
		int pos = 0;
		while (pos < len) {
			int remainder = (len - pos);
			if (remainder >= 4 && str.charAt(pos) == '^'
					&& str.charAt(pos + 2) == '-') {
				// negated range
				set.add(new TSCharRangeUtil(str.charAt(pos + 1), str
						.charAt(pos + 3), true));
				pos += 4;
			} else if (remainder >= 3 && str.charAt(pos + 1) == '-') {
				// range
				set.add(new TSCharRangeUtil(str.charAt(pos), str.charAt(pos + 2)));
				pos += 3;
			} else if (remainder >= 2 && str.charAt(pos) == '^') {
				// negated char
				set.add(new TSCharRangeUtil(str.charAt(pos + 1), true));
				pos += 2;
			} else {
				// char
				set.add(new TSCharRangeUtil(str.charAt(pos)));
				pos += 1;
			}
		}
	}

	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the internal set as an array of CharRange objects.
	 * </p>
	 * 
	 * @return an array of immutable CharRange objects
	 * @since 2.0
	 */
	public TSCharRangeUtil[] getCharRanges() {
		return (TSCharRangeUtil[]) set.toArray(new TSCharRangeUtil[set.size()]);
	}

	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Does the <code>CharSet</code> contain the specified character
	 * <code>ch</code>.
	 * </p>
	 * 
	 * @param ch
	 *            the character to check for
	 * @return <code>true</code> if the set contains the characters
	 */
	public boolean contains(char ch) {
		for (Iterator it = set.iterator(); it.hasNext();) {
			TSCharRangeUtil range = (TSCharRangeUtil) it.next();
			if (range.contains(ch)) {
				return true;
			}
		}
		return false;
	}

	// Basics
	//-----------------------------------------------------------------------
	/**
	 * <p>
	 * Compares two CharSet objects, returning true if they represent exactly
	 * the same set of characters defined in the same way.
	 * </p>
	 * 
	 * <p>
	 * The two sets <code>abc</code> and <code>a-c</code> are <i>not </i>
	 * equal according to this method.
	 * </p>
	 * 
	 * @param obj
	 *            the object to compare to
	 * @return true if equal
	 * @since 2.0
	 */
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj instanceof TSCharSet == false) {
			return false;
		}
		TSCharSet other = (TSCharSet) obj;
		return (set.equals(other.set));
	}

	/**
	 * <p>
	 * Gets a hashCode compatable with the equals method.
	 * </p>
	 * 
	 * @return a suitable hashCode
	 * @since 2.0
	 */
	public int hashCode() {
		return 89 + set.hashCode();
	}

	/**
	 * <p>
	 * Gets a string representation of the set.
	 * </p>
	 * 
	 * @return string representation of the set
	 */
	public String toString() {
		return set.toString();
	}

}