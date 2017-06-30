package com;

import java.util.ArrayList;
import java.util.List;

import com.model.Dish;

public class Main {
	static ArrayList<ArrayList> finalList = new ArrayList<>();

	// Print all subsets of given set[]
	 static ArrayList<ArrayList> printSubsets(List<Dish> dishList) {
		List<Dish> setList = null;

		int sum = 0;
		int n = dishList.size();
		for (int i = 0; i < (1 << n); i++) {
			System.out.print("{ ");
			setList = new ArrayList<Dish>();
			// Print current subset
			for (int j = 0; j < n; j++) {

				if ((i & (1 << j)) > 0) {
					setList.add(dishList.get(j));	
					System.out.print(dishList.get(j).getDishID() + " ");
				}
			}finalList.add((ArrayList) setList);

			System.out.println("}");
		}
		
		return finalList;
	}

	// Driver code
	public static void main(String[] args) {
		List<Dish> dishList = new ArrayList<Dish>();
		Dish dish1 = new Dish();
		dish1.setDishID(2);
		dish1.setEatingTime(3);
		dish1.setSatisfaction(7);
		dishList.add(dish1);

		Dish dish2 = new Dish();
		dish2.setDishID(2);
		dish2.setEatingTime(4);
		dish2.setSatisfaction(6);
		dishList.add(dish2);

		Dish dish3 = new Dish();
		dish3.setDishID(3);
		dish3.setEatingTime(4);
		dish3.setSatisfaction(9);
		dishList.add(dish3);

		List<ArrayList> str = printSubsets(dishList);

		for (List<Dish> dishlist : str) {
			for (Dish dish : dishList) {
				System.out.print( dish.getDishID()+" ,");
			}
			System.out.println();
		}
	}
}
