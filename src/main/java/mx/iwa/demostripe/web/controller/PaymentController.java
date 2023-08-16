package mx.iwa.demostripe.web.controller;

import lombok.RequiredArgsConstructor;
import mx.iwa.demostripe.form.PaymentForm;
import mx.iwa.demostripe.service.StripeService;
import mx.iwa.demostripe.utils.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments/payment")
@RequiredArgsConstructor
public class PaymentController {

  private StripeService stripeService;

  @PostMapping("/create-charge")
  public @ResponseBody Response createCharge(final PaymentForm paymentForm) {

    final String chargeId = stripeService.createCharge(paymentForm);
    return new Response(true, "Success, charge id: " + chargeId);
  }
}
