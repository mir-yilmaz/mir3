package internetprog.GetirEcza.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrunDTO {
    private Long id;
    private String ad;
    private String aciklama;
    private BigDecimal fiyat;
    private Integer stokDurumu;
    private Long kategoriId; // sadece ID yeterli
}
