package javafive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProductColor", uniqueConstraints = { @UniqueConstraint(columnNames = { "product_id", "color_id" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
}
