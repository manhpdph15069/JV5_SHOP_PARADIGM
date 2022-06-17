package edu.poly.shop.service.impl;

import edu.poly.shop.beans.__ParadigmModel;
import edu.poly.shop.entities._Category;
import edu.poly.shop.entities._Figure;
import edu.poly.shop.entities._Material;
import edu.poly.shop.entities._Paradigm;
import edu.poly.shop.repository.ICategoryRepository;
import edu.poly.shop.repository.IFigureRepository;
import edu.poly.shop.repository.IParadigmRepository;
import edu.poly.shop.service.IParadigmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class ParadigmService implements IParadigmService {
    @Autowired
    ServletContext app;
    @Autowired
    private IParadigmRepository paradigmRepository;
    @Autowired
    private IFigureRepository figureRepository;
    @Autowired
    private ICategoryRepository categoryRepository;


    public Page<_Paradigm> getByPage(int pageNumber, int maxRecord) {
        Pageable pageable = PageRequest.of(pageNumber, maxRecord);
        Page<_Paradigm> page = paradigmRepository.findAll(pageable);
        return page;
    }

    @Override
    public List<_Paradigm> findAll() {
        return paradigmRepository.findAll();
    }

    @Override
    public Optional<_Paradigm> findById(Integer integer) {
        return paradigmRepository.findById(integer);
    }

    @Override
    public List<_Paradigm> findName(String name) {
        return paradigmRepository.findAllByParadigmNameContains("%" + name + "%");
    }

    @Override
    public Optional<_Paradigm> findByParadigmName(String paradigmName) {
        return paradigmRepository.findByParadigmName(paradigmName);
    }


    @Override
    public _Paradigm insert(__ParadigmModel dto, RedirectAttributes attributes) {
        Optional<_Paradigm> optional_pardigm = findById(dto.getId());
        _Paradigm paradigm = null;
        String image = "NoImage.png";
        Timestamp crDate = dto.getCreateDate();
        Path path = Paths.get("uploads/");
        if (optional_pardigm.isPresent()) {
            //update
            crDate = optional_pardigm.get().getCreateDate();
            if (dto.getImage().isEmpty()) {
                image = optional_pardigm.get().getImage();
            } else {
                try {
                    InputStream inputStream = dto.getImage().getInputStream();
                    Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
                            StandardCopyOption.REPLACE_EXISTING);
                    image = dto.getImage().getOriginalFilename().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            attributes.addFlashAttribute("messageTC","Cập nhập sản phẩm "+dto.getParadigmName()+" có mã sản phẩm là "+dto.getId()+" thành công");
        } else {
//            add
            if (!dto.getImage().isEmpty()) {
                System.out.println(dto.getImage().getOriginalFilename());
                File file = new File("uploads/", dto.getImage().getOriginalFilename());
                if (file.exists()) {
                    image = dto.getImage().getOriginalFilename();
                } else {
                    try {
                        InputStream inputStream = dto.getImage().getInputStream();
                        Files.copy(inputStream, path.resolve(dto.getImage().getOriginalFilename()),
                                StandardCopyOption.REPLACE_EXISTING);
                        image = dto.getImage().getOriginalFilename().toString();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            attributes.addFlashAttribute("messageTC","Thêm mới sản phẩm "+dto.getParadigmName()+" thành công");
        }
        paradigm = new _Paradigm(dto.getId(),
                dto.getParadigmName(),
                dto.getPrice(),
                dto.getQuantity(),
                new _Category(dto.getCategory_id(), ""),
                image,
                dto.getDimension(),
                new _Material(dto.getMaterial_id(), ""),
                crDate,
                new _Figure(dto.getFigure_id(), ""),
                1
        );
        return paradigmRepository.save(paradigm);
    }


    @Override
    public _Paradigm delete(Integer id) {
        if (id != null) {
            Optional<_Paradigm> p = paradigmRepository.findById(id);
            if (p.isPresent()) {
                paradigmRepository.deleteById(id);
                return p.get();
            }
        }
        return null;
    }
}
