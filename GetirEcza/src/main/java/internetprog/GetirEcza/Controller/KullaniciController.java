package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.KullaniciDTO;
import internetprog.GetirEcza.Model.Kullanici;
import internetprog.GetirEcza.Services.KullaniciService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kullanicilar")
@RequiredArgsConstructor
public class KullaniciController {

    private final KullaniciService kullaniciService;

    // Tüm kullanıcıları getir
    @GetMapping


    public List<KullaniciDTO> tumKullanicilariGetir() {
        return kullaniciService.tumKullanicilariGetir()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ID ile kullanıcı getir
    @GetMapping("/{id}")
    public ResponseEntity<KullaniciDTO> kullaniciGetir(@PathVariable Long id) {
        return kullaniciService.kullaniciGetir(id)
                .map(k -> ResponseEntity.ok(mapToDto(k)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Yeni kullanıcı oluştur
    @PostMapping
    public ResponseEntity<KullaniciDTO> kullaniciOlustur(@RequestBody KullaniciDTO dto) {
        Kullanici yeni = mapToEntity(dto);
        Kullanici kaydedilen = kullaniciService.kullaniciOlustur(yeni);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(kaydedilen));
    }

    // Kullanıcı güncelle
    @PutMapping("/{id}")
    public ResponseEntity<KullaniciDTO> kullaniciGuncelle(@PathVariable Long id, @RequestBody KullaniciDTO dto) {
        Kullanici guncel = mapToEntity(dto);
        Kullanici guncellenmis = kullaniciService.kullaniciGuncelle(id, guncel);
        return ResponseEntity.ok(mapToDto(guncellenmis));
    }

    // Kullanıcı sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kullaniciSil(@PathVariable Long id) {
        kullaniciService.kullaniciSil(id);
        return ResponseEntity.noContent().build();
    }

    // ----- Yardımcı Metotlar -----

    private KullaniciDTO mapToDto(Kullanici k) {
        return new KullaniciDTO(
                k.getKullaniciId(),
                k.getAd(),
                k.getSoyad(),
                k.getEposta(),
                k.getTelefon()
        );
    }

    private Kullanici mapToEntity(KullaniciDTO dto) {
        Kullanici k = new Kullanici();
        k.setKullaniciId(dto.getId());
        k.setAd(dto.getAd());
        k.setSoyad(dto.getSoyad());
        k.setEposta(dto.getEposta());
        k.setTelefon(dto.getTelefon());
        return k;
    }
}
