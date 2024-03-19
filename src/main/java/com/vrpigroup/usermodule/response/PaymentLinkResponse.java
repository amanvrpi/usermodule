package com.vrpigroup.usermodule.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class PaymentLinkResponse {
    private String payment_link_url;
    private String payment_link_id;
}
