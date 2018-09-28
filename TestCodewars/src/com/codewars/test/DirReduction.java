package com.codewars.test;

import java.util.*;
import java.util.ListIterator;

//5 kyu
public class DirReduction {

	private static final String NORTH = "NORTH";
	private static final String SOUTH = "SOUTH";
	private static final String WEST = "WEST";
	private static final String EAST = "EAST";

	public static void main(String[] args) {

		String[] arr = new String[] { "NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST" };

		String[] res = dirReduc(arr);

		System.out.println("Size: " + res.length);
		for (int i = 0; i < res.length; i++) {
			System.out.println(res[i]);
		}

	}

	public static String[] dirReduc(String[] arr) {
		List<String> list = new ArrayList<String>(Arrays.asList(arr));
		boolean hasChanged = true;

		// comparar los 2 primeros items consecutivos (y avanzar)
		ListIterator<String> iter = list.listIterator();
		List<String> resultList = new ArrayList<String>();
		int n = 0;
		while (hasChanged) {
			System.out.println("start of while");
			System.out.println("Initial list: " + list.toString());
			iter = list.listIterator();
			n++;
			hasChanged = false;
			int p = 0;
			while (iter.hasNext()) {
				System.out.println("iter has next 1");
				p++;
				String s1 = iter.next();
				if (iter.hasNext()) {
					System.out.println("iter has next 2");
					String s2 = iter.next();
					if ((s1.equals(NORTH) && s2.equals(SOUTH)) || (s1.equals(SOUTH) && s2.equals(NORTH))
							|| (s1.equals(WEST) && s2.equals(EAST)) || (s1.equals(EAST) && s2.equals(WEST))) {
						// if(iter.hasPrevious()){
						iter.remove();
						iter.previous();
						iter.remove();
						// }
						hasChanged = true;
						System.out.println("Change in -> Iteration: " + n + " Position: " + p);
						System.out.println("s1: " + s1 + " - s2: " + s2);

						// borrar luego
						// ListIterator<String> tempIter = iter;
						// List<String> tempList = new ArrayList<String>();
						// tempIter.forEachRemaining(tempList::add); // Java 8
						// System.out.println(tempList.toString());
						list = new ArrayList<String>();
						while(iter.hasPrevious()) {
							iter.previous();
						}
						iter.forEachRemaining(list::add); // Java 8
						System.out.println("End list: " + list.toString());

						// break;
					} else {
						System.out.println("if (iter.hasPrevious())");
						if (iter.hasPrevious()) {
							System.out.println("To previous...");
							iter.previous();
						}
					}

				} else {
					// se llego al final de la comparacion
					System.out.println("End of iter.");
					while(iter.hasPrevious()) {
						iter.previous();
					}
					iter.forEachRemaining(resultList::add); // Java 8
					System.out.println("RESULT: " + resultList.toString());
					break;
				}
				System.out.println(hasChanged);
			}
		}

		// List<Element> resultList = Lists.newArrayList(iter); //Guava
		// List<Element> resultList = IteratorUtils.toList(iter); //Apache commons
		
		
		String[] result = new String[resultList.size()];
		result = resultList.toArray(result);

		// si son opuestos, eliminarlos
		// se debe generar un nuevo array que no contenga dichos elementos
		// si se eliminan 2 items, volver a iniciar la comparacion desde el inicio con
		// el nuevo array

		// si no son opuestos, continuar con el siguiente item (siguente posicion del
		// array)

		// finalizado, retornar el array resultado

		return result;
	}

}
