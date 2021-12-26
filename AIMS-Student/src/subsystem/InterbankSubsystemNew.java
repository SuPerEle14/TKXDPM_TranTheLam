package subsystem;

import common.exception.PaymentException;
import common.exception.UnrecognizedException;
import entity.payment.PaymentCard;
import entity.payment.PaymentTransaction;

public class InterbankSubsystemNew implements PayOrder {

	@Override
    public PaymentTransaction payOrder(PaymentCard card, int amount, String contents) throws PaymentException, UnrecognizedException {
        return null;
    }
}
