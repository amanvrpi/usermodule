package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.PaymentDetailsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRequestRepo extends JpaRepository<PaymentDetailsRequest, Long> {
}
