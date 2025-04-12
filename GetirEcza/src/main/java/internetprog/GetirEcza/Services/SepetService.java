package internetprog.GetirEcza.Services;

import internetprog.GetirEcza.DTO.SepetDTO;
import internetprog.GetirEcza.Model.Kullanici;
import internetprog.GetirEcza.Model.Sepet;
import internetprog.GetirEcza.Model.Urun;
import internetprog.GetirEcza.Repository.KullaniciRepository;
import internetprog.GetirEcza.Repository.SepetRepository;
import internetprog.GetirEcza.Repository.UrunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SepetService {

    private final SepetRepository sepetRepository;
    private final KullaniciRepository kullaniciRepository;
    private final UrunRepository urunRepository;

    public List<Sepet> kullanicininSepeti(Long kullaniciId) {
        return sepetRepository.findByKullanici_KullaniciId(kullaniciId);
    }

    public Sepet sepeteUrunEkle(SepetDTO dto) {
        Kullanici kullanici = kullaniciRepository.findById(dto.getKullaniciId())
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        Urun urun = urunRepository.findById(dto.getUrunId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        // Daha önce aynı ürün sepete eklendiyse, adeti artır
        Optional<Sepet> mevcutSepet = sepetRepository.findByKullanici_KullaniciIdAndUrun_UrunId(
                dto.getKullaniciId(), dto.getUrunId());

        if (mevcutSepet.isPresent()) {
            Sepet s = mevcutSepet.get();
            s.setAdet(s.getAdet() + dto.getAdet());
            return sepetRepository.save(s);
        }

        Sepet yeni = new Sepet();
        yeni.setKullanici(kullanici);
        yeni.setUrun(urun);
        yeni.setAdet(dto.getAdet());
        return sepetRepository.save(yeni);
    }

    public Sepet sepetGuncelle(Long id, SepetDTO dto) {
        Sepet sepet = sepetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sepet bulunamadı"));

        sepet.setAdet(dto.getAdet());
        return sepetRepository.save(sepet);
    }

    public void sepettenUrunCikar(Long id) {
        sepetRepository.deleteById(id);
    }

    public void sepetiTemizle(Long kullaniciId) {
        List<Sepet> sepetList = sepetRepository.findByKullanici_KullaniciId(kullaniciId);
        sepetRepository.deleteAll(sepetList);
    }
}
