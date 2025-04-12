package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.SiparisDTO;
import internetprog.GetirEcza.Model.Siparis;
import internetprog.GetirEcza.Services.SiparisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/siparisler")
@RequiredArgsConstructor
public class SiparisController {

    private final SiparisService siparisService;

    @GetMapping
    public List<SiparisDTO> tumSiparisler() {

        return siparisService.tumSiparisler()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public List<SiparisDTO> kullaniciSiparisleri(@PathVariable Long kullaniciId) {
        return siparisService.kullaniciSiparisleri(kullaniciId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiparisDTO> siparisGetir(@PathVariable Long id) {
        return siparisService.siparisGetir(id)
                .map(s -> ResponseEntity.ok(mapToDto(s)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SiparisDTO> siparisOlustur(@RequestBody SiparisDTO dto) {
        Siparis kayit = siparisService.siparisOlustur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(kayit));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiparisDTO> siparisGuncelle(@PathVariable Long id, @RequestBody SiparisDTO dto) {
        Siparis guncel = siparisService.siparisGuncelle(id, dto);
        return ResponseEntity.ok(mapToDto(guncel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> siparisSil(@PathVariable Long id) {
        siparisService.siparisSil(id);
        return ResponseEntity.noContent().build();
    }

    private SiparisDTO mapToDto(Siparis s) {
        return new SiparisDTO(
                s.getSiparisId(),
                s.getKullanici().getKullaniciId(),
                s.getSiparisTarihi(),
                s.getToplamTutar(),
                s.getDurum().name()
        );
    }
}
