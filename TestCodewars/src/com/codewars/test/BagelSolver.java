package com.codewars.test;

//Bagel
//4 kyu
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class BagelSolver {

	public static void main(String[] args) {
		Bagel bagel = BagelSolver.getBagel();
		System.out.println(bagel.getValue() == 4);

	}

	@SuppressWarnings("unused")
	private static void setFinalStatic(Field field, Object value) throws Exception {
		field.setAccessible(true);

		Field modifiers = Field.class.getDeclaredField("modifiers");
		modifiers.setAccessible(true);
		modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, value);
	}

	public static Bagel getBagel() {
		try {
			Field field = Boolean.class.getField("TRUE");
			field.setAccessible(true);

			Field modField = Field.class.getDeclaredField("modifiers");
			modField.setAccessible(true);
			modField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

			field.set(null, false);
		} catch (Exception ex) {
			ex.printStackTrace(System.err);
		}
		return new Bagel();
	}

}
