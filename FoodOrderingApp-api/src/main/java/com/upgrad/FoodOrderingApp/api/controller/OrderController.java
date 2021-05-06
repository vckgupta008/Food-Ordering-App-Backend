package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.CouponDetailsResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemQuantity;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderRequest;
import com.upgrad.FoodOrderingApp.api.model.SaveOrderResponse;
import com.upgrad.FoodOrderingApp.service.businness.*;
import com.upgrad.FoodOrderingApp.service.entity.*;
import com.upgrad.FoodOrderingApp.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ItemService itemService;

    /**
     * RestController method called when the request pattern is of type "/order/coupon/{coupon_name}"
     * and the incoming request is of 'GET' type
     * Retrieve coupon details using coupon name
     *
     * @param authorization - String represents authorization token
     * @param couponName    - This represents coupon name
     * @return - ResponseEntity(CouponDetailsResponse, HttpStatus.OK)
     * @throws AuthorizationFailedException - if incorrect/ invalid authorization Token is sent,
     *                                      or the user has already signed out
     * @throws CouponNotFoundException      - if incorrect/ invalid coupon name is sent,
     *                                      or the coupon name doesn't exist
     */
    @RequestMapping(method = RequestMethod.GET, path = "/order/coupon/{coupon_name}",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CouponDetailsResponse> getAllAnswersToQuestion(@RequestHeader("authorization") final String authorization,
                                                                         @PathVariable("coupon_name") final String couponName)
            throws AuthorizationFailedException, CouponNotFoundException {
        final String accessToken = authorization.split("Bearer ")[1];
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        CouponEntity couponEntity = orderService.getCouponByCouponName(couponName);
        CouponDetailsResponse couponDetailsResponse = new CouponDetailsResponse()
                .id(UUID.fromString(couponEntity.getUuid()))
                .couponName(couponEntity.getCoupon_name())
                .percent(couponEntity.getPercent());

        return new ResponseEntity<CouponDetailsResponse>(couponDetailsResponse, HttpStatus.OK);
    }

    /**
     * RestController method called when the request pattern is of type "/order"
     * and the incoming request is of 'POST' type
     * Save order details
     *
     * @param authorization    - String represents authorization token
     * @param saveOrderRequest - SaveOrderRequest to be persisted into the database
     * @return - ResponseEntity(SaveOrderResponse, HttpStatus.OK)
     * @throws AuthorizationFailedException   - if incorrect/ invalid authorization Token is sent,
     *                                        or the user has already signed out
     * @throws CouponNotFoundException        - if invalid coupon uuid is sent
     * @throws AddressNotFoundException       - if invalid address uuid is sent, or the address does not belong to the logged in user
     * @throws PaymentMethodNotFoundException - if invalid payment uuid is sent
     * @throws RestaurantNotFoundException    - if invalid restaurant uuid is sent
     * @throws ItemNotFoundException          - if invalid item uuid is sent
     */
    @RequestMapping(method = RequestMethod.POST, path = "/order",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SaveOrderResponse> saveOrder(@RequestHeader("authorization") final String authorization,
                                                       @RequestBody(required = false) final SaveOrderRequest saveOrderRequest)
            throws AuthorizationFailedException, CouponNotFoundException, AddressNotFoundException,
            PaymentMethodNotFoundException, RestaurantNotFoundException, ItemNotFoundException {

        final String accessToken = authorization.split("Bearer ")[1];
        // Retrieve all the required entities to set in the OrderEntity
        CustomerEntity customerEntity = customerService.getCustomer(accessToken);
        CouponEntity couponEntity = orderService.getCouponByCouponId(saveOrderRequest.getCouponId().toString());
        PaymentEntity paymentEntity = paymentService.getPaymentByUUID(saveOrderRequest.getPaymentId().toString());
        AddressEntity addressEntity = addressService.getAddressByUUID(saveOrderRequest.getAddressId(), customerEntity);
        RestaurantEntity restaurantEntity = restaurantService.restaurantByUUID(saveOrderRequest.getRestaurantId().toString());

        // Set OrderItemEntity from the list of OrderItemEntity sent in request
        List<OrderItemEntity> orderItemEntities = new ArrayList<>();
        for (ItemQuantity itemQuantity : saveOrderRequest.getItemQuantities()) {
            ItemEntity itemEntity = itemService.getItemById(itemQuantity.getItemId().toString());
            OrderItemEntity orderItemEntity = new OrderItemEntity();
            orderItemEntity.setItem(itemEntity);
            orderItemEntity.setQuanity(itemQuantity.getQuantity());
            orderItemEntity.setPrice(itemQuantity.getPrice());
            orderItemEntities.add(orderItemEntity);
        }

        // Set OrderEntity
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setUuid(UUID.randomUUID().toString());
        orderEntity.setAddress(addressEntity);
        orderEntity.setPayment(paymentEntity);
        orderEntity.setBill(saveOrderRequest.getBill().doubleValue());
        orderEntity.setDiscount(saveOrderRequest.getDiscount().doubleValue());
        orderEntity.setCoupon(couponEntity);
        orderEntity.setRestaurant(restaurantEntity);
        orderEntity.setCustomer(customerEntity);
        final Date currentDate = new Date();
        orderEntity.setDate(currentDate);

        OrderEntity savedOrderEntity = orderService.saveOrder(orderEntity);

        for (OrderItemEntity orderItemEntity : orderItemEntities) {
            orderItemEntity.setOrder(orderEntity);
            OrderItemEntity savedOrderItemEntity = orderService.saveOrderItem(orderItemEntity);
        }

        SaveOrderResponse saveOrderResponse = new SaveOrderResponse()
                .id(savedOrderEntity.getUuid())
                .status("ORDER SUCCESSFULLY PLACED");

        return new ResponseEntity<SaveOrderResponse>(saveOrderResponse, HttpStatus.CREATED);
    }

}
