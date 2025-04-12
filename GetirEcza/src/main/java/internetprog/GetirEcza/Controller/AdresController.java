package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.AdresDTO;
import internetprog.GetirEcza.Model.Adres;
import internetprog.GetirEcza.Services.AdresService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/adresler")
@RequiredArgsConstructor
public class AdresController {

    private final AdresService adresService;

    @GetMapping
    public List<AdresDTO> tumAdresleriGetir() {
        return adresService.tumAdresleriGetir()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdresDTO> adresGetir(@PathVariable Long id) {
        return adresService.adresGetir(id)
                .map(adres -> ResponseEntity.ok(mapToDto(adres)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public List<AdresDTO> kullaniciAdresleri(@PathVariable Long kullaniciId) {
        return adresService.kullaniciAdresleriniGetir(kullaniciId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<AdresDTO> adresOlustur(@RequestBody AdresDTO dto) {
        Adres kaydedilen = adresService.adresOlustur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(kaydedilen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdresDTO> adresGuncelle(@PathVariable Long id, @RequestBody AdresDTO dto) {
        Adres guncellenen = adresService.adresGuncelle(id, dto);
        return ResponseEntity.ok(mapToDto(guncellenen));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> adresSil(@PathVariable Long id) {
        adresService.adresSil(id);
        return ResponseEntity.noContent().build();
    }

    // Yardımcı metot
    private AdresDTO mapToDto(Adres adres) {
        return new AdresDTO(
                adres.getAdresId(),
                adres.getAdresDetay(),
                adres.getKullanici().getKullaniciId()
        );
    }
}

