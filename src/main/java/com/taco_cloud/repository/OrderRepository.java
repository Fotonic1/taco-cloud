package com.taco_cloud.repository;

import com.taco_cloud.data.TacoOrder;

public interface OrderRepository {
    TacoOrder save(TacoOrder order);
}
