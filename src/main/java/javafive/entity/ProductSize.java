package javafive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProductSize", uniqueConstraints = { @UniqueConstraint(columnNames = { "product_id", "size_id" }) })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private Size size;

    @Column(name = "quantity", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer quantity;
}
