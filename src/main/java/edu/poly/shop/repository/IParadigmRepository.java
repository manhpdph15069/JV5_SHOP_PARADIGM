package edu.poly.shop.repository;

import edu.poly.shop.entities._Paradigm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IParadigmRepository extends JpaRepository<_Paradigm,Integer> {
    @Query("select p from _Paradigm p where p.status=1")
    public Page<_Paradigm> getFindAllPage(Pageable pageable);


    @Query("select p from _Paradigm p where p.status=1 and p.paradigmName=?1")
    public Optional<_Paradigm> findByParadigmName(String paradigmName);

}
