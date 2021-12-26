package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import utils.Configs;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceRushOrderController extends PlaceOrderController {
	
	/**
     * This method show the processing in console
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

	 /**
     * This method checks the avaibility of product when user click PlaceOrder button
     * And check the product supported rush order
     * @throws SQLException
     */
	public void placeRushOrder() throws SQLException{
        super.placeOrder();
    }
	
	/**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
 /*       Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
 */
    	return super.createOrder();
    }
    
    
    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
    	return super.createInvoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfoForRushOrderItem(HashMap info) throws InterruptedException, IOException{
    	super.processDeliveryInfo(info);
		validateRushOrderDeliveryInfo(info);
    }
    
    /**
     * The method validates the info
     * @param info this info include delivery information ex: address, phone, name, card ,...
     * @throws InterruptedException
     * @throws IOException
     */
    public void validateRushOrderDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
    	super.validateDeliveryInfo(info);
    	if (validateProvince(info.get("province")) == false) {
			throw new IOException("error: rush order invalid");
		}
    }
    
/*    public boolean validateName(String name) {
    	// Trần Thế Lâm - 20183937
    	// check null   	
    	if (name == null) return false;
    	
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
*/
    
	/**
     * this method check rush order for user address
     * if the province is Ha Noi => true
     * @param province : Tinh
     * @return
     */
    public boolean validateProvince(String province){
    	if (province.equalsIgnoreCase("Hà Nội"))
			return true;
		else
			return false;
    }

    
    /**
     * This method calculates the shipping fees of Rush order
     * @param order
     * @return shippingFee
     */
    /*
    @Override
    public int calculateRushOrderShippingFee(Order order){
//        int totalFees = super.calculateShippingFee(order);
        
        // Tinh phi cho moi mat hang lua chon Rush Order
        int NumberMediaHasRushOrder = 0;
		for(int i = 0; i<order.getlstOrderMedia().size(); i++) {
			OrderMedia cartMedia = (OrderMedia)order.getlstOrderMedia().get(i);
			if(cartMedia.getMedia().getSupportRushOrder())
				NumberMediaHasRushOrder	 += ((OrderMedia)order.getlstOrderMedia().get(i)).getQuantity();
		}
		
		
		LOGGER.info("Number of Rush Order Media: " + NumberMediaHasRushOrder);
		
		int rushOrderFees = NumberMediaHasRushOrder * 10;
//		order.setRushOrderFees(rushOrderFees);
		return rushOrderFees;
	}
*/   
}
