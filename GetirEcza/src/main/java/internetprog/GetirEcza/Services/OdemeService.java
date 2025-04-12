package internetprog.GetirEcza.Services;

import internetprog.GetirEcza.DTO.OdemeDTO;
import internetprog.GetirEcza.Enum.OdemeDurumu;
import internetprog.GetirEcza.Enum.OdemeYontemi;
import internetprog.GetirEcza.Model.Odeme;
import internetprog.GetirEcza.Model.Siparis;
import internetprog.GetirEcza.Repository.OdemeRepository;
import internetprog.GetirEcza.Repository.SiparisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OdemeService {

    private final OdemeRepository odemeRepository;
    private final SiparisRepository siparisRepository;

    public List<Odeme> tumOdemeler() {
        return odemeRepository.findAll();
    }

    public Optional<Odeme> odemeGetir(Long id) {
        return odemeRepository.findById(id);
    }

    public Optional<Odeme> siparisOdemesiGetir(Long siparisId) {
        return odemeRepository.findBySiparis_SiparisId(siparisId);
    }

    public Odeme odemeOlustur(OdemeDTO dto) {
        Siparis siparis = siparisRepository.findById(dto.getSiparisId())
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        Odeme odeme = new Odeme();
        odeme.setSiparis(siparis);
        odeme.setOdemeTutari(dto.getOdemeTutari());
        odeme.setOdemeTarihi(dto.getOdemeTarihi());
        odeme.setOdemeYontemi(OdemeYontemi.valueOf(dto.getOdemeYontemi()));
        odeme.setOdemeDurumu(OdemeDurumu.valueOf(dto.getOdemeDurumu()));

        return odemeRepository.save(odeme);
    }

    public Odeme odemeGuncelle(Long id, OdemeDTO dto) {
        Odeme odeme = odemeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ödeme bulunamadı"));

        odeme.setOdemeTutari(dto.getOdemeTutari());
        odeme.setOdemeTarihi(dto.getOdemeTarihi());
        odeme.setOdemeYontemi(OdemeYontemi.valueOf(dto.getOdemeYontemi()));
        odeme.setOdemeDurumu(OdemeDurumu.valueOf(dto.getOdemeDurumu()));

        return odemeRepository.save(odeme);
    }

    public void odemeSil(Long id) {
        odemeRepository.deleteById(id);
    }
}
