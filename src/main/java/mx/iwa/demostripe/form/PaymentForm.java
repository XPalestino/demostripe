package mx.iwa.demostripe.form;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Data;
import org.jsondoc.core.annotation.ApiObjectField;

@Data
public class PaymentForm implements Serializable {

  private static final long serialVersionUID = 1L;

  @Digits(integer = 9, fraction = 2)
  @NotNull
  @DecimalMin("0.01")
  @DecimalMax("999999999.99")
  @ApiObjectField(name = "amount", description = "Credit note amount", required = true)
  private BigDecimal amount;
}
