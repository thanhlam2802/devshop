package javafive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Size")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Size {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "size_id")
    private Integer sizeId;

    @Column(name = "size", nullable = false, length = 10)
    private String size;
}