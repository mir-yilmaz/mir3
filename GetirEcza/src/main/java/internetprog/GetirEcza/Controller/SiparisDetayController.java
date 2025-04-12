package internetprog.GetirEcza.Controller;

import internetprog.GetirEcza.DTO.SiparisDetayDTO;
import internetprog.GetirEcza.Model.SiparisDetay;
import internetprog.GetirEcza.Services.SiparisDetayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/siparis-detay")
@RequiredArgsConstructor
public class SiparisDetayController {

    private final SiparisDetayService siparisDetayService;

    @GetMapping("/siparis/{siparisId}")
    public List<SiparisDetayDTO> siparisDetaylari(@PathVariable Long siparisId) {
        return siparisDetayService.sipariseAitDetaylar(siparisId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<SiparisDetayDTO> detayEkle(@RequestBody SiparisDetayDTO dto) {
        SiparisDetay detay = siparisDetayService.detayEkle(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(detay));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> detaySil(@PathVariable Long id) {
        siparisDetayService.detaySil(id);
        return ResponseEntity.noContent().build();
    }

    private SiparisDetayDTO mapToDto(SiparisDetay d) {
        return new SiparisDetayDTO(
                d.getSiparisDetayId(),
                d.getSiparis().getSiparisId(),
                d.getUrun().getUrunId(),
                d.getAdet(),
                d.getBirimFiyat()
        );
    }
}
