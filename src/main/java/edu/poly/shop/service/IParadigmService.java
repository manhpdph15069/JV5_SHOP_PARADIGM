package edu.poly.shop.service;

import edu.poly.shop.beans.__ParadigmModel;
import edu.poly.shop.entities._Paradigm;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface IParadigmService {

    public Page<_Paradigm> getByPage(int pageNumber, int maxRecord);

    Optional<_Paradigm> findById(Integer integer);


    public _Paradigm delete(Integer id);

    Optional<_Paradigm> findByParadigmName(String paradigmName);

    _Paradigm insert(__ParadigmModel dto);
}
