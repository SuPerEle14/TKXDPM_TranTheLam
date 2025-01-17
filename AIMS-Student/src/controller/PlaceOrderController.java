package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;
import shippingfees.CalculateRushOrderShippingFees;
import shippingfees.CalculateShippingFees;
import shippingfees.ShippingFeeCalculator;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());
    
    /**
     * Shipping Fees
     */
    
    private ShippingFeeCalculator calculateShippingFee = new CalculateShippingFees();
    private ShippingFeeCalculator calculateRushOrderShippingFee = new CalculateRushOrderShippingFees();

    /**
     * This method checks the availability of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice(),
                                                   cartMedia.getRushOrder());        										
            		                                 
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	if(validateAddress(info.get("address")) == false 
    			|| validatePhoneNumber(info.get("phone")) == false 
    			|| validateName(info.get("name")) == false) {
    		throw new InvalidDeliveryInfoException("Load Order Invalid!");
    	}
    }
    
    public boolean validatePhoneNumber(String phoneNumber) {
    	// Trần Thế Lâm - 20183937
    	// check the phone number has 10 digits
    	if (phoneNumber.length() != 10) return false;
    	
    	// check the phone number start with 
    	if (!phoneNumber.startsWith("0")) return false;
    	
    	try {
    		Integer.parseInt(phoneNumber);
    	} catch (NumberFormatException e) {
    		return false;
    	}
    	return true;
    }
    
    public boolean validateName(String name) {
    	// Trần Thế Lâm - 20183937
    	// check null   	
    	if (name == null) return false;
    	
    	// check null character
    	if(name.split(" ").length >5){
            return false;
        }
    	
    	// Check special_character
    	Pattern p = Pattern.compile("[^a-z ]", Pattern.CASE_INSENSITIVE);
    	Matcher m = p.matcher(name);
    	boolean hasSpecialChar = m.find();
    	
    	if (hasSpecialChar) return false;
    	
    	return true;
    }
    
    public boolean validateAddress(String address) {
    	// Trần Thế Lâm - 20183937
    	// check null   	
    	if (address == null) return false;
    	
    	// Check special_character
    	Pattern p = Pattern.compile("[^\s a-zA-Z0-9 ]", Pattern.CASE_INSENSITIVE);
    	Matcher m = p.matcher(address);
    	boolean hasSpecialChar = m.find();
    	
    	if (hasSpecialChar) return false;
    	
    	return true;
    }
    
    
    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        return calculateShippingFee.calculateShippingFees(order);
    }
    
    
    /**
     * This method calculates Rush Order shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateRushOrderShippingFee(Order order){
        return calculateRushOrderShippingFee.calculateShippingFees(order);
    }
    
/*    
    public int calculateShippingFee(Order order){
    	// Hang Hoa gia tri cao duoc Free Ship
    	if(order.getAmount() > 1000000)
        	return 0;
    	
    	
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount());
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
    
    public int calculateRushOrderShippingFee(Order order) {
    	int k = order.getlstOrderMedia().size();
    	int numOfRush = 0;
    	for (int i = 0; i < k; i++) {
    		OrderMedia ordMedia = (OrderMedia) order.getlstOrderMedia().get(i);
    		if (ordMedia.getCheckRush()) numOfRush++; // Tinh so san pham chon Phuong Thuc Rush Order
    		
    	}
    	LOGGER.info("Number of Item choose Rush Order:" + order.getRushOrderFees());
    	
    	// Moi loai mat hang su dung Rush Order se tinh 2000đ
    	return numOfRush * 2000;
    }
    
    public int calculateNewShippingFees(Order order) {
    	int fees = 0;
    	
    	int k = order.getlstOrderMedia().size();
    	int numOfRush = 0;
    	for (int i = 0; i < k; i++) {
    		OrderMedia ordMedia = (OrderMedia) order.getlstOrderMedia().get(i);
    		int iLength, iWidth, iWeight;
    		ordMedia.getMedia().getLength(); 
    		
    	}
    	return fees;
    }
*/    

}
