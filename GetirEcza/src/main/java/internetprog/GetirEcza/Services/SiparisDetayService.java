package internetprog.GetirEcza.Services;

import internetprog.GetirEcza.DTO.SiparisDetayDTO;
import internetprog.GetirEcza.Model.Siparis;
import internetprog.GetirEcza.Model.SiparisDetay;
import internetprog.GetirEcza.Model.Urun;
import internetprog.GetirEcza.Repository.SiparisDetayRepository;
import internetprog.GetirEcza.Repository.SiparisRepository;
import internetprog.GetirEcza.Repository.UrunRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SiparisDetayService {

    private final SiparisDetayRepository siparisDetayRepository;
    private final SiparisRepository siparisRepository;
    private final UrunRepository urunRepository;

    public List<SiparisDetay> sipariseAitDetaylar(Long siparisId) {
        return siparisDetayRepository.findBySiparis_SiparisId(siparisId);
    }

    public SiparisDetay detayEkle(SiparisDetayDTO dto) {
        Siparis siparis = siparisRepository.findById(dto.getSiparisId())
                .orElseThrow(() -> new RuntimeException("Sipariş bulunamadı"));

        Urun urun = urunRepository.findById(dto.getUrunId())
                .orElseThrow(() -> new RuntimeException("Ürün bulunamadı"));

        SiparisDetay detay = new SiparisDetay();
        detay.setSiparis(siparis);
        detay.setUrun(urun);
        detay.setAdet(dto.getAdet());
        detay.setBirimFiyat(dto.getBirimFiyat());

        return siparisDetayRepository.save(detay);
    }

    public void detaySil(Long id) {
        siparisDetayRepository.deleteById(id);
    }
}
