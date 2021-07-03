package dunzo.coffeeMachine;

public class Ingredient {
	private String ingredientName;
	protected Integer quantity;
	

	public Ingredient(String ingredientName, Integer quantity) {
		super();
		this.ingredientName = ingredientName;
		this.quantity = quantity;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}

}
