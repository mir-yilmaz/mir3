package internetprog.GetirEcza.Services;

import internetprog.GetirEcza.DTO.SiparisDTO;
import internetprog.GetirEcza.Enum.SiparisDurum;
import internetprog.GetirEcza.Model.Kullanici;
import internetprog.GetirEcza.Model.Siparis;
import internetprog.GetirEcza.Repository.KullaniciRepository;
import internetprog.GetirEcza.Repository.SiparisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SiparisService {

    private final SiparisRepository siparisRepository;
    private final KullaniciRepository kullaniciRepository;

    public List<Siparis> tumSiparisler() {
        return siparisRepository.findAll();
    }

    public List<Siparis> kullaniciSiparisleri(Long kullaniciId) {
        return siparisRepository.findByKullanici_KullaniciId(kullaniciId);
    }

    public Optional<Siparis> siparisGetir(Long id) {
        return siparisRepository.findById(id);
    }

    public Siparis siparisOlustur(SiparisDTO dto) {
        Kullanici kullanici = kullaniciRepository.findById(dto.getKullaniciId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Siparis siparis = new Siparis();
        siparis.setKullanici(kullanici);
        siparis.setSiparisTarihi(dto.getSiparisTarihi());
        siparis.setToplamTutar(dto.getToplamTutar());
        siparis.setDurum(SiparisDurum.valueOf(dto.getDurum()));

        return siparisRepository.save(siparis);
    }

    public Siparis siparisGuncelle(Long id, SiparisDTO dto) {
        Siparis siparis = siparisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        siparis.setToplamTutar(dto.getToplamTutar());
        siparis.setDurum(SiparisDurum.valueOf(dto.getDurum()));

        return siparisRepository.save(siparis);
    }

    public void siparisSil(Long id) {
        siparisRepository.deleteById(id);
    }
}
