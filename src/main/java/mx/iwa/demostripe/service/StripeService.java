package mx.iwa.demostripe.service;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import java.util.HashMap;
import java.util.Map;
import mx.iwa.demostripe.form.PaymentForm;
import org.springframework.beans.factory.annotation.Value;

public class StripeService {

  @Value("${stripe.key.secret}")
  private String apiSecretKey;

  public StripeService() {}

  public String createCharge(final PaymentForm form) {

    String chargeId = null;

    try {
      Stripe.apiKey = apiSecretKey;

      final Map<String, Object> chargeParams = new HashMap<>();
      chargeParams.put("amount", form.getAmount());
      chargeParams.put("currency", "usd");

      final PaymentIntent paymentIntent = PaymentIntent.create(chargeParams);
      chargeId = paymentIntent.getId();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return chargeId;
  }
}
