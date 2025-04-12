package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.KategoriDTO;
import internetprog.GetirEcza.Model.Kategori;
import internetprog.GetirEcza.Services.KategoriService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/kategoriler")
@RequiredArgsConstructor
public class KategoriController {

    private final KategoriService kategoriService;

    @GetMapping
    public List<KategoriDTO> tumKategoriler() {
        return kategoriService.tumKategoriler()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KategoriDTO> kategoriGetir(@PathVariable Long id) {
        return kategoriService.kategoriGetir(id)
                .map(k -> ResponseEntity.ok(mapToDto(k)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KategoriDTO> kategoriOlustur(@RequestBody KategoriDTO dto) {
        Kategori k = kategoriService.kategoriOlustur(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(k));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KategoriDTO> kategoriGuncelle(@PathVariable Long id, @RequestBody KategoriDTO dto) {
        Kategori k = kategoriService.kategoriGuncelle(id, dto);
        return ResponseEntity.ok(mapToDto(k));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> kategoriSil(@PathVariable Long id) {
        kategoriService.kategoriSil(id);
        return ResponseEntity.noContent().build();
    }

    // Yardımcı DTO dönüşüm metodu
    private KategoriDTO mapToDto(Kategori kategori) {
        return new KategoriDTO(
                kategori.getKategoriId(),
                kategori.getAd(),
                kategori.getAciklama()
        );
    }
}
