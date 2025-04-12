package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.YorumDTO;
import internetprog.GetirEcza.Model.Yorum;
import internetprog.GetirEcza.Services.YorumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/yorumlar")
@RequiredArgsConstructor
public class YorumController {

    private final YorumService yorumService;

    @GetMapping("/urun/{urunId}")
    public List<YorumDTO> urunYorumlari(@PathVariable Long urunId) {
        return yorumService.urunYorumlari(urunId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/kullanici/{kullaniciId}")
    public List<YorumDTO> kullaniciYorumlari(@PathVariable Long kullaniciId) {
        return yorumService.kullaniciYorumlari(kullaniciId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<YorumDTO> yorumGetir(@PathVariable Long id) {
        return yorumService.yorumGetir(id)
                .map(y -> ResponseEntity.ok(mapToDto(y)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<YorumDTO> yorumEkle(@RequestBody YorumDTO dto) {
        Yorum yeni = yorumService.yorumEkle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(yeni));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> yorumSil(@PathVariable Long id) {
        yorumService.yorumSil(id);
        return ResponseEntity.noContent().build();
    }

    private YorumDTO mapToDto(Yorum y) {
        return new YorumDTO(
                y.getYorumId(),
                y.getUrun().getUrunId(),
                y.getKullanici().getKullaniciId(),
                y.getIcerik(),
                y.getPuan(),
                y.getTarih()
        );
    }
}
