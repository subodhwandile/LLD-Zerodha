package zerodhLLD;

import java.util.*;
import java.util.Map.Entry;

public class Zerodha {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ZerodhaSystem zerodhaSystem = ZerodhaSystem.getInstance();
		zerodhaSystem.addUser("U1", "Subodh");
		zerodhaSystem.createStock("ST1", "Reliance", 122.0);
		zerodhaSystem.placeOrder("U1", "ST1", 5, true, OrderType.LIMIT, Exchange.BSE);
	}

}

class ZerodhaSystem {
	public static ZerodhaSystem instance;
	private UserController userController;
	private StockController stockController;
	private OrderManager orderManager;
	private ZerodhaSystem() {
		userController = UserController.getInstance();
		stockController = StockController.getInstance();
		orderManager = new OrderManager();
	}
	
	public void placeOrder(String userID, String stockID, int units, boolean isBuyOrder, OrderType type, Exchange exchange) {
		// TODO Auto-generated method stub
		User user = userController.getUser(userID);
		String orderID = UUID.randomUUID().toString();
		Stock stock = stockController.getStock(stockID);
		Order newOrder = new Order(orderID, units, stock, isBuyOrder, type, exchange, user);
		orderManager.executeOrder(newOrder);
	}

	public void createStock(String userID, String name, double price) {
		// TODO Auto-generated method stub
		Stock newStock = new Stock(userID, name, price);
		stockController.addStock(newStock);
	}

	public void addUser(String userID, String userName) {
		// TODO Auto-generated method stub
		userController.addUser(new User(userID, userName));
	}

	public static ZerodhaSystem getInstance() {
		if (instance == null) {
			synchronized(ZerodhaSystem.class) {
				if (instance == null) {
					instance = new ZerodhaSystem();
				}
			}
		}
		return instance;
	}
}
class Wallet {
	private String walletID;
	private Double amount;
	public void orderPayment() {
		
	}
}
class UserController {
	public static UserController instance;
	private Map<String, User> userMap;
	private UserController() {
		this.userMap = new HashMap<String, User>();
	}
	
	public void addUser(User user) {
		// TODO Auto-generated method stub
		userMap.putIfAbsent(user.getUserID(), user);
	}

	public static UserController getInstance() {
		if (instance == null) {
			synchronized(UserController.class) {
				if (instance == null) {
					instance = new UserController();
				}
			}
		}
		return instance;
	}
	
	public User getUser(String userID) {
		if (userMap.containsKey(userID)) {
			return userMap.get(userID);
		}
		return null;
	}
}
class User {
	private String userID;
	private String userName;
	private Wallet wallet;
	public User(String userID, String userName) {
		this.userID = userID;
		this.userName = userName;
		this.wallet = new Wallet();
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Wallet getWallet() {
		return wallet;
	}
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
	
}
class StockController {
	public static StockController instance;
	private Map<String, Stock> stockList;
	private StockController() {
		this.stockList = new HashMap<String, Stock>();
	}
	
	public Stock getStock(String stockID) {
		// TODO Auto-generated method stub
		if (stockList.containsKey(stockID)) {
			return stockList.get(stockID);
		}
		return null;
	}

	public static StockController getInstance() {
		if (instance == null) {
			synchronized(StockController.class) {
				if (instance == null) {
					instance = new StockController();
				}
			}
		}
		return instance;
	}
	public List<Stock> displayStocks() {
		List<Stock> result = new ArrayList<Stock>();
		for (Entry<String, Stock> entry : stockList.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}
	public void addStock(Stock stock) {
		stockList.putIfAbsent(stock.getStockID(), stock);
	}
}
class Stock {
	private String StockID;
	private String StockName;
	private double latestTradedPrice;
	public Stock(String stockID, String stockName, double latestTradedPrice) {
		StockID = stockID;
		StockName = stockName;
		this.latestTradedPrice = latestTradedPrice;
	}
	public void updateStockPrice(double price) {
		latestTradedPrice = price;
	}
	public String getStockID() {
		return StockID;
	}
	public void setStockID(String stockID) {
		StockID = stockID;
	}
	public String getStockName() {
		return StockName;
	}
	public void setStockName(String stockName) {
		StockName = stockName;
	}
	public double getLatestTradedPrice() {
		return latestTradedPrice;
	}
}
enum OrderType {
	MARKET,
	LIMIT,
	STOP_LOSS,
	STOP_LIMIT
}
enum Exchange {
	NSE,
	BSE
}
class Transaction {
	private String txnID;
	private User contributor;
	private int volume;
	private double tradedPrice;
}
class Order {
	private String orderID;
	private int orderVolume;
	private Stock stock;
	private boolean isBuyOrder;
	private OrderType orderType;
	private Exchange stockExchange;
	private User initiator;
	private List<Transaction> transactionList;
	public Order(String orderID, int orderVolume, Stock stock, boolean isBuyOrder, OrderType orderType,
			Exchange stockExchange, User initiator) {
		super();
		this.orderID = orderID;
		this.orderVolume = orderVolume;
		this.stock = stock;
		this.isBuyOrder = isBuyOrder;
		this.orderType = orderType;
		this.stockExchange = stockExchange;
		this.initiator = initiator;
		this.transactionList = new ArrayList<Transaction>();
	}
}
class OrderValidator {
	public boolean validate(Order order) {
		return true;
	}
}
class StockExchangeConnector {

    // Private constructor to prevent instantiation
    private StockExchangeConnector() {
    }

    // Static inner class responsible for holding the singleton instance
    private static class SingletonHelper {
        // Lazy initialization of the instance
        private static final StockExchangeConnector INSTANCE = new StockExchangeConnector();
    }

    // Public method to provide access to the singleton instance
    public static StockExchangeConnector getInstance() {
        return SingletonHelper.INSTANCE;
    }

    // Method to place an order
    public void placeOrder(Order order) {
        // Logic for placing an order
    	System.out.println("Order sent to Exchange");
    }
}

class OrderExecutioner {
	public void sendOrderToExchange(Order order) {
		StockExchangeConnector exConnector = StockExchangeConnector.getInstance();
		exConnector.placeOrder(order);
	}
}
class OrderManager {
	private List<Order> orderList;
	private OrderValidator orderValidator;
	private OrderExecutioner executioner;
	public OrderManager() {
		super();
		orderList = new ArrayList<>();
		this.orderValidator = new OrderValidator();
		this.executioner = new OrderExecutioner();
	}
	
	public void executeOrder(Order order) {
		orderList.add(order);
		if (orderValidator.validate(order)) {
			System.out.println("Order validated now starting execution");
			executioner.sendOrderToExchange(order);
		} else {
			System.out.println("Invalid order");
		}
	}
	
}



