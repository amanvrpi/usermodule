package com.vrpigroup.usermodule.repo;

import com.vrpigroup.usermodule.entity.PaymentDetailsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentDetailsRequestRepo extends JpaRepository<PaymentDetailsRequest, Long> {

    PaymentDetailsRequest findByUserId(Long userId);

    Optional<PaymentDetailsRequest> findByUserIdAndCourseId(Long userId, Long courseId);
}
