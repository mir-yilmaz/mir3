package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.FavoriDTO;
import internetprog.GetirEcza.Model.Favori;
import internetprog.GetirEcza.Services.FavoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/favoriler")
@RequiredArgsConstructor
public class FavoriController {

    private final FavoriService favoriService;

    @GetMapping("/kullanici/{kullaniciId}")
    public List<FavoriDTO> kullaniciFavorileri(@PathVariable Long kullaniciId) {
        return favoriService.kullaniciFavorileri(kullaniciId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<FavoriDTO> favoriEkle(@RequestBody FavoriDTO dto) {
        Favori favori = favoriService.favoriEkle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(favori));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> favoriSil(@PathVariable Long id) {
        favoriService.favoriSil(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/kaldir")
    public ResponseEntity<Void> favoriKaldir(@RequestParam Long kullaniciId, @RequestParam Long urunId) {
        favoriService.favoriKaldir(kullaniciId, urunId);
        return ResponseEntity.noContent().build();
    }

    // Dönüşüm
    private FavoriDTO mapToDto(Favori favori) {
        return new FavoriDTO(
                favori.getFavoriId(),
                favori.getKullanici().getKullaniciId(),
                favori.getUrun().getUrunId(),
                favori.getFavoriTarihi()
        );
    }
}
