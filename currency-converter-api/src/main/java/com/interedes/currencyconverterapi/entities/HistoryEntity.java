package com.interedes.currencyconverterapi.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted_at IS NULL")
@Table(name = "historial_divisa")
public class HistoryEntity {

    @Id
    @Column(name = "id", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "moneda_origen", nullable = false)
    private String sourceCurrency;

    @Column(name = "moneda_objetivo", nullable = false)
    private String targetCurrency;

    @Column(name = "monto", nullable = false)
    private double amount;

    @Column(name = "valor_conversion", nullable = false)
    private double conversionValue;

    @Column(name = "fecha_conversion", updatable = false, nullable = false)
    private LocalDateTime conversionDate = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)  return true;
        if ( obj == null || getClass() != obj.getClass() )  return false;
        HistoryEntity other = (HistoryEntity) obj;
        return Objects.equals(id, other.getId());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("id=").append(id).append("]");
        return sb.toString();
    }
}
