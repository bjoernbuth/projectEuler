package com.euler;

import com.euler.common.Timing;

public class Euler017 {
	private final static String ONE = "one";
	private final static String TWO = "two";
	private final static String THREE = "three";
	private final static String FOUR = "four";
	private final static String FIVE = "five";
	private final static String SIX = "six";
	private final static String SEVEN = "seven";
	private final static String EIGHT = "eight";
	private final static String NINE = "nine";
	private final static String TEN = "ten";
	private final static String ELEVEN = "eleven";
	private final static String TWELVE = "twelve";
	private final static String THIRTEEN = "thirteen";
	private final static String FOURTEEN = "fourteen";
	private final static String FIFTEEN = "fifteen";
	private final static String SIXTEEN = "sixteen";
	private final static String SEVENTEEN = "seventeen";
	private final static String EIGHTEEN = "eighteen";
	private final static String NINETEEN = "nineteen";
	private final static String TWENTY = "twenty";
	private final static String THIRTY = "thirty";
	private final static String FORTY = "forty";
	private final static String FIFTY = "fifty";
	private final static String SIXTY = "sixty";
	private final static String SEVENTY = "seventy";
	private final static String EIGHTY = "eighty";
	private final static String NINETY = "ninety";
	private final static String HUNDRED = "hundred";
	private final static String THOUSAND = "thousand";
	private final static String AND = "and";

	private static long solve() {
		long total = 0;
		// ONE appears: as such; in every ty-X after 21; in every "X hundred"; in every
		// "Y-hundred and X"; in 1000.
		total += ONE.length() * 191;
		// TWO, THREE, ... NINE appear: as such; in every ty-X after 21; in every "X
		// hundred"; in every "Y-hundred and X".
		total += (TWO.length() + THREE.length() + FOUR.length() + FIVE.length() + SIX.length() + SEVEN.length()
				+ EIGHT.length() + NINE.length()) * 190;
		// TEN, ELEVEN, ... NINETEEN appear: as such; in every "Y-hundred and X".
		total += (TEN.length() + ELEVEN.length() + TWELVE.length() + THIRTEEN.length() + FOURTEEN.length()
				+ FIFTEEN.length() + SIXTEEN.length() + SEVENTEEN.length() + EIGHTEEN.length() + NINETEEN.length())
				* 10;
		// TWENTY, THIRTY, ... NINETY appear: as such; in every Y-hundred and X-Z"
		total += (TWENTY.length() + THIRTY.length() + FORTY.length() + FIFTY.length() + SIXTY.length()
				+ SEVENTY.length() + EIGHTY.length() + NINETY.length()) * 100;
		// HUNDRED appears: in every number from 100 to 999, included.
		total += HUNDRED.length() * 900;
		// THOUSAND appears: in One Thousand.
		total += THOUSAND.length();
		// AND appears: in every number from 100 to 999, except the exact hundreds.
		total += AND.length() * 891;
		return total;
	}

	public static void main(String[] args) {
		Timing.time(Euler017::solve);
	}
}
