package doNotModify;

import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.MethodOrdererContext;

import java.util.Comparator;

public class CustomTestMethodOrder implements MethodOrderer {
    @Override
    public void orderMethods(MethodOrdererContext context) {
    	context.getMethodDescriptors().sort(Comparator.comparingInt(this::getTestMethodOrder));
    }

    private int getTestMethodOrder(MethodDescriptor methodDescriptor) {
    	//System.out.println(methodDescriptor.getMethod().getAnnotation(Order.class));
        Order orderAnnotation = methodDescriptor.getMethod().getAnnotation(Order.class);
        //System.out.println(orderAnnotation.value());
        return (orderAnnotation != null) ? orderAnnotation.value() : 0;
    }
}