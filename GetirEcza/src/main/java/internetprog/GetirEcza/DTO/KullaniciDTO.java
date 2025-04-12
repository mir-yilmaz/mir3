package internetprog.GetirEcza.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KullaniciDTO {
    private Long id;
    private String ad;
    private String soyad;
    private String eposta;
    private String telefon;
}
