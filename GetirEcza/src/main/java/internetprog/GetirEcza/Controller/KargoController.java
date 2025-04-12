package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.KargoDTO;
import internetprog.GetirEcza.Model.Kargo;
import internetprog.GetirEcza.Services.KargoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kargolar")
@RequiredArgsConstructor
public class KargoController {

    private final KargoService kargoService;

    @GetMapping
    public List<KargoDTO> tumKargolar() {
        return kargoService.tumKargolar()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KargoDTO> kargoGetir(@PathVariable Long id) {
        return kargoService.kargoGetir(id)
                .map(k -> ResponseEntity.ok(mapToDto(k)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/siparis/{siparisId}")
    public ResponseEntity<KargoDTO> siparisKargosunuGetir(@PathVariable Long siparisId) {
        return kargoService.siparisKargosunuGetir(siparisId)
                .map(k -> ResponseEntity.ok(mapToDto(k)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KargoDTO> kargoOlustur(@RequestBody KargoDTO dto) {
        Kargo k = kargoService.kargoOlustur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(k));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KargoDTO> kargoGuncelle(@PathVariable Long id, @RequestBody KargoDTO dto) {
        Kargo k = kargoService.kargoGuncelle(id, dto);
        return ResponseEntity.ok(mapToDto(k));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kargoSil(@PathVariable Long id) {
        kargoService.kargoSil(id);
        return ResponseEntity.noContent().build();
    }

    // Yardımcı metot
    private KargoDTO mapToDto(Kargo k) {
        return new KargoDTO(
                k.getKargoId(),
                k.getSiparis().getSiparisId(),
                k.getKargoSirketi(),
                k.getTakipNumarasi(),
                k.getDurum().name(),
                k.getGonderimTarihi(),
                k.getTeslimTarihi()
        );
    }
}
