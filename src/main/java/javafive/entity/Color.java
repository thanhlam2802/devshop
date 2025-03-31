package javafive.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Color")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Color {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
  
    private Integer color_id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "hex_code", length = 7)
    private String hexCode;
    @Builder
    public Color(Integer color_id, String name) {
        this.color_id = color_id;
        this.name = name;
    }

}
