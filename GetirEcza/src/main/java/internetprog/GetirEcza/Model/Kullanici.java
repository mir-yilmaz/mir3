package internetprog.GetirEcza.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kullaniciId;

    private String ad;
    private String soyad;
    private String eposta;
    private String sifre;
    private String telefon;



    @OneToMany(mappedBy = "kullanici", cascade = CascadeType.ALL)
    private List<Adres> adresler;

    @OneToMany(mappedBy = "kullanici")
    private List<Yorum> yorumlar;

    @OneToMany(mappedBy = "kullanici")
    private List<Sepet> sepet;

    @OneToMany(mappedBy = "kullanici")
    private List<Favori> favoriler;

    @OneToMany(mappedBy = "kullanici")
    private List<Siparis> siparisler;
}
