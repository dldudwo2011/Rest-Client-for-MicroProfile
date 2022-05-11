package dmit2015.youngjaelee.assignment07.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;



@Data
@Getter
@Setter
public class Bill {
    String payeeName;
    LocalDate dueDate;
    BigDecimal amountDue;
    BigDecimal amountBalance;
}
