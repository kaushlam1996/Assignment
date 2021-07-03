package dunzo.coffeeMachine;

import java.util.List;

public class Beverage {
	private List<Ingredient> ingredient;
	private String name;
	
	public Beverage(List<Ingredient> ingredient, String name) {
		this.ingredient = ingredient;
		this.name = name;
	}
	public List<Ingredient> getIngredient() {
		return ingredient;
	}
	public void setIngredient(List<Ingredient> ingredient) {
		this.ingredient = ingredient;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
