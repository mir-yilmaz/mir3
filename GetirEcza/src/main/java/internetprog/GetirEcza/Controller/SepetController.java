package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.SepetDTO;
import internetprog.GetirEcza.Model.Sepet;
import internetprog.GetirEcza.Services.SepetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sepet")
@RequiredArgsConstructor
public class SepetController {

    private final SepetService sepetService;

    @GetMapping("/{kullaniciId}")
    public List<SepetDTO> kullaniciSepeti(@PathVariable Long kullaniciId) {
        return sepetService.kullanicininSepeti(kullaniciId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<SepetDTO> sepeteEkle(@RequestBody SepetDTO dto) {
        Sepet eklenen = sepetService.sepeteUrunEkle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(eklenen));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SepetDTO> sepetGuncelle(@PathVariable Long id, @RequestBody SepetDTO dto) {
        Sepet guncel = sepetService.sepetGuncelle(id, dto);
        return ResponseEntity.ok(mapToDto(guncel));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sepettenUrunCikar(@PathVariable Long id) {
        sepetService.sepettenUrunCikar(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/temizle/{kullaniciId}")
    public ResponseEntity<Void> sepetiTemizle(@PathVariable Long kullaniciId) {
        sepetService.sepetiTemizle(kullaniciId);
        return ResponseEntity.noContent().build();
    }

    // Yardımcı dönüşüm
    private SepetDTO mapToDto(Sepet s) {
        return new SepetDTO(
                s.getSepetId(),
                s.getKullanici().getKullaniciId(),
                s.getUrun().getUrunId(),
                s.getAdet()
        );
    }
}
