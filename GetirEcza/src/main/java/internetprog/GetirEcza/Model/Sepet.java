package internetprog.GetirEcza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sepet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sepetId;

    @ManyToOne
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @ManyToOne
    @JoinColumn(name = "urun_id")
    private Urun urun;

    private Integer adet;
}
