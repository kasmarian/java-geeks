package com.hillel.elementary.java_geeks.services;

import com.hillel.elementary.java_geeks.domain.*;
import com.hillel.elementary.java_geeks.repositories.InMemOrderRepo;
import com.hillel.elementary.java_geeks.repositories.OrderRepo;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class DefaultOrderServiceTest {

    @Test
    void shouldAddAndGetOrders() {

        //given
        OrderRepo realOrderRepo = new InMemOrderRepo();
        Pizza pizza = new Pizza(0, "pizza", PizzaType.MEAT, 200, new BigDecimal(200));
        Order order = new Order(new Customer(0L, "John"), pizza);
        PizzaService mockPizzaService = mock(DefaultPizzaService.class);
        OrderService orderService = new DefaultOrderService(realOrderRepo, mockPizzaService);

        //when
        Order registeredOrder = orderService.saveOrder(order);

        //than
        assertThat(orderService.getOrder(0)).isEqualTo(registeredOrder);
    }

    @Test
    void shouldChangeOrderStatus() {

        //given
        OrderRepo realOrderRepo = new InMemOrderRepo();
        Pizza pizza = new Pizza(0, "pizza", PizzaType.MEAT, 200, new BigDecimal(200));
        Order order = new Order(new Customer(0L, "John"), pizza);
        PizzaService mockPizzaService = mock(DefaultPizzaService.class);
        OrderService orderService = new DefaultOrderService(realOrderRepo, mockPizzaService);
        orderService.saveOrder(order);

        //when
        orderService.changeStatus(OrderStatus.DONE, 0L);

        //than
        assertThat(orderService.getOrder(0).getStatus()).isEqualTo(OrderStatus.DONE);
    }

    @Test
    void shouldThrowExceptionWhenPizzaIsNull() {
        OrderRepo realOrderRepo = new InMemOrderRepo();
        PizzaService mockPizzaService = mock(DefaultPizzaService.class);
        OrderService orderService = new DefaultOrderService(realOrderRepo, mockPizzaService);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.saveOrder(new Customer(0L ,"John"), null, null);
        });
    }

    @Test
    void shouldThrowExceptionWhenCustomerIsNull() {
        OrderRepo realOrderRepo = new InMemOrderRepo();
        PizzaService mockPizzaService = mock(DefaultPizzaService.class);
        OrderService orderService = new DefaultOrderService(realOrderRepo, mockPizzaService);
        Pizza pizza = new Pizza(0, "pizza", PizzaType.MEAT, 200, new BigDecimal(200));

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.saveOrder(null, pizza);
        });
    }
}