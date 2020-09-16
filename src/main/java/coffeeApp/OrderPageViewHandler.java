package coffeeApp;

import coffeeApp.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPageViewHandler {


    @Autowired
    private OrderPageRepository orderPageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationAccepted_then_CREATE (@Payload OrderAccepted orderAccepted) {
        try {
            if (orderAccepted.isMe()) {
                // view 객체 생성
                OrderPage orderPage = new OrderPage();
                // view 객체에 이벤트의 Value 를 set 함
                orderPage.setOrderId(orderAccepted.getOrderId());
                orderPage.setProductId(orderAccepted.getProductId());
                orderPage.setStatus(orderAccepted.getStatus());
                // view 레파지 토리에 save
                orderPageRepository.save(orderPage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenReservationCanceled_then_UPDATE (@Payload OrderCanceled orderCanceled) {
        try {
            if (orderCanceled.isMe()) {
                // view 객체 조회
                List<OrderPage> orderList = orderPageRepository.findByOrderId(orderCanceled.getOrderId());
                for(OrderPage orderPage : orderList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderPage.setStatus(orderCanceled.getStatus());
                    // view 레파지 토리에 save
                    orderPageRepository.save(orderPage);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}