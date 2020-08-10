package com.fp.cloud.main.repository;


import com.fp.cloud.main.domain.PaymentMethodView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepo extends JpaRepository<PaymentMethodView, Long> {
    PaymentMethodView findById(int id);
}
