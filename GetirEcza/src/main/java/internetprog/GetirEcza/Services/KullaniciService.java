package internetprog.GetirEcza.Services;

import internetprog.GetirEcza.Model.Kullanici;
import internetprog.GetirEcza.Repository.KullaniciRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KullaniciService {

    private final KullaniciRepository kullaniciRepository;

    // Tüm kullanıcıları getir
    public List<Kullanici> tumKullanicilariGetir() {
        return kullaniciRepository.findAll();
    }

    // ID'ye göre kullanıcı getir
    public Optional<Kullanici> kullaniciGetir(Long id) {
        return kullaniciRepository.findById(id);
    }

    // Yeni kullanıcı oluştur
    public Kullanici kullaniciOlustur(Kullanici kullanici) {
        return kullaniciRepository.save(kullanici);
    }

    // Kullanıcı güncelle
    public Kullanici kullaniciGuncelle(Long id, Kullanici guncelKullanici) {
        return kullaniciRepository.findById(id)
                .map(k -> {
                    k.setAd(guncelKullanici.getAd());
                    k.setSoyad(guncelKullanici.getSoyad());
                    k.setEposta(guncelKullanici.getEposta());
                    k.setSifre(guncelKullanici.getSifre());
                    k.setTelefon(guncelKullanici.getTelefon());
                    return kullaniciRepository.save(k);
                })
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));
    }

    // Kullanıcı sil
    public void kullaniciSil(Long id) {
        kullaniciRepository.deleteById(id);
    }

    // E-posta ile kullanıcı getir (giriş için kullanılabilir)
    public Optional<Kullanici> epostaIleGetir(String eposta) {
        return kullaniciRepository.findByEposta(eposta);
    }
}
