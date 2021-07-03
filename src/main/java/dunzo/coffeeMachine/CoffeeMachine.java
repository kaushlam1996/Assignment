package dunzo.coffeeMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CoffeeMachine {
	private Integer numOutlets;
	private List<Beverage> beverages;
	private final ItemsQuantity inventory;
	private final Logger log = LoggerFactory.getLogger(CoffeeMachine.class.getSimpleName());
	
	public CoffeeMachine(Integer numOutlets, List<Beverage> beverages, ItemsQuantity inventoryItem) {
		this.numOutlets = numOutlets;
		this.beverages = beverages;
		this.inventory = inventoryItem;
	}

	public ArrayList<String> startProcessing() {
		
		ExecutorService service = prepareRuntimeOutletEnvironment();
        
		List<IRunningContext> submitableThread = prepareRunningContextToServeBeverage(); // IRunningContext is an Callable interface to submit each outlet to the the service
		
		
		ArrayList<String> output = new ArrayList<>();
		try {
            List<Future<String>> futures = service.invokeAll(submitableThread); // Invoking all Context but at max numOutlets threads will be running simultaneously 
            for (Future<String> future : futures) {
                String status = future.get(); // to get output from the thread
                output.add(status);
            }
        } catch (ExecutionException e) {
            log.error("Something went wrong");
        } catch (InterruptedException e) {
            log.error("Thread running the task was interrupted");
        } finally {
        	service.shutdown();
		}
		return output;
	}

	private List<IRunningContext> prepareRunningContextToServeBeverage() {
		List<IRunningContext> submitableThread = new ArrayList<>();
		for (Beverage beverage :beverages) {
			  IRunningContext runningContext = new RunningContext();
			  runningContext.setContextParameter(beverage,inventory);
			  submitableThread.add(runningContext);
		}
		return submitableThread;
	}

	private ExecutorService prepareRuntimeOutletEnvironment() {
		int noOfThread = Math.min(numOutlets, Runtime.getRuntime().availableProcessors() - 1); // chunk of running thread can be seen as parallelly running outlets
        return  Executors.newFixedThreadPool(noOfThread);
	}
	
	public String isItemLowInStock(String name) {
		return inventory.isItemLowInStock(name);
	}
	
	public void addOrRefillItemInInventoryContext(String name,int quanta) {
		inventory.addOrRefillItemInInventoryContext(name, quanta);
	}

	public void printInventoryState() {
		inventory.printInventoryState();
	}
	
	public Integer getNumOutlets() {
		return numOutlets;
	}

	public void setNumOutlets(Integer numOutlets) {
		this.numOutlets = numOutlets;
	}

	public List<Beverage> getBeverages() {
		return beverages;
	}

	public void setBeverages(List<Beverage> beverages) {
		this.beverages = beverages;
	}

}
