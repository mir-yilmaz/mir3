package internetprog.GetirEcza.Services;

import internetprog.GetirEcza.DTO.KategoriDTO;
import internetprog.GetirEcza.Model.Kategori;
import internetprog.GetirEcza.Repository.KategoriRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KategoriService {

    private final KategoriRepository kategoriRepository;

    public List<Kategori> tumKategoriler() {
        return kategoriRepository.findAll();
    }

    public Optional<Kategori> kategoriGetir(Long id) {
        return kategoriRepository.findById(id);
    }

    public Kategori kategoriOlustur(KategoriDTO dto) {
        Kategori kategori = new Kategori();
        kategori.setAd(dto.getAd());
        kategori.setAciklama(dto.getAciklama());
        return kategoriRepository.save(kategori);
    }

    public Kategori kategoriGuncelle(Long id, KategoriDTO dto) {
        Kategori kategori = kategoriRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Kategori bulunamadı"));

        kategori.setAd(dto.getAd());
        kategori.setAciklama(dto.getAciklama());

        return kategoriRepository.save(kategori);
    }

    public void kategoriSil(Long id) {
        kategoriRepository.deleteById(id);
    }
}
