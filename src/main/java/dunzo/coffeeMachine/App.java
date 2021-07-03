package dunzo.coffeeMachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class App {
	private static Logger log = LoggerFactory.getLogger(App.class.getSimpleName());
	public static void main(String[] args) {
		
		try {
			CoffeeMachine machine = parseCoffeeMachine();
			log.debug("Machine has been parsed successfully"); 
			//machine.printInventoryState();
			machine.startProcessing().forEach(x -> System.out.println(x));

		} catch (FileNotFoundException e) {
			log.error("File not found : Please check the path");
		} catch (IOException e) {
			log.error("Error while reading Json : Please check the file and try again");
		} catch (ParseException e) {
			log.error("File could not be parsed : Please check the file and try again");
		} catch (IllegalAccessException e) {
			log.error("File could not be parsed : Incorrect Json -> " + e.getLocalizedMessage());
		}

	}

	public static CoffeeMachine parseCoffeeMachine() throws IOException, ParseException, FileNotFoundException, IllegalAccessException {
		JSONParser parser = new JSONParser();
		JSONObject coffeeMachineJson = (JSONObject) parser
				.parse(new FileReader("src/test/resources/dunzo/coffeeMachine/test1.json"));

		JSONObject machineObject = (JSONObject) coffeeMachineJson.get("machine");
		if (machineObject == null) throw new IllegalAccessException("Machine object is null");
		
		HashMap<String, InventoryItem> inventorycontext =  setInventoryContext(machineObject);

		List<Beverage> beverages = prepareBeverages(machineObject);

		JSONObject outletsObject = (JSONObject) machineObject.get("outlets");
		if (outletsObject == null) throw new IllegalAccessException("outlets object is null");
		
		if (outletsObject.get("count_n") == null) throw new IllegalAccessException("count_n object is null");
		long noOfOutlet =  (long) outletsObject.get("count_n");
		if (noOfOutlet < 1) throw new IllegalAccessException("count_n cannot be less than 1");
		
		CoffeeMachine machine = new CoffeeMachine((int) noOfOutlet, beverages, new ItemsQuantity(inventorycontext));
		return machine;
	}

	@SuppressWarnings("unchecked")
	private static List<Beverage> prepareBeverages(JSONObject machineObject) throws IllegalAccessException {
		JSONObject beveragesJson = (JSONObject) machineObject.get("beverages");
		if (beveragesJson == null) throw new IllegalAccessException("beverages object is null");
		
		List<Beverage> beverages = new ArrayList<>();
		beveragesJson.forEach((beverageName, composition) -> {
			List<Ingredient> ingredients = new ArrayList<>();
			((JSONObject) composition).forEach((itemName, quantity) -> {
				ingredients.add(new Ingredient((String) itemName, (int) (long) quantity));
			});

			beverages.add(new Beverage(ingredients, (String) beverageName));
		});
		return beverages;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, InventoryItem> setInventoryContext(JSONObject machineObject) throws IllegalAccessException {
		 HashMap<String, InventoryItem> inventorycontext = new HashMap<String, InventoryItem>();
		 
		JSONObject itemsQuantityObject = (JSONObject) machineObject.get("total_items_quantity");
		if (itemsQuantityObject == null) throw new IllegalAccessException("total_items_quantity object is null");
		
		itemsQuantityObject.forEach((K, V) -> inventorycontext.put((String) K,
				new InventoryItem((String) K, (int) (long) V)));
		return inventorycontext;
	}

}
